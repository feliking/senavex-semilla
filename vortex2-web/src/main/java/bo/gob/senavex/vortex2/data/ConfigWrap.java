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
package bo.gob.senavex.vortex2.data;

import lombok.*;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.faces.cc.Menu;
import com.hiska.result.Param;
import java.util.Date;
import java.util.List;

/**
 * @author Willyams Yujra
 */
@Getter
public class ConfigWrap {
   private boolean login = false;
   private String correoElectronico;
   private SegPersona segPersona;
   private SegUsuario segUsuario;
   private CliEmpresa cliEmpresa;
   private List<Menu> menus;
   private List<Long> idEmpresas;
   private Date loginDate;
   private Date configDate;
   private Date systemDate;
   private Param tipo;

   public void assertLogin(String correoElectronico) {
      this.login = true;
      this.correoElectronico = correoElectronico;
      this.loginDate = new Date();
   }

   public void assertLogout() {
      login = false;
      segPersona = null;
      segUsuario = null;
      cliEmpresa = null;
      menus = null;
      idEmpresas = null;
      loginDate = null;
      configDate = null;
      systemDate = null;
      tipo = null;
   }

   public void assertSegUsuario(SegUsuario segUsuario) {
      this.segUsuario = segUsuario;
      this.segPersona = segUsuario == null ? null : segUsuario.getSegPersona();
      this.tipo = segUsuario == null ? null : segUsuario.getTipo();
      this.configDate = new Date();
   }

   public void assertIdEmpresas(List<Long> idEmpresas) {
      this.idEmpresas = idEmpresas;
      this.configDate = new Date();
   }

   public void assertMenu(List<Menu> menus) {
      this.menus = menus;
      this.configDate = new Date();
   }

   public boolean isUsuarioExterno() {
      return Param.isEquals(tipo, "EXT");
   }

   public boolean isUsuarioInterno() {
      return Param.isEquals(tipo, "INT");
   }

   public boolean isUsuarioSistema() {
      return Param.isEquals(tipo, "SIS");
   }
}
