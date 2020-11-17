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
package bo.gob.senavex.vortex2.impl;

import bo.gob.senavex.vortex2.logic.RegRegistroFactory;
import bo.gob.senavex.vortex2.data.Captcha;
import bo.gob.senavex.vortex2.data.RuexWrap;
import bo.gob.senavex.vortex2.i16d.I16DService;
import bo.gob.senavex.vortex2.i16d.data.FEResponse;
import bo.gob.senavex.vortex2.i16d.data.SINResponse;
import bo.gob.senavex.vortex2.logic.CliCategoriaLogic;
import bo.gob.senavex.vortex2.logic.CliContactoLogic;
import bo.gob.senavex.vortex2.logic.CliDireccionLogic;
import bo.gob.senavex.vortex2.logic.CliDocumentoLogic;
import bo.gob.senavex.vortex2.logic.CliEmpresaLogic;
import bo.gob.senavex.vortex2.logic.CtaPagoLogic;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Logger;
import com.hiska.result.Result;
import com.hiska.result.ResultItem;
import com.hiska.result.MessageBuilder;
import com.hiska.result.ResultBuilder;
import com.hiska.result.Message;
import bo.gob.senavex.vortex2.model.RegRegistro;
import bo.gob.senavex.vortex2.serv.RegRuexServ;
import bo.gob.senavex.vortex2.logic.RegRegistroLogic;
import bo.gob.senavex.vortex2.logic.RegQueryLogic;
import bo.gob.senavex.vortex2.logic.SegCorreoFactory;
import bo.gob.senavex.vortex2.logic.SegOperadorLogic;
import bo.gob.senavex.vortex2.logic.SegCorreoLogic;
import bo.gob.senavex.vortex2.logic.SegUsuarioLogic;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.CtaPago;
import bo.gob.senavex.vortex2.model.SegCorreo;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.result.Param;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

