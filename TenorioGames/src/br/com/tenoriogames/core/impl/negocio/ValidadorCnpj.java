package br.com.tenoriogames.core.impl.negocio;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;

public class ValidadorCnpj implements IStrategy {
	private static char[] aCnpj;

	@Override
	public String processar(EntidadeDominio entidade) {

		if (entidade instanceof Fornecedor) {
			Fornecedor fornecedor =  new Fornecedor();
			fornecedor =  (Fornecedor) entidade;
			String cnpj =  fornecedor.getCNPJ();
			CNPJValidator validator = new CNPJValidator();
			
			try {
			    // lógica de negócio ...
			    validator.assertValid(cnpj);
			    // continuação da lógica de negócio ...
			} catch (InvalidStateException e) { // exception lançada quando o documento é inválido
			    FacesUtil.adicionarMSGError("CNPJ invalido");
			    return "erro";
			}
	}
		return null;
	}
}
