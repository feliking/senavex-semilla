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
@Table(name = "CTA_PAGO")
@ToString(callSuper = true, exclude = {"cliEmpresa", "ctaDepositoList",})
@EqualsAndHashCode(callSuper = false, of = {"idPago",})
public class CtaPago extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_PAGO")
   private Long idPago;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_EMPRESA", nullable = false)
   @NotNull(message = "{CtaPago.cliEmpresa.NotNull.message}")
   private CliEmpresa cliEmpresa;
   // ----------------------------------------
   @Column(name = "FECHA_REGISTRO", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaRegistro;
   @Column(name = "FECHA_ACEPTACION", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaAceptacion;
   @Column(name = "MONTO", nullable = false)
   @NotNull(message = "{CtaPago.monto.NotNull.message}")
   private Integer monto;
   @Column(name = "OBSERVACION", length = 300, nullable = true)
   @Size(message = "{CtaPago.observacion.Size.message}", max = 300)
   private String observacion;
   @Column(name = "DESCRIPCION", length = 300, nullable = false)
   @Size(message = "{CtaPago.descripcion.Size.message}", min = 5, max = 300)
   @NotNull(message = "{CtaPago.descripcion.NotNull.message}")
   private String descripcion;
   // ----------------------------------------
   @Column(name = "VD_TIPO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CtaPago.tipo.NotNull.message}")
   private Param tipo;
   @Column(name = "VD_MONEDA", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CtaPago.moneda.NotNull.message}")
   private Param moneda;
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{CtaPago.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   @OneToMany(mappedBy = "ctaPago")
   private List<CtaDeposito> ctaDepositoList;
   // ----------------------------------------
}
