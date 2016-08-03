package br.com.tenoriogames.core.impl.negocio;

import java.util.Date;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;

public class ValidadorDadosObrigatoriosFornecedor implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Fornecedor){
			Fornecedor fornecedor = (Fornecedor)entidade;
			String CNPJ=fornecedor.getCNPJ();
			String NomeFantasia=fornecedor.getNomeFantasia();
			String RazaoSocial=fornecedor.getRazaoSocial();
			String Telefone=fornecedor.getTelefone();
			String Bairro=fornecedor.getEndereco().getBairro();
			String CEP=fornecedor.getEndereco().getCEP();
			String Cidade=fornecedor.getEndereco().getCidade();
			String Estado=fornecedor.getEndereco().getEstado();
			String Rua=fornecedor.getEndereco().getRua();
			Integer numero=fornecedor.getEndereco().getNumero();
			
			
			
			if(CNPJ==null || NomeFantasia==null || 
					RazaoSocial==null|| Telefone==null
					|| Telefone==null|| Bairro==null
					||CEP==null|| Cidade==null
					|| Estado==null	|| Rua==null
					|| numero == null){
				FacesUtil.adicionarMSGError("Todos os campos  são obrigatórios e devem ser preenchidos corretamente!");
				return  "erro";
			}
			
			if(CNPJ.trim().equals("") || NomeFantasia.trim().equals("") || 
					RazaoSocial.trim().equals("")|| Telefone.trim().equals("")
					|| Bairro.trim().equals("")|| CEP.trim().equals("")
					|| Cidade.trim().equals("")|| Estado.trim().equals("")
					|| Rua.trim().equals("")){
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
