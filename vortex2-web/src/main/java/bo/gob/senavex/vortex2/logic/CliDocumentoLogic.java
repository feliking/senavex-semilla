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
import bo.gob.senavex.vortex2.model.CliDocumento;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class CliDocumentoLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public CliDocumentoLogic() {
   }

   public CliDocumentoLogic(EntityManager em) {
      this.em = em;
   }

   public Result cliDocumentoPersist(final CliEmpresa cliEmpresa, final List<CliDocumento> cliDocumentoList) {
      Result result = new Result();
      for (CliDocumento cliDocumento : cliDocumentoList) {
         cliDocumento.setCliEmpresa(cliEmpresa);
         if (cliDocumento.isEsRequerido()) {
            ResultItem<CliDocumento> item = cliDocumentoPersist(cliDocumento);
            result.accept(item);
         } else if (cliDocumento.getNumero() != null) {
            // persiste pero ignoro los errores
            cliDocumentoPersist(cliDocumento);
            // ResultItem<CliDocumento> item =
            // cliDocumentoPersist(cliDocumento);
            // result.accept(item);
         }
         if (result.isError()) {
            break;
         }
      }
      return result;
   }

   public ResultItem<CliDocumento> cliDocumentoPersist(final CliDocumento cliDocumento) {
      cliDocumento.setIdDocumento(null);
      ResultItem<CliDocumento> result = new ResultItem<>();
      cliDocumento.setFechaRegistro(new Date());
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Documento")
            .validate(cliDocumento, "idDocumento")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(cliDocumento);
      result.setValue(cliDocumento);
      result.message("CLI-1001: El Documento fue registrado correctamente")
            .cause("ID: " + cliDocumento.getIdDocumento())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliDocumento> cliDocumentoMerge(final CliDocumento cliDocumento) {
      ResultItem<CliDocumento> result = new ResultItem<>();
      Message message = MessageBuilder.create("CLI-2001: Validacion Registro Documento")
            .validate(cliDocumento, "idDocumento")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      CliDocumento cliDocumentoNew = em.merge(cliDocumento);
      result.setValue(cliDocumentoNew);
      result.message("CLI-1001: El Documento fue actualizado correctamente")
            .cause("ID: " + cliDocumentoNew.getIdDocumento())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }

   public ResultItem<CliDocumento> cliDocumentoRemove(Long id) {
      CliDocumento cliDocumento = em.find(CliDocumento.class, id);
      return cliDocumentoRemove(cliDocumento);
   }

   public ResultItem<CliDocumento> cliDocumentoRemove(CliDocumento cliDocumento) {
      ResultItem<CliDocumento> result = new ResultItem<>();
      em.remove(cliDocumento);
      result.message("CLI-1000: El Documento fue eliminado correctamente")
            .cause("ID: " + cliDocumento.getIdDocumento())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}