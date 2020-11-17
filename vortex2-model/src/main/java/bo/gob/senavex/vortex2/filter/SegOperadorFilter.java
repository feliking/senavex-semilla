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
public class SegOperadorFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idOperador;
   @FilterElement(name = "cliEmpresa.idEmpresa")
   private Filter<Long> idEmpresa;
   @FilterElement(name = "cliEmpresa.refKey")
   private Filter<String> cliEmpresaRefKey;
   @FilterElement(name = "cliEmpresa.refName")
   private Filter<String> cliEmpresaRefName;
   @FilterElement(name = "segUsuario.idUsuario")
   private Filter<Long> idUsuario;;
   @FilterElement(name = {"segUsuario.segPersona.nombres", "segUsuario.segPersona.primerApellido", "segUsuario.segPersona.segundoApellido"})
   private Filter<String> nombreCompleto;
   @FilterElement
   private Filter<String> nombreCargo;
   @FilterElement(name = {"correoElectronico", "segUsuario.correoElectronico"})
   private Filter<String> correoElectronico;
   @FilterElement(name = {"numeroDocumento", "segUsuario.segPersona.numeroDocumento"})
   private Filter<String> numeroDocumento;
   @FilterElement
   private Filter<Date> fechaDesde;
   @FilterElement
   private Filter<Date> fechaHasta;
   @FilterElement
   private Filter<Date> fechaRespuesta;
   @FilterElement
   private Filter<Date> fechaSolicitud;
   @FilterElement
   private Filter<Param> perfil;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idOperador = null;
      idEmpresa = null;
      cliEmpresaRefKey = null;
      cliEmpresaRefName = null;
      idUsuario = null;
      nombreCompleto = null;
      nombreCargo = null;
      correoElectronico = null;
      numeroDocumento = null;
      fechaDesde = null;
      fechaHasta = null;
      fechaRespuesta = null;
      fechaSolicitud = null;
      perfil = null;
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
