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

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * @author Willyams Yujra
 */
@RunWith(JUnitPlatform.class)
@SelectClasses({
      T01_ParSmokeTest.class,
      T02_SegSmokeTest.class,
      T03_CliSmokeTest.class,
      T04_RegSmokeTest.class,
      T99_ViewSmokeTest.class
})
public class TestAllSuite {
}
