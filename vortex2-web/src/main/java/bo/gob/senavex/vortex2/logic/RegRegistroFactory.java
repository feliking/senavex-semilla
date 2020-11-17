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
package bo.gob.senavex.vortex2.logic;

import bo.gob.senavex.vortex2.data.RuexWrap;
import bo.gob.senavex.vortex2.i16d.data.FEResponse;
import bo.gob.senavex.vortex2.i16d.data.SINResponse;
import bo.gob.senavex.vortex2.model.CliCategoria;
import bo.gob.senavex.vortex2.model.CliContacto;
import bo.gob.senavex.vortex2.model.CliDireccion;
import bo.gob.senavex.vortex2.model.CliEmpresa;
import bo.gob.senavex.vortex2.model.SegOperador;
import bo.gob.senavex.vortex2.model.SegPersona;
import bo.gob.senavex.vortex2.model.SegUsuario;
import com.hiska.result.Param;
import java.util.Arrays;
import java.util.List;

/**
 * @author yracnet
 */
public class RegRegistroFactory {
   public static void createAutocompletar(RuexWrap wrap, SegUsuario usuario, SINResponse r1, FEResponse r2) {
      CliEmpresa cliEmpresa = wrap.getEmpresa();
      cliEmpresa.setRefKey(wrap.getNit().getNumero());
      cliEmpresa.setRefName(r2.getNombreComercial());
      // ------------------------------------
      cliEmpresa.setEstado(Param.create("VERF"));
      cliEmpresa.setActividad(r1.getActividadPrincipal());
      cliEmpresa.setTipo(r2.getTipoConstitucion());
      cliEmpresa.setDescripcion(r2.getDescripcion());
      cliEmpresa.setFechaFundacion(r2.getFechaFundacion());
      cliEmpresa.setFechaOperacion(r2.getFechaOperacion());
      cliEmpresa.setAfiliaciones(Arrays.asList(Param.create("CAINCO"), Param.create("CNI")));
      // ------------------------------------
      createCliDireccion(wrap, r1.getDireccion(), r2.getDireccion());
      createCliCategoria(wrap);
      createCliContacto(wrap);
      createSegOperadorRL(wrap, usuario, null, null);
      // createCliDocumento(cliEmpresa, ruexWrap);
   }

   public static void createRegistroSimple(RuexWrap wrap, SegUsuario usuario) {
      CliEmpresa cliEmpresa = wrap.getEmpresa();
      cliEmpresa.setRefKey(wrap.getNit().getNumero());
      cliEmpresa.setRefName(wrap.getNombreComercial());
      // ------------------------------------
      cliEmpresa.setEstado(Param.create("REG"));
      cliEmpresa.setAfiliaciones(Arrays.asList(Param.create("CAINCO"), Param.create("CNI")));
      // ------------------------------------
      createCliDireccion(wrap, null, null);
      createCliCategoria(wrap);
      createCliContacto(wrap);
      createSegOperadorRL(wrap, usuario, null, null);
      // createCliDocumento(cliEmpresa, ruexWrap);
   }

   private static void createSegOperadorRL(RuexWrap ruexWrap, SegUsuario usuario, SegPersona sin, SegPersona fun) {
      SegOperador operador = ruexWrap.getRepresentante();
      if (sin == null && fun == null) {
         operador.setEsRequerido(true);
         operador.setSegUsuario(usuario);
         operador.setCorreoElectronico(usuario.getCorreoElectronico());
         SegPersona p = usuario.getSegPersona();
         operador.setNumeroDocumento(p.getDocumentoIdentidad());
         operador.setNombreCargo("Gerente General");
         operador.setPerfil(Param.create("RL", "Representante Legal"));
         operador.setEstado(Param.create("ACT", "Activado"));
      }
      if (sin != null) {
         operador.setEsRequerido(true);
      }
   }