@Stateless
@Local(RegRuexServ.class)
public class RegRuexImpl implements RegRuexServ {
   private static final Logger LOGGER = Logger.getLogger(RegRuexImpl.class.getName());
   @Resource
   private SessionContext sessionContext;
   @Inject
   private RegRegistroLogic regRegistroLogic;
   @Inject
   private CliEmpresaLogic cliEmpresaLogic;
   @Inject
   private CliDocumentoLogic cliDocumentoLogic;
   @Inject
   private CliDireccionLogic cliDireccionLogic;
   @Inject
   private CliContactoLogic cliContactoLogic;
   @Inject
   private CliCategoriaLogic cliCategoriaLogic;
   @Inject
   private SegOperadorLogic segOperadorLogic;
   @Inject
   private SegUsuarioLogic segUsuarioLogic;
   @Inject
   private CtaPagoLogic conPagoLogic;
   @Inject
   private RegQueryLogic regQueryLogic;
   @Inject
   private SegCorreoLogic segCorreoLogic;
   @Inject
   private I16DService i16dService;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("REG-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + regRegistroLogic)
            .cause("Logic: " + regQueryLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultItem<RuexWrap> verificarDocumentos(Captcha<RuexWrap> captcha) {
      FEResponse fer = null;
      SINResponse sinr = null;
      SegUsuario usuario = null;
      RuexWrap wrap = captcha.getData();
      Result result = segCorreoLogic.assertCodigoCaptcha(captcha);
      if (result.isSuccess()) {
         ResultItem<SegUsuario> r0 = segUsuarioLogic.getCurrentSegUsuario();
         result.accept(r0);
         usuario = r0.getValue();
      }
      if (result.isSuccess()) {
         ResultItem<FEResponse> r1 = i16dService.fundempresaVerificar(wrap.getFun());
         fer = r1.getValue();
         ResultItem<SINResponse> r2 = i16dService.sinVerificar(wrap.getNit());
         sinr = r2.getValue();
         result.append(r1);
         result.append(r2);
         result.setSuccess(r1.isSuccess() && r2.isSuccess());
      }
      if (result.isSuccess()) {
         RegRegistroFactory.createAutocompletar(wrap, usuario, sinr, fer);
         result.clear();
         result.message("I16D-1001: Se ha verificado los datos enviados!")
               .action("Complete los datos faltantes");
      } else {
         RegRegistroFactory.createRegistroSimple(wrap, usuario);
         result.message("I16D-2001: No se ha podido verificar los datos, su registro estare pendiente a verificacion manual")
               .action("Complete los datos");
         result.setSuccess(true);
      }
      RegRegistroFactory.testData(wrap);
      return new ResultItem<>(wrap, result);
   }

   @Override
   public ResultItem<SegOperador> verificarOperador(SegOperador segOperador) {
      ResultItem<SegOperador> result = new ResultItem<>(segOperador);
      ResultItem<SegUsuario> r1 = segUsuarioLogic.segUsuarioByCorreoElectronico(segOperador.getCorreoElectronico());
      r1.ifSuccess(() -> {
         SegUsuario u = r1.getValue();
         segOperador.setSegUsuario(u);
      });
      return result;
   }

   @Override
   public ResultItem<RuexWrap> registrarRUEX(RuexWrap wrap) {
      RegRegistro regRegistro = wrap.getRegistro();
      CliEmpresa cliEmpresa = wrap.getEmpresa();
      ResultItem<RuexWrap> result = new ResultItem<>();
      boolean isEmpresaVerificada = false;
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_EMPRESA: {0}", cliEmpresa);
         ResultItem<CliEmpresa> r1 = cliEmpresaLogic.cliEmpresaPersist(cliEmpresa);
         result.accept(r1);
         cliEmpresa = r1.getValue();
         isEmpresaVerificada = result.isSuccess() && Param.isEquals(cliEmpresa.getEstado(), "VERF");
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_DOCUMENTO_LIST: {0}", wrap.getDocumentoList());
         Result r1 = cliDocumentoLogic.cliDocumentoPersist(cliEmpresa, wrap.getDocumentoList());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_DIRECCION_LIST: {0}", wrap.getDireccionList());
         Result r1 = cliDireccionLogic.cliDireccionPersist(cliEmpresa, wrap.getDireccionList());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_CATEGORIA_LIST: {0}", wrap.getCategoriaList());
         Result r1 = cliCategoriaLogic.cliCategoriaPersist(cliEmpresa, wrap.getCategoriaList());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_CONTACTO_LIST: {0}", wrap.getContactoList());
         Result r1 = cliContactoLogic.cliContactoPersist(cliEmpresa, wrap.getContactoList());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_OPERADOR_LIST: {0}", wrap.getAllOperadorList());
         Result r1 = segOperadorLogic.segOperadorPersist(cliEmpresa, wrap.getAllOperadorList());
         result.accept(r1);
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "CLI_OPERADOR_LIST: {0} MAILS", wrap.getAllOperadorList());
         for (SegOperador so : wrap.getOperadorList()) {
            SegCorreo segCorreo = SegCorreoFactory.correoOperadorInvitacion(so);
            Result r1 = segCorreoLogic.segCorreoPersist(segCorreo);
            result.accept(r1);
            if (r1.isError()) {
               return result;
            }
         }
      }
      if (result.isSuccess()) {
         LOGGER.log(Level.INFO, "REG_REGISTRO: {0}", regRegistro);
         regRegistro.setCliEmpresa(cliEmpresa);
         regRegistro.setCodigo("00000");
         regRegistro.setDescripcion("Registro Solicitado");
         regRegistro.setOrigen(Param.create("NEW"));
         regRegistro.setEstado(isEmpresaVerificada ? Param.create("REG") : Param.create("ACEP"));
         regRegistro.setTipo(Param.create("RUEX"));
         Result r1 = regRegistroLogic.regRegistroPersist(regRegistro);
         result.accept(r1);
      }
      if (result.isSuccess() && isEmpresaVerificada) {
         CtaPago ctaPago = new CtaPago();
         ctaPago.setCliEmpresa(cliEmpresa);
         ctaPago.setEstado(Param.create("REG"));
         ctaPago.setMonto(100);
         ctaPago.setMoneda(Param.create("BS"));
         ctaPago.setTipo(Param.create("RUEX"));
         ctaPago.setDescripcion("PAGO RUEX: Registro ID: " + regRegistro.getIdRegistro());
         LOGGER.log(Level.INFO, "CTA_PAGO: {0}", ctaPago);
         Result r1 = conPagoLogic.ctaPagoPersist(ctaPago);
         result.accept(r1);
         if (r1.isSuccess()) {
            regRegistro.setCtaPago(ctaPago);
            r1 = regRegistroLogic.regRegistroMerge(regRegistro);
            result.accept(r1);
         }
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Informacion Registrada exitosamente")
               .action("Pruede proceder a realizar el Pago correspondiente");
      }
      return result;
   }
}
