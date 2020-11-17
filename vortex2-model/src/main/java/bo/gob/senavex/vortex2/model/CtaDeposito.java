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
@Table(name = "CTA_DEPOSITO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idDeposito",})
public class CtaDeposito extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_DEPOSITO")
   private Long idDeposito;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_PAGO", nullable = false)
   @NotNull(message = "{CtaDeposito.ctaPago.NotNull.message}")
   private CtaPago ctaPago;
   // ----------------------------------------
   @Column(name = "COMPROBANTE", length = 50, nullable = true)
   @Size(message = "{CtaDeposito.comprobante.Size.message}", min = 1, max = 50)
   private String comprobante;
   @Column(name = "MONTO", nullable = true)
   private Double monto;
   @Column(name = "FECHA_REGISTRO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaRegistro;
   @Column(name = "FECHA_DEPOSITO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaDeposito;
   @Column(name = "FECHA_VERIFICACION", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaVerificacion;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{CtaDeposito.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{CtaDeposito.descripcion.NotNull.message}")
   private String descripcion;
   // ----------------------------------------
   @Embedded
   private DocArchivo docComprobante;
   // ----------------------------------------
   @Column(name = "VD_CUENTA", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CtaDeposito.cuenta.NotNull.message}")
   private Param cuenta;
   @Column(name = "VD_MONEDA", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CtaDeposito.moneda.NotNull.message}")
   private Param moneda;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CtaDeposito.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------

   public Part getDocComprobantePart() {
      return null;
   }

   public void setDocComprobantePart(Part part) {
      docComprobante = new DocArchivo();
      if (part != null) {
         docComprobante.setNombre(part.getSubmittedFileName());
         docComprobante.setTipo(part.getContentType());
         try {
            byte content[] = part.getInputStream().readAllBytes();
            docComprobante.setContenido(content);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
