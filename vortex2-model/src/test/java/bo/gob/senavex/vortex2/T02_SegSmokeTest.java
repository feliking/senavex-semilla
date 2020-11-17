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
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.result.Param;
import java.util.Date;
import java.util.logging.Logger;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author willyams yujra
 */
public class T02_SegSmokeTest extends SmokeTest {
   private static final Logger LOGGER = Logger.getLogger(T02_SegSmokeTest.class.getName());

   @Test
   @Order(10)
   public void assertSegParams() {
      assertTableParams("SEG_PERSONA", 2);
      assertTableParamValues("SEG_PERSONA", "VD_PAIS", 10);
      assertTableParamValues("SEG_PERSONA", "VF_ESTADO", 2);
      assertTableParams("SEG_USUARIO", 2);
      assertTableParamValues("SEG_USUARIO", "VD_TIPO", 3);
      assertTableParamValues("SEG_USUARIO", "VF_ESTADO", 2);
   }

   private static Long idPersonaA;
   private static Long idPersonaB;

   @Test
   @Order(20)
   public void insertSegPersonaA() {
      SegPersona sp = new SegPersona();
      sp.setPrimerApellido("Yujra");
      sp.setSegundoApellido("Huanca");
      sp.setNombres("Willyams Ricardo");
      sp.setGenero(Param.create("M"));
      sp.setFechaNacimiento(new Date());
      sp.setTipoDocumento(Param.create("CI"));
      sp.setNumeroDocumento("480000");
      sp.setComplementoDocumento("");
      sp.setExpedicionDocumento(Param.create("LP"));
      sp.setNumeroCelular("70189721");
      sp.setNumeroTelefonico("70189721");
      sp.setPais(Param.create("BO"));
      sp.setDireccion("S/D");
      sp.setEstado(Param.create("REG"));
      em.persist(sp);
      assertTrue(sp.getIdPersona() != null, "Create Persona " + sp.getIdPersona());
      idPersonaA = sp.getIdPersona();
   }

   @Test
   @Order(21)
   public void insertSegPersonaB() {
      SegPersona sp = new SegPersona();
      sp.setPrimerApellido("Demo");
      sp.setSegundoApellido("Demo");
      sp.setNombres("Demo");
      sp.setGenero(Param.create("M"));
      sp.setFechaNacimiento(new Date());
      sp.setTipoDocumento(Param.create("CI"));
      sp.setNumeroDocumento("480001");
      sp.setComplementoDocumento("");
      sp.setExpedicionDocumento(Param.create("LP"));
      sp.setNumeroCelular("70189700");
      sp.setNumeroTelefonico("70189001");
      sp.setPais(Param.create("BO"));
      sp.setDireccion("S/D");
      sp.setEstado(Param.create("REG"));
      em.persist(sp);
      assertTrue(sp.getIdPersona() != null, "Create Persona " + sp.getIdPersona());
      idPersonaB = sp.getIdPersona();
   }

   @Test
   @Order(31)
   public void insertSegUsuarioSIS() {
      SegUsuario su;
      su = createSegUsuario("admin@senavex.gob.bo", "ACT", "SIS", idPersonaA);
      em.persist(su);
      assertTrue(su.getIdUsuario() != null, "Create Usuario " + su.getIdUsuario());
   }

   @Test
   @Order(32)
   public void insertSegUsuarioINT() {
      SegUsuario su;
      su = createSegUsuario("wyujra@senavex.gob.bo", "ACT", "INT", idPersonaA);
      em.persist(su);
      assertTrue(su.getIdUsuario() != null, "Create Usuario " + su.getIdUsuario());
   }

   @Test
   @Order(33)
   public void insertSegUsuarioEXT() {
      SegUsuario su;
      su = createSegUsuario("yracnet@gmail.com", "ACT", "EXT", idPersonaA);
      em.persist(su);
      assertTrue(su.getIdUsuario() != null, "Create Usuario " + su.getIdUsuario());
      su = createSegUsuario("yr1@gmail.com", "ACT", "EXT", idPersonaB);
      em.persist(su);
      su = createSegUsuario("yr2@gmail.com", "ACT", "EXT", idPersonaB);
      em.persist(su);
      su = createSegUsuario("yr3@gmail.com", "ACT", "EXT", idPersonaB);
      em.persist(su);
   }

   private SegUsuario createSegUsuario(String correoElectronico, String estado, String tipo, Long idPersona) {
      SegUsuario su = new SegUsuario();
      su.setCorreoElectronico(correoElectronico);
      // SHA256(12345)
      su.setClaveAcceso("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
      SegPersona sp = em.getReference(SegPersona.class, idPersona);
      su.setSegPersona(sp);
      su.setEstado(Param.create(estado));
      su.setTipo(Param.create(tipo));
      return su;
   }
}
