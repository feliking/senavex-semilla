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
import com.hiska.result.Result;
import com.hiska.result.ResultItem;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.CliDireccion;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class CliDireccionLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public CliDireccionLogic() {
   }

   public CliDireccionLogic(EntityManager em) {
      this.em = em;
   }

   public Result cliDireccionPersist(final CliEmpresa cliEmpresa, final List<CliDireccion> cliDireccionList) {
      Result result = new Result();
      for (CliDireccion cliDireccion : cliDireccionList) {
         cliDireccion.setCliEmpresa(cliEmpresa);
         if (cliDireccion.isEsRequerido()) {
            ResultItem<CliDireccion> item = cliDireccionPersist(cliDireccion);
            result.accept(item);
         } else {
            Result ignore = cliDireccionPersist(cliDireccion);
            ignore.log("Ingnore");
         }
         if (result.isError()) {
            break;
         }
      }
      return result;
   }

   public ResultItem<CliDireccion> cliDireccionPersist(final CliDireccion cliDireccion) {
      cliDireccion.setIdDireccion(null);
      cliDireccion.setFechaRegistro(new Date());
      ResultItem<CliDireccion> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Direccion")
            .validate(cliDireccion, "idDireccion")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(cliDireccion);
      result.setValue(cliDireccion);
      result.message("CLI-1001: El Direccion fue registrado correctamente")
            .cause("ID: " + cliDireccion.getIdDireccion())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliDireccion> cliDireccionMerge(final CliDireccion cliDireccion) {
      ResultItem<CliDireccion> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Direccion")
            .validate(cliDireccion, "idDireccion")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CliDireccion cliDireccionNew = em.merge(cliDireccion);
      result.setValue(cliDireccionNew);
      result.message("CLI-1001: El Direccion fue actualizado correctamente")
            .cause("ID: " + cliDireccionNew.getIdDireccion())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliDireccion> cliDireccionRemove(Long id) {
      CliDireccion cliDireccion = em.find(CliDireccion.class, id);
      return cliDireccionRemove(cliDireccion);
   }

   public ResultItem<CliDireccion> cliDireccionRemove(CliDireccion cliDireccion) {
      ResultItem<CliDireccion> result = new ResultItem<>();
      em.remove(cliDireccion);
      result.message("CLI-1000: El Direccion fue eliminado correctamente")
            .cause("ID: " + cliDireccion.getIdDireccion())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}