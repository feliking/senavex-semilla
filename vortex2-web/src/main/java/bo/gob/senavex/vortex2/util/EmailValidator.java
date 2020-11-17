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
package bo.gob.senavex.vortex2.util;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author yracnet
 */
@FacesValidator("senavex.EmailValidator")
public class EmailValidator implements Validator {
   private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]+@[a-zA-Z_][a-zA-Z_0-9]+[\\.a-zA-Z_0-9]+[a-zA-Z0-9]");

   @Override
   public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
      if (o == null) {
         return;
      }
      String email = (String) o;
      boolean matches = EMAIL_PATTERN.matcher(email).matches();
      if (!matches) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato del correo no valido", null);
         throw new ValidatorException(msg);
      }
   }
}
