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
import bo.gob.senavex.vortex2.model.CliContacto;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class CliContactoLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public CliContactoLogic() {
   }

   public CliContactoLogic(EntityManager em) {
      this.em = em;
   }

   public Result cliContactoPersist(final CliEmpresa cliEmpresa, final List<CliContacto> cliContactoList) {
      Result result = new Result();
      for (CliContacto cliContacto : cliContactoList) {
         cliContacto.setCliEmpresa(cliEmpresa);
         if (cliContacto.isEsRequerido()) {
            ResultItem<CliContacto> item = cliContactoPersist(cliContacto);
            result.accept(item);
         } else if (cliContacto.getTipo() != null) {
            // persiste pero ignoro los errores
            cliContactoPersist(cliContacto);
         }
         if (result.isError()) {
            break;
         }
      }
      return result;
   }

   public ResultItem<CliContacto> cliContactoPersist(final CliContacto cliContacto) {
      cliContacto.setIdContacto(null);
      cliContacto.setFechaRegistro(new Date());
      ResultItem<CliContacto> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Contacto")
            .validate(cliContacto, "idContacto")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(cliContacto);
      result.setValue(cliContacto);
      result.message("CLI-1001: El Contacto fue registrado correctamente")
            .cause("ID: " + cliContacto.getIdContacto())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliContacto> cliContactoMerge(final CliContacto cliContacto) {
      ResultItem<CliContacto> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Contacto")
            .validate(cliContacto, "idContacto")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CliContacto cliContactoNew = em.merge(cliContacto);
      result.setValue(cliContactoNew);
      result.message("CLI-1001: El Contacto fue actualizado correctamente")
            .cause("ID: " + cliContactoNew.getIdContacto())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliContacto> cliContactoRemove(Long id) {
      CliContacto cliContacto = em.find(CliContacto.class, id);
      return cliContactoRemove(cliContacto);
   }

   public ResultItem<CliContacto> cliContactoRemove(CliContacto cliContacto) {
      ResultItem<CliContacto> result = new ResultItem<>();
      em.remove(cliContacto);
      result.message("CLI-1000: El Contacto fue eliminado correctamente")
            .cause("ID: " + cliContacto.getIdContacto())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}