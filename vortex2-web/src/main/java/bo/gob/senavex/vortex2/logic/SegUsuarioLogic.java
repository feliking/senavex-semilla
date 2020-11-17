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
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import com.hiska.result.Param;
import com.hiska.result.Result;
import com.hiska.result.ResultItem;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

@Stateless
@LocalBean
public class SegUsuarioLogic {
   @Resource
   private SessionContext sessionContext;
   @lombok.Getter
   @PersistenceContext(unitName = Model.SEG_PERSIST)
   private EntityManager em;
   public static final Param E_REGISTRADO = Param.create("REG", "Usuario Registrado");
   public static final Param E_ACTIVO = Param.create("ACT", "Usuario Activo");
   public static final Param T_EXTERNO = Param.create("EXT", "Usuario Externo");
   public static final Param T_INTERNO = Param.create("INT", "Usuario Interno");
   public static final Param T_SISTEMA = Param.create("SIS", "Usuario Sistema");

   public SegUsuarioLogic() {
   }

   public SegUsuarioLogic(EntityManager em) {
      this.em = em;
   }

   public Result segUsuarioChangePassword(Long id, String claveAcceso) {
      Result result = new Result();
      SegUsuario segUsuario = em.find(SegUsuario.class, id);
      if (segUsuario == null) {
         result.setSuccess(false);
         result.message("SEG-1001: Usuario no valido");
      } else {
         claveAcceso = sha256Hex(claveAcceso);
         segUsuario.setClaveAcceso(claveAcceso);
         em.merge(segUsuario);
         result.message("SEG-1001: La clave de acceso fue actualizada")
               .cause("ID: " + segUsuario.getIdUsuario())
               .action("Puede ingresar al sistema");
      }
      return result;
   }

   public ResultItem<SegUsuario> segUsuarioPersist(final SegUsuario segUsuario) {
      segUsuario.setIdUsuario(null);
      String correoElectronico = segUsuario.getCorreoElectronico().trim().toLowerCase();
      String claveAcceso = segUsuario.getClaveAcceso();
      claveAcceso = sha256Hex(claveAcceso);
      segUsuario.setClaveAcceso(claveAcceso);
      if (correoElectronico.endsWith("@senavex.gob.bo")) {
         segUsuario.setTipo(SegUsuarioLogic.T_INTERNO);
      } else {
         segUsuario.setTipo(SegUsuarioLogic.T_EXTERNO);
      }
      ResultItem<SegUsuario> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Usuario")
            .validate(segUsuario, "idUsuario")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(segUsuario);
      result.setValue(segUsuario);
      result.message("SEG-1001: El Usuario fue registrado correctamente")
            .cause("ID: " + segUsuario.getIdUsuario())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

//    public ResultItem<SegUsuario> segUsuarioMerge(final SegUsuario segUsuario) {
//        ResultItem<SegUsuario> result = new ResultItem<>();
//        Message message = MessageBuilder.create("SEG-2001: Validacion Registro Usuario")
//              .validate(segUsuario, "idUsuario")
//              .get();
//        if (!message.isCauseEmpty()) {
//           result.setSuccess(false);
//           result.addMessage(message);
//           return result;
//        }
//        SegUsuario segUsuarioNew = em.merge(segUsuario);
//        result.setValue(segUsuarioNew);
//        result.message("SEG-1001: El Usuario fue actualizado correctamente")
//              .cause("ID: " + segUsuarioNew.getIdUsuario())
//              .action("Puede proceder a realizar mas operaciones");
//        return result;
//    }
//
//    public ResultItem<SegUsuario> segUsuarioRemove(Long id) {
//        SegUsuario segUsuario = em.find(SegUsuario.class, id);
//        return segUsuarioRemove(segUsuario);
//    }
//
//    public ResultItem<SegUsuario> segUsuarioRemove(SegUsuario segUsuario) {
//        ResultItem<SegUsuario> result = new ResultItem<>();
//        em.remove(segUsuario);
//        result.message("SEG-1000: El Usuario fue eliminado correctamente")
//              .cause("ID: " + segUsuario.getIdUsuario())
//              .action("Puede proceder a realizar mas operaciones");
//        return result;
//    }
//
   public ResultItem<SegUsuario> segUsuarioVerify(String correoElectronico) {
      ResultItem<SegUsuario> result = new ResultItem<>();
      List<SegUsuario> list = em.createQuery("SELECT o FROM SegUsuario o WHERE o.correoElectronico = :correoElectronico")
            .setParameter("correoElectronico", correoElectronico)
            .getResultList();
      if (list.size() > 0) {
         result.setSuccess(false);
         Message message = result.message("SEG-1001: La cuenta ya esta registrada!").get();
         SegUsuario se = list.get(0);
         message.addCause("La cuenta esta en un estado de: " + se.getEstado());
      }
      return result;
   }

   public static String sha256Hex(String originalString) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
         StringBuilder hexString = new StringBuilder();
         for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if (hex.length() == 1) {
               hexString.append('0');
            }
            hexString.append(hex);
         }
         return hexString.toString();
      } catch (Exception e) {
         throw new RuntimeException("Error al encriptar valor", e);
      }
   }

   public ResultItem<SegUsuario> segUsuarioByCorreoElectronico(String correoElectronico) {
      List<SegUsuario> list = em.createQuery("SELECT o FROM SegUsuario o WHERE o.correoElectronico = :c")
            .setParameter("c", correoElectronico)
            .getResultList();
      SegUsuario segUsuario = list.isEmpty() ? null : list.get(0);
      ResultItem<SegUsuario> result = new ResultItem<>(segUsuario);
      if (segUsuario == null) {
         result.setSuccess(false);
         result.message("SEG-2001: No existe el usuario solicitado");
      }
      return result;
   }

   public ResultItem<SegUsuario> getCurrentSegUsuario() {
      ResultItem<SegUsuario> result = new ResultItem<>();
      Principal principal = sessionContext.getCallerPrincipal();
      if (principal == null) {
         result.message("SEG-2001: Session no valida");
         result.setSuccess(false);
         return result;
      }
      List<SegUsuario> list = em.createQuery("SELECT o FROM SegUsuario o WHERE o.correoElectronico = :correo")
            .setParameter("correo", principal.getName())
            .getResultList();
      if (list.isEmpty()) {
         result.message("SEG-2001: El usuario no existe!");
         result.setSuccess(false);
         return result;
      }
      result.setValue(list.get(0));
      return result;
   }

   public ResultItem<SegUsuario> segUsuarioActive(Long idUsuario) {
      ResultItem<SegUsuario> result = new ResultItem<>();
      result.setSuccess(false);
      SegUsuario segUsuario = (SegUsuario) em.createQuery("SELECT o FROM SegUsuario o WHERE o.idUsuario = :id")
            .setParameter("id", idUsuario)
            .getSingleResult();
      if (segUsuario == null) {
         result.message("SEG-3001: Usuario no valido!");
      } else if (!E_REGISTRADO.equals(segUsuario.getEstado())) {
         result.message("SEG-3001: Estado del usuario no valido!");
      } else {
         // segUsuario.setFechaActivacion(new Date());
         segUsuario.setEstado(E_ACTIVO);
         em.merge(segUsuario);
         result.message("SEG-1001: Usuario activado!");
         result.setSuccess(true);
         result.setValue(segUsuario);
      }
      return result;
   }
}
