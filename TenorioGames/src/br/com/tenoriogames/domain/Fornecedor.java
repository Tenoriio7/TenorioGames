package br.com.tenoriogames.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import br.com.tenoriogames.domain.Endereco;

@Entity
@Table(name = "tb_fornecedor")
@NamedQueries
	({
	@NamedQuery(name="Fornecedor.listar",query="SELECT fornecedor FROM Fornecedor fornecedor"),
	@NamedQuery(name="Fornecedor.buscarPorCNPJ",query="SELECT fornecedor FROM Fornecedor fornecedor WHERE fornecedor.CNPJ=:CNPJ"),
	
	})

public class Fornecedor extends EntidadeDominio{
	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "forn_codigo")
	private long chavePrimaria;
	
	@Column(name = "forn_razao_social", length = 50, nullable = false)
	private String razaoSocial;
	
	@Column(name = "forn_nome_fantasia", length = 50, nullable = false)
	private String nomeFantasia ;
	
	
	@Column(name="forn_CNPJ",unique = true)
	private String CNPJ;
	
	@Column(name="forn_Telefone")
	private String Telefone;
	
	
	@Embedded
	private Endereco endereco = new Endereco();


	public String getRazaoSocial() {
		return razaoSocial;
	}


	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}


	public String getNomeFantasia() {
		return nomeFantasia;
	}


	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}


	public String getCNPJ() {
		return CNPJ;
	}


	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}


	public String getTelefone() {
		return Telefone;
	}


	public void setTelefone(String telefone) {
		Telefone = telefone;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codigo ^ (codigo >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	

	

}
