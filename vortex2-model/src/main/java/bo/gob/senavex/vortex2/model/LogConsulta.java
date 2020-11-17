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
@Table(name = "LOG_CONSULTA")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idConsulta",})
public class LogConsulta extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_CONSULTA")
   private Long idConsulta;
   // ----------------------------------------
   // ----------------------------------------
   @Column(name = "FECHA_CONSULTA", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaConsulta;
   @Column(name = "DATA_SOLICITUD", length = 1024, nullable = true)
   @Size(message = "{LogConsulta.dataSolicitud.Size.message}", min = 5, max = 1024)
   private String dataSolicitud;
   @Column(name = "DATA_RESPUESTA", length = 1024, nullable = true)
   @Size(message = "{LogConsulta.dataRespuesta.Size.message}", min = 5, max = 1024)
   private String dataRespuesta;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{LogConsulta.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VD_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{LogConsulta.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
