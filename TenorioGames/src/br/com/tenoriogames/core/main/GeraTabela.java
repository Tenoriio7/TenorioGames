 package br.com.tenoriogames.core.main;
import br.com.tenoriogames.core.util.HibernateUtil;

public class GeraTabela {

	public static void main(String[] args) {
		
		// pega a sess�o
		HibernateUtil.getSessionFactory();
		// finaliza a sess�o
		HibernateUtil.getSessionFactory().close();
	}

}
