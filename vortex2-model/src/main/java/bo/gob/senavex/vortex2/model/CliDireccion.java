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
@Table(name = "CLI_DIRECCION")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idDireccion",})
public class CliDireccion extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_DIRECCION")
   private Long idDireccion;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{CliDireccion.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   // ----------------------------------------
   @Column(name = "NOMBRE", length = 100, nullable = true)
   @Size(message = "{CliDireccion.nombre.Size.message}", min = 5, max = 100)
   private String nombre;
   @Column(name = "AVENIDA", length = 50, nullable = false)
   @Size(message = "{CliDireccion.avenida.Size.message}", min = 5, max = 50)
   @NotNull(message = "{CliDireccion.avenida.NotNull.message}")
   private String avenida;
   @Column(name = "CALLES", length = 100, nullable = true)
   @Size(message = "{CliDireccion.calles.Size.message}", min = 5, max = 100)
   private String calles;
   @Column(name = "DOMICILIO", length = 100, nullable = true)
   @Size(message = "{CliDireccion.domicilio.Size.message}", min = 5, max = 100)
   private String domicilio;
   @Column(name = "EDIFICIO", length = 100, nullable = true)
   @Size(message = "{CliDireccion.edificio.Size.message}", min = 5, max = 100)
   private String edificio;
   @Column(name = "OFICINA", length = 100, nullable = true)
   @Size(message = "{CliDireccion.oficina.Size.message}", min = 5, max = 100)
   private String oficina;
   @Column(name = "PISO", length = 10, nullable = true)
   @Size(message = "{CliDireccion.piso.Size.message}", min = 5, max = 10)
   private String piso;
   @Column(name = "FECHA_REGISTRO", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{CliDireccion.fechaRegistro.NotNull.message}")
   private Date fechaRegistro;
   @Column(name = "FECHA_FIN", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaFin;
   @Column(name = "FECHA_INICIO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaInicio;
   @Column(name = "GEO_REFERENCIA", length = 100, nullable = true)
   @Size(message = "{CliDireccion.geoReferencia.Size.message}", min = 5, max = 100)
   private String geoReferencia;
   @Column(name = "REFERENCIA", length = 300, nullable = true)
   @Size(message = "{CliDireccion.referencia.Size.message}", min = 5, max = 300)
   private String referencia;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliDireccion.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VD_DEPARTAMENTO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliDireccion.departamento.NotNull.message}")
   private Param departamento;
   @Column(name = "VD_MUNICIPIO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliDireccion.municipio.NotNull.message}")
   private Param municipio;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliDireccion.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
