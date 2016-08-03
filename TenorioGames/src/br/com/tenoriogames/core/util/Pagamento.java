package br.com.tenoriogames.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.Test;

import br.com.tenoriogames.core.DAO.ProdutoDAO;
import br.com.tenoriogames.core.DAO.UsuarioDAO;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class Pagamento {
	private static final String CEP_ORIGEM = "08751300";
	private static final String PESO = "10.000"; // alterar posteriormente

	

	
	public static String criarPagamento(Venda venda, List<br.com.tenoriogames.domain.Item> listaItem, BigDecimal frete){
		String response = "";
		
		Checkout checkout = new Checkout();
		for(br.com.tenoriogames.domain.Item item : listaItem){
			Item itemPagamento = new Item();
			itemPagamento.setAmount((item.getValor()));
			itemPagamento.setDescription(item.getProduto().getDescricao());
			itemPagamento.setId(item.getProduto().getCodigo().toString());
			itemPagamento.setQuantity(item.getQuantidade());
			itemPagamento.setWeight(item.getProduto().getPeso().longValue());// Definir peso para os produtos
			checkout.addItem(itemPagamento);
		}
		checkout.setShippingCost((frete));
		checkout.setShippingAddress(
				"BRA",
				venda.getUsuario().getEndereco().getEstado(), 
				venda.getUsuario().getEndereco().getCidade(),
				venda.getUsuario().getEndereco().getBairro(),
				venda.getUsuario().getEndereco().getCEP(),
				venda.getUsuario().getEndereco().getRua(),
				venda.getUsuario().getEndereco().getNumero().toString(),
				""
				);
		checkout.setShippingType(ShippingType.SEDEX);
		checkout.setSender(
			venda.getUsuario().getNome(),
			venda.getUsuario().getEmail(),
			"",
			venda.getUsuario().getTelefone(),
			DocumentType.CPF,
			venda.getUsuario().getCPF()
		);
		checkout.setCurrency(Currency.BRL);
		try {  
			  
		  boolean onlyCheckoutCode = false;  
		  response = checkout.register(PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode); 
		    
		} catch (PagSeguroServiceException e) {  
			  
			System.out.println("aqui");
		    e.printStackTrace();
		    System.out.println("aqui");
		}  
		return response;
	}
	
	@Test
	 public  void agoravai(){
	  
		UsuarioDAO dao = new UsuarioDAO();
		ProdutoDAO dao1 = new  ProdutoDAO();
		Venda venda  =  new  Venda();
		venda.setCodigo(1L);
	
		venda.setPeso(1D);
		Usuario usuario =  dao.buscarPorCodigo(1L);
		venda.setUsuario(usuario);
		venda.setValor(new BigDecimal(10.0));
		venda.setValor(venda.getValor().round(new MathContext(4)));
		List<br.com.tenoriogames.domain.Item>listaItem =  new ArrayList<>();
		br.com.tenoriogames.domain.Item item = new br.com.tenoriogames.domain.Item();
		Produto produto = (Produto) dao1.buscarPorCodigo(1L);
		
		item.setCodigo(1L);
		item.setQuantidade(1);
		item.setValor(new BigDecimal(10.11));
		item.setValor(item.getValor().round(new MathContext(4)));
		item.setVenda(venda);
		item.setProduto(produto);
		listaItem.add(item);
	
		BigDecimal frete= new BigDecimal("10.11");
		frete=frete.round(new  MathContext(4));
		
		String aux = Pagamento.criarPagamento(venda, listaItem, frete);
		System.out.println(aux);
		
		
	}
	


	
}
