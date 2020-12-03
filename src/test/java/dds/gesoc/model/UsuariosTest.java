package dds.gesoc.model;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.exceptions.ContraseniaConLongitudCortaException;
import dds.gesoc.exceptions.ContraseniaConNombreUsuarioException;
import dds.gesoc.exceptions.ContraseniaConRepetidosSeguidosException;
import dds.gesoc.exceptions.PeoresContraseniasException;
import dds.gesoc.exceptions.UsuarioConMismoNombreEncontradoException;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.repositorios.RepoUsuarios;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;
import dds.gesoc.model.usuarios.ValidacionContrasenia;
import dds.gesoc.model.usuarios.ValidadorDeContrasenias;

public class UsuariosTest implements WithGlobalEntityManager{

	private Empresa empresaEjemplo;
	private String nombreUsuarioEjemplo;
	private RepoUsuarios repositorio;

	//TODO: arreglar la categoria y el monto esperado, estan null y 0 respectivamente para que no tire error
	@Before
	public void init() {
		nombreUsuarioEjemplo = "usuario1";
		empresaEjemplo = new Empresa("mc Donaldo", null, 0, "Arcos Dorados SRL", "27-12345678-1",
				"Av. Corrientes 5600", new SectorServicios(), 150000);
		repositorio = RepoUsuarios.getInstance();
	}

	@Test(expected = ContraseniaConLongitudCortaException.class)
	public void contraseniaMenorAOchoNoEsAceptada() {
		ValidacionContrasenia.LONGITUD_MINIMA.ejecutar(nombreUsuarioEjemplo, "dds2020");
	}

	@Test(expected = ContraseniaConNombreUsuarioException.class)
	public void contraseniaConNombreUsuarioCaseSensitiveNoEsAceptada() {
		ValidacionContrasenia.SIN_NOMBRE_USUARIO.ejecutar(nombreUsuarioEjemplo, "ddsusuario12020");
	}

	@Test(expected = ContraseniaConNombreUsuarioException.class)
	public void contraseniaConNombreUsuarioCaseInsensitiveNoEsAceptada() {
		ValidacionContrasenia.SIN_NOMBRE_USUARIO.ejecutar(nombreUsuarioEjemplo, "ddsUSUarIO12020");
	}

	@Test(expected = ContraseniaConRepetidosSeguidosException.class)
	public void contraseniaConCaracteresRepetidosSeguidosNoEsAceptada() {
		ValidacionContrasenia.SIN_CARACTERES_REPETIDOS_SEGUIDOS.ejecutar(nombreUsuarioEjemplo, "EstaaaaContra");
	}

	@Test(expected = PeoresContraseniasException.class)
	public void contraseniaDentroDeLasPeoresNoEsAceptada() {
		ValidadorDeContrasenias.getInstance().controlarTopPeoresContrasenias("monkey1");
	}

	@Test
	public void comprobarCreacionDeUsuario() {
		Usuario nuevoUsuarioCreado = new Usuario(nombreUsuarioEjemplo, "ddsgrupo888", TipoUsuario.ADMINISTRADOR, empresaEjemplo);
		Assert.assertNotNull(nuevoUsuarioCreado);
	}

	@Test
	public void usuarioSeAutenticaCorrectamente() {
		Usuario nuevoUsuarioCreado = new Usuario(nombreUsuarioEjemplo, "2020grupo8disenio", TipoUsuario.ESTANDAR, empresaEjemplo);
		Assert.assertTrue(nuevoUsuarioCreado.autenticarUsuario("2020grupo8disenio"));
	}

	@Test
	public void usuarioFallaAlAutenticarse() {
		Usuario nuevoUsuarioCreado = new Usuario(nombreUsuarioEjemplo, "2020grupo8disenio", TipoUsuario.ADMINISTRADOR, empresaEjemplo);
		Assert.assertFalse(nuevoUsuarioCreado.autenticarUsuario("2020grupo8disenoo"));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void noSePuedeCrearUnUsuarioSinEspecificarContrasenia() {
		Usuario nuevoUsuarioCreado = new Usuario(nombreUsuarioEjemplo, null, TipoUsuario.ESTANDAR, empresaEjemplo);
	}
	
	@Test(expected = UsuarioConMismoNombreEncontradoException.class)
	public void noSePuedeCrearUnUsuarioConUnMismoNombre() {
		Usuario unUsuario = new Usuario(nombreUsuarioEjemplo, "2020grupo8disenio", TipoUsuario.ADMINISTRADOR, empresaEjemplo);
		entityManager().getTransaction().begin();
		repositorio.agregarUsuario(unUsuario);
		entityManager().getTransaction().commit();
		Usuario nuevoUsuario = new Usuario(nombreUsuarioEjemplo, "2020grupo7disenio", TipoUsuario.ADMINISTRADOR, empresaEjemplo);
		repositorio.agregarUsuario(nuevoUsuario);
	}
}
