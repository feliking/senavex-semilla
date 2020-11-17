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
@Table(name = "LOG_SESION")
@ToString(callSuper = true, exclude = {"logEventoList",})
@EqualsAndHashCode(callSuper = false, of = {"idSesion",})
public class LogSesion extends AuditorAbstract implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID_SESION")
   private Long idSesion;
   // ----------------------------------------
   @ManyToOne
   @JoinColumn(name = "ID_USUARIO", nullable = false)
   @NotNull(message = "{LogSesion.segUsuario.NotNull.message}")
   private SegUsuario segUsuario;
   // ----------------------------------------
   @Column(name = "FECHA_LOGIN", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "{LogSesion.fechaLogin.NotNull.message}")
   private Date fechaLogin;
   @Column(name = "FECHA_LOGOUT", nullable = true)
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaLogout;
   // ----------------------------------------
   @Column(name = "VF_ESTADO", length = 30, nullable = false)
   @Convert(converter = ParamConverter.class)
   @ParamElement
   @NotNull(message = "{LogSesion.estado.NotNull.message}")
   private Param estado;
   // ----------------------------------------
   @OneToMany(mappedBy = "logSesion")
   private List<LogEvento> logEventoList;
   // ----------------------------------------
}
