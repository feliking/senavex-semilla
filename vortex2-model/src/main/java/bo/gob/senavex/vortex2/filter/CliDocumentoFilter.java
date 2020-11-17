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
public class CliDocumentoFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idDocumento;
   @FilterElement(name = "cliEmpresa.idEmpresa")
   private Filter<Long> idEmpresa;
   @FilterElement
   private Filter<String> nombre;
   @FilterElement
   private Filter<String> numero;
   @FilterElement
   private Filter<Date> vigenciaDesde;
   @FilterElement
   private Filter<Date> vigenciaHasta;
   @FilterElement
   private Filter<String> observacion;
   @FilterElement
   private Filter<Date> fechaRegistro;
   @FilterElement
   private Filter<Date> fechaVerificacion;
   @FilterElement
   private Filter<String> descripcion;
   @FilterElement
   private Filter<Param> tipo;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idDocumento = null;
      idEmpresa = null;
      nombre = null;
      numero = null;
      vigenciaDesde = null;
      vigenciaHasta = null;
      observacion = null;
      fechaRegistro = null;
      fechaVerificacion = null;
      descripcion = null;
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
