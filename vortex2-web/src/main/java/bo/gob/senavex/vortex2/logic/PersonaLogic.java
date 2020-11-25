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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.senavex.vortex2.logic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import bo.gob.senavex.vortex2.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.hiska.result.Param;
import com.hiska.result.ResultItem;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import bo.gob.senavex.vortex2.model.Persona;

/**
 * @author Felix
 */
@Stateless
@LocalBean
public class PersonaLogic {
   @lombok.Getter
   @PersistenceContext(unitName = Model.PERSONA_PERSIST)
   private EntityManager em;
   public static final Param E_REGISTRADO = Param.create("REG", "Persona Registrada");

   public PersonaLogic() {
   }

   public PersonaLogic(EntityManager em) {
      this.em = em;
   }

   public ResultItem<Persona> personaPersist(final Persona persona) {
      persona.setId(null);
      ResultItem<Persona> result = new ResultItem<>();
      Message message = MessageBuilder.create("SEG-2001: Validacion Registro Persona")
            .validate(persona, "idPersona")
            .get();
      if (!message.isCauseEmpty()) {
         result.setSuccess(false);
         result.addMessage(message);
         return result;
      }
      em.persist(persona);
      result.setValue(persona);
      result.message("PER-1001: El Persona fue registrado correctamente")
            .cause("ID: " + persona.getId())
            .action("Puede proceder a realizar mas operaciones");
      return result;
   }
}
