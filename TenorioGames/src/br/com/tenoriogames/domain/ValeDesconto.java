package br.com.tenoriogames.domain;

import java.math.BigDecimal;

public class ValeDesconto extends EntidadeDominio {
	
	private BigDecimal valor;
	private Usuario usuario;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Usuario getUsuario() {
		if(usuario ==null)
			usuario =  new  Usuario();
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
