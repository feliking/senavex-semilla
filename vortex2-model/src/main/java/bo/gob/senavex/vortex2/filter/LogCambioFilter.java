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
public class LogCambioFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idCambio;
   @FilterElement(name = "logEvento.idEvento")
   private Filter<Long> idEvento;
   @FilterElement
   private Filter<String> nombreTabla;
   @FilterElement
   private Filter<String> nombreColumna;
   @FilterElement
   private Filter<String> valorPrevio;
   @FilterElement
   private Filter<String> valorActual;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idCambio = null;
      idEvento = null;
      nombreTabla = null;
      nombreColumna = null;
      valorPrevio = null;
      valorActual = null;
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
