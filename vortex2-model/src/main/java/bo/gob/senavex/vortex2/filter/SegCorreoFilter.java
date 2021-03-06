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
public class SegCorreoFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idCorreo;
   @FilterElement
   private Filter<String> idReferencia;
   @FilterElement
   private Filter<String> correoDestino;
   @FilterElement
   private Filter<String> correoCopia;
   @FilterElement
   private Filter<String> valorToken;
   @FilterElement
   private Filter<Date> fechaSolicitud;
   @FilterElement
   private Filter<Date> fechaUso;
   @FilterElement
   private Filter<Date> fechaVigencia;
   @FilterElement
   private Filter<Param> tipo;
   @FilterElement
   private Filter<Param> plantilla;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idCorreo = null;
      idReferencia = null;
      correoDestino = null;
      correoCopia = null;
      valorToken = null;
      fechaSolicitud = null;
      fechaUso = null;
      fechaVigencia = null;
      tipo = null;
      plantilla = null;
      estado = null;
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
