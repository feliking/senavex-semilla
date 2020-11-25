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
package bo.gob.senavex.vortex2.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import bo.gob.senavex.vortex2.serv.PersonaServ;
import bo.gob.senavex.vortex2.logic.PersonaLogic;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import javax.inject.Inject;
import bo.gob.senavex.vortex2.model.Persona;
import com.hiska.result.Param;
import com.hiska.result.ResultItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.hiska.result.ResultItem;

/**
 * @author Felix
 */
@Stateless
@Local(PersonaServ.class)
public class PersonaImpl implements PersonaServ {
   private static final Logger LOGGER = Logger.getLogger(PersonaImpl.class.getName());
   @Inject
   private PersonaLogic personaLogic;

   @Override
   public ResultItem<Persona> registrarPersona(Persona persona) {
      ResultItem<Persona> result = new ResultItem<>();
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "PERSONA: {0}", persona);
         ResultItem<Persona> r1 = personaLogic.personaPersist(persona);
         result.accept(r1);
      }
      return result;
   }
}
