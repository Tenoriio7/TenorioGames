package br.com.tenoriogames.core.web.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.impl.controle.FachadaVenda;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.web.command.ICommand;
import br.com.tenoriogames.core.web.impl.AlterarCommand;
import br.com.tenoriogames.core.web.impl.ExcluirCommand;
import br.com.tenoriogames.core.web.impl.SalvarCommand;
import br.com.tenoriogames.domain.Devolucao;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Genero;
import br.com.tenoriogames.domain.Item;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.ValeDesconto;
import br.com.tenoriogames.domain.Venda;

@ManagedBean
@ViewScoped
public class DevolucaoBean extends EntidadeDominio {

	private static Map<String, ICommand> commands;
	private Devolucao devolucao;
	private Long codigoVenda = 0L;
	private Long codigoProduto;
	private Long codigoItem;
	private List<EntidadeDominio> listaDevolucoes = new ArrayList<>();;
	private List<EntidadeDominio> listaDevolucoesFiltrados;
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	private String acao;
	private Fachada Fachada = new Fachada();
	// esse  atributo tem como função armazenar o valor da devolução
	private BigDecimal valor;  
	private String status;


	public DevolucaoBean() {

		commands = new HashMap<String, ICommand>();
		commands.put("Salvar", new SalvarCommand());
		commands.put("Excluir", new ExcluirCommand());
		commands.put("Editar", new AlterarCommand());
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public List<EntidadeDominio> getListaDevolucoesFiltrados() {
		if (listaDevolucoesFiltrados == null)
			listaDevolucoesFiltrados = new ArrayList<>();
		return listaDevolucoesFiltrados;
	}

	public void setListaDevolucoesFiltrados(List<EntidadeDominio> listaDevolucoesFiltrados) {
		this.listaDevolucoesFiltrados = listaDevolucoesFiltrados;
	}

	public List<EntidadeDominio> getListaDevolucoes() {
		if (listaDevolucoes == null)
			listaDevolucoes = new ArrayList<>();
		return listaDevolucoes;
	}

	public void setListaDevolucoes(List<EntidadeDominio> listaDevolucoes) {
		this.listaDevolucoes = listaDevolucoes;
	}

	public static Map<String, ICommand> getCommands() {
		return commands;
	}

	public static void setCommands(Map<String, ICommand> commands) {
		DevolucaoBean.commands = commands;
	}

	public Devolucao getDevolucao() {
		if (devolucao == null)
			devolucao = new Devolucao();
		return devolucao;
	}

	public void setDevolucao(Devolucao devolucao) {
		this.devolucao = devolucao;
	}

	public Long getCodigoVenda() {
		return codigoVenda;
	}

	public void setCodigoVenda(Long codigoVenda) {
		this.codigoVenda = codigoVenda;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Long getCodigoItem() {
		return codigoItem;
	}

	public void setCodigoItem(Long codigoItem) {
		this.codigoItem = codigoItem;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public void salvar() {
		try {// Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			devolucao.setStatus("Novo");
			devolucao.setUsuario(autenticacaoBean.getUsuarioLogado());
			devolucao.getVenda().setCodigo(codigoVenda);
			devolucao.getProduto().setCodigo(codigoProduto);
			devolucao.setValor(valor);

			/*
			 * Executa o command que chamará a fachada para executar a operação
			 * requisitada o retorno é uma instância da classe resultado que
			 * pode conter mensagens derro ou entidades de retorno
			 */
			command.execute(devolucao);
			devolucao = new Devolucao();

		} catch (RuntimeException ex) {

			FacesUtil.adicionarMSGError("Erro ao tentar incluir Devolução:" + ex.getMessage());

		}

	}

	public void carregarListaDevolucao() {
		List<EntidadeDominio> listaRecebe = new ArrayList<>();
		listaRecebe = Fachada.listar(new Devolucao());
		Devolucao devolucao = new Devolucao();
		
		
		// se o usuario for admiministrador ele terá acesso a todas as devoluções
		if (autenticacaoBean.getUsuarioLogado().getStatus().equals("admin")) {
			for (int i = 0; i < listaRecebe.size(); i++) {
				devolucao = (Devolucao) listaRecebe.get(i);
				listaDevolucoes.add(devolucao);
			}

		} 
			// se não, ele tera apenas aquelas que terá seu codigo
		else {
			for (int i = 0; i < listaRecebe.size(); i++) {
				devolucao = (Devolucao) listaRecebe.get(i);
				if (devolucao.getUsuario().getCodigo() == autenticacaoBean.getUsuarioLogado().getCodigo()) {
					listaDevolucoes.add(devolucao);
				}
			}

		}
	}

	public void carregarCadastro(){
		try{
			String valor = FacesUtil.getParam("devCod");
			if(valor != null)
			{
				Long codigo = Long.parseLong(valor);
				devolucao=(Devolucao) Fachada.buscarGenerico(codigo, new Devolucao());
			}
		
		} catch(RuntimeException ex){
			
		}
	}

	public void editarDevolucao() {
		try
		{
			//Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(acao);
			/*Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro 
			 * ou entidades de retorno			 */
			command.execute(devolucao);
			
			
		}catch(RuntimeException ex)
		{
			
			FacesUtil.adicionarMSGError("Erro ao tentar editar"+ex.getMessage());
			
		}
		
		if(devolucao.getStatus().equals("Finalizada")){
		acao =  "Salvar";
		ValeDesconto desconto =  new ValeDesconto();
		desconto.getUsuario().setCodigo(devolucao.getUsuario().getCodigo());
		desconto.setValor(valor);
		
		desconto.setStatus("Disponivel");
		ICommand command = commands.get(acao);
		command.execute(desconto);
		
		desconto = new ValeDesconto();
		}
		Produto produto =  (Produto) Fachada.buscarGenerico(devolucao.getProduto().getCodigo(), new Produto());
		produto.setQuantidade(produto.getQuantidade().add(new BigDecimal("1.0")));
		ICommand command = commands.get("Editar");
		command.execute(produto);
		devolucao= new Devolucao();
		
		

	}
}
