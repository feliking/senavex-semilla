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
public class SegPersonaFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idPersona;
   @FilterElement
   private Filter<String> nombres;
   @FilterElement
   private Filter<String> primerApellido;
   @FilterElement
   private Filter<String> segundoApellido;
   @FilterElement
   private Filter<Date> fechaNacimiento;
   @FilterElement
   private Filter<String> numeroDoc;
   @FilterElement
   private Filter<String> complementoDoc;
   @FilterElement
   private Filter<String> direccion;
   @FilterElement
   private Filter<String> numeroCelular;
   @FilterElement
   private Filter<String> numeroTelefonico;
   @FilterElement
   private Filter<Param> genero;
   @FilterElement
   private Filter<Param> tipoDoc;
   @FilterElement
   private Filter<Param> expedicionDoc;
   @FilterElement
   private Filter<Param> pais;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idPersona = null;
      nombres = null;
      primerApellido = null;
      segundoApellido = null;
      fechaNacimiento = null;
      numeroDoc = null;
      complementoDoc = null;
      direccion = null;
      numeroCelular = null;
      numeroTelefonico = null;
      genero = null;
      tipoDoc = null;
      expedicionDoc = null;
      pais = null;
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
