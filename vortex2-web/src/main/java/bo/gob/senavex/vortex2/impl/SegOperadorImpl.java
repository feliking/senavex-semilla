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
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.filter.SegOperadorFilter;
import bo.gob.senavex.vortex2.logic.SegCorreoLogic;
import bo.gob.senavex.vortex2.serv.SegOperadorServ;
import bo.gob.senavex.vortex2.logic.SegOperadorLogic;
import bo.gob.senavex.vortex2.logic.SegQueryLogic;
import bo.gob.senavex.vortex2.model.SegCorreo;
import com.hiska.result.Param;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

@Stateless
@Local(SegOperadorServ.class)
public class SegOperadorImpl implements SegOperadorServ {
   @Resource
   private SessionContext sessionContext;
   @Inject
   private SegCorreoLogic segCorreoLogic;
   @Inject
   private SegOperadorLogic segOperadorLogic;
   @Inject
   private SegQueryLogic segQueryLogic;

   @Override
   public Result ping() {
      Message message = MessageBuilder.create("SEG-0000: Ping Service")
            .cause("Date : " + new Date())
            .cause("This : " + this)
            .cause("Logic: " + segOperadorLogic)
            .cause("Logic: " + segQueryLogic)
            .get();
      return ResultBuilder.createResult(message);
   }

   @Override
   public ResultFilter<SegOperador> segOperadorFilter(final SegOperadorFilter segOperadorFilter) {
      LOGGER.log(Level.INFO, "segOperadorReload: {0}", segOperadorFilter);
      ResultFilter<SegOperador> result = segQueryLogic.segOperadorFilter(segOperadorFilter);
      return result;
   }

   @Override
   public ResultItem<SegOperador> segOperadorReload(final SegOperador segOperador) {
      LOGGER.log(Level.INFO, "segOperadorReload: {0}", segOperador);
      Long id = segOperador == null ? null : segOperador.getIdOperador();
      SegOperador segOperadorReload = segQueryLogic.segOperadorFind(id);
      return new ResultItem<>(segOperadorReload);
   }

   @Override
   public ResultItem<SegOperador> segOperadorPersist(final SegOperador segOperador) {
      LOGGER.log(Level.INFO, "segOperadorPersist: {0}", segOperador);
      ResultItem<SegOperador> result = segOperadorLogic.segOperadorPersist(segOperador);
      return result;
   }

   @Override
   public Result aceptarInvitacionToken(String valorToken) {
      LOGGER.log(Level.INFO, "aceptarInvitacionToken: {0}", valorToken);
      ResultItem<SegCorreo> result = segCorreoLogic.assertValorToken(valorToken, SegCorreoLogic.P_INVITACION);
      if (result.isSuccess()) {
         SegCorreo segCorreo = result.getValue();
         Long idOperador = segCorreo.getIdReferenciaLong();
         Result r1 = segOperadorLogic.aceptarInvitacion(idOperador);
         result.accept(r1);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      }
      return result.asResult();
   }

   @Override
   public Result activarInvitacion(SegOperador segOperador) {
      LOGGER.log(Level.INFO, "aceptarInvitacion: {0}", segOperador);
      Result result = segOperadorLogic.assertEstadosValidos(segOperador, "REG", "ACEP");
      if (result.isSuccess()) {
         ResultItem<SegOperador> r1 = segOperadorLogic.activarInvitacion(segOperador);
         result.accept(r1);
      }
      if (result.isError()) {
         sessionContext.setRollbackOnly();
      }
      return result;
   }

   @Override
   public ResultItem<SegOperador> rechazarInvitacion(SegOperador segOperador) {
      LOGGER.log(Level.INFO, "rechazarInvitacion: {0}", segOperador);
      Result result = segOperadorLogic.assertEstadoValido(segOperador, "REG");
      if (result.isSuccess()) {
         segOperador.setEstado(Param.create("RECH"));
         segOperador.setFechaRespuesta(new Date());
         ResultItem<SegOperador> r1 = segOperadorLogic.segOperadorMerge(segOperador);
         result.accept(r1);
         segOperador = r1.getValue();
      }
      return new ResultItem<>(segOperador, result);
   }

   @Override
   public ResultItem<SegOperador> inactiverOperador(SegOperador segOperador) {
      LOGGER.log(Level.INFO, "inactiverOperador: {0}", segOperador);
      Result result = segOperadorLogic.assertEstadoValido(segOperador, "ACT");
      if (result.isSuccess()) {
         segOperador.setEstado(Param.create("INAC"));
         segOperador.setFechaHasta(new Date());
         ResultItem<SegOperador> r1 = segOperadorLogic.segOperadorMerge(segOperador);
         result.accept(r1);
         segOperador = r1.getValue();
      }
      return new ResultItem<>(segOperador, result);
   }

   private static final Logger LOGGER = Logger.getLogger(SegOperadorImpl.class.getName());
}
