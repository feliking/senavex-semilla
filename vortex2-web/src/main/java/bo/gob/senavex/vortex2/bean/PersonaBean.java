/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.senavex.vortex2.bean;

import bo.gob.senavex.vortex2.model.Persona;
import com.hiska.result.ResultItem;
import com.hiska.faces.MessageUtil;

/**
 *
 * @author Felix
 */
@lombok.Getter
public class PersonaBean {
    private Persona persona;
    
    public PersonaBean(){
        persona = new Persona();
    }
    
    public void registrarPersona(){
        ResultItem<Persona> result = 
    }
}
