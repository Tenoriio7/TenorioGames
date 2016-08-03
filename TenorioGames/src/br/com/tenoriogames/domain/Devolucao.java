package br.com.tenoriogames.domain;

import java.math.BigDecimal;

public class Devolucao extends EntidadeDominio{
	
	private Usuario usuario =  new Usuario() ;
	private Venda venda =  new Venda();
	private Produto produto =  new Produto();
	private BigDecimal valor ;
	private String Status;
	
	public Produto getProduto() {
		if(produto == null)
			produto = new  Produto();
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Usuario getUsuario() {
		if(usuario ==null){
			usuario =  new  Usuario();
		}
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Venda getVenda() {
		if(venda == null){
			venda= new  Venda();
		}
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	
	

}
