package br.com.tenoriogames.core.impl.negocio;

import java.math.BigDecimal;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Produto;

public class ValidaQuantidadeProduto implements IStrategy {

public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Produto){
			Produto produto = (Produto)entidade;
			BigDecimal  quantidade = produto.getQuantidade();
			// realizado esse processo para verificar a quantidade
			Long auxQuantidade = 0L;
			
			if(quantidade != null &&  produto.getCodigo() == 0)
			{String aux =  quantidade.toString();
			 auxQuantidade =  Long.parseLong(aux);
			}

			if(auxQuantidade <=2){
				FacesUtil.adicionarMSGError("O campo quantidade deve ser maior ou igual a 3");
				return  "erro";
			}
		}
			
		else{
			FacesUtil.adicionarMSGError("Os Dados devem ser preenchidos corretamente");
			return  "erro";
		}
		
		return null;
	}

}
