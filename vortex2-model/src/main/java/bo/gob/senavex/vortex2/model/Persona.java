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

@Data
@Entity
@Table(name = "PERSONA")
public class Persona implements Serializable {
   // ----------------------------------------
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID")
   private Long id;
   // ----------------------------------------
   // ----------------------------------------
   @Column(name = "NOMBRE", length = 150, nullable = false)
   @NotNull(message = "Este campo es obligatorio")
   private String nombre;
   @Column(name = "FECHA_NACIMIENTO", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull(message = "Este campo es obligatorio")
   private Date fechaNacimiento;
   @Column(name = "CIUDAD_NACIMIENTO", length = 150, nullable = false)
   @NotNull(message = "Este campo es obligatorio")
   private String ciudadNacimiento;
}
