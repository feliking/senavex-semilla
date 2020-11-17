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
import com.hiska.result.ResultFilter;
import com.hiska.result.filter.FilterBuilder;
import bo.gob.senavex.vortex2.Model;

import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.CliDocumento;
import bo.gob.senavex.vortex2.model.CliDireccion;
import bo.gob.senavex.vortex2.model.CliContacto;
import bo.gob.senavex.vortex2.model.CliCategoria;

@Stateless
@LocalBean
@lombok.Getter
public class CliQueryLogic {
   @PersistenceContext(unitName = Model.CLI_PERSIST)
   private EntityManager em;

   public CliQueryLogic() {
   }

   public CliQueryLogic(EntityManager em) {
      this.em = em;
   }

   public ResultFilter<CliEmpresa> cliEmpresaFilter(Object filter) {
      return FilterBuilder.create(CliEmpresa.class, filter).getResultFilter(em);
   }

   public CliEmpresa cliEmpresaFind(Long id) {
      return em.find(CliEmpresa.class, id);
   }

   public ResultFilter<CliDocumento> cliDocumentoFilter(Object filter) {
      return FilterBuilder.create(CliDocumento.class, filter).getResultFilter(em);
   }

   public CliDocumento cliDocumentoFind(Long id) {
      return em.find(CliDocumento.class, id);
   }

   public ResultFilter<CliDireccion> cliDireccionFilter(Object filter) {
      return FilterBuilder.create(CliDireccion.class, filter).getResultFilter(em);
   }

   public CliDireccion cliDireccionFind(Long id) {
      return em.find(CliDireccion.class, id);
   }

   public ResultFilter<CliContacto> cliContactoFilter(Object filter) {
      return FilterBuilder.create(CliContacto.class, filter).getResultFilter(em);
   }

   public CliContacto cliContactoFind(Long id) {
      return em.find(CliContacto.class, id);
   }

   public ResultFilter<CliCategoria> cliCategoriaFilter(Object filter) {
      return FilterBuilder.create(CliCategoria.class, filter).getResultFilter(em);
   }

   public CliCategoria cliCategoriaFind(Long id) {
      return em.find(CliCategoria.class, id);
   }
}
