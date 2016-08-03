package br.com.tenoriogames.core.web.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.impl.controle.FachadaVenda;
import br.com.tenoriogames.core.util.CalculoFreteCorreio;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.web.command.ICommand;
import br.com.tenoriogames.core.web.impl.AlterarCommand;
import br.com.tenoriogames.core.web.impl.SalvarCommand;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Item;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.ValeDesconto;
import br.com.tenoriogames.domain.Venda;

@ManagedBean
@ViewScoped
public class VendaBean {
	public List<EntidadeDominio> listaProdutos;
	List<Produto> listaProdutosFiltrados;
	private Venda vendaCadastro;
	private List<Item> listaItens;
	private static Map<String, ICommand> commands;
	// pegando o valor que esta no autenticacaoBean passando para a variavel
	// local o valor do bean
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean =  new AutenticacaoBean();
	HashMap<Long, Integer> decreProduto = new HashMap<>();
	private Long maiorCod = 0L;
	private Long quantidade = 0L;
	public List<EntidadeDominio> bkplistaProdutos;
	private String acao;
	private BigDecimal valorFrete = new BigDecimal("0.0000");
	private Fachada Fachada = new Fachada();
	private List<EntidadeDominio> listaValeDesconto = new ArrayList<>();
	private List<EntidadeDominio> listaValeDescontoUsado = new ArrayList<>();

	public List<EntidadeDominio> getListaValeDesconto() {
		if (listaValeDesconto == null)
			listaValeDesconto = new ArrayList<>();
		return listaValeDesconto;
	}

	public void setListaValeDesconto(List<EntidadeDominio> listaValeDesconto) {
		this.listaValeDesconto = listaValeDesconto;
	}

