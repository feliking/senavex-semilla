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
public class CliEmpresaFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idEmpresa;
   @FilterElement
   private Filter<String> fechaFundacion;
   @FilterElement
   private Filter<String> fechaOperacion;
   @FilterElement
   private Filter<String> descripcion;
   @FilterElement
   private Filter<String> refKey;
   @FilterElement
   private Filter<String> refName;
   @FilterElement
   private Filter<Param> actividad;
   @FilterElement
   private Filter<Param> tipo;
   @FilterElement
   private Filter<Param> afiliaciones;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idEmpresa = null;
      fechaFundacion = null;
      fechaOperacion = null;
      descripcion = null;
      refKey = null;
      refName = null;
      actividad = null;
      tipo = null;
      afiliaciones = null;
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
