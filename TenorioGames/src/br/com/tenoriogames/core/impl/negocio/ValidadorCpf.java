package br.com.tenoriogames.core.impl.negocio;

import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;

public class ValidadorCpf implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		
		StringBuffer retorno = null;

		// Verifica se a classe passada no Parametro eh um objeto Pessoa
		if (entidade instanceof Usuario) {
			retorno = new StringBuffer();
			Usuario usuario = (Usuario) entidade;
			String cpf = usuario.getCPF();

			// retirando ponto e traço do cpf
			String novoCpf = cpf.replace(".", "");
			novoCpf = novoCpf.replace("-", "");
			int totalPosicoes = novoCpf.length();
			int aux = 10;
			int somaPrimeiroDigito = 0;
			int somaSegundoDigito = 0;

			// se não tiver 11 digito sai retorna FALSE
			if (totalPosicoes != 11) {
				retorno.append("CPF inválido");
				FacesUtil.adicionarMSGError("CPF Invalido");
				return retorno.toString();
			}

			if (novoCpf.equals("11111111111") || novoCpf.equals("22222222222") || novoCpf.equals("33333333333")
					|| novoCpf.equals("44444444444") || novoCpf.equals("55555555555") || novoCpf.equals("66666666666")
					|| novoCpf.equals("77777777777") || novoCpf.equals("88888888888") || novoCpf.equals("99999999999")
					|| novoCpf.equals("00000000000")) {
				retorno.append("CPF inválido");
				FacesUtil.adicionarMSGError("CPF Invalido");
				return retorno.toString();
			}

			// efetua o calculo do primeiro Digito
			for (int i = 0; i < 9; i++) {
				somaPrimeiroDigito += Integer.parseInt(String.valueOf(novoCpf.charAt(i))) * aux;
				aux = aux - 1;
			}

			int primeiroDigito = 11 - (somaPrimeiroDigito % 11);

			// verifica se o resultado deu 10 ou 11, se sim primeiro digito
			// recebe 0
			if (primeiroDigito == 11 || primeiroDigito == 10) {
				primeiroDigito = 0;
			}

			// valida o primerio digito
			if (Integer.parseInt(String.valueOf(novoCpf.charAt(9))) != primeiroDigito) {
				retorno.append("CPF inválido");
				FacesUtil.adicionarMSGError("CPF Invalido");
				return retorno.toString();
			}
			aux = 11; // aux recebe o valor 11 para calcular o segundo digito

			// efetua o calculo do segundo digito
			for (int i = 0; i < 10; i++) {
				somaSegundoDigito += Integer.parseInt(String.valueOf(novoCpf.charAt(i))) * aux;
				aux = aux - 1;
			}

			int segundoDigito = 11 - (somaSegundoDigito % 11);
			// verifica se o resultado deu 10 ou 11, se sim primeiro digito
			// recebe 0
			if (segundoDigito == 11 || segundoDigito == 10) {
				segundoDigito = 0;
			}

			// valida o segundo digito
			if (Integer.parseInt(String.valueOf(novoCpf.charAt(10))) != segundoDigito) {
				retorno.append("CPF inválido");
				FacesUtil.adicionarMSGError("CPF Invalido");
				return retorno.toString();
			}

			return null;

		}
		return null;
	}

}
