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
@Getter
@Setter
@ToString
public class Captcha<T> implements Serializable {
   private String id;
   private String action;
   private String value = "-DEV-";
   private T data;
}
