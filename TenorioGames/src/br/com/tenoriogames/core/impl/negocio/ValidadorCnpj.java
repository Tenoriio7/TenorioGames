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
			    // l�gica de neg�cio ...
			    validator.assertValid(cnpj);
			    // continua��o da l�gica de neg�cio ...
			} catch (InvalidStateException e) { // exception lan�ada quando o documento � inv�lido
			    FacesUtil.adicionarMSGError("CNPJ invalido");
			    return "erro";
			}
	}
		return null;
	}
}
