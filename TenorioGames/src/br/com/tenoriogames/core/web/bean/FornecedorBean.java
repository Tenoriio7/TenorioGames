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
import br.com.tenoriogames.core.web.impl.ListarCommand;
import br.com.tenoriogames.core.web.impl.SalvarCommand;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;

@ManagedBean
@ViewScoped
public class FornecedorBean {
	private Fornecedor FornecedorCadastro;
	List<EntidadeDominio> listaFornecedors;
	List<Fornecedor> listaFornecedorsFiltrados;
	private String acao;
	private Long codigo;
	private String CNPJ;
	private static Map<String, ICommand> commands;
	
	public FornecedorBean(){
		/* Utilizando o command para chamar a fachada e indexando cada command 
		 * pela operação garantimos que esta servelt atenderá qualquer operação */
		commands = new HashMap<String, ICommand>();
		commands.put("Salvar", new SalvarCommand());
		commands.put("Excluir", new ExcluirCommand());
		commands.put("Editar", new AlterarCommand());
		commands.put("Editar", new ListarCommand());
	}
	
	public String getCNPJ() {
		return CNPJ;
	}
	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}
	
	public Fornecedor getFornecedorCadastro() {
		if(FornecedorCadastro ==null)
			FornecedorCadastro= new Fornecedor();
		
		return FornecedorCadastro;
	}
	public void setFornecedorCadastro(Fornecedor FornecedorCadastro) {
		this.FornecedorCadastro = FornecedorCadastro;
	}
	
	public List<EntidadeDominio> getListaFornecedors() {
		return listaFornecedors;
	}
	
	public void setListaFornecedors(List<EntidadeDominio> listaFornecedors) {
		this.listaFornecedors = listaFornecedors;
	}
	
	public List<Fornecedor> getListaFornecedorsFiltrados() {
		return listaFornecedorsFiltrados;
	}
	public void setListaFornecedorsFiltrados(List<Fornecedor> listaFornecedorsFiltrados) {
		this.listaFornecedorsFiltrados = listaFornecedorsFiltrados;
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
		FornecedorCadastro = new Fornecedor();
	}
	
	public void salvar()
	{
		try
		{
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno			 */
			command.execute(FornecedorCadastro);
			FornecedorCadastro = new Fornecedor();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar incluir Fornecedor:"+ex.getMessage());
			
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
			command.execute(FornecedorCadastro);

			FornecedorCadastro = new Fornecedor();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar Excluir Fornecedor:"+ex.getMessage());
			
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
			command.execute(FornecedorCadastro);
			FornecedorCadastro = new Fornecedor();
			
			
		}catch(RuntimeException ex)
		{
			ex.printStackTrace();
			FacesUtil.adicionarMSGError("Erro ao tentar incluir Fornecedor:"+ex.getMessage());
			
		}
		
	}
	
	public void carregarCadastro(){
		try{
			if(codigo != null)
			{
				FornecedorCadastro=(Fornecedor) Fachada.buscarGenerico(codigo,new Fornecedor());
				
			}
		
		} catch(RuntimeException ex){
			
		}
	}
	
	public void carregarPesquisa()
	{
		try
		{
			FornecedorCadastro = new Fornecedor();
			Fachada  fachada = new  Fachada();
			listaFornecedors = fachada.listar(new Fornecedor());
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar listar os  fornecedores:"+ex.getMessage());
			
		}
	}
	
	public void teste(){
		
		FacesUtil.adicionarMSGInfo("teste");
	}
	
	
	
	

}
