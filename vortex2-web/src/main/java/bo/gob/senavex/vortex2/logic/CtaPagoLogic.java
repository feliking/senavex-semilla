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
import bo.gob.senavex.vortex2.model.CtaPago;

@Stateless
@LocalBean
public class CtaPagoLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public CtaPagoLogic() {
   }

   public CtaPagoLogic(EntityManager em) {
      this.em = em;
   }

   public ResultItem<CtaPago> ctaPagoPersist(final CtaPago ctaPago) {
      ResultItem<CtaPago> result = new ResultItem<>();
      Message message = MessageBuilder.create("CTA-2001: Validacion Registro Pago")
            .validate(ctaPago, "idPago")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(ctaPago);
      result.setValue(ctaPago);
      result.message("CTA-1001: El Pago fue registrado correctamente")
            .cause("ID: " + ctaPago.getIdPago())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CtaPago> ctaPagoMerge(final CtaPago ctaPago) {
      ResultItem<CtaPago> result = new ResultItem<>();
      Message message = MessageBuilder.create("CTA-2001: Validacion Registro Pago")
            .validate(ctaPago, "idPago")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CtaPago ctaPagoNew = em.merge(ctaPago);
      result.setValue(ctaPagoNew);
      result.message("CTA-1001: El Pago fue actualizado correctamente")
            .cause("ID: " + ctaPagoNew.getIdPago())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CtaPago> ctaPagoRemove(Long id) {
      CtaPago ctaPago = em.find(CtaPago.class, id);
      return ctaPagoRemove(ctaPago);
   }

   public ResultItem<CtaPago> ctaPagoRemove(CtaPago ctaPago) {
      ResultItem<CtaPago> result = new ResultItem<>();
      em.remove(ctaPago);
      result.message("CTA-1000: El Pago fue eliminado correctamente")
            .cause("ID: " + ctaPago.getIdPago())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}