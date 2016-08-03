package br.com.tenoriogames.domain;

import javax.persistence.Embeddable;


@Embeddable
public class Endereco extends EntidadeDominio  {	
	private String Rua;
	private Integer Numero;

	private String Bairro;

	private String Estado;	

	private String Cidade;	

	private String CEP;
	

	public String getRua() {
		return Rua;
	}

	public void setRua(String rua) {
		Rua = rua;
	}

	public Integer getNumero() {
		return Numero;
	}

	public void setNumero(Integer numero) {
		Numero = numero;
	}

	public String getBairro() {
		return Bairro;
	}

	public void setBairro(String bairro) {
		Bairro = bairro;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public String getCidade() {
		return Cidade;
	}

	public void setCidade(String cidade) {
		Cidade = cidade;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	@Override
	public String toString() {
		return "Endereco [Rua=" + Rua + ", Numero=" + Numero + ", Bairro=" + Bairro + ", Estado="
				+ Estado + ", Cidade=" + Cidade + ", CEP=" + CEP + "]";
	}	
	
	

}
