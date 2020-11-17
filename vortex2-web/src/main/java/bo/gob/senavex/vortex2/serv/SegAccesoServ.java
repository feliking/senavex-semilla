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
import bo.gob.senavex.vortex2.data.ConfigWrap;
import bo.gob.senavex.vortex2.data.Login;
import com.hiska.result.Result;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.result.ResultItem;

public interface SegAccesoServ {
   Result ping();

   ResultItem<ConfigWrap> segUsuarioLogin(final Captcha<Login> captcha);

   ResultItem<ConfigWrap> configRecargar(final ConfigWrap configWrap);

   Result segUsuarioRegisterCorreoToken(final Captcha<SegUsuario> captcha);

   Result segUsuarioRegisterConfirm(final String id);

   Result segUsuarioRecoveryCorreoToken(final Captcha<String> captcha);

   Result segUsuarioRecoveryConfirm(final Captcha<String> captcha);

   Result segCorreoTokenDelete(final String id);

   Result segUsuarioLogout();
}
