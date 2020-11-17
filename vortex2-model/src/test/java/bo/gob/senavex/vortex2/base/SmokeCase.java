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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.List;

/**
 * @author Willyams Yujra
 */
@lombok.Getter
@lombok.Setter
//@lombok.ToString
public class SmokeCase {
   private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

   @lombok.Getter
   // @lombok.ToString
   public static class Entry {
      private String name;
      private boolean ignore;
      private String inputType;
      private JsonObject inputData;
      private String resultType;
      private JsonObject resultData;
      private List<Entry> children;

      public <T> T getInputObject(Class<T> in) {
         return gson.fromJson(inputData, in);
      }

      public <T> T getResultObject(Class<T> in) {
         return gson.fromJson(resultData, in);
      }

      public Object[] getParam() {
         return new Object[]{name, inputType, inputData, resultType, resultData};
      }
   }

   private String name;
   private boolean error;
   private String message;
   private Exception exception;
   private List<Entry> entries;

   public Entry getFirst() {
      return entries == null || entries.isEmpty() ? null : entries.get(0);
   }
}
