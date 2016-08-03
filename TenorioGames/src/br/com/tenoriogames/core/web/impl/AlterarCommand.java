
package br.com.tenoriogames.core.web.impl;

import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Resultado;


public class AlterarCommand extends AbstractCommand{

	
	public Resultado execute(EntidadeDominio entidade) {
		
		return fachada.alterar(entidade);
	}

}
