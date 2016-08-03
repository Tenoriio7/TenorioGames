package br.com.tenoriogames.core.web.bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.web.command.ICommand;
import br.com.tenoriogames.core.web.impl.AlterarCommand;
import br.com.tenoriogames.core.web.impl.ConsultarCommand;
import br.com.tenoriogames.core.web.impl.ExcluirCommand;
import br.com.tenoriogames.core.web.impl.SalvarCommand;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Resultado;
import br.com.tenoriogames.domain.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {
	private Usuario UsuarioCadastro;
	List<EntidadeDominio> listaUsuarios;
	List<EntidadeDominio> listaUsuariosFiltrados;
	private String acao;
	private Long codigo;
	private static Map<String, ICommand> commands;
	Fachada Fachada =  new Fachada();
	
	public UsuarioBean(){
		/* Utilizando o command para chamar a fachada e indexando cada command 
		 * pela operação garantimos que esta servelt atenderá qualquer operação */
		commands = new HashMap<String, ICommand>();
		commands.put("Salvar", new SalvarCommand());
		commands.put("Excluir", new ExcluirCommand());
		commands.put("Editar", new AlterarCommand());
		commands.put("Consultar", new ConsultarCommand());
	}
	
	
	public String getAcao() {
		return acao;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
		
	
	public Usuario getUsuarioCadastro() {
		if(UsuarioCadastro ==null)
			UsuarioCadastro= new Usuario();
			UsuarioCadastro.setStatus("ativo");
		
		return UsuarioCadastro;
	}
	public void setUsuarioCadastro(Usuario UsuarioCadastro) {
		this.UsuarioCadastro = UsuarioCadastro;
	}
	
	public List<EntidadeDominio> getListaUsuarios() {
		return listaUsuarios;
	}
	
	public void setListaUsuarios(List<EntidadeDominio> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	public List<EntidadeDominio> getListaUsuariosFiltrados() {
		return listaUsuariosFiltrados;
	}
	public void setListaUsuariosFiltrados(List<EntidadeDominio> listaUsuariosFiltrados) {
		this.listaUsuariosFiltrados = listaUsuariosFiltrados;
	}
	
	public void novo()
	{
		UsuarioCadastro = new Usuario();
	}
	
	public void salvar()
	{
		try
		{
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno*/
			command.execute(UsuarioCadastro);
			UsuarioCadastro = new Usuario();
			
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar incluir Usuario:"+ex.getMessage());
			
		}
		
	}
	
	public void carregarPesquisa()
	{
		try
		{
			listaUsuarios = Fachada.listar(new Usuario());			
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar listar os  Usuarios:"+ex.getMessage());
			
		}
	}
	
	public void carregarCadastro(){
		try{
			String valor = FacesUtil.getParam("usrCod");
			if(valor != null)
			{
				Long codigo = Long.parseLong(valor);
				UsuarioCadastro=(Usuario) Fachada.buscarGenerico(codigo, new Usuario());
			}
		
		} catch(RuntimeException ex){
			
		}
	}
	
	public void excluir()
	{
		try
		{
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno*/
			command.execute(UsuarioCadastro);
			UsuarioCadastro = new Usuario();
			
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar remover Usuario:"+ex.getMessage());
			
		}
		
	}
	
	
	public void editar()
	{
		try
		{
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno*/
			command.execute(UsuarioCadastro);
			UsuarioCadastro = new Usuario();
			
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar editar Usuario:"+ex.getMessage());
			
		}
		
	}
	
	public void filtrarUsuarioCPF() throws IOException{
		ICommand command = commands.get("Consultar");
		 Resultado resultado =command.execute(UsuarioCadastro);
		 if(resultado.getMsg() == "Informação invalida")
		 {
			 FacesUtil.adicionarMSGError("CPF Cadastrado ou invalido");
		 }
		 else
		 {
			 ExternalContext externalContext = FacesContext.getCurrentInstance()
						.getExternalContext();
				externalContext.redirect("/TenorioGames/Pages/Usuario/usuarioCadastro.xhtml?usrAcao=Salvar");
			 
		 }
		
	}
	
	

}
