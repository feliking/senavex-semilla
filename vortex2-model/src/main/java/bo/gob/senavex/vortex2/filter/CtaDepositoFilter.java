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
public class CtaDepositoFilter extends FilterAbstract {
   @FilterElement
   private Filter<Long> idDeposito;
   @FilterElement(name = "ctaPago.idPago")
   private Filter<Long> idPago;
   @FilterElement
   private Filter<String> comprobante;
   @FilterElement
   private Filter<Double> monto;
   @FilterElement
   private Filter<Date> fechaRegistro;
   @FilterElement
   private Filter<Date> fechaDeposito;
   @FilterElement
   private Filter<Date> fechaVerificacion;
   @FilterElement
   private Filter<String> descripcion;
   @FilterElement
   private Filter<Param> cuenta;
   @FilterElement
   private Filter<Param> moneda;
   @FilterElement
   private Filter<Param> estado;
   @FilterElement(name = {})
   private Filter<String> text;

   public void clean() {
      idDeposito = null;
      idPago = null;
      comprobante = null;
      monto = null;
      fechaRegistro = null;
      fechaDeposito = null;
      fechaVerificacion = null;
      descripcion = null;
      cuenta = null;
      moneda = null;
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
