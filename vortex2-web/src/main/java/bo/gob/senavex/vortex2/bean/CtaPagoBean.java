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

import bo.gob.senavex.vortex2.data.ConfigWrap;
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
import com.hiska.faces.MessageUtil;
import com.hiska.faces.ViewKeeped;
import bo.gob.senavex.vortex2.serv.CtaPagoServ;
import bo.gob.senavex.vortex2.model.CtaPago;
import bo.gob.senavex.vortex2.filter.CtaPagoFilter;
import com.hiska.result.Filter;
import com.hiska.result.ResultItem;
import javax.faces.bean.ManagedProperty;

@Getter
@ToString
@ManagedBean(name = "_ctaPago")
@ViewScoped
@ViewKeeped("/pago")
public class CtaPagoBean implements Serializable {
   @Setter
   @ManagedProperty("#{_acceso.config}")
   private ConfigWrap config;
   @Inject
   private CtaPagoServ ctaPagoServ;
   private CtaPago ctaPago;
   private List<CtaPago> ctaPagoList;
   private final CtaPagoFilter ctaPagoFilter = new CtaPagoFilter();

   @PostConstruct
   public void init() {
      ctaPagoFilterApplyListener();
   }
   // ============== FILTER ACTION ==============

   public boolean ctaPagoFilterApplyListener() {
      if (config.isUsuarioExterno()) {
         ctaPagoFilter.setIdEmpresa(Filter.create(config.getIdEmpresas()));
      }
      LOGGER.log(Level.INFO, "filter: {0}", ctaPagoFilter);
      ResultFilter<CtaPago> result = ctaPagoServ.ctaPagoFilter(ctaPagoFilter);
      LOGGER.log(Level.INFO, "result: {0}", result);
      MessageUtil.processResult(result);
      ctaPagoList = result.getValue();
      ctaPagoFilter.setPagination(result.getPagination());
      return true;
   }

   public boolean ctaPagoFilterCleanListener() {
      ctaPagoFilter.clean();
      ctaPagoFilterApplyListener();
      return true;
   }
   // ============== INBOX ACTION ==============

   public String navegationAction(CtaPago row, String outcome) {
      if (row != null) {
         ResultItem<CtaPago> result = ctaPagoServ.ctaPagoReloadAll(row);
         MessageUtil.processResult(result);
         if (result.isSuccess()) {
            ctaPago = result.getValue();
         }
      }
      return outcome;
   }
   // ============== SERVICE ACTION ==============

   private static final Logger LOGGER = Logger.getLogger(CtaPagoBean.class.getName());
}
