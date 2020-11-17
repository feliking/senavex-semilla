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
@Table(name = "REG_SEGUIMIENTO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idSeguimiento",})
public class RegSeguimiento extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_SEGUIMIENTO")
   private Long idSeguimiento;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_USUARIO", nullable = false)
   @NotNull(message = "{RegSeguimiento.segUsuario.NotNull.message}")
   private SegUsuario segUsuario;
   // ----------------------------------------
   @Column(name = "ID_REFERENCIA", length = 50, nullable = true)
   @Size(message = "{RegSeguimiento.idReferencia.Size.message}", min = 5, max = 50)
   private String idReferencia;
   @Column(name = "FECHA_ASIGNACION", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{RegSeguimiento.fechaAsignacion.NotNull.message}")
   private Date fechaAsignacion;
   @Column(name = "FECHA_RESPUESTA", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaRespuesta;
   @Column(name = "FECHA_CIERRE", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaCierre;
   @Column(name = "REFERENCIA", length = 50, nullable = true)
   @Size(message = "{RegSeguimiento.referencia.Size.message}", min = 5, max = 50)
   private String referencia;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{RegSeguimiento.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{RegSeguimiento.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
