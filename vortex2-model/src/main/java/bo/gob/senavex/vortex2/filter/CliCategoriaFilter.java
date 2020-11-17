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
public class CliCategoriaFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idCategoria;
   @FilterElement(name = "cliEmpresa.idEmpresa")
   private Filter<Long> idEmpresa;
   @FilterElement
   private Filter<Date> fechaRegistro;
   @FilterElement
   private Filter<Param> categoria;
   @FilterElement
   private Filter<Param> nroEmpleados;
   @FilterElement
   private Filter<Param> activosUfv;
   @FilterElement
   private Filter<Param> ventasUfv;
   @FilterElement
   private Filter<Param> exportacionesUfv;
   @FilterElement
   private Filter<Param> importacionesUfv;
   @FilterElement
   private Filter<Param> exportacionesRubros;
   @FilterElement
   private Filter<Param> importacionesRubros;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idCategoria = null;
      idEmpresa = null;
      fechaRegistro = null;
      categoria = null;
      nroEmpleados = null;
      activosUfv = null;
      ventasUfv = null;
      exportacionesUfv = null;
      importacionesUfv = null;
      exportacionesRubros = null;
      importacionesRubros = null;
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
