
package br.com.tenoriogames.core.web.impl;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Resultado;


public class ListarCommand extends AbstractCommand{

	
	public Resultado execute(EntidadeDominio entidade) {
		
		Fachada fachada =  new Fachada();
		return (Resultado) fachada.listar(entidade);
	}

}
