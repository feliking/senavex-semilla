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
package bo.gob.senavex.vortex2.logic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.hiska.result.ResultItem;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.RegRegistro;
import com.hiska.result.Param;
import java.util.Date;

@Stateless
@LocalBean
public class RegRegistroLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public RegRegistroLogic() {
   }

   public RegRegistroLogic(EntityManager em) {
      this.em = em;
   }

   public ResultItem<RegRegistro> regRegistroPersist(final RegRegistro regRegistro) {
      regRegistro.setIdRegistro(null);
      regRegistro.setFechaSolicitud(new Date());
      ResultItem<RegRegistro> result = new ResultItem<>();
      Message message = MessageBuilder.create("REG-2001: Validacion Registro Registro")
            .validate(regRegistro, "idRegistro")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(regRegistro);
      result.setValue(regRegistro);
      result.message("REG-1001: El Registro fue registrado correctamente")
            .cause("ID: " + regRegistro.getIdRegistro())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<RegRegistro> regRegistroMerge(final RegRegistro regRegistro) {
      ResultItem<RegRegistro> result = new ResultItem<>();
      Message message = MessageBuilder.create("REG-2001: Validacion Registro Registro")
            .validate(regRegistro, "idRegistro")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      RegRegistro regRegistroNew = em.merge(regRegistro);
      result.setValue(regRegistroNew);
      result.message("REG-1001: El Registro fue actualizado correctamente")
            .cause("ID: " + regRegistroNew.getIdRegistro())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<RegRegistro> regRegistroCancelar(Long id) {
      RegRegistro regRegistro = em.find(RegRegistro.class, id);
      return regRegistroCancelar(regRegistro);
   }

   public ResultItem<RegRegistro> regRegistroCancelar(RegRegistro regRegistro) {
      ResultItem<RegRegistro> result = assertEstadoValido(regRegistro, "REG");
      regRegistro.setEstado(Param.create("CAN"));
      em.merge(regRegistro);
      result.message("REG-1000: El Registro fue cancelado")
            .cause("ID: " + regRegistro.getIdRegistro())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<RegRegistro> assertEstadoValido(RegRegistro regRegistro, String estado) {
      ResultItem<RegRegistro> result = new ResultItem<>();
      Param param = regRegistro.getEstado();
      if (!Param.isEquals(param, estado)) {
         result.message("REG-2001: Estado del registro no valido")
               .cause("Estado del registro: " + param)
               .cause("Estado requerido: " + estado);
         result.setSuccess(false);
      }
      return result;
   }

   public ResultItem<RegRegistro> assertEstadosValidos(RegRegistro regRegistro, String... estados) {
      ResultItem<RegRegistro> result = new ResultItem<>();
      Param param = regRegistro.getEstado();
      if (Param.isIn(param, estados)) {
         return result;
      }
      MessageBuilder mb = result.message("REG-2001: Estado del registro no valido")
            .cause("Estado del registro: " + param);
      for (String estado : estados) {
         mb.cause("Estado aceptado: " + estado);
      }
      result.setSuccess(false);
      return result;
   }
}
