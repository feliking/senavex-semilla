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
public class RegRegistroFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idRegistro;
   @FilterElement(name = "cliEmpresa.idEmpresa")
   private Filter<Long> idEmpresa;
   @FilterElement(name = "cliEmpresa.refKey")
   private Filter<String> refKey;
   @FilterElement(name = "cliEmpresa.refName")
   private Filter<String> refName;
   @FilterElement(name = "ctaPago.idPago")
   private Filter<Long> idPago;
   @FilterElement
   private Filter<String> codigo;
   @FilterElement
   private Filter<Date> fechaSolicitud;
   @FilterElement
   private Filter<Date> fechaEmision;
   @FilterElement
   private Filter<Date> fechaVencimiento;
   @FilterElement
   private Filter<String> descripcion;
   @FilterElement
   private Filter<Param> origen;
   @FilterElement
   private Filter<Param> tipo;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idRegistro = null;
      idEmpresa = null;
      refKey = null;
      refName = null;
      idPago = null;
      codigo = null;
      fechaSolicitud = null;
      fechaEmision = null;
      fechaVencimiento = null;
      descripcion = null;
      origen = null;
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
