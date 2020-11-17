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
@Table(name = "CLI_CONTACTO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idContacto",})
public class CliContacto extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_CONTACTO")
   private Long idContacto;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{CliContacto.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   // ----------------------------------------
   @Column(name = "NOMBRE", length = 100, nullable = true)
   @Size(message = "{CliContacto.nombre.Size.message}", min = 5, max = 100)
   private String nombre;
   @Column(name = "VALOR", length = 100, nullable = true)
   @Size(message = "{CliContacto.valor.Size.message}", min = 5, max = 100)
   private String valor;
   @Column(name = "FECHA_REGISTRO", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{CliContacto.fechaRegistro.NotNull.message}")
   private Date fechaRegistro;
   @Column(name = "FECHA_VERFICACION", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaVerficacion;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{CliContacto.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{CliContacto.descripcion.NotNull.message}")
   private String descripcion;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliContacto.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliContacto.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
