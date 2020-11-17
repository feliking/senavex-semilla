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
@Table(name = "PAR_VALOR")
@ToString(callSuper = true, exclude = {})
@EqualsAndHashCode(callSuper = false, of = {"idValor",})
public class ParValor extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_VALOR")
   private Long idValor;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_DOMINIO", nullable = false)
   @NotNull(message = "{ParValor.parDominio.NotNull.message}")
   private ParDominio parDominio;
   // ----------------------------------------
   @Column(name = "ORDEN", nullable = true)
   private Integer orden;
   @Column(name = "PADRE", length = 50, nullable = true)
   @Size(message = "{ParValor.padre.Size.message}", min = 5, max = 50)
   private String padre;
   @Column(name = "VALOR", length = 50, nullable = true)
   @Size(message = "{ParValor.valor.Size.message}", min = 5, max = 50)
   private String valor;
   @Column(name = "ETIQUETA", length = 300, nullable = true)
   @Size(message = "{ParValor.etiqueta.Size.message}", min = 5, max = 300)
   private String etiqueta;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{ParValor.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{ParValor.descripcion.NotNull.message}")
   private String descripcion;
   // ----------------------------------------
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{ParValor.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   // ----------------------------------------
}
