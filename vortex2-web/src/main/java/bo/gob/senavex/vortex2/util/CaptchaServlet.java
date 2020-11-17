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

import dev.yracnet.captcha.CaptchaBuild;
import dev.yracnet.captcha.CaptchaConfig;
import static dev.yracnet.captcha.CaptchaHelp.*;
import dev.yracnet.captcha.effect.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Willyams Yujra
 */
@WebServlet(name = "CaptchaServlet", urlPatterns = {"captcha.png"})
public class CaptchaServlet extends HttpServlet {
   private static CaptchaBuild build = CaptchaBuild.createInstance();
   static {
      CaptchaConfig config = build.getConfig();
      config.addTextFont(urlFromName("/default/font1.ttf"));
      config.addTextFont(urlFromName("/captcha/ubuntu.ttf"));
      config.addTextFont(urlFromName("/captcha/fake.ttf"));
      config.addMaskImage(urlFromName("/captcha/bg1.jpg"));
      config.addMaskImage(urlFromName("/captcha/bg2.jpg"));
      config.addMaskImage(urlFromName("/captcha/bg3.jpg"));
      config.addMaskImage(urlFromName("/captcha/bg4.jpg"));
      config.addMaskImage(urlFromName("/captcha/bg5.jpg"));
      config.addTextColor("#30106c");
      config.addTextColor("#feda00");
      config.addTextColor("#eaf871");
      config.addTextColor("#e72849");
      config.addTextColor("#28e74e");
      config.addTextEffect(new TwirlEffect());
      config.addTextEffect(new ConvolveEffect());
      config.addMaskEffect(CaptchaConfig.NONE_EFFECT);
      config.addMergeEffect(CaptchaConfig.NONE_EFFECT);
      config.addMergeEffect(new TwirlEffect());
      config.addMergeEffect(new BoxBlurEffect());
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      String code = build.createCodeRandom(5);
      request.getSession(true).setAttribute("CAPTCHA_CODE", code);
      byte[] catpcha = build.createCaptchaAsByteArray(code, 1, 90);
      response.setContentType("image/png");
      response.setContentLength(catpcha.length);
      response.setHeader("X-CODE", code);
      ServletOutputStream os = response.getOutputStream();
      os.write(catpcha);
      os.flush();
      catpcha = null;
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      response.sendError(501, "Not support!");
   }

   @Override
   public String getServletInfo() {
      return "Captcha description";
   }
}
