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
@Table(name = "CLI_EMPRESA")
@ToString(callSuper = true, exclude = {"cliCategoriaList", "cliContactoList", "cliDireccionList", "cliDocumentoList", "ctaPagoList", "regRegistroList", "segOperadorList",})
@EqualsAndHashCode(callSuper = false, of = {"idEmpresa",})
public class CliEmpresa extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_EMPRESA")
   private Long idEmpresa;
   // ----------------------------------------
   // ----------------------------------------
   @Column(name = "FECHA_FUNDACION", length = 10, nullable = false)
   @Size(message = "{CliEmpresa.fechaFundacion.Size.message}", min = 4, max = 10)
   @NotNull(message = "{CliEmpresa.fechaFundacion.NotNull.message}")
   private String fechaFundacion;
   @Column(name = "FECHA_OPERACION", length = 10, nullable = false)
   @Size(message = "{CliEmpresa.fechaOperacion.Size.message}", min = 4, max = 10)
   @NotNull(message = "{CliEmpresa.fechaOperacion.NotNull.message}")
   private String fechaOperacion;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{CliEmpresa.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{CliEmpresa.descripcion.NotNull.message}")
   private String descripcion;
   @Column(name = "REF_KEY", length = 30, nullable = false)
   @Size(message = "{CliEmpresa.refKey.Size.message}", min = 5, max = 30)
   @NotNull(message = "{CliEmpresa.refKey.NotNull.message}")
   private String refKey;
   @Column(name = "REF_NAME", length = 150, nullable = false)
   @Size(message = "{CliEmpresa.refName.Size.message}", min = 5, max = 150)
   @NotNull(message = "{CliEmpresa.refName.NotNull.message}")
   private String refName;
   // ----------------------------------------
   @Column(name = "VD_ACTIVIDAD", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliEmpresa.actividad.NotNull.message}")
   private Param actividad;
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliEmpresa.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VD_AFILIACIONES", length = 200, nullable = false)
   @Convert(converter = ParamListConverter.class)
   @ParamElement
   @NotNull(message = "{CliEmpresa.afiliaciones.NotNull.message}")
   private List<Param> afiliaciones;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliEmpresa.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   @OneToMany(mappedBy = "cliEmpresa")
   private List<CliDocumento> cliDocumentoList;
   @OneToMany(mappedBy = "cliEmpresa")
   private List<CliCategoria> cliCategoriaList;
   @OneToMany(mappedBy = "cliEmpresa")
   private List<CliContacto> cliContactoList;
   @OneToMany(mappedBy = "cliEmpresa")
   private List<CliDireccion> cliDireccionList;
   @OneToMany(mappedBy = "cliEmpresa")
   private List<CtaPago> ctaPagoList;
   @OneToMany(mappedBy = "cliEmpresa")
   private List<RegRegistro> regRegistroList;
   @OneToMany(mappedBy = "cliEmpresa")
   private List<SegOperador> segOperadorList;
   // ----------------------------------------
}
