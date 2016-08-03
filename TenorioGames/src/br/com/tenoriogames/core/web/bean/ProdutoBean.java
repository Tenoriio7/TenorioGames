package br.com.tenoriogames.core.web.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.web.command.ICommand;
import br.com.tenoriogames.core.web.impl.AlterarCommand;
import br.com.tenoriogames.core.web.impl.ExcluirCommand;
import br.com.tenoriogames.core.web.impl.SalvarCommand;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Genero;
import br.com.tenoriogames.domain.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean  extends EntidadeDominio {
	private Produto ProdutoCadastro;
	List<EntidadeDominio> listaProdutos;
	List<Produto> listaProdutosFiltrados;
	List<EntidadeDominio>listaFornecedores;
	List<EntidadeDominio>listaGeneros;	
	Fachada Fachada =  new Fachada();
	private String acao;
	private Long codigo;
	private static Map<String, ICommand> commands;
	
	public ProdutoBean(){
		/* Utilizando o command para chamar a fachada e indexando cada command 
		 * pela operação garantimos que esta servelt atenderá qualquer operação */
		commands = new HashMap<String, ICommand>();
		commands.put("Salvar", new SalvarCommand());
		commands.put("Excluir", new ExcluirCommand());
		commands.put("Editar", new AlterarCommand());
	}
	
	public List<EntidadeDominio> getListaGeneros() {
		return listaGeneros;
	}
	public void setListaGeneros(List<EntidadeDominio> listaGeneros) {
		this.listaGeneros = listaGeneros;
	}
	public static Map<String, ICommand> getCommands() {
		return commands;
	}
	public static void setCommands(Map<String, ICommand> commands) {
		ProdutoBean.commands = commands;
	}
	public List<EntidadeDominio> getListaFornecedores() {
		return listaFornecedores;
	}
	public void setListaFornecedores(List<EntidadeDominio> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}
	
	
	
	public Produto getProdutoCadastro() {
		if(ProdutoCadastro ==null)
			ProdutoCadastro= new Produto();
		
		return ProdutoCadastro;
	}
	public void setProdutoCadastro(Produto ProdutoCadastro) {
		this.ProdutoCadastro = ProdutoCadastro;
	}
	
	public List<EntidadeDominio> getListaProdutos() {
		return listaProdutos;
	}
	
	public void setListaProdutos(List<EntidadeDominio> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	public List<Produto> getListaProdutosFiltrados() {
		return listaProdutosFiltrados;
	}
	public void setListaProdutosFiltrados(List<Produto> listaProdutosFiltrados) {
		this.listaProdutosFiltrados = listaProdutosFiltrados;
	}
	
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public void novo()
	{
		ProdutoCadastro = new Produto();
	}
	
	public void salvar()
	{
		ProdutoCadastro.setStatus("Ativo");
		try
		{
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno*/
			command.execute(ProdutoCadastro);
			ProdutoCadastro = new Produto();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar incluir Produto:"+ex.getMessage());
			
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
			 * ou entidades de retorno
			 */
			command.execute(ProdutoCadastro);

			ProdutoCadastro = new Produto();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar Excluir Produto:"+ex.getMessage());
			
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
			 * ou entidades de retorno
			 */
			command.execute(ProdutoCadastro);
			ProdutoCadastro = new Produto();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar incluir Produto:"+ex.getMessage());
			
		}
		
	}
	
	public void manipular()
	{
		String statusPro;
		if(ProdutoCadastro.getStatus().equals("Ativo")){
			 statusPro = "Desativado";
		}
		else{
			 statusPro = "Ativo";
		}
		try
		{
			ProdutoCadastro.setStatus(statusPro);
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno
			 */
			command.execute(ProdutoCadastro);
			ProdutoCadastro = new Produto();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar incluir Produto:"+ex.getMessage());
			
		}
		
		
	}
	
	
	public void carregarCadastro(){
		try{
			if(codigo != null)
			{
				ProdutoCadastro=(Produto) Fachada.buscarGenerico(codigo, new Produto());
				
			}
		
		} catch(RuntimeException ex){
			
		}
	}
	
	public void carregarPesquisa()
	{
		try
		{
			
			listaProdutos = Fachada.listar(new Produto());		
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar listar os  Produtoes:"+ex.getMessage());
			
		}
	}

		public void carregarPesquisaFornecedor()
		{
			try
			{
				
				listaFornecedores = Fachada.listar(new Fornecedor());			
			}catch(RuntimeException ex)
			{
				
				FacesUtil.adicionarMSGError("Erro ao tentar listar os  Fornecedores:"+ex.getMessage());
				
			}
	}
	
		public void carregarPesquisaGeneros()
		{
			try
			{
				
				listaGeneros = Fachada.listar(new Genero());			
			}catch(RuntimeException ex)
			{
				
				FacesUtil.adicionarMSGError("Erro ao tentar listar os  Generos:"+ex.getMessage());
				
			}
	}
	

	

}
