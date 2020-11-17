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
package bo.gob.senavex.vortex2;

import com.hiska.result.Pagination;

/**
 * @author Willyams Yujra
 */
@lombok.Getter
@lombok.Setter
//@lombok.ToString
public abstract class FilterAbstract {
   private Pagination pagination;

   public FilterAbstract() {
      pagination = new Pagination();
      defaultSort(pagination);
   }

   public void setPagination(Pagination value) {
      pagination = new Pagination(value);
      defaultSort(pagination);
   }

   public void paginationClean() {
      pagination.clean();
      defaultSort(pagination);
   }

   public void paginationReload() {
      pagination.reload();
      defaultSort(pagination);
   }

   public abstract void defaultSort(Pagination value);
}
