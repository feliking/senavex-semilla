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

import bo.gob.senavex.vortex2.model.CtaPago;
import bo.gob.senavex.vortex2.filter.CtaPagoFilter;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;

public interface CtaPagoServ {
   Result ping();

   ResultFilter<CtaPago> ctaPagoFilter(CtaPagoFilter ctaPagoFilter);

   ResultItem<CtaPago> ctaPagoReload(CtaPago ctaPago);

   ResultItem<CtaPago> ctaPagoReloadAll(CtaPago ctaPago);
}