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
@FacesValidator("senavex.DocValidator")
public class DocValidator implements Validator {
   private static final Pattern DOC_PATTERN = Pattern.compile("[0-9]{4,12}[A-Z]{2,10}");

   @Override
   public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
      if (o == null) {
         return;
      }
      String doc = (String) o;
      boolean matches = DOC_PATTERN.matcher(doc).matches();
      if (!matches) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato del documento no valido, ####ABC", null);
         throw new ValidatorException(msg);
      }
   }
}
