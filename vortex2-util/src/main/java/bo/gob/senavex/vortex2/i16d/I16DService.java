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
package bo.gob.senavex.vortex2.i16d;

import bo.gob.senavex.vortex2.i16d.data.FEResponse;
import bo.gob.senavex.vortex2.i16d.data.SINResponse;
import bo.gob.senavex.vortex2.model.CliDireccion;
import bo.gob.senavex.vortex2.model.CliDocumento;
import bo.gob.senavex.vortex2.model.SegPersona;
import com.hiska.result.Message;
import com.hiska.result.Param;
import com.hiska.result.Result;
import com.hiska.result.ResultItem;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author Willyams Yujra
 */
@Stateless
@LocalBean
public class I16DService {
   public Result segipVerificar(SegPersona segPersona) {
      Result result = new Result();
      Message message = new Message();
      message.setCode("SOAP-1001");
      message.setDescription("Error la consul ser");
      message.addCause("Servicio mnop disponible");
      message.addCause("Nombre de la person cno es igual");
      message.addCause("Nombre de la person cno es igual");
      message.addCause("Nombre de la person cno es igual");
      message.addCause("Nombre de la person cno es igual");
      result.addMessage(message);
      result.setSuccess(true); // forzar ok
      return result;
   }

   public ResultItem<FEResponse> fundempresaVerificar(CliDocumento cliDocumento) {
      ResultItem<FEResponse> result = new ResultItem<>();
      FEResponse response = new FEResponse();
      SegPersona segPersona = new SegPersona();
      CliDireccion cliDireccion = new CliDireccion();
      // ------------------
      response.setNombreComercial(cliDocumento.getNombre());
      response.setCodigo(cliDocumento.getNumero());
      response.setTipoConstitucion(Param.create("SA", "Sociedad Anonima"));
      response.setDescripcion("Informacion rescata de FUND-EMPRESA");
      response.setFechaFundacion("1990");
      response.setFechaOperacion("1991");
      // ------------------
      cliDocumento.setEstado(Param.create("VERF", "Verificado"));
      cliDocumento.setVigenciaDesde(new Date());
      cliDocumento.setVigenciaHasta(new Date());
      response.setDocumento(cliDocumento);
      // ------------------
      segPersona.setPrimerApellido("????");
      segPersona.setSegundoApellido("????");
      segPersona.setTipoDocumento(Param.create("CI"));
      segPersona.setNumeroDocumento("123456789");
      segPersona.setExpedicionDocumento(Param.create("LP", "La Paz"));
      response.setRepresentante(segPersona);
      // ------------------
      cliDireccion.setEstado(Param.create("REG", "Registrado"));
      cliDireccion.setTipo(Param.create("OFI", "OFICINA"));
      cliDireccion.setDepartamento(Param.create("LP", "La Paz"));
      cliDireccion.setMunicipio(Param.create("La Paz", "La Paz"));
      cliDireccion.setDomicilio("s/n...");
      cliDireccion.setEdificio("s/n...");
      cliDireccion.setOficina("s/n...");
      cliDireccion.setAvenida("s/n...");
      cliDireccion.setPiso("s/n...");
      cliDireccion.setCalles("s/n...");
      cliDireccion.setGeoReferencia("LT0.0-LG0.0");
      cliDireccion.setReferencia("Sin referencia....");
      cliDireccion.setNombre("Domicilio Comercial");
      cliDireccion.setEsRequerido(false);
      response.setDireccion(cliDireccion);
      // ------------------
      result.setValue(response);
      return result;
   }

   public ResultItem<SINResponse> sinVerificar(CliDocumento cliDocumento) {
      ResultItem<SINResponse> result = new ResultItem<>();
      SINResponse response = new SINResponse();
      SegPersona segPersona = new SegPersona();
      CliDireccion cliDireccion = new CliDireccion();
      // ------------------
      cliDocumento.setEstado(Param.create("VERF", "Verificado"));
      cliDocumento.setVigenciaDesde(new Date());
      cliDocumento.setVigenciaHasta(new Date());
      response.setDocumento(cliDocumento);
      // ------------------
      response.setNit(cliDocumento.getNumero());
      response.setRazonSocial(cliDocumento.getNombre());
      response.setTipoContribuyente(Param.create("PRO", "Producto"));
      response.setGranActividad(Param.create("PRO", "Producto"));
      response.setActividadPrincipal(Param.create("PRO", "Producto"));
      // ------------------
      cliDireccion.setEstado(Param.create("VERF", "Verificado"));
      response.setDireccion(cliDireccion);
      // ------------------
      segPersona.setPrimerApellido("????");
      segPersona.setSegundoApellido("????");
      segPersona.setTipoDocumento(Param.create("CI"));
      segPersona.setNumeroDocumento("123456789");
      segPersona.setExpedicionDocumento(Param.create("LP", "La Paz"));
      response.setRepresentante(segPersona);
      // ------------------
      cliDireccion.setEstado(Param.create("REG", "Registrado"));
      cliDireccion.setTipo(Param.create("OFI", "OFICINA"));
      cliDireccion.setDepartamento(Param.create("LP", "La Paz"));
      cliDireccion.setMunicipio(Param.create("La Paz", "La Paz"));
      cliDireccion.setDomicilio("s/n...");
      cliDireccion.setEdificio("s/n...");
      cliDireccion.setOficina("s/n...");
      cliDireccion.setAvenida("s/n...");
      cliDireccion.setPiso("s/n...");
      cliDireccion.setCalles("s/n...");
      cliDireccion.setGeoReferencia("LT0.0-LG0.0");
      cliDireccion.setReferencia("Sin referencia....");
      cliDireccion.setNombre("Domicilio Fiscal");
      cliDireccion.setEsRequerido(true);
      response.setDireccion(cliDireccion);
      // ------------------
      result.setValue(response);
      return result;
   }
}
