package br.com.tenoriogames.core.impl.negocio;

import java.math.BigDecimal;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Genero;
import br.com.tenoriogames.domain.Produto;

public class ValidaDadosObrigatoriosProduto implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Produto){
			Produto produto = (Produto)entidade;
			String descricao = produto.getDescricao();
			Long codigoForn = produto.getFornecedor().getCodigo();
			Long codigoGen = produto.getGenero().getCodigo();
			Double peso = produto.getPeso();
			String plataforma = produto.getPlataforma();
			BigDecimal preco = produto.getPreco();
			BigDecimal  quantidade = produto.getQuantidade();
			String setor = produto.getSetor();
			
			// realizado esse processo para verificar a quantidade
			
			if(descricao==null || codigoForn==null
			|| codigoGen==null || peso==null
			|| plataforma==null || preco==null
			|| quantidade==null || setor==null){
				FacesUtil.adicionarMSGError("Todos os campos  são obrigatórios e devem ser preenchidos corretamente!");
				return  "erro";
			}
			
			if(descricao.trim().equals("")){
				FacesUtil.adicionarMSGError("Todos os campos são obrigatórios e devem ser preenchidos corretamente!");
				return  "erro";
			}
		
			
		}else{
			FacesUtil.adicionarMSGError("Os Dados devem ser preenchidos corretamente");
			return  "erro";
		}
		
		return null;
	}

}
