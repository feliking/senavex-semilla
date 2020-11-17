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
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.RegRegistro;
import com.hiska.result.Param;
import java.util.logging.Logger;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * @author willyams yujra
 */
public class T04_RegSmokeTest extends SmokeTest {
   private static final Logger LOGGER = Logger.getLogger(T04_RegSmokeTest.class.getName());
   static Param O_NEW = Param.create("NEW");
   static Param T_RUEX = Param.create("RUEX");

   @Test
   @Order(10)
   public void assertCliParams() {
      assertTableParams("REG_REGISTRO", 3);
      assertTableParamValues("REG_REGISTRO", "VD_ORIGEN", 2);
      assertTableParamValues("REG_REGISTRO", "VD_TIPO", 2);
      assertTableParamValues("REG_REGISTRO", "VF_ESTADO", 7);
   }

   @Test
   @Order(20)
   public void insertRegistro_1() {
//      CliEmpresa ce = (CliEmpresa) em.createQuery("SELECT o FROM CliEmpresa o").getResultList().get(0);
//      RegRegistro rr;
//      rr = create("REG", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 1: {0}", rr.getEstado());
//      rr = create("REG_OBS", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 2: {0}", rr.getEstado());
//      rr = create("REG_CORR", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 3: {0}", rr.getEstado());
//      rr = new RegRegistro();
//      rr = create("ACEP", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 4: {0}", rr.getEstado());
//      rr = create("PAG", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 5: {0}", rr.getEstado());
//      rr = create("PAG_OBS", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 6: {0}", rr.getEstado());
//      rr = create("PAG_CORR", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 7: {0}", rr.getEstado());
//      rr = create("APRO", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 8: {0}", rr.getEstado());
//      rr = create("CAN", ce);
//      em.persist(rr);
//      LOGGER.log(Level.INFO, "REGISTRO 9: {0}", rr.getEstado());
   }

   public static RegRegistro create(String name, CliEmpresa ce) {
      RegRegistro rr = new RegRegistro();
      rr.setCodigo("00000");
      rr.setCliEmpresa(ce);
      rr.setDescripcion("DESC: [" + name + "]");
      rr.setEstado(Param.create(name));
      rr.setTipo(T_RUEX);
      rr.setOrigen(O_NEW);
      return rr;
   }
}
