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
/**
 *
 * @author Felix
 */
@Stateless
@Local(PersonaServ.class)
public class PersonaImpl implements PersonaServ {
    @Inject
   private PersonaLogic personaLogic;
    
    
}
