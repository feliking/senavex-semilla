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
public class RegSeguimientoFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idSeguimiento;
   @FilterElement(name = "segUsuario.idUsuario")
   private Filter<Long> idUsuario;
   @FilterElement
   private Filter<String> idReferencia;
   @FilterElement
   private Filter<Date> fechaAsignacion;
   @FilterElement
   private Filter<Date> fechaRespuesta;
   @FilterElement
   private Filter<Date> fechaCierre;
   @FilterElement
   private Filter<String> referencia;
   @FilterElement
   private Filter<Param> tipo;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idSeguimiento = null;
      idUsuario = null;
      idReferencia = null;
      fechaAsignacion = null;
      fechaRespuesta = null;
      fechaCierre = null;
      referencia = null;
      tipo = null;
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