	public BigDecimal getValorFrete() {

		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		valorFrete = valorFrete.round(new MathContext(4));
		this.valorFrete = valorFrete;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Long getMaiorCod() {
		return maiorCod;
	}

	public void setMaiorCod(Long maiorCod) {
		this.maiorCod = maiorCod;
	}

	public HashMap<Long, Integer> getDecreProduto() {
		return decreProduto;
	}

	public void setDecreProduto(HashMap<Long, Integer> decreProduto) {
		this.decreProduto = decreProduto;
	}

	public Venda getVendaCadastro() {
		if (vendaCadastro == null) {
			vendaCadastro = new Venda();
			vendaCadastro.setValor(new BigDecimal(0));
		}
		return vendaCadastro;
	}

	public VendaBean() {
		/*
		 * Utilizando o command para chamar a fachada e indexando cada command
		 * pela operação garantimos que esta servelt atenderá qualquer operação
		 */
		commands = new HashMap<String, ICommand>();
		commands.put("Salvar", new SalvarCommand());
		commands.put("Editar", new AlterarCommand());
		try {
			listaProdutos = Fachada.listar(new Produto());
			bkplistaProdutos = Fachada.listar(new Produto());

				List<EntidadeDominio> listaRecebeDesconto = Fachada.listar(new ValeDesconto());
				for (EntidadeDominio vale : listaRecebeDesconto) {
					ValeDesconto valeCorrente = (ValeDesconto) vale;
					if (valeCorrente.getStatus().equals("Disponivel")) {
						listaValeDesconto.add(valeCorrente);
					}

				}
			

		} catch (RuntimeException ex) {

			FacesUtil.adicionarMSGError("Erro ao tentar listar os  Produtos:" + ex.getMessage());

		}
	}

	public void setVendaCadastro(Venda vendaCadastro) {
		this.vendaCadastro = vendaCadastro;
	}

	public List<Item> getListaItens() {
		if (listaItens == null)
			listaItens = new ArrayList<>();
		return listaItens;
	}

	public void setListaItens(List<Item> listaItens) {
		this.listaItens = listaItens;
	}

	public List<Produto> getListaProdutosFiltrados() {
		return listaProdutosFiltrados;
	}

	public void setListaProdutosFiltrados(List<Produto> listaProdutosFiltrados) {
		this.listaProdutosFiltrados = listaProdutosFiltrados;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public List<EntidadeDominio> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<EntidadeDominio> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public void inserirvalorfrete() {
		valorFrete = new BigDecimal(CalculoFreteCorreio.CalcularFrete(vendaCadastro));
		valorFrete = valorFrete.setScale(2, 2);
		vendaCadastro.setValor(vendaCadastro.getValor().add((valorFrete)));
	}

	public void adicionar(Produto produto) {
		int posicaoEncontrada = -1; // começa -1 pra ser nenhuma posição
		Produto produtoTemp = null;
		
		// verifica se o produto que irá ser adicionado ja esta na lista de itens
		for (int pos = 0; pos < listaItens.size() && posicaoEncontrada < 0; pos++) {
			Item itemTemp = listaItens.get(pos);

			if (itemTemp.getProduto().equals(produto)) {
				// ja encontrou o produto, pega a posição dele
				posicaoEncontrada = pos;

			}

		}

		for (Item item : listaItens) {
			if (item.getProduto().equals(produto)) {
				produtoTemp = new Produto();
				produtoTemp = item.getProduto();
			}
		}

		Item item = new Item();
		item.setProduto(produto);
		item.setPrdDescricao(produto.getDescricao());
		vendaCadastro.setPeso(vendaCadastro.getPeso() + produto.getPeso());

		// produto novo
		if (posicaoEncontrada < 0) {
			item.setQuantidade(1);
			
			// verifica se a quantidade vendida é viavel
			if (item.getProduto().getQuantidade().compareTo(new BigDecimal(item.getQuantidade())) == 1) {

				item.setValor(produto.getPreco());
				listaItens.add(item);
				decreProduto.put(item.getProduto().getCodigo(), item.getQuantidade());
				decrementarQuantidade(produto);
				if (item.getProduto().getCodigo() > maiorCod) {
					maiorCod = item.getProduto().getCodigo();

				}
			} else {
				FacesUtil.adicionarMSGError("Quantidade indisponivel");
			}

		}
		// produto ja inserido na lista de itens
		else {
			Item itemTemp = listaItens.get(posicaoEncontrada);
			item.setQuantidade(itemTemp.getQuantidade() + 1);
			if (item.getProduto().getQuantidade().min(new BigDecimal(item.getQuantidade())) != null) {
				item.setValor(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));
				listaItens.set(posicaoEncontrada, item);
				item.setValor(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));
				listaItens.set(posicaoEncontrada, item);
				decreProduto.put(item.getProduto().getCodigo(), item.getQuantidade());
				decrementarQuantidade(produto);
			} else {
				FacesUtil.adicionarMSGError("Quantidade indisponivel");
			}

		}

		vendaCadastro.setValor(vendaCadastro.getValor().add(produto.getPreco()));
	}

	public void remover(Item item) {
		incrementarQuantidade(item.getProduto());
		int posicaoEncontrada = -1;
		for (int pos = 0; pos < listaItens.size(); pos++) {
			Item itemTemp = listaItens.get(pos);

			if (itemTemp.getProduto().equals(item.getProduto())) {
				posicaoEncontrada = pos;

			}

		}
		if (posicaoEncontrada > -1)
			listaItens.remove(posicaoEncontrada);
		vendaCadastro.setValor(vendaCadastro.getValor().subtract(item.getValor()));
		decreProduto.remove(item.getProduto().getCodigo());

	}

	public void carregarDadosVenda() {
		vendaCadastro.setHorario(new Date());
		Usuario usuario;
		usuario = (Usuario) Fachada.buscarGenerico(autenticacaoBean.getUsuarioLogado().getCodigo(), new Usuario());
		vendaCadastro.setUsuario(usuario);

	}

