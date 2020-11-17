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

import bo.gob.senavex.vortex2.model.RegSeguimiento;
import bo.gob.senavex.vortex2.model.RegRegistro;

@Stateless
@LocalBean
@lombok.Getter
public class RegQueryLogic {
   @PersistenceContext(unitName = Model.REG_PERSIST)
   private EntityManager em;

   public RegQueryLogic() {
   }

   public RegQueryLogic(EntityManager em) {
      this.em = em;
   }

   public ResultFilter<RegSeguimiento> regSeguimientoFilter(Object filter) {
      return FilterBuilder.create(RegSeguimiento.class, filter).getResultFilter(em);
   }

   public RegSeguimiento regSeguimientoFind(Long id) {
      return em.find(RegSeguimiento.class, id);
   }

   public ResultFilter<RegRegistro> regRegistroFilter(Object filter) {
      return FilterBuilder.create(RegRegistro.class, filter).getResultFilter(em);
   }

   public RegRegistro regRegistroFind(Long id) {
      return em.find(RegRegistro.class, id);
   }
}