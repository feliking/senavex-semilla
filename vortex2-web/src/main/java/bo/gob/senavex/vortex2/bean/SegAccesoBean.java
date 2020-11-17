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
import bo.gob.senavex.vortex2.data.ConfigWrap;
import bo.gob.senavex.vortex2.data.Login;
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.model.SegUsuario;
import bo.gob.senavex.vortex2.serv.SegAccesoServ;
import com.hiska.faces.MessageUtil;
import com.hiska.result.Result;
import com.hiska.result.ResultItem;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import lombok.*;

@Named(value = "_acceso")
@SessionScoped
@Getter
public class SegAccesoBean implements Serializable {
   @Inject
   private SegAccesoServ segAccesoServ;
   private Login form = new Login();
   private ConfigWrap config = new ConfigWrap();
   private Captcha<Login> captcha = new Captcha<>();

   public String loginAction() {
      captcha.setData(form);
      ResultItem<ConfigWrap> result = segAccesoServ.segUsuarioLogin(captcha);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         config = result.getValue();
      });
      return "home";
   }

   public String logoutAction() {
      Result result = segAccesoServ.segUsuarioLogout();
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
         config = new ConfigWrap();
      });
      return result.isSuccess() ? "logout" : null;
   }

   public String removeValorTokenAction(String id) {
      if (id != null && !id.isEmpty()) {
         Result result = segAccesoServ.segCorreoTokenDelete(id);
         MessageUtil.processResult(result);
         return "home";
      }
      return null;
   }

   public String recargarAction() {
      ResultItem<ConfigWrap> result = segAccesoServ.configRecargar(config);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         config = result.getValue();
      });
      return null;
   }

   public boolean isEsCliente() {
      return config.isUsuarioExterno();
   }

   public boolean isEsOperador() {
      return config.isUsuarioInterno();
   }

   public boolean isEsSistema() {
      return config.isUsuarioSistema();
   }

   public List getMenus() {
      return config.getMenus();
   }

   public SegUsuario getUsuario() {
      return config.getSegUsuario();
   }

   public SegPersona getPersona() {
      return config.getSegPersona();
   }

   public List<Long> getIdEmpresas() {
      return config.getIdEmpresas();
   }

   public String getStyleClass() {
      return config.isUsuarioExterno() ? "xx-externo" : config.isUsuarioInterno() ? "xx-interno" : config.isUsuarioSistema() ? "xx-sistema" : "xx-ignore";
   }

   public String loginFASTAction(String mail) {
      form.setCorreoElectronico(mail);
      form.setClaveAcceso("12345");
      captcha.setValue("-DEV-");
      return loginAction();
   }
}
