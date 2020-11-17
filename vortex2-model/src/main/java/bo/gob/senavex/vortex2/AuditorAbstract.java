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
package bo.gob.senavex.vortex2;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Transient;

/**
 * @author willyams yujra
 */
@MappedSuperclass
@EntityListeners({AuditorListener.class, ParamListener.class})
@lombok.Getter
@lombok.Setter
//@lombok.ToString(callSuper = true)
public abstract class AuditorAbstract {
   @Transient
   private Object auditor;
   @Transient
   private boolean esRequerido;
   @Transient
   private boolean esEditable;
   @Transient
   private boolean esSeleccionado;
   @Basic
   @Column(name = "AUD_UPDATED_AT", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedAt;
   @Basic
   @Column(name = "AUD_UPDATED_BY", length = 50, nullable = false)
   private String updatedBy;
   @Version
   @Column(name = "AUD_VERSION", nullable = false)
   private Long version;
}
