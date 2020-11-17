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

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.hiska.result.ResultItem;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.data.Captcha;
import bo.gob.senavex.vortex2.model.SegCorreo;
import com.hiska.result.Param;
import com.hiska.result.Result;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Stateless
@LocalBean
public class SegCorreoLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.SEG_PERSIST)
   private EntityManager em;
   @Inject
   private HttpServletRequest request;
   public static final Param E_REGISTRADO = Param.create("REG", "Correo Registrado");
   public static final Param E_RENVIADO = Param.create("REN", "Correo para Renviar");
   public static final Param E_ENVIADO = Param.create("SEND", "Correo Enviado");
   public static final Param E_ERROR = Param.create("ERR", "Error Correo");
   public static final Param E_USADO = Param.create("USE", "Correo Utilizado");
   public static final Param E_ELIMINADO = Param.create("DEL", "Correo Eliminado");
   public static final Param P_ACTIVACION = Param.create("USER_ACT", "Activacion de Usuario");
   public static final Param P_RECUPERAR = Param.create("USER_REC", "Recuperacion de Usuario");
   public static final Param P_INVITACION = Param.create("USER_INV", "Invitacion de Usuario");
   public static final Param T_ID_USUARIO = Param.create("ID_USUARIO", "Id Tabla SegUsuario");
   public static final Param T_ID_OPERADOR = Param.create("ID_OPERADOR", "Id Tabla SegOperador");

   public SegCorreoLogic() {
   }

   public SegCorreoLogic(EntityManager em) {
      this.em = em;
   }

   public ResultItem<SegCorreo> segCorreoPersist(final SegCorreo segCorreo) {
      segCorreo.setIdCorreo(null);
      String key = UUID.randomUUID().toString();
      segCorreo.setValorToken(key);
      ResultItem<SegCorreo> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Correo")
            .validate(segCorreo, "idCorreo")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(segCorreo);
      result.setValue(segCorreo);
      result.message("SEG-1001: El Correo fue registrado correctamente")
            .cause("ID: " + segCorreo.getIdCorreo())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<SegCorreo> segCorreoMerge(final SegCorreo segCorreo) {
      ResultItem<SegCorreo> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Correo")
            .validate(segCorreo, "idCorreo")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      SegCorreo segCorreoNew = em.merge(segCorreo);
      result.setValue(segCorreoNew);
      result.message("SEG-1001: El Correo fue actualizado correctamente")
            .cause("ID: " + segCorreoNew.getIdCorreo())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public Result segCorreoTokenDelete(String valorToken) {
      ResultItem<SegCorreo> result = new ResultItem<>();
      result.setSuccess(false);
      SegCorreo tk = (SegCorreo) em.createQuery("SELECt o FROM SegCorreo o WHERE o.valorToken = :vt")
            .setParameter("vt", valorToken)
            .getSingleResult();
      if (tk == null || !E_ENVIADO.equals(tk.getEstado())) {
         result.message("SEG-1001: TOKEN no valido!");
      } else if (tk.getFechaVigencia().compareTo(new Date()) > 0) {
         result.message("SEG-1001: TOKEN no vigente!");
      } else {
         tk.setFechaUso(new Date());
         tk.setEstado(E_ELIMINADO);
         em.merge(tk);
         result.message("SEG-1001: Se ha eliminado el TOKEN!");
         result.setSuccess(true);
      }
      return result;
   }

   public ResultItem<String> assertValorToken(String valorToken) {
      ResultItem<String> result = new ResultItem<>();
      result.setSuccess(false);
      SegCorreo sc = (SegCorreo) em.createQuery("SELECt o FROM SegCorreo o WHERE o.valorToken = :vt")
            .setParameter("vt", valorToken)
            .getSingleResult();
      if (sc == null || !E_ENVIADO.equals(sc.getEstado())) {
         result.message("SEG-1001: TOKEN no valido!");
      } else if (sc.getFechaVigencia().compareTo(new Date()) > 0) {
         result.message("SEG-1001: TOKEN no vigente!");
      } else {
         result.setValue(sc.getPlantilla().getValue());
         result.setSuccess(true);
      }
      return result;
   }

   public ResultItem<SegCorreo> assertValorToken(String valorToken, Param plantilla) {
      ResultItem<SegCorreo> result = new ResultItem<>();
      result.setSuccess(false);
      List<SegCorreo> tks = em.createQuery("SELECt o FROM SegCorreo o WHERE o.valorToken = :vt AND o.plantilla = :p")
            .setParameter("vt", valorToken)
            .setParameter("p", plantilla)
            .getResultList();
      if (tks.isEmpty()) {
         result.message("SEG-1001: TOKEN no existe!");
         return result;
      }
      SegCorreo tk = tks.get(0);
      if (tk == null || !E_ENVIADO.equals(tk.getEstado())) {
         result.message("SEG-1001: TOKEN no valido!");
      } else if (tk.getFechaVigencia().compareTo(new Date()) > 0) {
         result.message("SEG-1001: TOKEN no vigente!");
      } else {
         result.setValue(tk);
         tk.setFechaUso(new Date());
         tk.setEstado(E_USADO);
         em.merge(tk);
         result.setSuccess(true);
      }
      return result;
   }

   public Result assertCodigoCaptcha(Captcha captcha) {
      String value = captcha.getValue();
      Result result = new Result();
      String code = request == null ? null : (String) request.getSession().getAttribute("CAPTCHA_CODE");
      request.getSession().removeAttribute("CAPTCHA_CODE");
      if ("-DEV-".equalsIgnoreCase(value)) {
         result.message("DEV-0000: Codigo catcha valido modo DESARROLLO!");
         return result;
      }
      if (code == null || !code.equalsIgnoreCase(value)) {
         result.setSuccess(false);
         result.message("SEG-0000: Codigo Captha no valido!");
      }
      return result;
   }
}
