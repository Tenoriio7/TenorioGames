package br.com.tenoriogames.core.impl.negocio;

import java.math.BigDecimal;
import java.util.Date;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;

public class ValidadorDadosObrigatoriosVenda implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Venda){
			Venda venda = (Venda)entidade;
			Usuario usuario=venda.getUsuario();
			BigDecimal valor=venda.getValor();
			
			
			
			
			if(usuario==null || valor==null){
				FacesUtil.adicionarMSGError("Todos os campos  são obrigatórios e devem ser preenchidos corretamente!");
				return  "erro";
			}
			
			
		}else{
			FacesUtil.adicionarMSGError("Os Dados devem ser preenchidos corretamente");
			return  "erro";
		}
		
		return null;
	}

}
