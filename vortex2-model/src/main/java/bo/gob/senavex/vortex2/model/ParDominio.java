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
@Table(name = "PAR_DOMINIO")
@ToString(callSuper = true, exclude = {"parValorList",})
@EqualsAndHashCode(callSuper = false, of = {"idDominio",})
public class ParDominio extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_DOMINIO")
   private Long idDominio;
   // ----------------------------------------
   // ----------------------------------------
   @Column(name = "CODIGO", length = 50, nullable = false)
   @Size(message = "{ParDominio.codigo.Size.message}", min = 5, max = 50)
   @NotNull(message = "{ParDominio.codigo.NotNull.message}")
   private String codigo;
   @Column(name = "NOMBRE_CAMPO", length = 50, nullable = false)
   @Size(message = "{ParDominio.nombreCampo.Size.message}", min = 5, max = 50)
   @NotNull(message = "{ParDominio.nombreCampo.NotNull.message}")
   private String nombreCampo;
   @Column(name = "NOMBRE_TABLA", length = 50, nullable = false)
   @Size(message = "{ParDominio.nombreTabla.Size.message}", min = 5, max = 50)
   @NotNull(message = "{ParDominio.nombreTabla.NotNull.message}")
   private String nombreTabla;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{ParDominio.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{ParDominio.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   @OneToMany(mappedBy = "parDominio")
   private List<ParValor> parValorList;
   // ----------------------------------------
}
