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

import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.ParValor;
import com.hiska.result.Option;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@LocalBean
public class OptionLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public OptionLogic() {
   }

   public OptionLogic(EntityManager em) {
      this.em = em;
   }

   public List<ParValor> parValorList(String nombreTabla, String nombreCampo) {
      return em.createQuery("SELECT o "
            + " FROM ParValor o "
            + " WHERE o.parDominio.nombreTabla = :nombreTabla "
            + "   AND o.parDominio.nombreCampo = :nombreCampo"
            + "   AND ( o.padre IS NULL OR o.padre = 'NONE' OR o.padre = '' )"
            + " ORDER BY o.orden ASC")
            .setParameter("nombreTabla", nombreTabla)
            .setParameter("nombreCampo", nombreCampo)
            .getResultList();
   }

   public List<ParValor> parValorList(String nombreTabla, String nombreCampo, String padre) {
      return em.createQuery("SELECT o "
            + " FROM ParValor o "
            + " WHERE o.parDominio.nombreTabla = :nombreTabla "
            + "   AND o.parDominio.nombreCampo = :nombreCampo"
            + "   AND o.padre = :padre"
            + " ORDER BY o.orden ASC")
            .setParameter("nombreTabla", nombreTabla)
            .setParameter("nombreCampo", nombreCampo)
            .setParameter("padre", padre)
            .getResultList();
   }

   public List<Option> optionSimple(String nombreTabla, String nombreCampo) {
      return parValorList(nombreTabla, nombreCampo)
            .stream()
            .map(it -> Option.create(it.getValor(), it.getEtiqueta(), it.getDescripcion()))
            .collect(Collectors.toList());
   }

   public List<Option> optionSimple(String nombreTabla, String nombreCampo, String valorPadre) {
      return parValorList(nombreTabla, nombreCampo, valorPadre)
            .stream()
            .map(it -> Option.create(it.getValor(), it.getEtiqueta(), it.getDescripcion()))
            .collect(Collectors.toList());
   }

   public List<Option> optionTree(String nombreTabla, String nombreCampo) {
      List<Option> result = parValorList(nombreTabla, nombreCampo)
            .stream()
            .map(it -> Option.create(it.getValor(), it.getEtiqueta(), it.getDescripcion()))
            .collect(Collectors.toList());
      optionRecursive(result, nombreTabla, nombreCampo);
      return result;
   }

   public Option optionValue(String nombreTabla, String nombreCampo, String valor) {
      List<ParValor> result = em.createQuery("SELECT o "
            + " FROM ParValor o "
            + " WHERE o.parDominio.nombreTabla = :nombreTabla "
            + "   AND o.parDominio.nombreCampo = :nombreCampo"
            + "   AND o.valor = :valor"
            + " ORDER BY o.orden ASC")
            .setParameter("nombreTabla", nombreTabla)
            .setParameter("nombreCampo", nombreCampo)
            .setParameter("valor", valor)
            .getResultList();
      if (result.isEmpty()) {
         return null;
      }
      ParValor parValor = result.get(0);
      Option option = new Option();
      option.setValue(parValor.getValor());
      option.setLabel(parValor.getEtiqueta());
      option.setDescription(parValor.getDescripcion());
      return option;
   }

   private void optionRecursive(List<Option> list, String nombreTabla, String nombreCampo) {
      if (list != null) {
         for (Option o : list) {
            List<Option> children = parValorList(nombreTabla, nombreCampo, o.getValue())
                  .stream()
                  .map(it -> Option.create(it.getValor(), it.getEtiqueta(), it.getDescripcion()))
                  .collect(Collectors.toList());
            o.setChildren(children);
            optionRecursive(children, nombreTabla, nombreCampo);
         }
      }
   }
}
