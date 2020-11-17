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
package bo.gob.senavex.vortex2.model;

import lombok.*;
import java.io.*;
import javax.persistence.*;

@Embeddable
@Getter
@Setter
@ToString(callSuper = true, exclude = {})
public class DocArchivo implements Serializable {
   @Column(name = "NOMBRE_ARCHIVO", length = 50, nullable = true, updatable = false)
   private String nombre;
   @Column(name = "TIPO_ARCHIVO", length = 50, nullable = true, updatable = false)
   private String tipo;
   @Lob
   @Column(name = "CONTENIDO_ARCHIVO", nullable = true, updatable = false)
   private byte[] contenido;
}
