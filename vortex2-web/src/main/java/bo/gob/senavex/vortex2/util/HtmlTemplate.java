/**
 *   ____
 *  / ___|  ___ _ __   __ ___   _______  __
 *  \___ \ / _ \ '_ \ / _` \ \ / / _ \ \/ /
 *   ___) |  __/ | | | (_| |\ V /  __/>  <
 *  |____/ \___|_| |_|\__,_| \_/ \___/_/\_\
 *
 *  Copyright © 2020
 *  http://www.senavex.gob.bo/licenses/LICENSE-1.0
 */
package bo.gob.senavex.vortex2.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Willyams Yujra
 */
public class HtmlTemplate {
   private static final Logger LOGGER = Logger.getLogger(HtmlTemplate.class.getName());
   private static final String REGEX = "\\$\\{" + "([^}]+)" + "\\}";
   private static final Pattern PATTERN = Pattern.compile(REGEX);
   private static final String TAG_STYLE = "style";
   private static final String TAG_LINK = "link";
   private final Map<String, String> TEMPLATES = new HashMap<>();
   private final Map<String, String> STYLES = new HashMap<>();

   public void addFileStyle(String name, URL url) {
      try {
         String content = new Scanner(url.openStream()).useDelimiter("\\A").next();
         STYLES.put(name, content);
      } catch (IOException e) {
         STYLES.put(name, "/*ERROR READ FILE*/");
         LOGGER.log(Level.SEVERE, "addFileStyle", e);
      }
   }

   public void addFileTemplate(String name, URL url) {
      try {
         String content = new Scanner(url.openStream()).useDelimiter("\\A").next();
         Document doc = Jsoup.parse(content);
         content = process(doc).select("body").html();
         TEMPLATES.put(name, content);
      } catch (IOException e) {
         TEMPLATES.put(name, "<!--ERROR READ FILE-->");
         LOGGER.log(Level.SEVERE, "addFileTemplate", e);
      }
   }

   public String getTemplate(String name) {
      return TEMPLATES.getOrDefault(name, "<!--NOT FOUND-->");
   }

   public String getStyle(String name) {
      return STYLES.getOrDefault(name, "/*NOT FOUND*/");
   }

   private Document process(Document doc) throws IOException {
      Elements links = doc.select(TAG_LINK);
      for (Element e : links) {
         String href = e.attr("href");
         String content = getStyle(href);
         e.text(content);
         e.tagName(TAG_STYLE);
      }
      Elements styles = doc.select(TAG_STYLE);
      for (Element e : styles) {
         String delims = "{}";
         String styleRules = e.getAllElements().html();
         StringTokenizer st = new StringTokenizer(styleRules, delims);
         while (st.countTokens() > 1) {
            String selector = st.nextToken(), properties = st.nextToken();
            Elements selectedElements = doc.select(selector);
            for (Element selElem : selectedElements) {
               String oldProperties = selElem.attr(TAG_STYLE);
               selElem.attr(TAG_STYLE, oldProperties.length() > 0 ? concatenateProperties(oldProperties, properties) : properties);
            }
         }
         e.remove();
      }
      return doc;
   }

   private String concatenateProperties(String oldProp, String newProp) {
      newProp = newProp.trim();
      oldProp = oldProp.trim();
      if (!newProp.endsWith(";")) {
         newProp += ";";
      }
      return oldProp + newProp;
   }

   public String format(String name, Map<String, String> params) {
      String result = getTemplate(name);
      Matcher m = PATTERN.matcher(result);
      while (m.find()) {
         String key = m.group(1);
         String newVal = params.getOrDefault(key, "[" + key + "]");
         result = result.replaceFirst(REGEX, newVal);
      }
      return result;
   }

   public static URL get(String name) {
      return HtmlTemplate.class.getResource("/email/" + name);
   }

   public static void main(String[] args) throws Exception {
      HtmlTemplate ht = new HtmlTemplate();
      ht.addFileStyle("styles.css", get("styles.css"));
      ht.addFileTemplate("T1", get("usuario-invitacion.html"));
      Map<String, String> params = new HashMap<>();
      params.put("email", "yracnet@gmail.com");
      params.put("url-base", "http://www.senavex.gob.bo/app");
      params.put("code", "AA23423DG");
      params.put("empresa", "Abc Abc Abc");
      String content = ht.format("T1", params);
      File temp = new File("target/temp.html");
      System.out.println("--->" + temp.getAbsolutePath());
      System.out.println("--->" + content);
      BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
      writer.write(content);
      writer.close();
      // sendAsHtml("yracnet@gmail.com", "xxx1", content);
   }
}
