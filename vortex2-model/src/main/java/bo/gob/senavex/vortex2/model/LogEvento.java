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
@Table(name = "LOG_EVENTO")
@ToString(callSuper = true, exclude = {"logCambioList",})
@EqualsAndHashCode(callSuper = false, of = {"idEvento",})
public class LogEvento extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_EVENTO")
   private Long idEvento;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_SESION", nullable = false)
   @NotNull(message = "{LogEvento.logSesion.NotNull.message}")
   private LogSesion logSesion;
   // ----------------------------------------
   @Column(name = "FECHA_EVENTO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaEvento;
   @Column(name = "OPERACION", length = 100, nullable = true)
   @Size(message = "{LogEvento.operacion.Size.message}", min = 5, max = 100)
   private String operacion;
   // ----------------------------------------
   // ----------------------------------------
   @OneToMany(mappedBy = "logEvento")
   private List<LogCambio> logCambioList;
   // ----------------------------------------
}
