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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.senavex.vortex2.data;

import bo.gob.senavex.vortex2.model.CtaDeposito;
import com.hiska.result.Param;
import java.util.List;
import lombok.Getter;

/**
 * @author yracnet
 */
@Getter
public class DepositoPart {
   private String name = "LIST";
   private CtaDeposito item;

   public void crear() {
      item = new CtaDeposito();
      item.setEstado(Param.create("REG", "Registrado"));
      name = "FORM";
   }

   public void editar(CtaDeposito item) {
      this.item = item;
      name = "FORM";
   }

   public void quitar(List list, CtaDeposito item) {
      list.remove(item);
      name = "LIST";
   }

   public void adicionar(List list) {
      list.remove(item);
      list.add(item);
      name = "LIST";
   }

   public void cancelar() {
      item = null;
      name = "LIST";
   }

   public boolean isPartForm() {
      return "FORM".equals(name);
   }

   public boolean isPartList() {
      return "LIST".equals(name);
   }
}
