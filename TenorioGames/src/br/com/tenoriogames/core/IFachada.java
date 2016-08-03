package br.com.tenoriogames.core;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Resultado;



public interface IFachada {

	public Resultado salvar(EntidadeDominio entidade);
	public Resultado alterar(EntidadeDominio entidade);
	public Resultado excluir(EntidadeDominio entidade);
	public Long salvarVenda(EntidadeDominio entidade);
	public Resultado consultar(EntidadeDominio entidade);
	
	
	
}
