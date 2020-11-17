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
import bo.gob.senavex.vortex2.model.SegPersona;
import com.hiska.result.Param;
import java.util.List;

@Stateless
@LocalBean
public class SegPersonaLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.SEG_PERSIST)
   private EntityManager em;
   public static final Param E_REGISTRADO = Param.create("REG", "Persona Registrado");

   public SegPersonaLogic() {
   }

   public SegPersonaLogic(EntityManager em) {
      this.em = em;
   }

   public ResultItem<SegPersona> segPersonaPersist(final SegPersona segPersona) {
      segPersona.setIdPersona(null);
      ResultItem<SegPersona> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Persona")
            .validate(segPersona, "idPersona")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(segPersona);
      result.setValue(segPersona);
      result.message("SEG-1001: El Persona fue registrado correctamente")
            .cause("ID: " + segPersona.getIdPersona())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<SegPersona> segPersonaVerify(final SegPersona segPersona) {
      ResultItem<SegPersona> result = new ResultItem<>();
      List<SegPersona> list = em.createQuery("SELECT o"
            + " FROM SegPersona o"
            + " WHERE o.tipoDoc = :p1 "
            + "   AND o.numeroDoc = :p2 "
            + "   AND o.complementoDoc = :p3 "
            + "   AND o.expedicionDoc = :p4 ")
            .setParameter("p1", segPersona.getTipoDocumento())
            .setParameter("p2", segPersona.getNumeroDocumento())
            .setParameter("p3", segPersona.getComplementoDocumento())
            .setParameter("p4", segPersona.getExpedicionDocumento())
            .getResultList();
      if (list.size() > 0) {
         result.setSuccess(false);
         Message message = result.message("SEG-1001: La persona ya esta registrada!").get();
         SegPersona se = list.get(0);
         message.addCause("La cuenta esta en un estado de: " + se.getEstado());
      }
      return result;
   }

   public ResultItem<SegPersona> segPersonaMerge(final SegPersona segPersona) {
      ResultItem<SegPersona> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Persona")
            .validate(segPersona, "idPersona")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      SegPersona segPersonaNew = em.merge(segPersona);
      result.setValue(segPersonaNew);
      result.message("SEG-1001: El Persona fue actualizado correctamente")
            .cause("ID: " + segPersonaNew.getIdPersona())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<SegPersona> segPersonaRemove(Long id) {
      SegPersona segPersona = em.find(SegPersona.class, id);
      return segPersonaRemove(segPersona);
   }

   public ResultItem<SegPersona> segPersonaRemove(SegPersona segPersona) {
      ResultItem<SegPersona> result = new ResultItem<>();
      em.remove(segPersona);
      result.message("SEG-1000: El Persona fue eliminado correctamente")
            .cause("ID: " + segPersona.getIdPersona())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}
