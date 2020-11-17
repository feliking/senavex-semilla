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
import bo.gob.senavex.vortex2.model.CliEmpresa;

@Stateless
@LocalBean
public class CliEmpresaLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.CLI_PERSIST)
   private EntityManager em;

   public CliEmpresaLogic() {
   }

   public CliEmpresaLogic(EntityManager em) {
      this.em = em;
   }

   public ResultItem<CliEmpresa> cliEmpresaPersist(final CliEmpresa cliEmpresa) {
      cliEmpresa.setIdEmpresa(null);
      ResultItem<CliEmpresa> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Empresa")
            .validate(cliEmpresa, "idEmpresa")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(cliEmpresa);
      result.setValue(cliEmpresa);
      result.message("CLI-1001: El Empresa fue registrado correctamente")
            .cause("ID: " + cliEmpresa.getIdEmpresa())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliEmpresa> cliEmpresaMerge(final CliEmpresa cliEmpresa) {
      ResultItem<CliEmpresa> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Empresa")
            .validate(cliEmpresa, "idEmpresa")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CliEmpresa cliEmpresaNew = em.merge(cliEmpresa);
      result.setValue(cliEmpresaNew);
      result.message("CLI-1001: El Empresa fue actualizado correctamente")
            .cause("ID: " + cliEmpresaNew.getIdEmpresa())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliEmpresa> cliEmpresaRemove(Long id) {
      CliEmpresa cliEmpresa = em.find(CliEmpresa.class, id);
      return cliEmpresaRemove(cliEmpresa);
   }

   public ResultItem<CliEmpresa> cliEmpresaRemove(CliEmpresa cliEmpresa) {
      ResultItem<CliEmpresa> result = new ResultItem<>();
      em.remove(cliEmpresa);
      result.message("CLI-1000: El Empresa fue eliminado correctamente")
            .cause("ID: " + cliEmpresa.getIdEmpresa())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}
