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
@Table(name = "SEG_CORREO")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idCorreo",})
public class SegCorreo extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_CORREO")
   private Long idCorreo;
   // ----------------------------------------
   // ----------------------------------------
   @Column(name = "ID_REFERENCIA", length = 50, nullable = false)
   @Size(message = "{SegCorreo.idReferencia.Size.message}", min = 1, max = 50)
   @NotNull(message = "{SegCorreo.idReferencia.NotNull.message}")
   private String idReferencia;
   @Column(name = "CORREO_DESTINO", length = 300, nullable = false)
   @Size(message = "{SegCorreo.correoDestino.Size.message}", min = 5, max = 300)
   @NotNull(message = "{SegCorreo.correoDestino.NotNull.message}")
   private String correoDestino;
   @Column(name = "CORREO_COPIA", length = 300, nullable = true)
   @Size(message = "{SegCorreo.correoCopia.Size.message}", min = 5, max = 300)
   private String correoCopia;
   @Column(name = "VALOR_TOKEN", length = 40, nullable = true)
   @Size(message = "{SegCorreo.valorToken.Size.message}", min = 5, max = 40)
   private String valorToken;
   @Column(name = "FECHA_SOLICITUD", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{SegCorreo.fechaSolicitud.NotNull.message}")
   private Date fechaSolicitud;
   @Column(name = "FECHA_USO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaUso;
   @Column(name = "FECHA_VIGENCIA", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{SegCorreo.fechaVigencia.NotNull.message}")
   private Date fechaVigencia;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull
   private Param tipo;
   @Column(name = "VD_PLANTILLA", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull
   private Param plantilla;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------

   public Long getIdReferenciaLong() {
      return idReferencia == null ? null : Long.parseLong(idReferencia);
   }
}