   private static void createCliDireccion(RuexWrap ruexWrap, CliDireccion sin, CliDireccion fun) {
      List<CliDireccion> cliDireccionList = ruexWrap.getDireccionList();
      cliDireccionList.clear();
      if (sin == null && fun == null) {
         CliDireccion d1 = new CliDireccion();
         d1.setEsRequerido(true);
         d1.setNombre("Direccion Fiscal");
         d1.setEsRequerido(true);
         cliDireccionList.add(d1);
      }
      if (sin != null) {
         sin.setNombre("Direccion Fiscal");
         sin.setEsRequerido(true);
         cliDireccionList.add(sin);
      }
   }

   private static void createCliCategoria(RuexWrap ruexWrap) {
      CliCategoria cat = ruexWrap.getCategoria();
      cat.setCategoria(Param.NONE);
      cat.setEstado(Param.create("REG"));
   }

   private static void createCliContacto(RuexWrap ruexWrap) {
      List<CliContacto> cliContactoList = ruexWrap.getContactoList();
      cliContactoList.clear();
      CliContacto email = new CliContacto();
      email.setEsRequerido(true);
      email.setTipo(Param.create("EMAIL", "Correo electronico"));
      email.setEstado(Param.create("REG"));
      cliContactoList.add(email);
      CliContacto site = new CliContacto();
      site.setEsRequerido(true);
      site.setTipo(Param.create("SITE", "Sitio WEB"));
      site.setEstado(Param.create("REG"));
      cliContactoList.add(site);
      CliContacto fax = new CliContacto();
      fax.setEsRequerido(true);
      fax.setTipo(Param.create("FAX", "Numero FAX"));
      fax.setEstado(Param.create("REG"));
      cliContactoList.add(fax);
      CliContacto fono = new CliContacto();
      fono.setEsRequerido(true);
      fono.setTipo(Param.create("FONO", "Numero Telefonicio"));
      fono.setEstado(Param.create("REG"));
      cliContactoList.add(fono);
   }

   public static void testData(RuexWrap ruexWrap) {
      // ------------------------
      CliCategoria cat = ruexWrap.getCategoria();
      cat.setNroEmpleados(Param.create("<9"));
      cat.setActivosUfv(Param.create("<150K"));
      cat.setVentasUfv(Param.create("<0.6M"));
      cat.setExportacionesUfv(Param.create("<750K"));
      cat.setExportacionesRubros(Arrays.asList(Param.create("1"), Param.create("4")));
      // -------------------------
      SegOperador o1;
      o1 = createSegOperador("wyujra@senavex.gob.bo", "Responsable", "ART", "REG");
      ruexWrap.getOperadorList().add(o1);
      o1 = createSegOperador("yr1@gmail.com", "Temporal", "ART", "ACT");
      ruexWrap.getOperadorList().add(o1);
      o1 = createSegOperador("yr2@gmail.com", "Consultor", "ART", "REG");
      ruexWrap.getOperadorList().add(o1);
      // ------------------------
      List<CliContacto> cliContactoList = ruexWrap.getContactoList();
      CliContacto email = cliContactoList.get(0);
      CliContacto site = cliContactoList.get(1);
      CliContacto fax = cliContactoList.get(2);
      CliContacto fono = cliContactoList.get(3);
      email.setNombre("Correo Contacto");
      email.setValor("info@info.info");
      email.setDescripcion("Correo informativo");
      site.setNombre("Portal Corporativo");
      site.setValor("https;//info.info");
      site.setDescripcion("Sitio Oficial");
      fax.setNombre("Numero Piloto");
      fax.setValor("2220154");
      fax.setDescripcion("Fax 08:00am - 16:00pm");
      fono.setNombre("Call Center");
      fono.setValor("800-10-0000");
      fono.setDescripcion("Numero piloto a nivel nacional");
   }

   private static SegOperador createSegOperador(String correo, String cargo, String perfil, String estado) {
      SegOperador o1 = new SegOperador();
      o1.setCorreoElectronico(correo);
      o1.setNumeroDocumento("00000LP");
      o1.setNombreCargo(cargo);
      o1.setPerfil(Param.create(perfil));
      o1.setEstado(Param.create(estado, estado));
      return o1;
   }
}
