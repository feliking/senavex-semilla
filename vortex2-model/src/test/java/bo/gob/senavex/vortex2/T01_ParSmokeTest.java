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
import bo.gob.senavex.vortex2.base.SmokeCase;
import bo.gob.senavex.vortex2.model.ParDominio;
import bo.gob.senavex.vortex2.model.ParValor;
import com.hiska.result.Param;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author willyams yujra
 */
public class T01_ParSmokeTest extends SmokeTest {
   private static final Logger LOGGER = Logger.getLogger(T01_ParSmokeTest.class.getName());

   @Test
   @Order(10)
   public void initParametersInsert() {
      SmokeCase smokeCase = readSmokeCase("ParSmokeTest_VALUE.json");
      LOGGER.log(Level.INFO, "TEST: {0}", smokeCase.getName());
      assertEquals(smokeCase.isError(), false, "MSG: " + smokeCase.getMessage());
      for (SmokeCase.Entry entry : smokeCase.getEntries()) {
         if (entry.isIgnore()) {
            continue;
         }
         LOGGER.log(Level.INFO, "------------------------------------------------------------------------");
         LOGGER.log(Level.INFO, "--TEST: {0}", entry.getName());
         LOGGER.log(Level.INFO, "--PARENT: {0} = {2}", entry.getParam());
         ParDominio pd = entry.getInputObject(ParDominio.class);
         setterDefault(pd.getEstado(), pd::setEstado, Param.create("ACT"));
         setterDefault(pd.getTipo(), pd::setTipo, Param.create("OPT"));
         em.persist(pd);
         LOGGER.log(Level.INFO, "--PARENT ID: {0}", pd.getIdDominio());
         int order = 1;
         for (SmokeCase.Entry child : entry.getChildren()) {
            LOGGER.log(Level.INFO, "::CHILD: {0} = {2}", child.getParam());
            ParValor pv = child.getInputObject(ParValor.class);
            pv.setParDominio(pd);
            setterDefault(pv.getOrden(), pv::setOrden, order++);
            setterDefault(pv.getEstado(), pv::setEstado, Param.create("ACT"));
            setterDefault(pv.getDescripcion(), pv::setDescripcion, "Sin descripcion");
            em.persist(pv);
            LOGGER.log(Level.INFO, "::CHILD ID: {0}", pv.getIdValor());
         }
      }
   }
}
