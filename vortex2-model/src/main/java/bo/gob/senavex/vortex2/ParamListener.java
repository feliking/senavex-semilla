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

import java.util.List;
import javax.inject.Inject;
import javax.persistence.PostLoad;
import com.hiska.result.Option;
import com.hiska.result.Param;
import com.hiska.result.definition.ParamDefinition;

import bo.gob.senavex.vortex2.logic.OptionLogic;

@lombok.Getter
public class ParamListener {
   @Inject
   public OptionLogic optionLogic;

   public ParamListener() {
   }

   @PostLoad
   public void postLoad(Object o) {
      if (optionLogic == null) {
         return;
      }
      List<ParamDefinition> entries = ParamDefinition.get(o.getClass());
      for (ParamDefinition entry : entries) {
         String domain = entry.getDomain();
         String classifier = entry.getClassifier();
         Object object = entry.invokeMethod(o);
         if (object instanceof List) {
            List<Param> paramList = (List) object;
            for (Param param : paramList) {
               String value = param.getValue();
               Option option = optionLogic.optionValue(domain, classifier, value);
               if (option != null) {
                  param.setLabel(option.getLabel());
                  param.setDescription(option.getDescription());
               }
            }
         } else if (object instanceof Param) {
            Param param = (Param) object;
            String value = param.getValue();
            Option option = optionLogic.optionValue(domain, classifier, value);
            if (option != null) {
               param.setLabel(option.getLabel());
               param.setDescription(option.getDescription());
            }
         }
      }
   }
}
