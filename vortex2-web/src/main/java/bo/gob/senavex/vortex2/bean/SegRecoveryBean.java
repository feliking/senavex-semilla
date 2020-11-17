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

import bo.gob.senavex.vortex2.data.Captcha;
import bo.gob.senavex.vortex2.serv.SegAccesoServ;
import com.hiska.faces.MessageUtil;
import com.hiska.result.Result;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import lombok.*;

@Named(value = "_recovery")
@ViewScoped
@Getter
public class SegRecoveryBean implements Serializable {
   @Inject
   private SegAccesoServ segAccesoServ;
   private final Captcha<String> captcha = new Captcha<>();
   @Setter
   private String correoElectronico;
   @Setter
   private String claveAcceso;
   @Setter
   private String claveConfirm;

   @PostConstruct
   public void initAction() {
   }

   public String recoveryAction() {
      captcha.setData(correoElectronico);
      Result result = segAccesoServ.segUsuarioRecoveryCorreoToken(captcha);
      MessageUtil.processResult(result);
      return "home";
   }

   public String passwordInitAction(String id) {
      if (id != null && !id.isEmpty()) {
         captcha.setId(id);
         return null;
      }
      return "home";
   }

   public String passwordChangeAction() {
      if (claveConfirm == null || !claveConfirm.equals(claveAcceso)) {
         MessageUtil.error("La clave no fue confirmada!");
         return null;
      }
      captcha.setData(claveAcceso);
      Result result = segAccesoServ.segUsuarioRecoveryConfirm(captcha);
      MessageUtil.processResult(result);
      return result.isSuccess() ? "home" : null;
   }
}
