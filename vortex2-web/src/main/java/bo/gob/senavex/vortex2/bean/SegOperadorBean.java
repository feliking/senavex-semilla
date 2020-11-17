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
import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;
import com.hiska.result.Result;
import com.hiska.result.Filter;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;
import com.hiska.faces.MessageUtil;
import com.hiska.faces.ViewKeeped;
import bo.gob.senavex.vortex2.serv.SegOperadorServ;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.filter.SegOperadorFilter;

@Getter
@ToString
@ManagedBean(name = "_segOperador")
@ViewScoped
@ViewKeeped("/operador")
public class SegOperadorBean implements Serializable {
   @Inject
   private SegOperadorServ segOperadorServ;
   @Setter
   @ManagedProperty("#{_acceso.config}")
   private ConfigWrap config;
   private SegOperador segOperador;
   private List<SegOperador> segOperadorList;
   private final SegOperadorFilter segOperadorFilter = new SegOperadorFilter();

   @PostConstruct
   public void init() {
      segOperadorFilterApplyListener();
   }
   // ============== FILTER ACTION ==============

   public boolean segOperadorFilterApplyListener() {
      if (config.isUsuarioExterno()) {
         segOperadorFilter.setIdEmpresa(Filter.create(config.getIdEmpresas()));
      }
      LOGGER.log(Level.INFO, "filter: {0}", segOperadorFilter);
      ResultFilter<SegOperador> result = segOperadorServ.segOperadorFilter(segOperadorFilter);
      LOGGER.log(Level.INFO, "result: {0}", result);
      MessageUtil.processResult(result);
      segOperadorList = result.getValue();
      segOperadorFilter.setPagination(result.getPagination());
      return true;
   }

   public boolean segOperadorFilterCleanListener() {
      segOperadorFilter.clean();
      segOperadorFilterApplyListener();
      return true;
   }
   // ============== INBOX ACTION ==============

   public String segOperadorCreateAction() {
      segOperador = new SegOperador();
      return "create";
   }

   public String navegationAction(SegOperador row, String outcome) {
      segOperador = row;
      return outcome;
   }
   // ============== SERVICE ACTION ==============

   public String segOperadorPersistAction() {
      LOGGER.log(Level.INFO, "value: {0}", segOperador);
      ResultItem<SegOperador> result = segOperadorServ.segOperadorPersist(segOperador);
      LOGGER.log(Level.INFO, "result: {0}", result);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         segOperador = null;
         segOperadorFilter.paginationReload();
         segOperadorFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String aceptarInvitacionTokenAction(String id) {
      Result result = segOperadorServ.aceptarInvitacionToken(id);
      MessageUtil.processResult(result);
      return "home";
   }

   public String activarInvitacionAction() {
      Result result = segOperadorServ.activarInvitacion(segOperador);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         segOperador = null;
         segOperadorFilter.paginationReload();
         segOperadorFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String rechazarInvitacionAction() {
      ResultItem<SegOperador> result = segOperadorServ.rechazarInvitacion(segOperador);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         segOperador = null;
         segOperadorFilter.paginationReload();
         segOperadorFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String inactiverOperadorAction() {
      LOGGER.log(Level.INFO, "value: {0}", segOperador);
      Result result = segOperadorServ.inactiverOperador(segOperador);
      LOGGER.log(Level.INFO, "result: {0}", result);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         segOperador = null;
         segOperadorFilter.paginationReload();
         segOperadorFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   private static final Logger LOGGER = Logger.getLogger(SegOperadorBean.class.getName());
}
