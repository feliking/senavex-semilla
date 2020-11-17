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
package bo.gob.senavex.vortex2.base;

import bo.gob.senavex.vortex2.T03_CliSmokeTest;
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.ParDominio;
import bo.gob.senavex.vortex2.model.ParValor;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/**
 * @author willyams yujra
 */
@TestMethodOrder(OrderAnnotation.class)
public class SmokeTest {
   static {
      try {
         InputStream stream = SmokeTest.class.getClassLoader().getResourceAsStream("logging.properties");
         LogManager.getLogManager().readConfiguration(stream);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   private static final Logger LOGGER = Logger.getLogger(T03_CliSmokeTest.class.getName());
   private static final String DB_ACTION = "javax.persistence.schema-generation.database.action";
   protected static EntityManagerFactory emf;
   protected static EntityManager em;
   protected static File SMOKES = new File("src/test/resources/smokes");
   protected static File FILES = new File("src/test/resources/files");
   private static boolean created = false;

   @BeforeAll
   public static void setUpClass() {
      LOGGER.log(Level.INFO, "=====================================================");
      Map<String, String> props = new HashMap<>();
      if (created == false) {
         created = true;
         props.put(DB_ACTION, "drop-and-create");
         LOGGER.log(Level.INFO, "CREATE DATABASE");
      } else {
         props.put(DB_ACTION, "none");
      }
      emf = Persistence.createEntityManagerFactory(Model.SEG_PERSIST, props);
      em = emf.createEntityManager();
      em = new EntityManagerWrap(em);
   }

   @AfterAll
   public static void tearDownClass() {
      LOGGER.log(Level.INFO, "=====================================================");
      em.close();
      emf.close();
   }

   @BeforeEach
   public void setUp() {
      LOGGER.log(Level.INFO, "----------------------START-------------------------");
      em.getTransaction().begin();
   }

   @AfterEach
   public void tearDown() {
      LOGGER.log(Level.INFO, "----------------------END---------------------------");
      em.getTransaction().commit();
      em.clear();
   }

   public static SmokeCase readSmokeCase(String name) {
      SmokeCase smokeEntry = new SmokeCase();
      File file = new File(SMOKES, name);
      try {
         Reader reader = new FileReader(file);
         smokeEntry = new Gson().fromJson(reader, SmokeCase.class);
      } catch (Exception e) {
         smokeEntry.setError(true);
         smokeEntry.setMessage("Error al cargar el archivo: " + file);
         smokeEntry.setException(e);
      }
      return smokeEntry;
   }

   public static String readFileCase(String name) {
      File file = new File(FILES, name);
      try {
         byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
         return new String(encoded, StandardCharsets.UTF_8);
      } catch (IOException e) {
         System.err.println("Error: " + e.getMessage());
      }
      return "";
   }

   public static void assertTableParams(String table, int min) {
      LOGGER.log(Level.INFO, "ASSERT PARAMETERS {0}", table);
      List<ParDominio> list = em.createQuery("SELECT o"
            + " FROM ParDominio o"
            + " WHERE o.nombreTabla = :nombreTabla")
            .setParameter("nombreTabla", table)
            .getResultList();
      LOGGER.log(Level.INFO, "RESULT: {0}", list.size());
      assertTrue(list.size() >= min, table + " -> Parameter ->" + min + " - " + list.size());
      for (ParDominio pd : list) {
         LOGGER.log(Level.INFO, "PARAM: {0} - {1}", new Object[]{pd.getNombreTabla(), pd.getNombreCampo()});
      }
   }

   public static void assertTableParamValues(String table, String column, int min) {
      LOGGER.log(Level.INFO, "ASSERT PARAMETERS {0}", table);
      List<ParValor> list = em.createQuery("SELECT o"
            + " FROM ParValor o"
            + " WHERE o.parDominio.nombreTabla = :nombreTabla"
            + "   AND o.parDominio.nombreCampo = :nombreCampo")
            .setParameter("nombreTabla", table)
            .setParameter("nombreCampo", column)
            .getResultList();
      LOGGER.log(Level.INFO, "RESULT: {0}", list.size());
      assertTrue(list.size() >= min, table + " -> Parameter ->" + min + " - " + list.size());
      for (ParValor pv : list) {
         LOGGER.log(Level.INFO, "VALUE: {0} - {1}", new Object[]{pv.getValor(), pv.getEtiqueta()});
      }
   }

   public static <T> void setterDefault(T value, java.util.function.Consumer<T> set, T other) {
      if (value == null) {
         set.accept(other);
      }
   }

   public static void main(String[] args) {
      String data = readFileCase("ViewSmokeTest_A001.sql");
      System.out.println("->" + data);
      String[] sqls = data.split(";");
      for (String sql : sqls) {
         System.out.println("->" + sql);
         System.out.println("->" + sql.trim().isEmpty());
      }
   }
}
