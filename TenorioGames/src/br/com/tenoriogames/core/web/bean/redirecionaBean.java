package br.com.tenoriogames.core.web.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.tenoriogames.core.DAO.ProdutoDAO;
import br.com.tenoriogames.core.DAO.UsuarioDAO;
import br.com.tenoriogames.core.util.Pagamento;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;

@ManagedBean

public class redirecionaBean {
	
	public static String direcionarPagSeguro(Venda venda, List<br.com.tenoriogames.domain.Item> listaItem, BigDecimal frete) throws IOException{
	
		
		String url = Pagamento.criarPagamento(venda, listaItem, frete);
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		externalContext.redirect(url);
		
		
		return url;
	}
	

}
