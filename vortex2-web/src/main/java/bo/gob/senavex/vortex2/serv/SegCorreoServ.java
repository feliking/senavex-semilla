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

import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.filter.SegCorreoFilter;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;

public interface SegCorreoServ {
   Result ping();

   ResultFilter<SegCorreo> segCorreoFilter(SegCorreoFilter segCorreoFilter);

   ResultItem<SegCorreo> renviarCorreo(SegCorreo segCorreo);

   ResultItem<String> assertValorToken(String valorToken);

   Result removeValorToken(String valorToken);
}