
package br.com.tenoriogames.core.web.impl;

import br.com.tenoriogames.core.web.command.ICommand;
import br.com.tenoriogames.core.IFachada;
import br.com.tenoriogames.core.impl.controle.Fachada;



public abstract class AbstractCommand implements ICommand {

	protected IFachada fachada = new Fachada();

}
