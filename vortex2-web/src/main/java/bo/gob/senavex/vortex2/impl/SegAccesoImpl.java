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
package bo.gob.senavex.vortex2.impl;

import bo.gob.senavex.vortex2.logic.SegCorreoFactory;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.hiska.result.Result;
import com.hiska.result.Message;
import com.hiska.result.ResultItem;
import com.hiska.result.ResultBuilder;
import com.hiska.result.MessageBuilder;

import bo.gob.senavex.vortex2.serv.*;
import bo.gob.senavex.vortex2.model.SegUsuario;
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.i16d.I16DService;
import bo.gob.senavex.vortex2.logic.SegPersonaLogic;
import bo.gob.senavex.vortex2.logic.SegCorreoLogic;
import bo.gob.senavex.vortex2.logic.SegUsuarioLogic;
import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.data.Captcha;
import bo.gob.senavex.vortex2.data.ConfigWrap;
import bo.gob.senavex.vortex2.data.Login;
import bo.gob.senavex.vortex2.logic.SegConfigLogic;
import bo.gob.senavex.vortex2.logic.SegOperadorLogic;
import com.hiska.faces.cc.Menu;
import com.hiska.result.ResultList;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@Local(SegAccesoServ.class)
public class SegAccesoImpl implements SegAccesoServ {
   private static final Logger LOGGER = Logger.getLogger(SegAccesoImpl.class.getName());
   @Resource
   private SessionContext sessionContext;
   @Inject
   private SegPersonaLogic segPersonaLogic;
   @Inject
   private SegUsuarioLogic segUsuarioLogic;
   @Inject
   private SegOperadorLogic segOperadorLogic;
   @Inject
   private SegConfigLogic segConfigLogic;
   @Inject
   private SegCorreoLogic segCorreoLogic;
   @Inject
   private I16DService i16dService;
   @Inject
   private HttpServletRequest request;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("SEG-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + segUsuarioLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultItem<ConfigWrap> segUsuarioLogin(final Captcha<Login> captcha) {
      Login login = captcha.getData();
      Result result = segCorreoLogic.assertCodigoCaptcha(captcha);
      ConfigWrap cw = new ConfigWrap();
      if (result.isSuccess()) {
         try {
            request.login(login.getCorreoElectronico(), login.getClaveAcceso());
            cw.assertLogin(login.getCorreoElectronico());
         } catch (ServletException e) {
            result.setSuccess(false);
            result.message("SEG-1000: Usuario no valido!")
                  .error()
                  .exception(e);
            LOGGER.log(Level.SEVERE, "Login Error", e);
         }
      }
      if (result.isSuccess()) {
         ResultItem<ConfigWrap> r1 = configRecargar(cw);
         cw = r1.getValue();
         result.accept(r1);
      }
      if (result.isError()) {
         cw.assertLogout();
         try {
            request.logout();
         } catch (ServletException e) {
            LOGGER.log(Level.SEVERE, "Logout Error", e);
         }
      } else {
         result.clear();
         result.message("SEG-0001: Acceso exitoso")
               .info();
      }
      return new ResultItem<>(cw, result);
   }

   @Override
   public ResultItem<ConfigWrap> configRecargar(final ConfigWrap cw) {
      ResultItem<ConfigWrap> result = new ResultItem(cw);
      if (result.isSuccess()) {
         ResultItem<SegUsuario> r1 = segUsuarioLogic.segUsuarioByCorreoElectronico(cw.getCorreoElectronico());
         cw.assertSegUsuario(r1.getValue());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         ResultList<Menu> r1 = segConfigLogic.configMenuSession(cw.getSegUsuario());
         cw.assertMenu(r1.getValue());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         ResultList<Long> r1 = segConfigLogic.configIdEmpresasSession(cw.getSegUsuario());
         cw.assertIdEmpresas(r1.getValue());
         result.accept(r1);
      }
      return result;
   }

