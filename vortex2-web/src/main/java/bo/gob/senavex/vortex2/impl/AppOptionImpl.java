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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import com.hiska.result.Option;
import com.hiska.result.Result;
import com.hiska.result.ResultBuilder;
import com.hiska.result.ResultList;
import bo.gob.senavex.vortex2.serv.*;
import bo.gob.senavex.vortex2.logic.OptionLogic;

@Stateless
@Local(AppOptionServ.class)
public class AppOptionImpl implements AppOptionServ {
   @Inject
   private OptionLogic optionLogic;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("PAR-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + optionLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultList<Option> optionList(String domain, String classifier) {
      LOGGER.log(Level.INFO, "optionList: {0}:{1}", new Object[]{domain, classifier});
      List<Option> list = optionLogic.optionSimple(domain, classifier);
      return new ResultList<>(list);
   }

   @Override
   public ResultList<Option> optionList(String domain, String classifier, String parent) {
      LOGGER.log(Level.INFO, "optionList: {0}:{1}-{2}", new Object[]{domain, classifier, parent});
      List<Option> list = optionLogic.optionSimple(domain, classifier, parent);
      return new ResultList<>(list);
   }

   @Override
   public ResultList<Option> optionTree(String domain, String classifier) {
      LOGGER.log(Level.INFO, "optionTree: {0}:{1}", new Object[]{domain, classifier});
      List<Option> list = optionLogic.optionTree(domain, classifier);
      return new ResultList<>(list);
   }

   private static final Logger LOGGER = Logger.getLogger(AppOptionImpl.class.getName());
}
