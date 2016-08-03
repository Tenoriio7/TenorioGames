package br.com.tenoriogames.core.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tenoriogames.core.impl.controle.FachadaVenda;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Item;

@ManagedBean
@ViewScoped
public class VerItensBean {
	private List<EntidadeDominio> listaItens =  new ArrayList<>();
	
	public List<EntidadeDominio> getListaItens() {
		return listaItens;
	}
	public void setListaItens(List<EntidadeDominio> listaItens) {
		this.listaItens = listaItens;
	}

	public void carregarItens(){
		FachadaVenda fachadaVenda =  new FachadaVenda();
		listaItens =  fachadaVenda.listarGenerico(new Item());
	}
}
