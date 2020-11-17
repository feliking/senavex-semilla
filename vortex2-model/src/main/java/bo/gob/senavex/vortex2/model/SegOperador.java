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
@Table(name = "SEG_OPERADOR")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idOperador",})
public class SegOperador extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_OPERADOR")
   private Long idOperador;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{SegOperador.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   @ManyToOne
   @JoinColumn(name = "ID_USUARIO", nullable = true)
   private SegUsuario segUsuario;
   // ----------------------------------------
   @Column(name = "NOMBRE_CARGO", length = 150, nullable = false)
   @Size(message = "{SegOperador.nombreCargo.Size.message}", min = 5, max = 150)
   @NotNull(message = "{SegOperador.nombreCargo.NotNull.message}")
   private String nombreCargo;
   @Column(name = "CORREO_ELECTRONICO", length = 100, nullable = true)
   @Size(message = "{SegOperador.correoElectronico.Size.message}", min = 5, max = 100)
   private String correoElectronico;
   @Column(name = "NUMERO_DOCUMENTO", length = 100, nullable = true)
   @Size(message = "{SegOperador.numeroDocumento.Size.message}", min = 5, max = 100)
   private String numeroDocumento;
   @Column(name = "FECHA_DESDE", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaDesde;
   @Column(name = "FECHA_HASTA", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaHasta;
   @Column(name = "FECHA_RESPUESTA", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaRespuesta;
   @Column(name = "FECHA_SOLICITUD", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaSolicitud;
   // ----------------------------------------
   @Column(name = "VD_PERFIL", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegOperador.perfil.NotNull.message}")
   private Param perfil;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegOperador.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------

   public String getCorreoElectronicoReal() {
      return segUsuario == null ? correoElectronico : segUsuario.getCorreoElectronico();
   }

   public String getNumeroDocumentoReal() {
      return segUsuario == null || segUsuario.getSegPersona() == null ? numeroDocumento : segUsuario.getSegPersona().getNumeroDocumento();
   }
   // ----------------------------------------

   public boolean compareCorreoElectronico() {
      return correoElectronico != null && correoElectronico.equalsIgnoreCase(getSegUsuarioCorreoElectronico());
   }

   public boolean compareNumeroDocumento() {
      return numeroDocumento != null && numeroDocumento.equalsIgnoreCase(getSegPersonaNumeroDocumento());
   }

   public String getSegUsuarioCorreoElectronico() {
      return segUsuario == null ? null : segUsuario.getCorreoElectronico();
   }

   public String getSegPersonaNombreCompleto() {
      return segUsuario == null || segUsuario.getSegPersona() == null ? null : segUsuario.getSegPersona().getNombreCompleto();
   }

   public String getSegPersonaNumeroDocumento() {
      return segUsuario == null || segUsuario.getSegPersona() == null ? null : segUsuario.getSegPersona().getNumeroDocumento();
   }

   public String getSegPersonaDocumentoIdentidad() {
      return segUsuario == null || segUsuario.getSegPersona() == null ? null : segUsuario.getSegPersona().getDocumentoIdentidad();
   }

   public String getCliEmpresaRefKey() {
      return cliEmpresa == null ? null : cliEmpresa.getRefKey();
   }

   public String getCliEmpresaRefName() {
      return cliEmpresa == null ? null : cliEmpresa.getRefName();
   }
}
