 package br.com.tenoriogames.core.main;
import br.com.tenoriogames.core.util.HibernateUtil;

public class GeraTabela {

	public static void main(String[] args) {
		
		// pega a sessão
		HibernateUtil.getSessionFactory();
		// finaliza a sessão
		HibernateUtil.getSessionFactory().close();
	}

}
