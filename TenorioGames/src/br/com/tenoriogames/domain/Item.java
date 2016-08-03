package br.com.tenoriogames.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item extends EntidadeDominio  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
	private BigDecimal valor;
	
	private Integer quantidade;
	
	private String prdDescricao;
	
	private Venda venda;
	
	private Produto produto;


	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Venda getVenda() {
		if (venda == null)
			venda=  new  Venda();
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Produto getProduto() {
		if(produto == null)
			produto =  new Produto();
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public String getPrdDescricao() {
		return prdDescricao;
	}
	
	public void setPrdDescricao(String prdDescricao) {
		this.prdDescricao = prdDescricao;
	}
	@Override
	public String toString() {
		return "Item [codigo=" + codigo + ", valor=" + valor + ", quantidade=" + quantidade + ", venda=" + venda
				+ ", produto=" + produto + "]";
	}
	
	
	
	
}
