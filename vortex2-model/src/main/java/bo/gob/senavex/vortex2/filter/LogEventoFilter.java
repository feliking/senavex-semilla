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
package bo.gob.senavex.vortex2.filter;

import lombok.*;
import java.util.*;
import com.hiska.result.Param;
import com.hiska.result.Filter;
import com.hiska.result.Pagination;
import com.hiska.result.FilterElement;
import bo.gob.senavex.vortex2.FilterAbstract;

@Data
@ToString
public class LogEventoFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idEvento;
   @FilterElement(name = "logSesion.idSesion")
   private Filter<Long> idSesion;
   @FilterElement
   private Filter<Date> fechaEvento;
   @FilterElement
   private Filter<String> operacion;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idEvento = null;
      idSesion = null;
      fechaEvento = null;
      operacion = null;
      text = null;
      paginationClean();
   }

   @Override
   public void defaultSort(Pagination pagination) {
      if (!pagination.hasSort()) {
         pagination.setSort(Pagination.Sort.desc);
         pagination.setAttr("updatedAt");
      }
   }
}
