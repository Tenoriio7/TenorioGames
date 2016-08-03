package br.com.tenoriogames.domain;

public class EntidadeDominio {
	
	protected Long codigo;
	public Long getCodigo() {
		if(codigo == null)
			codigo=0L;
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
