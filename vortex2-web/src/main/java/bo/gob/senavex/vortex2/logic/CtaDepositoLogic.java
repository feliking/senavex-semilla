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
import bo.gob.senavex.vortex2.model.CtaDeposito;
import bo.gob.senavex.vortex2.model.CtaPago;
import java.util.List;

@Stateless
@LocalBean
public class CtaDepositoLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public CtaDepositoLogic() {
   }

   public CtaDepositoLogic(EntityManager em) {
      this.em = em;
   }

   public Result ctaDepositoPersist(final CtaPago ctaPago, final List<CtaDeposito> ctaDepositoList) {
      Result result = new Result();
      for (CtaDeposito ctaDeposito : ctaDepositoList) {
         ctaDeposito.setCtaPago(ctaPago);
         if (ctaDeposito.getIdDeposito() == null) {
            ResultItem<CtaDeposito> item = ctaDepositoPersist(ctaDeposito);
            result.accept(item);
         } else {
            ResultItem<CtaDeposito> item = ctaDepositoMerge(ctaDeposito);
            result.accept(item);
         }
         if (result.isError()) {
            break;
         }
      }
      return result;
   }

   public ResultItem<CtaDeposito> ctaDepositoPersist(final CtaDeposito ctaDeposito) {
      ResultItem<CtaDeposito> result = new ResultItem<>();
      Message message = MessageBuilder.create("CTA-2001: Validacion Registro Deposito")
            .validate(ctaDeposito, "idDeposito")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(ctaDeposito);
      result.setValue(ctaDeposito);
      result.message("CTA-1001: El Deposito fue registrado correctamente")
            .cause("ID: " + ctaDeposito.getIdDeposito())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CtaDeposito> ctaDepositoMerge(final CtaDeposito ctaDeposito) {
      ResultItem<CtaDeposito> result = new ResultItem<>();
      Message message = MessageBuilder.create("CTA-2001: Validacion Registro Deposito")
            .validate(ctaDeposito, "idDeposito")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CtaDeposito ctaDepositoNew = em.merge(ctaDeposito);
      result.setValue(ctaDepositoNew);
      result.message("CTA-1001: El Deposito fue actualizado correctamente")
            .cause("ID: " + ctaDepositoNew.getIdDeposito())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CtaDeposito> ctaDepositoRemove(Long id) {
      CtaDeposito ctaDeposito = em.find(CtaDeposito.class, id);
      return ctaDepositoRemove(ctaDeposito);
   }

   public ResultItem<CtaDeposito> ctaDepositoRemove(CtaDeposito ctaDeposito) {
      ResultItem<CtaDeposito> result = new ResultItem<>();
      em.remove(ctaDeposito);
      result.message("CTA-1000: El Deposito fue eliminado correctamente")
            .cause("ID: " + ctaDeposito.getIdDeposito())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}