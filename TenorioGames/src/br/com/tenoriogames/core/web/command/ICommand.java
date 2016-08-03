package br.com.tenoriogames.core.web.command;

import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Resultado;

public interface ICommand  {
	public Resultado execute(EntidadeDominio entidade);

}
