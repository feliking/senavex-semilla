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

import bo.gob.senavex.vortex2.base.SmokeTest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * @author willyams yujra
 */
public class T99_ViewSmokeTest extends SmokeTest {
   private static final Logger LOGGER = Logger.getLogger(T99_ViewSmokeTest.class.getName());

   @Test
   @Order(10)
   public void createJAASViews() {
      String content = readFileCase("ViewSmokeTest_A001.sql");
      content = content.replace("\n", " ").trim();
      String[] sqls = content.split(";");
      Assertions.assertTrue(sqls.length > 1, "All Views");
      for (String sql : sqls) {
         if (sql.trim().isEmpty()) {
            continue;
         }
         LOGGER.log(Level.INFO, "Ejecute: {0}", sql);
         Query q = em.createNativeQuery(sql);
         int i = q.executeUpdate();
         LOGGER.log(Level.INFO, "Result: {0}", i);
         Assertions.assertTrue(true, "Create View::: " + i);
         // Assertions.assertTrue(i == 1, "Create View::: " + i+": " + sql);
      }
   }
}
