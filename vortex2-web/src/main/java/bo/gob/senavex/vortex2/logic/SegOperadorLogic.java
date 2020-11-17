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
import com.hiska.result.Result;
import com.hiska.result.ResultItem;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.result.Param;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class SegOperadorLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public SegOperadorLogic() {
   }

   public SegOperadorLogic(EntityManager em) {
      this.em = em;
   }

   public Result assertEstadoValido(SegOperador segOperador, String estado) {
      Result result = new Result();
      // Param param = regRegistro.getEstado();
      Param param = (Param) em.createQuery("SELECT o.estado FROM SegOperador o WHERE o = :o")
            .setParameter("o", segOperador)
            .getSingleResult();
      if (!Param.isEquals(param, estado)) {
         result.message("REG-2001: Estado del registro no valido")
               .cause("Estado del registro: " + param)
               .cause("Estado requerido: " + estado);
         result.setSuccess(false);
      }
      return result;
   }

   public Result assertEstadosValidos(SegOperador segOperador, String... estados) {
      Result result = new Result();
      // Param param = regRegistro.getEstado();
      Param param = (Param) em.createQuery("SELECT o.estado FROM SegOperador o WHERE o = :o")
            .setParameter("o", segOperador)
            .getSingleResult();
      if (Param.isIn(param, estados)) {
         return result;
      }
      MessageBuilder mb = result.message("REG-2001: Estado del registro no valido")
            .cause("Estado del registro: " + param);
      for (String estado : estados) {
         mb.cause("Estado aceptado: " + estado);
      }
      result.setSuccess(false);
      return result;
   }

   public Result segOperadorPersist(final CliEmpresa cliEmpresa, final List<SegOperador> segOperadorList) {
      Result result = new Result();
      for (SegOperador segOperador : segOperadorList) {
         segOperador.setCliEmpresa(cliEmpresa);
         segOperador.setFechaSolicitud(new Date());
         ResultItem<SegOperador> item = segOperadorPersist(segOperador);
         result.accept(item);
         if (result.isError()) {
            break;
         }
      }
      return result;
   }

   public Result aceptarInvitacion(final Long idOperador) {
      Result result = new Result();
      result.setSuccess(false);
      SegOperador segOperador = em.find(SegOperador.class, idOperador);
      if (segOperador == null) {
         result.message("SEG-1001: Operador no valido!");
      } else if (!Param.isEquals(segOperador.getEstado(), "REG")) {
         result.message("SEG-1001: Estado del operador no valido!")
               .cause("Estado valido: REG")
               .cause("Estado actual:" + segOperador.getEstado());
      } else if (segOperador.getSegUsuario() != null) {
         return activarInvitacion(segOperador);
      } else {
         segOperador.setFechaRespuesta(new Date());
         segOperador.setEstado(Param.create("ACEP", "Aceptado"));
         em.merge(segOperador);
         result.message("SEG-1001: Invitacion aceptada!")
               .action("Debe crear una cuenta");
         result.setSuccess(true);
      }
      return result;
   }

   public Result activarInvitacion(final SegUsuario segUsuario) {
      if (segUsuario != null) {
         String correoElectronico = segUsuario.getCorreoElectronico();
         List<SegOperador> list = em.createQuery("SELECT o FROM SegOperador o WHERE o.correoElectronico = :ce AND o.estado in (:e1, :e2)")
               .setParameter("ce", correoElectronico)
               .setParameter("e1", Param.create("REG"))
               .setParameter("e2", Param.create("ACEP"))
               .getResultList();
         for (SegOperador so : list) {
            so.setSegUsuario(segUsuario);
            Result r1 = activarInvitacion(so);
            if (r1.isError()) {
               return r1;
            }
         }
      }
      return new Result();
   }

   public ResultItem<SegOperador> activarInvitacion(final SegOperador segOperador) {
      ResultItem<SegOperador> result = new ResultItem<>(segOperador);
      result.setSuccess(false);
      if (segOperador.getSegUsuario() == null) {
         result.message("SEG-3001: Se requiere un usuario!");
      } else if (!Param.isIn(segOperador.getEstado(), "REG", "ACEP")) {
         result.message("SEG-3001: Estado del operador no valido!")
               .cause("Estado valido: REG, ACEP")
               .cause("Estado actual:" + segOperador.getEstado());
      } else if (!segOperador.compareCorreoElectronico()) {
         result.message("SEG-3001: El Correo Electrono no valido!");
      } else if (!segOperador.compareNumeroDocumento()) {
         result.message("SEG-3001: El Numero Documento no valido!");
      } else {
         segOperador.setFechaRespuesta(new Date());
         segOperador.setEstado(Param.create("ACT"));
         segOperador.setFechaRespuesta(new Date());
         em.merge(segOperador);
         result.message("SEG-1001: Operador activado!");
         result.setSuccess(true);
      }
      return result;
   }

   public ResultItem<SegOperador> segOperadorPersist(final SegOperador segOperador) {
      segOperador.setIdOperador(null);
      List<SegUsuario> segUsuarioList = em.createQuery("SELECT o FROM SegUsuario o WHERE o.correoElectronico = :ce")
            .setParameter("ce", segOperador.getCorreoElectronico())
            .getResultList();
      if (segUsuarioList.size() > 0) {
         segOperador.setSegUsuario(segUsuarioList.get(0));
      }
      ResultItem<SegOperador> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Operador")
            .validate(segOperador, "idOperador")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(segOperador);
      result.setValue(segOperador);
      result.message("SEG-1001: El Operador fue registrado correctamente")
            .cause("ID: " + segOperador.getIdOperador())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<SegOperador> segOperadorMerge(final SegOperador segOperador) {
      ResultItem<SegOperador> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Operador")
            .validate(segOperador, "idOperador")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      SegOperador segOperadorNew = em.merge(segOperador);
      result.setValue(segOperadorNew);
      result.message("SEG-1001: El Operador fue actualizado correctamente")
            .cause("ID: " + segOperadorNew.getIdOperador())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<SegOperador> segOperadorRemove(Long id) {
      SegOperador segOperador = em.find(SegOperador.class, id);
      return segOperadorRemove(segOperador);
   }

   public ResultItem<SegOperador> segOperadorRemove(SegOperador segOperador) {
      ResultItem<SegOperador> result = new ResultItem<>();
      em.remove(segOperador);
      result.message("SEG-1000: El Operador fue eliminado correctamente")
            .cause("ID: " + segOperador.getIdOperador())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}
