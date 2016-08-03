package br.com.tenoriogames.core.impl.negocio;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Produto;

public class ValidadorQtdProduto implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Produto){
			Produto c = (Produto)entidade;
			
//			if(c.getQuantidade() < 1){
//				return "Quantidade deve ser no minimo 1!";
//			}
			
		}else{
			return "Quantidade não pode ser válidado, pois entidade não é um produto!";
		}
		
		
		return null;
	}

}
