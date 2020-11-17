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
import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.filter.SegCorreoFilter;
import bo.gob.senavex.vortex2.serv.SegCorreoServ;
import bo.gob.senavex.vortex2.logic.SegCorreoLogic;
import bo.gob.senavex.vortex2.logic.SegQueryLogic;
import com.hiska.result.Param;

@Stateless
@Local(SegCorreoServ.class)
public class SegCorreoImpl implements SegCorreoServ {
   @Inject
   private SegCorreoLogic segCorreoLogic;
   @Inject
   private SegQueryLogic segQueryLogic;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("SEG-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + segCorreoLogic)
            .cause("Logic: " + segQueryLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultFilter<SegCorreo> segCorreoFilter(final SegCorreoFilter segCorreoFilter) {
      LOGGER.log(Level.INFO, "segCorreoReload: {0}", segCorreoFilter);
      ResultFilter<SegCorreo> result = segQueryLogic.segCorreoFilter(segCorreoFilter);
      return result;
   }

   @Override
   public ResultItem<SegCorreo> renviarCorreo(final SegCorreo segCorreo) {
      LOGGER.log(Level.INFO, "segCorreoMerge: {0}", segCorreo);
      segCorreo.setEstado(Param.create("REN"));
      ResultItem<SegCorreo> result = segCorreoLogic.segCorreoMerge(segCorreo);
      return result;
   }

   @Override
   public ResultItem<String> assertValorToken(final String valorToken) {
      LOGGER.log(Level.INFO, "assertValorToken: {0}", valorToken);
      return segCorreoLogic.assertValorToken(valorToken);
   }

   @Override
   public Result removeValorToken(final String valorToken) {
      LOGGER.log(Level.INFO, "removeValorToken: {0}", valorToken);
      return segCorreoLogic.segCorreoTokenDelete(valorToken);
   }

   private static final Logger LOGGER = Logger.getLogger(SegCorreoImpl.class.getName());
}