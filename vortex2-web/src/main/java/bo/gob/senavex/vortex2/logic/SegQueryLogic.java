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

import bo.gob.senavex.vortex2.model.SegUsuario;
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.model.SegCorreo;

@Stateless
@LocalBean
@lombok.Getter
public class SegQueryLogic {
   @PersistenceContext(unitName = Model.SEG_PERSIST)
   private EntityManager em;

   public SegQueryLogic() {
   }

   public SegQueryLogic(EntityManager em) {
      this.em = em;
   }

   public ResultFilter<SegUsuario> segUsuarioFilter(Object filter) {
      return FilterBuilder.create(SegUsuario.class, filter).getResultFilter(em);
   }

   public SegUsuario segUsuarioFind(Long id) {
      return em.find(SegUsuario.class, id);
   }

   public ResultFilter<SegPersona> segPersonaFilter(Object filter) {
      return FilterBuilder.create(SegPersona.class, filter).getResultFilter(em);
   }

   public SegPersona segPersonaFind(Long id) {
      return em.find(SegPersona.class, id);
   }

   public ResultFilter<SegOperador> segOperadorFilter(Object filter) {
      return FilterBuilder.create(SegOperador.class, filter).getResultFilter(em);
   }

   public SegOperador segOperadorFind(Long id) {
      return em.find(SegOperador.class, id);
   }

   public ResultFilter<SegCorreo> segCorreoFilter(Object filter) {
      return FilterBuilder.create(SegCorreo.class, filter).getResultFilter(em);
   }

   public SegCorreo segCorreoFind(Long id) {
      return em.find(SegCorreo.class, id);
   }
}
