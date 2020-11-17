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
package bo.gob.senavex.vortex2.logic;

import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.util.HtmlTemplate;
import com.hiska.result.Document;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willyams Yujra
 */
public class SegCorreoTemplate {
   private static HtmlTemplate ht = new HtmlTemplate();
   static {
      String path = SegCorreoTemplate.class.getResource("/email").getFile();
      ht.addFileStyle("styles.css", HtmlTemplate.get("styles.css"));
      ht.addFileTemplate("usuario-activar", HtmlTemplate.get("usuario-activar.html"));
      ht.addFileTemplate("usuario-recuperacion", HtmlTemplate.get("usuario-recuperacion.html"));
      ht.addFileTemplate("usuario-invitacion", HtmlTemplate.get("usuario-invitacion.html"));
   }

   public static Document usuarioActivarMail(SegCorreo it) {
      Document mail = new Document();
      Map<String, String> params = createParams();
      params.put("email", it.getCorreoDestino());
      params.put("id", it.getValorToken());
      String content = ht.format("usuario-activar", params);
      mail.setFileName("Activacion de Cuenta");
      mail.setContent(content.getBytes());
      mail.setType(Document.Type.HTML);
      return mail;
   }

   public static Document usuarioRecuperacionMail(SegCorreo it) {
      Document mail = new Document();
      Map<String, String> params = createParams();
      params.put("email", it.getCorreoDestino());
      params.put("id", it.getValorToken());
      String content = ht.format("usuario-recuperacion", params);
      mail.setFileName("Recuperacion de Acceso");
      mail.setContent(content.getBytes());
      mail.setType(Document.Type.HTML);
      return mail;
   }

   public static Document usuarioInvitacionMail(SegCorreo it, SegOperador op) {
      Document mail = new Document();
      Map<String, String> params = createParams();
      params.put("email", it.getCorreoDestino());
      params.put("empresa", op.getCliEmpresa().getRefName());
      params.put("cargo", op.getNombreCargo());
      params.put("perfil", op.getPerfil().toString());
      params.put("id", it.getValorToken());
      String content = ht.format("usuario-invitacion", params);
      mail.setFileName("Invitacion Operador de Comercio Exterior");
      mail.setContent(content.getBytes());
      mail.setType(Document.Type.HTML);
      return mail;
   }

   public static Map<String, String> createParams() {
      Map<String, String> params = new HashMap<>();
      params.put("url-base", System.getProperty("vortex2.urlBase", "http://localhost:8080/vortex2"));
      params.put("url-logo", System.getProperty("vortex2.urlLogo", "https://yracnet.github.io/images/snv/banner-400.png"));
      return params;
   }
}
