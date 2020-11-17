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
package bo.gob.senavex.vortex2.data;

import java.io.Serializable;
import lombok.*;

/**
 * @author Willyams Yujra
 */
@Data
@ToString
public class Login implements Serializable {
   private String correoElectronico;
   private String claveAcceso;
}
