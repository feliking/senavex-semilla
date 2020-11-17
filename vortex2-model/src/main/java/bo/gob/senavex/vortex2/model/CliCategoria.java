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
import com.hiska.result.converter.ParamListConverter;

@Data
@Entity
@Table(name = "CLI_CATEGORIA")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idCategoria",})
public class CliCategoria extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_CATEGORIA")
   private Long idCategoria;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{CliCategoria.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   // ----------------------------------------
   @Column(name = "FECHA_REGISTRO", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{CliCategoria.fechaRegistro.NotNull.message}")
   private Date fechaRegistro;
   // ----------------------------------------
   @Column(name = "VD_CATEGORIA", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliCategoria.categoria.NotNull.message}")
   private Param categoria;
   @Column(name = "VD_NRO_EMPLEADOS", length = 30, nullable = true)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   private Param nroEmpleados;
   @Column(name = "VD_ACTIVOS_UFV", length = 30, nullable = true)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   private Param activosUfv;
   @Column(name = "VD_VENTAS_UFV", length = 30, nullable = true)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   private Param ventasUfv;
   @Column(name = "VD_EXPORTACIONES_UFV", length = 30, nullable = true)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   private Param exportacionesUfv;
   @Column(name = "VD_IMPORTACIONES_UFV", length = 30, nullable = true)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   private Param importacionesUfv;
   @Column(name = "VD_EXPORTACIONES_RUBROS", length = 300, nullable = true)
   @Convert(converter = ParamListConverter.class)
   @ParamElement
   private List<Param> exportacionesRubros;
   @Column(name = "VD_IMPORTACIONES_RUBROS", length = 300, nullable = true)
   @Convert(converter = ParamListConverter.class)
   @ParamElement
   private List<Param> importacionesRubros;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliCategoria.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
