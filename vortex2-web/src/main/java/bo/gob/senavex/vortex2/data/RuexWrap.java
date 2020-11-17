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
package bo.gob.senavex.vortex2.data;

import bo.gob.senavex.vortex2.model.CliCategoria;
import bo.gob.senavex.vortex2.model.CliContacto;
import bo.gob.senavex.vortex2.model.CliDireccion;
import bo.gob.senavex.vortex2.model.CliDocumento;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.RegRegistro;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.model.SegPersona;
import com.hiska.result.Param;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Willyams Yujra
 */
@lombok.Getter
public class RuexWrap {
   private CliDocumento nit;
   private CliDocumento fun;
   private CliDocumento oea;
   private RegRegistro registro;
   private CliEmpresa empresa;
   private CliCategoria categoria;
   private List<CliContacto> contactoList;
   private List<CliDireccion> direccionList;
   private SegOperador representante;
   private List<SegOperador> operadorList;
   private SegOperador operador;
   private String part = "LIST";

   public RuexWrap() {
      registro = new RegRegistro();
      empresa = new CliEmpresa();
      categoria = new CliCategoria();
      categoria.setEsRequerido(true);
      nit = createDocumento(true, "NIT", "Numero de Identificacion Tributaria");
      fun = createDocumento(true, "FUNDEMPRESA", "Codigo Fund Empresa");
      oea = createDocumento(false, "OEA", "Codigo OEA");
      contactoList = new ArrayList<>();
      direccionList = new ArrayList<>();
      representante = new SegOperador();
      operadorList = new ArrayList<>();
      operador = new SegOperador();
   }

   public String getNombreComercial() {
      return fun.getNombre();
   }

   private CliDocumento createDocumento(boolean requerido, String valor, String descripcion) {
      CliDocumento doc = new CliDocumento();
      doc.setEsRequerido(requerido);
      doc.setTipo(Param.create(valor, descripcion));
      doc.setEstado(Param.create("REG", "Registrado"));
      doc.setDescripcion(descripcion);
      return doc;
   }

   public List<CliDocumento> getDocumentoList() {
      return Arrays.asList(nit, fun, oea);
   }

   public List<SegOperador> getAllOperadorList() {
      List<SegOperador> list = new ArrayList<>();
      representante.setEsRequerido(true);
      representante.setEstado(Param.create("ACT"));
      list.add(representante);
      list.addAll(operadorList);
      return list;
   }

   public List<CliCategoria> getCategoriaList() {
      return Arrays.asList(categoria);
   }

   public SegPersona getPersona() {
      return representante.getSegUsuario().getSegPersona();
   }

   public void addOperador() {
      operador.setEsRequerido(true);
      operador.setEstado(Param.create("REG", "Registrado"));
      operadorList.add(operador);
      operador = new SegOperador();
      part = "LIST";
   }

   public void addOperador(SegOperador operador) {
      operador.setEsRequerido(true);
      operador.setEstado(Param.create("REG", "Registrado"));
      operadorList.add(operador);
      this.operador = new SegOperador();
      part = "LIST";
   }

   public void checkOperador(SegOperador ope) {
      operador = ope;
      part = "FORM";
   }

   public void removeOperador(SegOperador ope) {
      operadorList.remove(ope);
   }

   public boolean isPartList() {
      return "LIST".equals(part);
   }

   public boolean isPartForm() {
      return "FORM".equals(part);
   }

   public void openPartForm() {
      part = "FORM";
   }

   public void openPartList() {
      part = "LIST";
   }

   public void dataTest() {
      int nro = (int) (9999 * Math.random());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-HH-mm-ss");
      String count = simpleDateFormat.format(new Date());
      nit.setNombre("EMPRESA_" + count);
      nit.setNumero("00" + nro);
      fun.setNombre("COMERCIAL_" + count);
      fun.setNumero("01" + nro);
   }
}
