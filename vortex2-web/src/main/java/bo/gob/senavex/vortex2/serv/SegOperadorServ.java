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

import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.filter.SegOperadorFilter;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;

public interface SegOperadorServ {
   Result ping();

   ResultFilter<SegOperador> segOperadorFilter(SegOperadorFilter segOperadorFilter);

   ResultItem<SegOperador> segOperadorReload(SegOperador segOperador);

   ResultItem<SegOperador> segOperadorPersist(SegOperador segOperador);

   Result aceptarInvitacionToken(String valorToken);

   Result activarInvitacion(SegOperador segOperador);

   ResultItem<SegOperador> rechazarInvitacion(SegOperador segOperador);

   ResultItem<SegOperador> inactiverOperador(SegOperador segOperador);
}
