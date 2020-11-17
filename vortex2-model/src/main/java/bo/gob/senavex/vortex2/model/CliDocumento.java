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
@Table(name = "CLI_DOCUMENTO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idDocumento",})
public class CliDocumento extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_DOCUMENTO")
   private Long idDocumento;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{CliDocumento.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   // ----------------------------------------
   @Column(name = "NOMBRE", length = 100, nullable = true)
   @Size(message = "{CliDocumento.nombre.Size.message}", min = 5, max = 100)
   private String nombre;
   @Column(name = "NUMERO", length = 50, nullable = true)
   @Size(message = "{CliDocumento.numero.Size.message}", min = 5, max = 50)
   private String numero;
   @Column(name = "VIGENCIA_DESDE", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date vigenciaDesde;
   @Column(name = "VIGENCIA_HASTA", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date vigenciaHasta;
   @Column(name = "OBSERVACION", length = 300, nullable = true)
   @Size(message = "{CliDocumento.observacion.Size.message}", max = 300)
   private String observacion;
   @Column(name = "FECHA_REGISTRO", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{CliDocumento.fechaRegistro.NotNull.message}")
   private Date fechaRegistro;
   @Column(name = "FECHA_VERIFICACION", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaVerificacion;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{CliDocumento.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{CliDocumento.descripcion.NotNull.message}")
   private String descripcion;
   // ----------------------------------------
   @Embedded
   private DocArchivo docRespaldo;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliDocumento.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CliDocumento.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------

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
}
