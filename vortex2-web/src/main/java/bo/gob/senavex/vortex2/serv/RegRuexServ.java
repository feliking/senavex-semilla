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

import bo.gob.senavex.vortex2.data.Captcha;
import bo.gob.senavex.vortex2.data.RuexWrap;
import bo.gob.senavex.vortex2.model.SegOperador;
import com.hiska.result.Result;
import com.hiska.result.ResultItem;

public interface RegRuexServ {
   Result ping();

   ResultItem<RuexWrap> verificarDocumentos(Captcha<RuexWrap> captcha);

   ResultItem<SegOperador> verificarOperador(SegOperador segOperador);

   ResultItem<RuexWrap> registrarRUEX(RuexWrap ruexWrap);
}
