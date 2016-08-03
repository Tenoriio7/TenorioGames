package br.com.tenoriogames.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tb_Genero")
@NamedQueries
	({
	@NamedQuery(name="Genero.listar",query="SELECT Genero FROM Genero Genero")
	})

public class Genero  extends EntidadeDominio{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codigo ^ (codigo >>> 32));
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		Genero other = (Genero) obj;
		if (codigo != other.codigo)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "gen_codigo")
	private long chavePrimaria;
	
	//not empty serve para string e  deve sempre ter o message
	@NotEmpty(message="O campo descrição é obrigatório")
	// valida antes da solicitação do banco
	@Size(min =5, max=50, message="Tamanho invalido para o campo descrição de 5 - 50 caracteres")
	@Column(name = "gen_descricao", length = 50, nullable = false)
	private String descricao;

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Genero [codigo=" + codigo + ", descricao=" + descricao + "]";
	}

}
