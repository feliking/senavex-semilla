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
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.filter.CliEmpresaFilter;
import bo.gob.senavex.vortex2.serv.CliEmpresaServ;
import bo.gob.senavex.vortex2.logic.CliEmpresaLogic;
import bo.gob.senavex.vortex2.logic.CliQueryLogic;

@Stateless
@Local(CliEmpresaServ.class)
public class CliEmpresaImpl implements CliEmpresaServ {
   @Inject
   private CliEmpresaLogic cliEmpresaLogic;
   @Inject
   private CliQueryLogic cliQueryLogic;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("CLI-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + cliEmpresaLogic)
            .cause("Logic: " + cliQueryLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultFilter<CliEmpresa> cliEmpresaFilter(final CliEmpresaFilter cliEmpresaFilter) {
      LOGGER.log(Level.INFO, "cliEmpresaReload: {0}", cliEmpresaFilter);
      ResultFilter<CliEmpresa> result = cliQueryLogic.cliEmpresaFilter(cliEmpresaFilter);
      return result;
   }

   @Override
   public ResultItem<CliEmpresa> cliEmpresaReload(final CliEmpresa cliEmpresa) {
      LOGGER.log(Level.INFO, "cliEmpresaReload: {0}", cliEmpresa);
      Long id = cliEmpresa == null ? null : cliEmpresa.getIdEmpresa();
      CliEmpresa cliEmpresaReload = cliQueryLogic.cliEmpresaFind(id);
      return new ResultItem<>(cliEmpresaReload);
   }

   @Override
   public ResultItem<CliEmpresa> cliEmpresaReloadAll(final CliEmpresa cliEmpresa) {
      LOGGER.log(Level.INFO, "cliEmpresaReload: {0}", cliEmpresa);
      Long id = cliEmpresa == null ? null : cliEmpresa.getIdEmpresa();
      CliEmpresa cliEmpresaReload = cliQueryLogic.cliEmpresaFind(id);
      if (cliEmpresaReload != null) {
         cliEmpresaReload.getCliCategoriaList().size();
         cliEmpresaReload.getCliContactoList().size();
         cliEmpresaReload.getCliDireccionList().size();
         cliEmpresaReload.getCliDocumentoList().size();
         cliEmpresaReload.getSegOperadorList().size();
      }
      return new ResultItem<>(cliEmpresaReload);
   }

   private static final Logger LOGGER = Logger.getLogger(CliEmpresaImpl.class.getName());
}
