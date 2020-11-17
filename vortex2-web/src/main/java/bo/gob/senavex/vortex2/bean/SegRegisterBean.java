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
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.faces.MessageUtil;
import com.hiska.result.Result;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import lombok.*;

@Named(value = "_register")
@ViewScoped
@Getter
public class SegRegisterBean implements Serializable {
   @Inject
   private SegAccesoServ segAccesoServ;
   private SegPersona persona = new SegPersona();
   private SegUsuario usuario = new SegUsuario();
   private final Captcha<SegUsuario> captcha = new Captcha<>();

   @PostConstruct
   public void initAction() {
//      usuario.setCorreoElectronico("yracnet@gmail.com");
//      usuario.setClaveAcceso("12345");
//      claveConfirm = "12345";
//      usuario.setSegPersona(persona);
//      persona.setPrimerApellido("Xx");
//      persona.setSegundoApellido("Xx");
//      persona.setnombres("Xx");
//      persona.setGenero(Param.create("M"));
//      persona.setFechaNacimiento(new Date());
//      persona.setTipoDoc(Param.create("CI"));
//      persona.setNumeroDoc("0001212");
//      persona.setComplementoDoc(null);
//      persona.setExpedicionDoc(Param.create("LP"));
//      persona.setNumeroCelular("7011111");
//      persona.setNumeroTelefonico("22000");
//      persona.setPais(Param.create("BO"));
//      persona.setDireccion("S/Direcion");
   }

   public String activeInitAction(String id) {
      if (id != null && !id.isEmpty()) {
         Result result = segAccesoServ.segUsuarioRegisterConfirm(id);
         MessageUtil.processResult(result);
         return "home";
      }
      return null;
   }

   public String registerAction() {
      String claveAcceso = usuario.getClaveAcceso();
      String claveConfirm = usuario.getClaveConfirm();
      if (claveConfirm == null || !claveConfirm.equals(claveAcceso)) {
         MessageUtil.error("La clave no fue confirmada!");
         return null;
      }
      usuario.setSegPersona(persona);
      captcha.setData(usuario);
      Result result = segAccesoServ.segUsuarioRegisterCorreoToken(captcha);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         persona = new SegPersona();
         usuario = new SegUsuario();
      });
      return result.isSuccess() ? "home" : null;
   }
}
