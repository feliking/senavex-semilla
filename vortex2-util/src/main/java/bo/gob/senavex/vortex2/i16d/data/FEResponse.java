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
package bo.gob.senavex.vortex2.i16d.data;

import bo.gob.senavex.vortex2.model.CliDireccion;
import bo.gob.senavex.vortex2.model.CliDocumento;
import bo.gob.senavex.vortex2.model.SegPersona;
import com.hiska.result.Param;
import lombok.*;

/**
 * @author Willyams Yujra
 */
@Getter
@Setter
public class FEResponse {
   private String codigo;
   private String nombreComercial;
   private String descripcion;
   private CliDireccion direccion;
   private CliDocumento documento;
   private SegPersona representante;
   private Param tipoConstitucion;
   private String fechaFundacion;
   private String fechaOperacion;
}
