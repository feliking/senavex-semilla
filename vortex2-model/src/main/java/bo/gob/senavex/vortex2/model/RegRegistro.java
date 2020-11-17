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
@Table(name = "REG_REGISTRO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idRegistro",})
public class RegRegistro extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_REGISTRO")
   private Long idRegistro;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{RegRegistro.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   @OneToOne
   @JoinColumn(name = "ID_PAGO", nullable = true)
   private CtaPago ctaPago;
   // ----------------------------------------
   @Column(name = "CODIGO", length = 50, nullable = false)
   @Size(message = "{RegRegistro.codigo.Size.message}", min = 5, max = 50)
   @NotNull(message = "{RegRegistro.codigo.NotNull.message}")
   private String codigo;
   @Column(name = "FECHA_SOLICITUD", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaSolicitud;
   @Column(name = "FECHA_EMISION", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaEmision;
   @Column(name = "FECHA_VENCIMIENTO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaVencimiento;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{RegRegistro.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{RegRegistro.descripcion.NotNull.message}")
   private String descripcion;
   // ----------------------------------------
   @Column(name = "VD_ORIGEN", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{RegRegistro.origen.NotNull.message}")
   private Param origen;
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{RegRegistro.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{RegRegistro.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
