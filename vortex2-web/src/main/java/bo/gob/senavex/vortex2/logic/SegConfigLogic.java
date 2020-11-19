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
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.faces.cc.Menu;
import com.hiska.result.Param;
import com.hiska.result.ResultList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class SegConfigLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PAR_PERSIST)
   private EntityManager em;

   public ResultList<Menu> configMenuSession(SegUsuario segUsuario) {
      List<Menu> menus = new ArrayList<>();
      Menu item;
      boolean isExt = Param.isEquals(segUsuario.getTipo(), "EXT");
      boolean isInt = Param.isEquals(segUsuario.getTipo(), "INT");
      boolean isSis = Param.isEquals(segUsuario.getTipo(), "SIS");
      item = createMenu("Añadir una nueva persona", "fa fa-money-bill", "/persona/nuevo.xhtml");
      menus.add(item);
      item = createMenu("Listar personas", "fa fa-user-tie", "/persona/lista.xhtml");
      menus.add(item);
      return new ResultList<>(menus);
   }

   public ResultList<Long> configIdEmpresasSession(SegUsuario segUsuario) {
      // Verificar si puede exiri restriccion
      List<Long> ids = em.createQuery("SELECT o.cliEmpresa.idEmpresa FROM SegOperador o WHERE o.estado = :e AND o.segUsuario = :u")
            .setParameter("u", segUsuario)
            .setParameter("e", Param.create("ACT"))
            .getResultList();
      ids.add(-1L);
      return new ResultList<>(ids);
   }

   private Menu createMenu(String name, String icon, String target) {
      Menu menu = new Menu();
      menu.setName(name);
      menu.setDescription(name);
      menu.setIcon(icon);
      // assertMenu.setPath(request.getContextPath());
      menu.setPath("/vortex2");
      menu.setTarget(target);
      return menu;
   }
}
