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
package bo.gob.senavex.vortex2.bean;

import lombok.*;
import com.hiska.faces.bean.ConfigDefaultBean;
import java.io.Serializable;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Getter
@ToString
public class AppConfigBean extends ConfigDefaultBean implements Serializable {
   private String title = "VORTEX-v2 - Sistema de Verificacion de Exportaciones";
   private String url = "http://wwww.senavex.gob.bo";
   private String template = "/resources/template/login.xhtml";
   private String templateOut = "/resources/template/logout.xhtml";
   @Inject
   private HttpServletRequest request;

   @Override
   public String getTemplate() {
      Principal principal = request.getUserPrincipal();
      boolean isLogin = principal != null && !"".equals(principal.getName());
      return isLogin ? template : templateOut;
   }
}
