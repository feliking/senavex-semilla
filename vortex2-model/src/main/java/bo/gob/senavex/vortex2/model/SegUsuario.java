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
@Table(name = "SEG_USUARIO")
@ToString(callSuper = true, exclude = {"segOperadorList", "regSeguimientoList", "logSesionList",})
@EqualsAndHashCode(callSuper = false, of = {"idUsuario",})
public class SegUsuario extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_USUARIO")
   private Long idUsuario;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_PERSONA", nullable = false)
   @NotNull(message = "{SegUsuario.segPersona.NotNull.message}")
   private SegPersona segPersona;
   // ----------------------------------------
   @Column(name = "CORREO_ELECTRONICO", length = 100, nullable = false)
   @Size(message = "{SegUsuario.correoElectronico.Size.message}", min = 5, max = 100)
   @NotNull(message = "{SegUsuario.correoElectronico.NotNull.message}")
   private String correoElectronico;
   @Column(name = "CLAVE_ACCESO", length = 100, nullable = false)
   @Size(message = "{SegUsuario.claveAcceso.Size.message}", min = 5, max = 100)
   @NotNull(message = "{SegUsuario.claveAcceso.NotNull.message}")
   private String claveAcceso;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegUsuario.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegUsuario.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   @OneToMany(mappedBy = "segUsuario")
   private List<SegOperador> segOperadorList;
   @OneToMany(mappedBy = "segUsuario")
   private List<RegSeguimiento> regSeguimientoList;
   @OneToMany(mappedBy = "segUsuario")
   private List<LogSesion> logSesionList;
   // ----------------------------------------
   @Transient
   private String claveConfirm;
}
