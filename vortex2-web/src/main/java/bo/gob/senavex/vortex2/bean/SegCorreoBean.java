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
import bo.gob.senavex.vortex2.serv.SegCorreoServ;
import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.filter.SegCorreoFilter;

@Getter
@ToString
@ManagedBean(name = "_segCorreo")
@ViewScoped
@ViewKeeped("/correo")
public class SegCorreoBean implements Serializable {
   @Inject
   private SegCorreoServ segCorreoServ;
   private SegCorreo segCorreo;
   private List<SegCorreo> segCorreoList;
   private final SegCorreoFilter segCorreoFilter = new SegCorreoFilter();

   @PostConstruct
   public void init() {
      segCorreoFilterApplyListener();
   }
   // ============== FILTER ACTION ==============

   public boolean segCorreoFilterApplyListener() {
      LOGGER.log(Level.INFO, "filter: {0}", segCorreoFilter);
      ResultFilter<SegCorreo> result = segCorreoServ.segCorreoFilter(segCorreoFilter);
      LOGGER.log(Level.INFO, "result: {0}", result);
      MessageUtil.processResult(result);
      segCorreoList = result.getValue();
      segCorreoFilter.setPagination(result.getPagination());
      return true;
   }

   public boolean segCorreoFilterCleanListener() {
      segCorreoFilter.clean();
      segCorreoFilterApplyListener();
      return true;
   }
   // ============== INBOX ACTION ==============

   public String navegationAction(SegCorreo row, String outcome) {
      segCorreo = row;
      return outcome;
   }
   // ============== SERVICE ACTION ==============

   public String renviarCorreoAction() {
      ResultItem<SegCorreo> result = segCorreoServ.renviarCorreo(segCorreo);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         segCorreo = null;
         segCorreoFilter.paginationReload();
         segCorreoFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   private static final Logger LOGGER = Logger.getLogger(SegCorreoBean.class.getName());
}