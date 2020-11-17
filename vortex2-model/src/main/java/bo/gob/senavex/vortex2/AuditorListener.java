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

import java.security.Principal;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.*;

/**
 * @author willyams yujra
 */
public class AuditorListener {
   @Resource
   private SessionContext sessionContext;

   @PrePersist
   public void prePersist(Object o) {
      if (o instanceof AuditorAbstract) {
         AuditorAbstract a = (AuditorAbstract) o;
         a.setUpdatedBy(currentUsername());
         a.setUpdatedAt(new Date());
         a.setVersion(1L);
      }
   }

   @PreUpdate
   public void preUpdate(Object o) {
      if (o instanceof AuditorAbstract) {
         AuditorAbstract a = (AuditorAbstract) o;
         a.setUpdatedBy(currentUsername());
         a.setUpdatedAt(new Date());
      }
   }

   private String currentUsername() {
      Principal principal = sessionContext == null ? null : sessionContext.getCallerPrincipal();
      return principal == null ? "anonymous" : principal.getName();
   }
}
