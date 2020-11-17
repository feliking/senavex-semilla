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

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;
import com.hiska.result.MessageBuilder;
import com.hiska.result.ResultBuilder;
import com.hiska.result.Message;
import bo.gob.senavex.vortex2.model.RegRegistro;
import bo.gob.senavex.vortex2.filter.RegRegistroFilter;
import bo.gob.senavex.vortex2.logic.CliDocumentoLogic;
import bo.gob.senavex.vortex2.logic.CtaDepositoLogic;
import bo.gob.senavex.vortex2.logic.CtaPagoLogic;
import bo.gob.senavex.vortex2.serv.RegRegistroServ;
import bo.gob.senavex.vortex2.logic.RegRegistroLogic;
import bo.gob.senavex.vortex2.logic.RegQueryLogic;
import bo.gob.senavex.vortex2.model.CliDocumento;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.CtaDeposito;
import bo.gob.senavex.vortex2.model.CtaPago;
import com.hiska.result.Param;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

@Stateless
@Local(RegRegistroServ.class)
public class RegRegistroImpl implements RegRegistroServ {
   @Resource
   private SessionContext sessionContext;
   @Inject
   private RegRegistroLogic regRegistroLogic;
   @Inject
   private CliDocumentoLogic cliDocumentoLogic;
   @Inject
   private CtaDepositoLogic ctaDepositoLogic;
   @Inject
   private CtaPagoLogic ctaPagoLogic;
   @Inject
   private RegQueryLogic regQueryLogic;

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
   public ResultFilter<RegRegistro> regRegistroFilter(final RegRegistroFilter regRegistroFilter) {
      LOGGER.log(Level.INFO, "regRegistroReload: {0}", regRegistroFilter);
      ResultFilter<RegRegistro> result = regQueryLogic.regRegistroFilter(regRegistroFilter);
      return result;
   }

