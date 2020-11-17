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
import bo.gob.senavex.vortex2.model.CliCategoria;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import com.hiska.result.Param;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class CliCategoriaLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public CliCategoriaLogic() {
   }

   public CliCategoriaLogic(EntityManager em) {
      this.em = em;
   }

   public Result cliCategoriaPersist(final CliEmpresa cliEmpresa, final List<CliCategoria> cliCategoriaList) {
      Result result = new Result();
      for (CliCategoria cliCategoria : cliCategoriaList) {
         cliCategoria.setCliEmpresa(cliEmpresa);
         ResultItem<CliCategoria> item = cliCategoriaPersist(cliCategoria);
         result.accept(item);
         if (result.isError()) {
            break;
         }
      }
      return result;
   }

   public ResultItem<CliCategoria> cliCategoriaPersist(final CliCategoria cliCategoria) {
      cliCategoria.setIdCategoria(null);
      cliCategoria.setCategoria(Param.create("NONE"));
      cliCategoria.setFechaRegistro(new Date());
      ResultItem<CliCategoria> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Categoria")
            .validate(cliCategoria, "idCategoria")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(cliCategoria);
      result.setValue(cliCategoria);
      result.message("CLI-1001: El Categoria fue registrado correctamente")
            .cause("ID: " + cliCategoria.getIdCategoria())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliCategoria> cliCategoriaMerge(final CliCategoria cliCategoria) {
      ResultItem<CliCategoria> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Categoria")
            .validate(cliCategoria, "idCategoria")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CliCategoria cliCategoriaNew = em.merge(cliCategoria);
      result.setValue(cliCategoriaNew);
      result.message("CLI-1001: El Categoria fue actualizado correctamente")
            .cause("ID: " + cliCategoriaNew.getIdCategoria())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliCategoria> cliCategoriaRemove(Long id) {
      CliCategoria cliCategoria = em.find(CliCategoria.class, id);
      return cliCategoriaRemove(cliCategoria);
   }

   public ResultItem<CliCategoria> cliCategoriaRemove(CliCategoria cliCategoria) {
      ResultItem<CliCategoria> result = new ResultItem<>();
      em.remove(cliCategoria);
      result.message("CLI-1000: El Categoria fue eliminado correctamente")
            .cause("ID: " + cliCategoria.getIdCategoria())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}