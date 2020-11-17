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

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;
import com.hiska.result.MessageBuilder;
import com.hiska.result.ResultBuilder;
import com.hiska.result.Message;
import bo.gob.senavex.vortex2.model.CtaPago;
import bo.gob.senavex.vortex2.filter.CtaPagoFilter;
import bo.gob.senavex.vortex2.serv.CtaPagoServ;
import bo.gob.senavex.vortex2.logic.CtaPagoLogic;
import bo.gob.senavex.vortex2.logic.CtaQueryLogic;

@Stateless
@Local(CtaPagoServ.class)
public class CtaPagoImpl implements CtaPagoServ {
   @Inject
   private CtaPagoLogic ctaPagoLogic;
   @Inject
   private CtaQueryLogic ctaQueryLogic;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("CTA-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + ctaPagoLogic)
            .cause("Logic: " + ctaQueryLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultFilter<CtaPago> ctaPagoFilter(final CtaPagoFilter ctaPagoFilter) {
      LOGGER.log(Level.INFO, "ctaPagoReload: {0}", ctaPagoFilter);
      ResultFilter<CtaPago> result = ctaQueryLogic.ctaPagoFilter(ctaPagoFilter);
      return result;
   }

   @Override
   public ResultItem<CtaPago> ctaPagoReload(final CtaPago ctaPago) {
      LOGGER.log(Level.INFO, "ctaPagoReload: {0}", ctaPago);
      Long id = ctaPago == null ? null : ctaPago.getIdPago();
      CtaPago ctaPagoReload = ctaQueryLogic.ctaPagoFind(id);
      return new ResultItem<>(ctaPagoReload);
   }

   @Override
   public ResultItem<CtaPago> ctaPagoReloadAll(final CtaPago ctaPago) {
      LOGGER.log(Level.INFO, "ctaPagoReloadAll: {0}", ctaPago);
      Long id = ctaPago == null ? null : ctaPago.getIdPago();
      CtaPago ctaPagoReload = ctaQueryLogic.ctaPagoFind(id);
      if (ctaPagoReload != null) {
         ctaPagoReload.getCtaDepositoList().size();
      }
      return new ResultItem<>(ctaPagoReload);
   }

   private static final Logger LOGGER = Logger.getLogger(CtaPagoImpl.class.getName());
}
