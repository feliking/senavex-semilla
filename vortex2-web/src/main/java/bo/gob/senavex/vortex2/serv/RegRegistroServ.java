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

import bo.gob.senavex.vortex2.model.RegRegistro;
import bo.gob.senavex.vortex2.filter.RegRegistroFilter;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;

public interface RegRegistroServ {
   Result ping();

   ResultFilter<RegRegistro> regRegistroFilter(RegRegistroFilter regRegistroFilter);

   ResultItem<RegRegistro> regRegistroReload(RegRegistro regRegistro);

   ResultItem<RegRegistro> regRegistroReloadAll(RegRegistro regRegistro);

   Result cancelarRegistro(RegRegistro regRegistro);

   Result rechazarDocumentos(RegRegistro regRegistro);

   Result aceptarDocumentos(RegRegistro regRegistro);

   Result corregirDocumentos(RegRegistro regRegistro);

   Result guardarDepositos(RegRegistro regRegistro);

   Result rechazarDepositos(RegRegistro regRegistro);

   Result aceptarDepositos(RegRegistro regRegistro);
}
