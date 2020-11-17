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
public class CliDireccionFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idDireccion;
   @FilterElement(name = "cliEmpresa.idEmpresa")
   private Filter<Long> idEmpresa;
   @FilterElement
   private Filter<String> nombre;
   @FilterElement
   private Filter<String> avenida;
   @FilterElement
   private Filter<String> calles;
   @FilterElement
   private Filter<String> domicilio;
   @FilterElement
   private Filter<String> edificio;
   @FilterElement
   private Filter<String> oficina;
   @FilterElement
   private Filter<String> piso;
   @FilterElement
   private Filter<Date> fechaRegistro;
   @FilterElement
   private Filter<Date> fechaFin;
   @FilterElement
   private Filter<Date> fechaInicio;
   @FilterElement
   private Filter<String> geoReferencia;
   @FilterElement
   private Filter<String> referencia;
   @FilterElement
   private Filter<Param> tipo;
   @FilterElement
   private Filter<Param> departamento;
   @FilterElement
   private Filter<Param> municipio;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idDireccion = null;
      idEmpresa = null;
      nombre = null;
      avenida = null;
      calles = null;
      domicilio = null;
      edificio = null;
      oficina = null;
      piso = null;
      fechaRegistro = null;
      fechaFin = null;
      fechaInicio = null;
      geoReferencia = null;
      referencia = null;
      tipo = null;
      departamento = null;
      municipio = null;
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
