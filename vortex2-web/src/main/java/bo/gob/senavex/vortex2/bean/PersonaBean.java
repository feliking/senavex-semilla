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
package bo.gob.senavex.vortex2.bean;

import bo.gob.senavex.vortex2.model.Persona;
import com.hiska.result.ResultItem;
import com.hiska.faces.MessageUtil;
import javax.inject.Inject;
import bo.gob.senavex.vortex2.serv.PersonaServ;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.ToString;
import javax.annotation.PostConstruct;

/**
 * @author Felix
 */
@Getter
@ToString
@ManagedBean(name = "_persona")
@ViewScoped
public class PersonaBean implements Serializable {
   @Inject
   private PersonaServ personaServ;
   private Persona persona = new Persona();

   /* @PostConstruct
    * public void initAction() {
    * persona.setNombre("Felix");
    * persona.setCiudadNacimiento("La Paz");
    * } */
   public String registrarPersona() {
      ResultItem<Persona> result = personaServ.registrarPersona(persona);
      MessageUtil.processResult(result);
      return result.isError() ? null : "home";
   }
}
