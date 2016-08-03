package br.com.tenoriogames.core.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.impl.controle.FachadaVenda;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.Devolucao;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Item;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.Venda;

@ManagedBean
@ViewScoped
public class GestaoVendaBean {
	private List<EntidadeDominio> listaVendas = new ArrayList<>();
	private List<EntidadeDominio> listaVendasFiltradas = new ArrayList<>();
	private Long codigo;
	private Venda vendaCadastro = new Venda();
	private List<Produto> listaProdutoVenda = new ArrayList<>();
	private List<Item> listaitens = new ArrayList<>();
	private FachadaVenda fachada = new FachadaVenda();
	private String status;
	Fachada Fachada = new Fachada();
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	
	public List<EntidadeDominio> getListaVendasFiltradas() {
		return listaVendasFiltradas;
	}
	public void setListaVendasFiltradas(List<EntidadeDominio> listaVendasFiltradas) {
		this.listaVendasFiltradas = listaVendasFiltradas;
	}
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}
	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Item> getListaitens() {
		return listaitens;
	}

	public void setListaitens(List<Item> listaitens) {
		this.listaitens = listaitens;
	}

	public Venda getVendaCadastro() {
		if (vendaCadastro == null) {
			vendaCadastro = new Venda();
		}
		return vendaCadastro;
	}

	public void setVendaCadastro(Venda vendaCadastro) {
		this.vendaCadastro = vendaCadastro;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public List<EntidadeDominio> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<EntidadeDominio> listaVendas) {
		this.listaVendas = listaVendas;
	}

	public void carregarVendas() {

		listaVendas = Fachada.listar(new Venda());
		

	}

	public void carregarItens() {

		List<EntidadeDominio> listaRecebe = new ArrayList<>();
		listaRecebe = Fachada.listar(new Item());
		listaitens = new ArrayList<>();
		for (int i = 0; i < listaRecebe.size(); i++) {

			Item item = (Item) listaRecebe.get(i);
			if (item.getVenda().getCodigo().equals(codigo) ) {
				listaitens.add(item);
			}
		}

	}
	
	
	
	public void carregarVendasUsuario() {

		List<EntidadeDominio> listaRecebe = new ArrayList<>();
		listaRecebe = Fachada.listar(new Venda());
		listaVendas = new ArrayList<>();
		
		for (int i = 0; i < listaRecebe.size(); i++) {

			Venda  venda = (Venda) listaRecebe.get(i);
			if (venda.getUsuario().getCodigo()== autenticacaoBean.getUsuarioLogado().getCodigo() ) {
				listaVendas.add(venda);
			}
		}

	}
	
	

	@SuppressWarnings({ "unchecked", "static-access" })
	public void carregarCadastro() {
		try {
			String valor = FacesUtil.getParam("venCod");
			if (valor != null) {
				Long codigo = Long.parseLong(valor);
				vendaCadastro = (Venda) Fachada.buscarGenerico(codigo, new Venda());
				listaitens = (List<Item>) Fachada.buscarGenerico(vendaCadastro.getCodigo(), new Item());
				for (EntidadeDominio item : listaitens) {

					listaProdutoVenda.add((Produto) Fachada.buscarGenerico(((Produto) item).getCodigo(), new Produto()));
				}

			}

		} catch (RuntimeException ex) {

		}
	}

	public void editarVenda() {
		vendaCadastro = (Venda) Fachada.buscarGenerico(codigo, new Venda());
		vendaCadastro.setStatus(status);
		FachadaVenda.alterarGenerico(vendaCadastro);
		

	}

}
