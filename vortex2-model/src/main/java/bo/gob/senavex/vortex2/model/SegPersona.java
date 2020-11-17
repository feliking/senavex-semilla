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
import javax.servlet.http.Part;

@Data
@Entity
@Table(name = "SEG_PERSONA")
@ToString(callSuper = true, exclude = {"segUsuarioList",})
@EqualsAndHashCode(callSuper = false, of = {"idPersona",})
public class SegPersona extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_PERSONA")
   private Long idPersona;
   // ----------------------------------------
   // ----------------------------------------
   @Column(name = "NOMBRES", length = 150, nullable = false)
   @Size(message = "{SegPersona.nombres.Size.message}", min = 5, max = 150)
   @NotNull(message = "{SegPersona.nombres.NotNull.message}")
   private String nombres;
   @Column(name = "PRIMER_APELLIDO", length = 100, nullable = false)
   @Size(message = "{SegPersona.primerApellido.Size.message}", min = 5, max = 100)
   @NotNull(message = "{SegPersona.primerApellido.NotNull.message}")
   private String primerApellido;
   @Column(name = "SEGUNDO_APELLIDO", length = 100, nullable = true)
   @Size(message = "{SegPersona.segundoApellido.Size.message}", min = 5, max = 100)
   private String segundoApellido;
   @Column(name = "FECHA_NACIMIENTO", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{SegPersona.fechaNacimiento.NotNull.message}")
   private Date fechaNacimiento;
   @Column(name = "NUMERO_DOCUMENTO", length = 30, nullable = false)
   @Size(message = "{SegPersona.numeroDocumento.Size.message}", min = 5, max = 30)
   @NotNull(message = "{SegPersona.numeroDocumento.NotNull.message}")
   private String numeroDocumento;
   @Column(name = "COMPLEMENTO_DOCUMENTO", length = 30, nullable = true)
   @Size(message = "{SegPersona.complementoDocumento.Size.message}", max = 30)
   private String complementoDocumento;
   @Column(name = "DIRECCION", length = 300, nullable = false)
   @Size(message = "{SegPersona.direccion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{SegPersona.direccion.NotNull.message}")
   private String direccion;
   @Column(name = "NUMERO_CELULAR", length = 30, nullable = false)
   @Size(message = "{SegPersona.numeroCelular.Size.message}", min = 5, max = 30)
   @NotNull(message = "{SegPersona.numeroCelular.NotNull.message}")
   private String numeroCelular;
   @Column(name = "NUMERO_TELEFONICO", length = 30, nullable = false)
   @Size(message = "{SegPersona.numeroTelefonico.Size.message}", min = 5, max = 30)
   @NotNull(message = "{SegPersona.numeroTelefonico.NotNull.message}")
   private String numeroTelefonico;
   // ----------------------------------------
   @Embedded
   @AttributeOverrides({
         @AttributeOverride(name = "nombre", column = @Column(name = "DOC_NOMBRE")),
         @AttributeOverride(name = "tipo", column = @Column(name = "DOC_TIPO")),
         @AttributeOverride(name = "contenido", column = @Column(name = "DOC_ARCHIVO"))
   })
   private DocArchivo docRespaldo;
   @Embedded
   @AttributeOverrides({
         @AttributeOverride(name = "nombre", column = @Column(name = "FIRM_NOMBRE")),
         @AttributeOverride(name = "tipo", column = @Column(name = "FIRM_TIPO")),
         @AttributeOverride(name = "contenido", column = @Column(name = "FIRM_ARCHIVO"))
   })
   private DocArchivo docFirma;
   // ----------------------------------------
   @Column(name = "VD_GENERO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegPersona.genero.NotNull.message}")
   private Param genero;
   @Column(name = "VD_TIPO_DOCUMENTO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegPersona.tipoDocumento.NotNull.message}")
   private Param tipoDocumento;
   @Column(name = "VD_EXPEDICION_DOCUMENTO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegPersona.expedicionDocumento.NotNull.message}")
   private Param expedicionDocumento;
   @Column(name = "VD_PAIS", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegPersona.pais.NotNull.message}")
   private Param pais;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{SegPersona.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   @OneToMany(mappedBy = "segPersona")
   private List<SegUsuario> segUsuarioList;
   // ----------------------------------------

   public String getNombreCompleto() {
      return nombres + " " + primerApellido + " " + segundoApellido;
   }

   public String getDocumentoIdentidad() {
      return numeroDocumento + " " + (tipoDocumento != null ? tipoDocumento.getValue() : "");
   }

   public Part getDocRespaldoPart() {
      return null;
   }

   public void setDocRespaldoPart(Part part) {
      docRespaldo = new DocArchivo();
      if (part != null) {
         docRespaldo.setNombre(part.getSubmittedFileName());
         docRespaldo.setTipo(part.getContentType());
         try {
            byte content[] = part.getInputStream().readAllBytes();
            docRespaldo.setContenido(content);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   public Part getDocFirmaPart() {
      return null;
   }

   public void setDocFirmaPart(Part part) {
      docFirma = new DocArchivo();
      if (part != null) {
         docFirma.setNombre(part.getSubmittedFileName());
         docFirma.setTipo(part.getContentType());
         try {
            byte content[] = part.getInputStream().readAllBytes();
            docFirma.setContenido(content);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
