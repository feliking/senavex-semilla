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
import com.hiska.result.ResultFilter;
import com.hiska.result.filter.FilterBuilder;
import bo.gob.senavex.vortex2.Model;

import bo.gob.senavex.vortex2.model.CtaPago;
import bo.gob.senavex.vortex2.model.CtaDeposito;

@Stateless
@LocalBean
@lombok.Getter
public class CtaQueryLogic {
   @PersistenceContext(unitName = Model.CTA_PERSIST)
   private EntityManager em;

   public CtaQueryLogic() {
   }

   public CtaQueryLogic(EntityManager em) {
      this.em = em;
   }

   public ResultFilter<CtaPago> ctaPagoFilter(Object filter) {
      return FilterBuilder.create(CtaPago.class, filter).getResultFilter(em);
   }

   public CtaPago ctaPagoFind(Long id) {
      return em.find(CtaPago.class, id);
   }

   public ResultFilter<CtaDeposito> ctaDepositoFilter(Object filter) {
      return FilterBuilder.create(CtaDeposito.class, filter).getResultFilter(em);
   }

   public CtaDeposito ctaDepositoFind(Long id) {
      return em.find(CtaDeposito.class, id);
   }
}