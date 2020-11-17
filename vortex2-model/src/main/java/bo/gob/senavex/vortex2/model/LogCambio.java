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
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.hiska.result.Param;
import com.hiska.result.ParamElement;
import com.hiska.result.converter.ParamConverter;
import bo.gob.senavex.vortex2.AuditorAbstract;

@Data
@Entity
@Table(name = "LOG_CAMBIO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idCambio",})
public class LogCambio extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_CAMBIO")
   private Long idCambio;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EVENTO", nullable = false)
   @NotNull(message = "{LogCambio.logEvento.NotNull.message}")
   private LogEvento logEvento;
   // ----------------------------------------
   @Column(name = "NOMBRE_TABLA", length = 50, nullable = true)
   @Size(message = "{LogCambio.nombreTabla.Size.message}", min = 5, max = 50)
   private String nombreTabla;
   @Column(name = "NOMBRE_COLUMNA", length = 50, nullable = true)
   @Size(message = "{LogCambio.nombreColumna.Size.message}", min = 5, max = 50)
   private String nombreColumna;
   @Column(name = "VALOR_PREVIO", length = 300, nullable = true)
   @Size(message = "{LogCambio.valorPrevio.Size.message}", min = 5, max = 300)
   private String valorPrevio;
   @Column(name = "VALOR_ACTUAL", length = 300, nullable = true)
   @Size(message = "{LogCambio.valorActual.Size.message}", min = 5, max = 300)
   private String valorActual;
   // ----------------------------------------
   // ----------------------------------------
   // ----------------------------------------
}