	public void salvar() throws SQLException, IOException, ServletException {
		acao = "Salvar";

		try {

			Long codigodaVenda = FachadaVenda.salvarVenda(vendaCadastro);
			for (Item item : listaItens) {
				item.getVenda().setCodigo(codigodaVenda);
				ICommand command = commands.get(acao);
				command.execute(item);

			}
			// se algum vale descontofoi usado ele será indisponivel
			for (EntidadeDominio vale : listaValeDescontoUsado) {
				ValeDesconto valeRecebe = (ValeDesconto) vale;
				valeRecebe.setStatus("Indisponivel");
				ICommand command = commands.get("Editar");
				command.execute(valeRecebe);

			}

			// pega todas as keys e atribui para utilizar no for de decremento.
			Set<Long> keys = decreProduto.keySet();
			for (Long codigo : keys) {
				acao = "Editar";
				
				// verifica se o codigo possui valor a ser decrementado
				if (decreProduto.get(codigo.longValue()) != null) {
					// pega o valor que será subtraido
					int quantidade = decreProduto.get(codigo.longValue());
					Produto produto = (Produto) Fachada.buscarGenerico(codigo.longValue(), new Produto());
					produto.setQuantidade(produto.getQuantidade().subtract(new BigDecimal(quantidade)));
					ICommand command = commands.get(acao);
					command.execute(produto);
				}

			}

			vendaCadastro.setValor(vendaCadastro.getValor().round(new MathContext(4)));
			valorFrete = valorFrete.round(new MathContext(4));

			FacesUtil.adicionarMSGInfo("Venda Salva com sucesso");
			redirecionaBean.direcionarPagSeguro(vendaCadastro, listaItens, valorFrete);

		}	 catch (RuntimeException e) {
			 FacesUtil.adicionarMSGError("Erro ao Salvar venda:"
			 +e.getMessage());
		}

	}

	private void decrementarQuantidade(Produto produto) {

		produto.setQuantidade(produto.getQuantidade().subtract(new BigDecimal(1)));

		for (int i = 0; i < listaProdutos.size(); i++) {
			if (produto.equals(listaProdutos.get(i))) {
				listaProdutos.remove(i);
				listaProdutos.add(i, produto);

			}
		}

	}

	private void incrementarQuantidade(Produto produto) {
		
		bkplistaProdutos = Fachada.listar(new Produto());

		for (int i = 0; i < listaProdutos.size(); i++) {
			
			if (produto.equals(listaProdutos.get(i))) {
				Produto auxP = (Produto) listaProdutos.get(i);
				for (int j = 0; j < bkplistaProdutos.size(); j++) {
					if (listaProdutos.get(i).equals(bkplistaProdutos.get(j))) {
						listaProdutos.add(i, bkplistaProdutos.get(j));
						listaProdutos.remove(i + 1);

					}
				}

			}
		}

	}

	public void carregarValeDesconto() {
		List<EntidadeDominio> listaRecebe = new ArrayList<>();
		listaRecebe = Fachada.listar(new ValeDesconto());
		for (EntidadeDominio valeDesconto : listaRecebe) {
			if (valeDesconto instanceof ValeDesconto) {

				if (((ValeDesconto) valeDesconto).getUsuario()
						.getCodigo() == (autenticacaoBean.getUsuarioLogado().getCodigo())) {
					listaValeDesconto.add(valeDesconto);
				}
			}
		}
	}

	public void utilizarValeDesconto(ValeDesconto vale) {
		if (listaItens.size() < 1) {
			FacesUtil.adicionarMSGError("Insira produtos ao Carrinho");
			return;
		} else {

			// maior ou igual ao valor da venda ( < 1 ( que vale pelo 0 e - 1)
			if (vale.getValor().compareTo(vendaCadastro.getValor()) < 1) {
				vendaCadastro.setValor(vendaCadastro.getValor().subtract(vale.getValor()));
				for (int i = 0; i < listaValeDesconto.size(); i++) {
					if (listaValeDesconto.get(i).getCodigo() == vale.getCodigo()) {

						ValeDesconto valeCorrente = (ValeDesconto) listaValeDesconto.get(i);
						listaValeDesconto.add(i, valeCorrente);
						valeCorrente.setValor(new BigDecimal("0.0"));
						listaValeDescontoUsado.add(valeCorrente);
						listaValeDesconto.remove(i);

					}
				}
			}
		}

	}
	
	
	public void limparDados(){
		vendaCadastro=  new Venda();
		listaItens =  new ArrayList<>();
		valorFrete = new BigDecimal("0.00");
		
	}

}
