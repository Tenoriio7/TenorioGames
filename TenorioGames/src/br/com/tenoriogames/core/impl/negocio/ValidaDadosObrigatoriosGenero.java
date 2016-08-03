package br.com.tenoriogames.core.impl.negocio;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Genero;

public class ValidaDadosObrigatoriosGenero implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Genero){
			Genero genero = (Genero)entidade;
			String descricao = genero.getDescricao();
		
			
			
			
			if(descricao==null){
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
