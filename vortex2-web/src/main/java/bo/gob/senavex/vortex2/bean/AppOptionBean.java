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

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.Serializable;
import javax.inject.Inject;
import java.util.Collections;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import com.hiska.result.Option;
import com.hiska.result.ResultList;
import com.hiska.faces.MessageUtil;
import bo.gob.senavex.vortex2.serv.AppOptionServ;
import com.hiska.result.Param;
import java.util.ArrayList;
import java.util.Calendar;

@ViewScoped
@ManagedBean(name = "_option")
@lombok.ToString
public class AppOptionBean implements Serializable {
   @Inject
   private AppOptionServ optionServ;
   // ----------------------------------------------------------------

   public List<Option> getSegUsuarioTipoOption() {
      return getListOption("SEG_USUARIO", "VD_TIPO");
   }

   public List<Option> getSegUsuarioEstadoOption() {
      return getListOption("SEG_USUARIO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getSegPersonaGeneroOption() {
      return getListOption("SEG_PERSONA", "VD_GENERO");
   }

   public List<Option> getSegPersonaTipoDocumentoOption() {
      return getListOption("SEG_PERSONA", "VD_TIPO_DOCUMENTO");
   }

   public List<Option> getSegPersonaExpedicionDocumentoOption() {
      return getListOption("SEG_PERSONA", "VD_EXPEDICION_DOCUMENTO");
   }

   public List<Option> getSegPersonaPaisOption() {
      return getListOption("SEG_PERSONA", "VD_PAIS");
   }

   public List<Option> getSegPersonaEstadoOption() {
      return getListOption("SEG_PERSONA", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getSegOperadorPerfilOption() {
      return getListOption("SEG_OPERADOR", "VD_PERFIL");
   }

   public List<Option> getSegOperadorEstadoOption() {
      return getListOption("SEG_OPERADOR", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getSegCorreoTipoOption() {
      return getListOption("SEG_CORREO", "VD_TIPO");
   }

   public List<Option> getSegCorreoPlantillaOption() {
      return getListOption("SEG_CORREO", "VD_PLANTILLA");
   }

   public List<Option> getSegCorreoEstadoOption() {
      return getListOption("SEG_CORREO", "VF_ESTADO");
   }

   // ----------------------------------------------------------------
   public List<Option> getRegSeguimientoTipoOption() {
      return getListOption("REG_SEGUIMIENTO", "VD_TIPO");
   }

   public List<Option> getRegSeguimientoEstadoOption() {
      return getListOption("REG_SEGUIMIENTO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getRegRegistroOrigenOption() {
      return getListOption("REG_REGISTRO", "VD_ORIGEN");
   }

   public List<Option> getRegRegistroTipoOption() {
      return getListOption("REG_REGISTRO", "VD_TIPO");
   }

   public List<Option> getRegRegistroEstadoOption() {
      return getListOption("REG_REGISTRO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getParValorEstadoOption() {
      return getListOption("PAR_VALOR", "VF_ESTADO");
   }

   // ----------------------------------------------------------------
   public List<Option> getParDominioTipoOption() {
      return getListOption("PAR_DOMINIO", "VD_TIPO");
   }

   public List<Option> getParDominioEstadoOption() {
      return getListOption("PAR_DOMINIO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getLogSesionEstadoOption() {
      return getListOption("LOG_SESION", "VF_ESTADO");
   }

   // ----------------------------------------------------------------
   // ----------------------------------------------------------------
   public List<Option> getLogConsultaTipoOption() {
      return getListOption("LOG_CONSULTA", "VD_TIPO");
   }

   public List<Option> getLogConsultaEstadoOption() {
      return getListOption("LOG_CONSULTA", "VD_ESTADO");
   }

   // ----------------------------------------------------------------
   // ----------------------------------------------------------------
   public List<Option> getCtaPagoTipoOption() {
      return getListOption("CTA_PAGO", "VD_TIPO");
   }

   public List<Option> getCtaPagoMonedaOption() {
      return getListOption("CTA_PAGO", "VD_MONEDA");
   }

   public List<Option> getCtaPagoEstadoOption() {
      return getListOption("CTA_PAGO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getCtaDepositoCuentaOption() {
      return getListOption("CTA_DEPOSITO", "VD_CUENTA");
   }

   public List<Option> getCtaDepositoMonedaOption() {
      return getListOption("CTA_DEPOSITO", "VD_MONEDA");
   }

   public List<Option> getCtaDepositoEstadoOption() {
      return getListOption("CTA_DEPOSITO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getCliEmpresaActividadOption() {
      return getListOption("CLI_EMPRESA", "VD_ACTIVIDAD");
   }

   public List<Option> getCliEmpresaTipoOption() {
      return getListOption("CLI_EMPRESA", "VD_TIPO");
   }

   public List<Option> getCliEmpresaAfiliacionesOption() {
      return getListOption("CLI_EMPRESA", "VD_AFILIACIONES");
   }

   public List<Option> getCliEmpresaEstadoOption() {
      return getListOption("CLI_EMPRESA", "VF_ESTADO");
   }

   public List<Option> getCliEmpresaGestionOption() {
      List<Option> value = cache.get("CLI_EMPRESA$GESTION");
      if (value == null) {
         value = new ArrayList<>();
         int year = Calendar.getInstance().get(Calendar.YEAR);
         for (int i = 1990; i <= year; i++) {
            value.add(Option.create("" + i, "" + i));
         }
         cache.put("CLI_EMPRESA$GESTION", value);
      }
      return value;
   }
   // ----------------------------------------------------------------

   public List<Option> getCliDocumentoTipoOption() {
      return getListOption("CLI_DOCUMENTO", "VD_TIPO");
   }

   public List<Option> getCliDocumentoEstadoOption() {
      return getListOption("CLI_DOCUMENTO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getCliDireccionTipoOption() {
      return getListOption("CLI_DIRECCION", "VD_TIPO");
   }

   public List<Option> getCliDireccionDepartamentoOption() {
      return getListOption("CLI_DIRECCION", "VD_DEPARTAMENTO");
   }

   public List<Option> getCliDireccionMunicipioOption() {
      return getListOption("CLI_DIRECCION", "VD_MUNICIPIO");
   }

   public List<Option> cliDireccionMunicipioOption(Param parent) {
      String value = parent == null ? "N" : parent.getValue();
      return getListOptionParent("CLI_DIRECCION", "VD_MUNICIPIO", value);
   }

   public List<Option> getCliDireccionEstadoOption() {
      return getListOption("CLI_DIRECCION", "VF_ESTADO");
   }

   // ----------------------------------------------------------------
   public List<Option> getCliContactoTipoOption() {
      return getListOption("CLI_CONTACTO", "VD_TIPO");
   }

   public List<Option> getCliContactoEstadoOption() {
      return getListOption("CLI_CONTACTO", "VF_ESTADO");
   }
   // ----------------------------------------------------------------

   public List<Option> getCliCategoriaCategoriaOption() {
      return getListOption("CLI_CATEGORIA", "VD_CATEGORIA");
   }

   public List<Option> getCliCategoriaNroEmpleadosOption() {
      return getListOption("CLI_CATEGORIA", "VD_NRO_EMPLEADOS");
   }

   public List<Option> getCliCategoriaActivosUfvOption() {
      return getListOption("CLI_CATEGORIA", "VD_ACTIVOS_UFV");
   }

   public List<Option> getCliCategoriaVentasUfvOption() {
      return getListOption("CLI_CATEGORIA", "VD_VENTAS_UFV");
   }

   public List<Option> getCliCategoriaExportacionesUfvOption() {
      return getListOption("CLI_CATEGORIA", "VD_EXPORTACIONES_UFV");
   }

   public List<Option> getCliCategoriaImportacionesUfvOption() {
      return getListOption("CLI_CATEGORIA", "VD_IMPORTACIONES_UFV");
   }

   public List<Option> getCliCategoriaExportacionesRubrosOption() {
      return getListOption("CLI_CATEGORIA", "VD_EXPORTACIONES_RUBROS");
   }

   public List<Option> getCliCategoriaImportacionesRubrosOption() {
      return getListOption("CLI_CATEGORIA", "VD_IMPORTACIONES_RUBROS");
   }

   public List<Option> getCliCategoriaEstadoOption() {
      return getListOption("CLI_CATEGORIA", "VF_ESTADO");
   }

   private final Map<String, List<Option>> cache = new HashMap<>();

   private List<Option> getListOption(String domain, String classifier) {
      String key = domain + "#" + classifier;
      List<Option> value = cache.get(key);
      if (value == null) {
         ResultList<Option> result = optionServ.optionList(domain, classifier);
         MessageUtil.processResult(result);
         value = result.isError() ? Collections.emptyList() : result.getValue();
         cache.put(key, value);
      }
      return value;
   }

   private List<Option> getListOptionParent(String domain, String classifier, String parent) {
      String key = domain + "#" + classifier + ":" + parent;
      List<Option> value = cache.get(key);
      if (value == null) {
         ResultList<Option> result = optionServ.optionList(domain, classifier, parent);
         MessageUtil.processResult(result);
         value = result.isError() ? Collections.emptyList() : result.getValue();
         cache.put(key, value);
      }
      return value;
   }

   private List<Option> getTreeOption(String domain, String classifier) {
      String key = domain + "#" + classifier;
      List<Option> value = cache.get(key);
      if (value == null) {
         ResultList<Option> result = optionServ.optionTree(domain, classifier);
         MessageUtil.processResult(result);
         value = result.isError() ? Collections.emptyList() : result.getValue();
         cache.put(key, value);
      }
      return value;
   }
}
