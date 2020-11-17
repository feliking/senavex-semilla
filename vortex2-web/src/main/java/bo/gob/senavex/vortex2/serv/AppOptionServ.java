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
package bo.gob.senavex.vortex2.serv;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.hiska.result.Option;
import com.hiska.result.Result;
import com.hiska.result.ResultList;

@Stateless
@LocalBean
public interface AppOptionServ {
   Result ping();

   ResultList<Option> optionList(String domain, String classifier);

   ResultList<Option> optionList(String domain, String classifier, String parent);

   ResultList<Option> optionTree(String domain, String classifier);
}
