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

import lombok.Data;

/**
 * @author yracnet
 */
@Data
public class Wizard {
   private int index = 1;
   private int limit = 2;

   public Wizard() {
   }

   public Wizard(int limit) {
      this.limit = limit;
   }

   public void back() {
      if (index > 1) {
         index = index - 1;
      }
   }

   public void next() {
      if (index < limit) {
         index = index + 1;
      }
   }

   public boolean isStart() {
      return index == 1;
   }

   public boolean isEnd() {
      return index == limit;
   }

   public int getPercentage() {
      return (index * 100) / limit;
   }
}
