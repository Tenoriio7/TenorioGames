package br.com.tenoriogames.core.web.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.Usuario;

@ManagedBean
@SessionScoped  // diferença :  ele vai existir durante todo tempo de sessão
public class AutenticacaoBean {

	private Usuario usuarioLogado;
	Fachada Fachada =  new Fachada();
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null){
				usuarioLogado = new Usuario();
		}
		
		return usuarioLogado;
	}
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public String  autenticar(){
		try {
			usuarioLogado=Fachada.autenticar(usuarioLogado.getCPF(), usuarioLogado.getSenha());
			if(usuarioLogado.getCPF()== null){
				FacesUtil.adicionarMSGError("CPF ou Senha invalidos" );
				return null;
			}
			else{
				FacesUtil.adicionarMSGInfo("Usuario autenticado com sucesso");
				if(usuarioLogado.getStatus().equals("ativo"))
				{
					return "/Pages/Vendas/vendaCadastro.xhtml?faces-redirect=true";
				}
				else{
					return "/Pages/Usuario/principalAdmin.xhtml?faces-redirect=true";
				}
				
			}
			
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String  sair (){
		usuarioLogado =  null;
		return "/Pages/Usuario/autenticacao.xhtml?faces-redirect=true";
		
	}
	
}