   @Override
   public ResultItem<RegRegistro> regRegistroReload(final RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "regRegistroReload: {0}", regRegistro);
      Long id = regRegistro == null ? null : regRegistro.getIdRegistro();
      RegRegistro regRegistroReload = regQueryLogic.regRegistroFind(id);
      return new ResultItem<>(regRegistroReload);
   }

   @Override
   public ResultItem<RegRegistro> regRegistroReloadAll(final RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "regRegistroReload: {0}", regRegistro);
      Long id = regRegistro == null ? null : regRegistro.getIdRegistro();
      RegRegistro regRegistroReload = regQueryLogic.regRegistroFind(id);
      CtaPago ctaPago = regRegistroReload.getCtaPago();
      if (ctaPago != null) {
         ctaPago.getCtaDepositoList().size();
      }
      CliEmpresa cliEmpresa = regRegistroReload.getCliEmpresa();
      cliEmpresa.getCliCategoriaList().size();
      cliEmpresa.getCliContactoList().size();
      cliEmpresa.getCliDireccionList().size();
      cliEmpresa.getCliDocumentoList().size();
      cliEmpresa.getSegOperadorList().size();
      return new ResultItem<>(regRegistroReload);
   }

   @Override
   public ResultItem<RegRegistro> cancelarRegistro(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "cancelarRegistro: {0}", regRegistro);
      Long idRegistro = regRegistro == null ? null : regRegistro.getIdRegistro();
      ResultItem<RegRegistro> result = regRegistroLogic.regRegistroCancelar(idRegistro);
      return result;
   }

   @Override
   public ResultItem<RegRegistro> rechazarDocumentos(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "rechazarDocumentos: {0}", regRegistro);
      ResultItem<RegRegistro> result = regRegistroLogic.assertEstadosValidos(regRegistro, "REG", "REG_CORR");
      List<CliDocumento> list = regRegistro.getCliEmpresa().getCliDocumentoList();
      if (result.isSuccess()) {
         list = list.stream().filter(it -> it.isEsSeleccionado()).collect(Collectors.toList());
         if (list.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: Debe seleccionar un domumento observado");
         }
      }
      if (result.isSuccess()) {
         for (CliDocumento cd : list) {
            cd.setEstado(Param.create("RECH"));
            Result it = cliDocumentoLogic.cliDocumentoMerge(cd);
            result.accept(it);
            if (it.isError()) {
               break;
            }
         }
      }
      if (result.isSuccess()) {
         regRegistro.setEstado(Param.create("REG_OBS"));
         Result it = regRegistroLogic.regRegistroMerge(regRegistro);
         result.accept(it);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Se ha rechazado el registro RUEX");
      }
      return result;
   }

   @Override
   public ResultItem<RegRegistro> aceptarDocumentos(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "aceptarDocumentos: {0}", regRegistro);
      ResultItem<RegRegistro> result = regRegistroLogic.assertEstadosValidos(regRegistro, "REG", "REG_CORR");
      List<CliDocumento> list = regRegistro.getCliEmpresa().getCliDocumentoList();
      if (result.isSuccess()) {
         list = list.stream().filter(it -> it.isEsSeleccionado()).collect(Collectors.toList());
         if (!list.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: Existen documentos observados");
         }
      }
      if (result.isSuccess()) {
         list = regRegistro.getCliEmpresa().getCliDocumentoList();
         for (CliDocumento cd : list) {
            cd.setEstado(Param.create("ACEP"));
            Result it = cliDocumentoLogic.cliDocumentoMerge(cd);
            result.accept(it);
            if (it.isError()) {
               break;
            }
         }
      }
      if (result.isSuccess()) {
         regRegistro.setEstado(Param.create("ACEP"));
         Result it = regRegistroLogic.regRegistroMerge(regRegistro);
         result.accept(it);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Se ha aceptado el registro RUEX");
      }
      return result;
   }

   @Override
   public ResultItem<RegRegistro> corregirDocumentos(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "corregirDocumentos: {0}", regRegistro);
      ResultItem<RegRegistro> result = regRegistroLogic.assertEstadoValido(regRegistro, "REG_OBS");
      List<CliDocumento> list = regRegistro.getCliEmpresa().getCliDocumentoList();
      if (result.isSuccess()) {
         list = list.stream().filter(it -> Param.isEquals(it.getEstado(), "RECH")).collect(Collectors.toList());
         if (list.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: No existen documentos observados");
         }
      }
      if (result.isSuccess()) {
         for (CliDocumento cd : list) {
            cd.setEstado(Param.create("REG_CORR"));
            Result it = cliDocumentoLogic.cliDocumentoMerge(cd);
            result.accept(it);
            if (it.isError()) {
               break;
            }
         }
      }
      if (result.isSuccess()) {
         regRegistro.setEstado(Param.create("REG_CORR"));
         Result it = regRegistroLogic.regRegistroMerge(regRegistro);
         result.accept(it);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Se ha corregido el registro RUEX");
      }
      return result;
   }

   @Override
   public ResultItem<RegRegistro> guardarDepositos(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "guardarDepositos: {0}", regRegistro);
      CtaPago ctaPago = regRegistro.getCtaPago();
      List<CtaDeposito> list = regRegistro.getCtaPago().getCtaDepositoList();
      ResultItem<RegRegistro> result = regRegistroLogic.assertEstadosValidos(regRegistro, "ACEP", "PAG_OBS");
      boolean isAcep = Param.isEquals(regRegistro.getEstado(), "ACEP");
      if (result.isSuccess()) {
         // list = list.stream().filter(it -> Param.isEquals(it.getEstado(),
         // "RECH")).collect(Collectors.toList());
         if (list == null || list.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: No existen depositos registrados");
         }
      }
      if (result.isSuccess()) {
         Result r1 = ctaDepositoLogic.ctaDepositoPersist(ctaPago, list);
         result.accept(r1);
      }
      if (result.isSuccess()) {
         ctaPago.setEstado(isAcep ? Param.create("PAG") : Param.create("CORR"));
         Result it = ctaPagoLogic.ctaPagoMerge(ctaPago);
         result.accept(it);
      }
      if (result.isSuccess()) {
         regRegistro.setEstado(isAcep ? Param.create("PAG") : Param.create("PAG_CORR"));
         Result it = regRegistroLogic.regRegistroMerge(regRegistro);
         result.accept(it);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Se ha corregido el registro RUEX");
      }
      return result;
   }

   @Override
   public Result rechazarDepositos(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "rechazarDepositos: {0}", regRegistro);
      CtaPago ctaPago = regRegistro.getCtaPago();
      List<CtaDeposito> list = regRegistro.getCtaPago().getCtaDepositoList();
      ResultItem<RegRegistro> result = regRegistroLogic.assertEstadosValidos(regRegistro, "PAG", "PAG_CORR");
      if (result.isSuccess()) {
         if (list == null || list.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: No existen depositos registrados");
         }
         String text = ctaPago.getObservacion();
         if (text == null || text.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: Debe describir las observaciones");
         }
      }
      if (result.isSuccess()) {
         Result r1 = ctaDepositoLogic.ctaDepositoPersist(ctaPago, list);
         result.accept(r1);
      }
      if (result.isSuccess()) {
         ctaPago.setEstado(Param.create("OBS"));
         Result r1 = ctaPagoLogic.ctaPagoMerge(ctaPago);
         result.accept(r1);
      }
      if (result.isSuccess()) {
         regRegistro.setEstado(Param.create("PAG_OBS"));
         Result it = regRegistroLogic.regRegistroMerge(regRegistro);
         result.accept(it);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Se ha corregido el registro RUEX");
      }
      return result;
   }

   @Override
   public Result aceptarDepositos(RegRegistro regRegistro) {
      LOGGER.log(Level.INFO, "aceptarDepositos: {0}", regRegistro);
      CtaPago ctaPago = regRegistro.getCtaPago();
      List<CtaDeposito> list = regRegistro.getCtaPago().getCtaDepositoList();
      ResultItem<RegRegistro> result = regRegistroLogic.assertEstadosValidos(regRegistro, "PAG", "PAG_CORR");
      if (result.isSuccess()) {
         if (list == null || list.isEmpty()) {
            result.setSuccess(false);
            result.message("RUEX-2001: No existen depositos registrados");
         }
      }
      if (result.isSuccess()) {
         Result r1 = ctaDepositoLogic.ctaDepositoPersist(ctaPago, list);
         result.accept(r1);
      }
      if (result.isSuccess()) {
         ctaPago.setEstado(Param.create("ACEP"));
         Result it = ctaPagoLogic.ctaPagoMerge(ctaPago);
         result.accept(it);
      }
      if (result.isSuccess()) {
         regRegistro.setEstado(Param.create("APRO"));
         Result it = regRegistroLogic.regRegistroMerge(regRegistro);
         result.accept(it);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      } else {
         result.clear();
         result.message("RUEX-1001: Se ha aprobado el registro RUEX");
      }
      return result;
   }

   private static final Logger LOGGER = Logger.getLogger(RegRegistroImpl.class.getName());
}