   @Override
   public Result segUsuarioLogout() {
      Result result = new Result();
      try {
         request.logout();
         result.message("SEG-0001: Cierre session exitoso")
               .info();
      } catch (ServletException e) {
         result.setSuccess(false);
         result.message("SEG-1000: Error al cerrar la session!")
               .error()
               .exception(e);
         LOGGER.log(Level.SEVERE, "Login Error", e);
      }
      return result;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public Result segUsuarioRegisterCorreoToken(final Captcha<SegUsuario> captcha) {
      SegUsuario segUsuario = captcha.getData();
      SegPersona segPersona = segUsuario.getSegPersona();
      Result result = segCorreoLogic.assertCodigoCaptcha(captcha);
      if (result.isSuccess()) {
         Result r1 = segUsuarioLogic.segUsuarioVerify(segUsuario.getCorreoElectronico());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         Result r1 = i16dService.segipVerificar(segPersona);
         result.accept(r1);
      }
      if (result.isSuccess()) {
         segPersona.setEstado(SegPersonaLogic.E_REGISTRADO);
         ResultItem<SegPersona> resultItem = segPersonaLogic.segPersonaPersist(segPersona);
         segPersona = resultItem.getValue();
         segUsuario.setSegPersona(segPersona);
         result.accept(resultItem);
      }
      if (result.isSuccess()) {
         segUsuario.setEstado(SegUsuarioLogic.E_REGISTRADO);
         ResultItem<SegUsuario> resultItem = segUsuarioLogic.segUsuarioPersist(segUsuario);
         result.accept(resultItem);
      }
      if (result.isSuccess()) {
         SegCorreo segCorreo = SegCorreoFactory.correoUsuarioRegistrado(segUsuario);
         ResultItem<SegCorreo> resultItem = segCorreoLogic.segCorreoPersist(segCorreo);
         result.accept(resultItem);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("APP-1001: Registro exitoso!")
               .cause("Se ha enviado un correo con un codigo de activacion")
               .action("Revise su correo electronico");
      }
      return result;
   }

   @Override
   public Result segUsuarioRegisterConfirm(final String id) {
      // ignore captcha
      ResultItem<SegCorreo> result = segCorreoLogic.assertValorToken(id, SegCorreoLogic.P_ACTIVACION);
      SegUsuario segUsuario = null;
      if (result.isSuccess()) {
         SegCorreo segCorreo = result.getValue();
         Long idUsuario = segCorreo.getIdReferenciaLong();
         ResultItem<SegUsuario> r1 = segUsuarioLogic.segUsuarioActive(idUsuario);
         segUsuario = r1.getValue();
         result.accept(r1);
      }
      if (result.isSuccess()) {
         Result r1 = segOperadorLogic.activarInvitacion(segUsuario);
         result.accept(r1);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("SEG-1001: Usuario activado exitosamente");
      }
      return result.asResult();
   }

   @Override
   public Result segUsuarioRecoveryCorreoToken(Captcha<String> captcha) {
      String correoElectronico = captcha.getData();
      SegUsuario segUsuario = new SegUsuario();
      Result result = segCorreoLogic.assertCodigoCaptcha(captcha);
      if (result.isSuccess()) {
         ResultItem<SegUsuario> resultItem = segUsuarioLogic.segUsuarioByCorreoElectronico(correoElectronico);
         segUsuario = resultItem.getValue();
         result.accept(resultItem);
      }
      if (result.isSuccess()) {
         if (SegUsuarioLogic.E_REGISTRADO.equals(segUsuario.getEstado())) {
            SegCorreo segCorreo = SegCorreoFactory.correoUsuarioRegistrado(segUsuario);
            ResultItem<SegCorreo> resultItem = segCorreoLogic.segCorreoPersist(segCorreo);
            result.accept(resultItem);
         } else if (SegUsuarioLogic.E_ACTIVO.equals(segUsuario.getEstado())) {
            SegCorreo segCorreo = SegCorreoFactory.correoUsuarioRecuperacion(segUsuario);
            ResultItem<SegCorreo> resultItem = segCorreoLogic.segCorreoPersist(segCorreo);
            result.accept(resultItem);
         } else {
            result.message("SEG-1001: Estado del usuario no valido!");
            result.setSuccess(false);
         }
      }
      return result;
   }

   @Override
   public Result segUsuarioRecoveryConfirm(Captcha<String> captcha) {
      String claveAcceso = captcha.getData();
      SegCorreo segCorreo = null;
      Result result = segCorreoLogic.assertCodigoCaptcha(captcha);
      if (result.isSuccess()) {
         ResultItem<SegCorreo> r0 = segCorreoLogic.assertValorToken(captcha.getId(), SegCorreoLogic.P_RECUPERAR);
         segCorreo = r0.getValue();
         result.accept(r0);
      }
      if (result.isSuccess() && segCorreo != null) {
         Long idUsuario = segCorreo.getIdReferenciaLong();
         Result r1 = segUsuarioLogic.segUsuarioChangePassword(idUsuario, claveAcceso);
         result.accept(r1);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      }
      return result;
   }

   @Override
   public Result segCorreoTokenDelete(final String valorToken) {
      return segCorreoLogic.segCorreoTokenDelete(valorToken);
   }
}
