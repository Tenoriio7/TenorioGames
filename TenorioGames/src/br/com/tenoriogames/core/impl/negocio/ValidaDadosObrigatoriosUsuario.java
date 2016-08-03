package br.com.tenoriogames.core.impl.negocio;

import java.util.Date;

import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Usuario;

public class ValidaDadosObrigatoriosUsuario implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Usuario){
			Usuario usuario = (Usuario)entidade;
			String console = usuario.getConsole();
			String CPF = usuario.getCPF();
			String email = usuario.getEmail();
			String bairro=usuario.getEndereco().getBairro();
			String CEP=usuario.getEndereco().getCEP();
			String cidade=usuario.getEndereco().getCidade();
			String estado=usuario.getEndereco().getEstado();
			String rua=usuario.getEndereco().getRua();
			Integer numero=usuario.getEndereco().getNumero();
			Integer idade = usuario.getIdade();
			String nome = usuario.getNome();
			String RG = usuario.getRG();
			String senha = usuario.getSenha();
			String sexo = usuario.getSexo();
			String sobrenome = usuario.getSobrenome();
			String status = usuario.getStatus();
			String telefone = usuario.getTelefone();
			
			
			
			if(console==null || CPF==null || 
					email==null|| bairro==null
					|| CEP==null|| cidade==null
					||estado==null|| rua==null
					|| numero==null	|| idade==null
					|| nome == null
					|| RG==null || senha==null
					|| sexo==null || sobrenome==null
					|| status==null || telefone==null){
				FacesUtil.adicionarMSGError("Todos os campos  são obrigatórios e devem ser preenchidos corretamente!");
				return  "erro";
			}
			
			if(console.trim().equals("") || CPF.trim().equals("") || 
					email.trim().equals("")|| bairro.trim().equals("")
					|| bairro.trim().equals("")|| CEP.trim().equals("")
					|| CEP.trim().equals("")|| cidade.trim().equals("")
					|| estado.trim().equals("") || rua.trim().equals("")
					|| nome.trim().equals("") || RG.trim().equals("")
					|| senha.trim().equals("") || sexo.trim().equals("")
					|| sobrenome.trim().equals("") || status.trim().equals("")
					|| telefone.trim().equals("")){
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
