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

import bo.gob.senavex.vortex2.data.ConfigWrap;
import bo.gob.senavex.vortex2.data.DepositoPart;
import lombok.*;
import java.util.List;
import java.io.Serializable;
import javax.inject.Inject;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;
import com.hiska.result.Result;
import com.hiska.result.ResultFilter;
import com.hiska.result.ResultItem;
import com.hiska.faces.MessageUtil;
import com.hiska.faces.ViewKeeped;
import bo.gob.senavex.vortex2.serv.RegRegistroServ;
import bo.gob.senavex.vortex2.model.RegRegistro;
import bo.gob.senavex.vortex2.filter.RegRegistroFilter;
import bo.gob.senavex.vortex2.model.DocArchivo;
import com.hiska.faces.ContextUtil;
import com.hiska.result.Document;
import com.hiska.result.Filter;
import java.io.IOException;

@Getter
@ToString
@ManagedBean(name = "_regRegistro")
@ViewScoped
@ViewKeeped("/registro")
public class RegRegistroBean implements Serializable {
   @Inject
   private RegRegistroServ regRegistroServ;
   @Setter
   @ManagedProperty("#{_acceso.config}")
   private ConfigWrap config;
   private RegRegistro regRegistro;
   private List<RegRegistro> regRegistroList;
   private final RegRegistroFilter regRegistroFilter = new RegRegistroFilter();
   @Setter
   private int current = 1;
   private DepositoPart part = new DepositoPart();

   @PostConstruct
   public void init() {
      regRegistroFilterApplyListener();
   }
   // ============== FILTER ACTION ==============

   public boolean regRegistroFilterApplyListener() {
      if (config.isUsuarioExterno()) {
         regRegistroFilter.setIdEmpresa(Filter.create(config.getIdEmpresas()));
      }
      ResultFilter<RegRegistro> result = regRegistroServ.regRegistroFilter(regRegistroFilter);
      MessageUtil.processResult(result);
      regRegistroList = result.getValue();
      regRegistroFilter.setPagination(result.getPagination());
      return true;
   }

   public boolean regRegistroFilterCleanListener() {
      regRegistroFilter.clean();
      regRegistroFilterApplyListener();
      return true;
   }
   // ============== INBOX ACTION ==============

   public void descargarArchivoAction(DocArchivo docRespaldo) {
      Document doc = new Document();
      doc.setFileName(docRespaldo.getNombre());
      doc.setContent(docRespaldo.getContenido());
      try {
         ContextUtil.downloadDocument("inline", doc);
      } catch (IOException e) {
         MessageUtil.error("Error al descargar el archivo", e);
      }
   }

   public String reloadAction(RegRegistro row, String action) {
      ResultItem<RegRegistro> result = regRegistroServ.regRegistroReloadAll(row);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = result.getValue();
      });
      return result.isSuccess() ? action : null;
   }

   public String retornarAction() {
      regRegistro = null;
      return "inbox";
   }
   // ============== SERVICE ACTION ==============

   public String cancelarRegistroAction() {
      Result result = regRegistroServ.cancelarRegistro(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String rechazarDocumentosAction() {
      Result result = regRegistroServ.rechazarDocumentos(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String aceptarDocumentosAction() {
      Result result = regRegistroServ.aceptarDocumentos(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String corregirDocumentosAction() {
      Result result = regRegistroServ.corregirDocumentos(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String guardarDepositosAction() {
      Result result = regRegistroServ.guardarDepositos(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String rechazarDepositosAction() {
      Result result = regRegistroServ.rechazarDepositos(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }

   public String aceptarDepositosAction() {
      Result result = regRegistroServ.aceptarDepositos(regRegistro);
      MessageUtil.processResult(result);
      result.ifSuccess(() -> {
         regRegistro = null;
         regRegistroFilter.paginationReload();
         regRegistroFilterApplyListener();
      });
      return result.isError() ? null : "inbox";
   }
}
