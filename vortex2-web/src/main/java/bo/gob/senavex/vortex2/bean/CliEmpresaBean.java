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
package bo.gob.senavex.vortex2.bean;

import lombok.*;
import java.util.List;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;
import com.hiska.faces.MessageUtil;
import com.hiska.faces.ViewKeeped;
import bo.gob.senavex.vortex2.serv.CliEmpresaServ;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.filter.CliEmpresaFilter;

@Getter
@ToString
@ManagedBean(name = "_cliEmpresa")
@ViewScoped
@ViewKeeped("/empresa")
public class CliEmpresaBean implements Serializable {
   @Inject
   private CliEmpresaServ cliEmpresaServ;
   private CliEmpresa cliEmpresa;
   private List<CliEmpresa> cliEmpresaList;
   private final CliEmpresaFilter cliEmpresaFilter = new CliEmpresaFilter();

   @PostConstruct
   public void init() {
      cliEmpresaFilterApplyListener();
   }
   // ============== FILTER ACTION ==============

   public boolean cliEmpresaFilterApplyListener() {
      LOGGER.log(Level.INFO, "filter: {0}", cliEmpresaFilter);
      ResultFilter<CliEmpresa> result = cliEmpresaServ.cliEmpresaFilter(cliEmpresaFilter);
      LOGGER.log(Level.INFO, "result: {0}", result);
      MessageUtil.processResult(result);
      cliEmpresaList = result.getValue();
      cliEmpresaFilter.setPagination(result.getPagination());
      return true;
   }

   public boolean cliEmpresaFilterCleanListener() {
      cliEmpresaFilter.clean();
      cliEmpresaFilterApplyListener();
      return true;
   }
   // ============== INBOX ACTION ==============

   public String navegationAction(CliEmpresa row, String outcome) {
      cliEmpresa = row;
      if (row != null) {
         ResultItem<CliEmpresa> result = cliEmpresaServ.cliEmpresaReloadAll(cliEmpresa);
         MessageUtil.processResult(result);
         if (result.isSuccess()) {
            cliEmpresa = result.getValue();
            return outcome;
         }
      }
      return null;
   }
   // ============== SERVICE ACTION ==============

   private static final Logger LOGGER = Logger.getLogger(CliEmpresaBean.class.getName());
}
