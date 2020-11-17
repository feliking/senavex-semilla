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

public interface Model {
   static String VORTEX2_REALM = "vortex2-realm";
   static String VORTEX2_MAIL = "java:comp/env/mail/vortex2-send";
   static String VORTEX2_STORE = "java:comp/env/jdbc/vortex2-store";
   static String CLI_PERSIST = "vortex2-store";
   static String CTA_PERSIST = "vortex2-store";
   static String LOG_PERSIST = "vortex2-store";
   static String PAR_PERSIST = "vortex2-store";
   static String REG_PERSIST = "vortex2-store";
   static String SEG_PERSIST = "vortex2-store";
}
