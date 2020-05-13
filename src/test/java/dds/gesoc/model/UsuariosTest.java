package dds.gesoc.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.gesoc.exceptions.AtributoNullException;
import dds.gesoc.exceptions.ContraseniaConLongitudCortaException;
import dds.gesoc.exceptions.ContraseniaConNombreUsuarioException;
import dds.gesoc.exceptions.ContraseniaConRepetidosSeguidosException;
import dds.gesoc.exceptions.PeoresContraseniasException;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;
import dds.gesoc.model.usuarios.UsuarioBuilder;
import dds.gesoc.model.usuarios.ValidacionContrasenia;

public class UsuariosTest {

	private UsuarioBuilder usuario;

	@Before
	public void init() {
		usuario = new UsuarioBuilder();
		usuario.especificarNombreUsuario("usuario1");
		usuario.especificarEntidad(new Empresa("mc Donaldo", "Arcos Dorados SRL", "27-12345678-1",
				"Av. Corrientes 5600", new SectorServicios(), 150000));
	}

	@Test(expected = ContraseniaConLongitudCortaException.class)
	public void contraseniaMenorAOchoNoEsAceptada() {
		ValidacionContrasenia.LONGITUD_MINIMA.ejecutar(usuario.getNombreUsuario(), "dds2020");
	}

	@Test(expected = ContraseniaConNombreUsuarioException.class)
	public void contraseniaConNombreUsuarioCaseSensitiveNoEsAceptada() {
		ValidacionContrasenia.SIN_NOMBRE_USUARIO.ejecutar(usuario.getNombreUsuario(), "ddsusuario12020");
	}

	@Test(expected = ContraseniaConNombreUsuarioException.class)
	public void contraseniaConNombreUsuarioCaseInsensitiveNoEsAceptada() {
		ValidacionContrasenia.SIN_NOMBRE_USUARIO.ejecutar(usuario.getNombreUsuario(), "ddsUSUarIO12020");
	}

	@Test(expected = ContraseniaConRepetidosSeguidosException.class)
	public void contraseniaConCaracteresRepetidosSeguidosNoEsAceptada() {
		ValidacionContrasenia.SIN_CARACTERES_REPETIDOS_SEGUIDOS.ejecutar(usuario.getNombreUsuario(), "EstaaaaContra");
	}

	@Test(expected = PeoresContraseniasException.class)
	public void contraseniaDentroDeLasPeoresNoEsAceptada() {
		usuario.controlarTopPeoresContrasenias("monkey1");
	}

	@Test
	public void comprobarContraseniaValida() {
		usuario.especificarContrasenia("ddsgrupo82020");
		Assert.assertNotNull(usuario.getContrasenia());
	}

	@Test
	public void comprobarCreacionDeUsuario() {
		Usuario nuevoUsuarioCreado;
		usuario.especificarContrasenia("ddsgrupo888");
		usuario.especificarTipoUsuario(TipoUsuario.ADMINISTRADOR);
		nuevoUsuarioCreado = usuario.crearUsuario();
		Assert.assertNotNull(nuevoUsuarioCreado);
	}

	@Test
	public void usuarioSeAutenticaCorrectamente() {
		Usuario nuevoUsuarioCreado;
		usuario.especificarContrasenia("2020grupo8disenio");
		usuario.especificarTipoUsuario(TipoUsuario.ESTANDAR);
		nuevoUsuarioCreado = usuario.crearUsuario();
		Assert.assertTrue(nuevoUsuarioCreado.autenticarUsuario("2020grupo8disenio"));
	}

	@Test
	public void usuarioFallaAlAutenticarse() {
		Usuario nuevoUsuarioCreado;
		usuario.especificarContrasenia("2020grupo8disenio");
		usuario.especificarTipoUsuario(TipoUsuario.ADMINISTRADOR);
		nuevoUsuarioCreado = usuario.crearUsuario();
		Assert.assertFalse(nuevoUsuarioCreado.autenticarUsuario("2020grupo8disenoo"));
	}

	@Test(expected = AtributoNullException.class)
	public void noSePuedeCrearUnUsuarioSinEspecificarContrasenia() {
		usuario.especificarTipoUsuario(TipoUsuario.ESTANDAR);
		Usuario nuevoUsuarioCreado = usuario.crearUsuario();
	}
}
