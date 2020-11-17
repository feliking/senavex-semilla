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
import com.hiska.result.Param;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * @author willyams yujra
 */
public class T03_CliSmokeTest extends SmokeTest {
   private static final Logger LOGGER = Logger.getLogger(T03_CliSmokeTest.class.getName());

   @Test
   @Order(10)
   public void assertCliParams() {
      assertTableParams("CLI_EMPRESA", 2);
   }

   @Test
   @Order(20)
   public void insertSegEmpresa_1() {
      CliEmpresa ce = new CliEmpresa();
      ce.setRefKey("NIT o CODE");
      ce.setRefName("Empresa Semilla");
      ce.setDescripcion("Empresas de semillas");
      ce.setFechaFundacion("2020");
      ce.setFechaOperacion("2020");
      ce.setEstado(Param.create("VERF"));
      ce.setTipo(Param.create("COOP"));
      ce.setActividad(Param.create("PRO"));
      List<Param> list = List.of(Param.create("CADEX"), Param.create("CAMEX"));
      ce.setAfiliaciones(list);
      em.persist(ce);
      LOGGER.log(Level.INFO, "INSERT EMPRESA 1: {0}", ce.getIdEmpresa());
   }

   @Test
   @Order(21)
   public void insertSegEmpresa_2() {
      CliEmpresa ce = new CliEmpresa();
      ce.setRefKey("NIT o CODE");
      ce.setRefName("Empresa Minera");
      ce.setDescripcion("Empresas de Mineria");
      ce.setFechaFundacion("2020");
      ce.setFechaOperacion("2020");
      ce.setEstado(Param.create("REG"));
      ce.setTipo(Param.create("APUB"));
      ce.setActividad(Param.create("COM"));
      List<Param> list = List.of(Param.create("CAINCO"), Param.create("CAMEX"));
      ce.setAfiliaciones(list);
      em.persist(ce);
      LOGGER.log(Level.INFO, "INSERT EMPRESA 2: {0}", ce.getIdEmpresa());
   }
//
//    @Test
//    @Order(30)
//    public void selectOne() {
//        CliEmpresa e1 = (CliEmpresa) em.createQuery("SELECT o FROM CliEmpresa o WHERE o.nit = :nit ")
//                .setParameter("nit", "0001")
//                .getSingleResult();
//        LOGGER.log(Level.INFO, "SELECT EMPRESA 1: {0}", e1);
//    }
//
//    @Test
//    @Order(40)
//    public void selectAll() {
//        List<CliEmpresa> list = em.createQuery("SELECT o FROM CliEmpresa o WHERE o.nit = :nit ")
//                .setParameter("nit", "0001")
//                .getResultList();
//        for (CliEmpresa e : list) {
//            LOGGER.log(Level.INFO, "SELECT EMPRESA: {0}", e.getIdEmpresa());
//            LOGGER.log(Level.INFO, "SELECT EMPRESA: {0}", e.getEstado());
//        }
//    }
}
