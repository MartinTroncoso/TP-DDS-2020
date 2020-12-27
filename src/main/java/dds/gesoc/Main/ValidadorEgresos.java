package dds.gesoc.Main;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.repositorios.RepoEgresos;

public class ValidadorEgresos implements WithGlobalEntityManager, TransactionalOps {

	public static void main(String[] args) {
		new ValidadorEgresos().init();
	}

	private void init() {
		withTransaction(() -> {
			RepoEgresos.getInstance().validarEgresos();
		});
	}

}
