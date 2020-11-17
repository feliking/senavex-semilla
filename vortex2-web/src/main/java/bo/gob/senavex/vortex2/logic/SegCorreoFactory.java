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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.senavex.vortex2.logic;

import static bo.gob.senavex.vortex2.logic.SegCorreoLogic.*;
import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.model.SegUsuario;
import java.util.Date;

/**
 * @author yracnet
 */
public class SegCorreoFactory {
   public static SegCorreo correoUsuarioRegistrado(SegUsuario segUsuario) {
      SegCorreo segCorreo = new SegCorreo();
      segCorreo.setCorreoDestino(segUsuario.getCorreoElectronico());
      segCorreo.setEstado(E_REGISTRADO);
      segCorreo.setPlantilla(P_ACTIVACION);
      segCorreo.setTipo(T_ID_USUARIO);
      segCorreo.setIdReferencia("" + segUsuario.getIdUsuario());
      segCorreo.setFechaSolicitud(new Date());
      segCorreo.setFechaVigencia(new Date());
      return segCorreo;
   }

   public static SegCorreo correoUsuarioRecuperacion(SegUsuario segUsuario) {
      SegCorreo segCorreo = new SegCorreo();
      segCorreo.setCorreoDestino(segUsuario.getCorreoElectronico());
      segCorreo.setEstado(E_REGISTRADO);
      segCorreo.setPlantilla(P_RECUPERAR);
      segCorreo.setTipo(T_ID_USUARIO);
      segCorreo.setIdReferencia("" + segUsuario.getIdUsuario());
      segCorreo.setFechaSolicitud(new Date());
      segCorreo.setFechaVigencia(new Date());
      return segCorreo;
   }

   public static SegCorreo correoOperadorInvitacion(SegOperador segOperador) {
      SegCorreo segCorreo = new SegCorreo();
      segCorreo.setCorreoDestino(segOperador.getCorreoElectronico());
      segCorreo.setEstado(E_REGISTRADO);
      segCorreo.setPlantilla(P_INVITACION);
      segCorreo.setTipo(T_ID_USUARIO);
      segCorreo.setIdReferencia("" + segOperador.getIdOperador());
      segCorreo.setFechaSolicitud(new Date());
      segCorreo.setFechaVigencia(new Date());
      return segCorreo;
   }
}
