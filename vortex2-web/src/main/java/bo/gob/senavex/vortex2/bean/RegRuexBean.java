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
package bo.gob.senavex.vortex2.bean;

import bo.gob.senavex.vortex2.data.Captcha;
import bo.gob.senavex.vortex2.data.RuexWrap;
import bo.gob.senavex.vortex2.data.Wizard;
import bo.gob.senavex.vortex2.model.SegOperador;
import lombok.*;
import java.io.Serializable;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import com.hiska.result.ResultItem;
import com.hiska.faces.MessageUtil;
import bo.gob.senavex.vortex2.serv.RegRuexServ;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

@Getter
@ToString
@ManagedBean(name = "_regRuex")
@ViewScoped
public class RegRuexBean implements Serializable {
   @Inject
   private RegRuexServ regRuexServ;
   private RuexWrap wrap = new RuexWrap();
   private final Wizard wizard = new Wizard(7);
   private final Captcha<RuexWrap> captcha = new Captcha<>();

   public RegRuexBean() {
   }

   @PostConstruct
   public void init() {
      wrap.dataTest();
   }
   // ============== INBOX ACTION ==============

   public String regRegistroReturnAction() {
      wrap = new RuexWrap();
      return "home";
   }
   // ============== SERVICE ACTION ==============

   public String verificarDocumentosAction() {
      captcha.setData(wrap);
      ResultItem<RuexWrap> result = regRuexServ.verificarDocumentos(captcha);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         wrap = result.getValue();
         wizard.next();
      });
      return null;
   }

   public String addOperador() {
      SegOperador operador = wrap.getOperador();
      ResultItem<SegOperador> result = regRuexServ.verificarOperador(operador);
      MessageUtil.processResult(result);
      operador = result.getValue();
      wrap.addOperador(operador);
      return null;
   }

   public String regRegistroRUEXPersistAction() {
      ResultItem<RuexWrap> result = regRuexServ.registrarRUEX(wrap);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         wrap = null;
      });
      return result.isError() ? null : "home";
   }

   // FAST TEST
   public String fastTestAction() {
      verificarDocumentosAction();
      regRegistroRUEXPersistAction();
      wizard.back();
      wrap = new RuexWrap();
      wrap.dataTest();
      return null;
   }
}
