package br.com.tenoriogames.core.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Produto;

@ManagedBean
@ViewScoped
public class PrincipalBean {

	private List<EntidadeDominio> listaProdutos;
	private Fachada Fachada = new Fachada();

	public List<EntidadeDominio> getListaProdutos() {
		if (listaProdutos == null)
			listaProdutos = new ArrayList<>();
		return listaProdutos;
	}

	public void setListaProdutos(List<EntidadeDominio> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public void verificarDisponibilidade() {

		listaProdutos = Fachada.listar(new Produto());
		for (EntidadeDominio produto : listaProdutos) {
			Produto produtoCorrente = (Produto) produto;
			String recebeValor = produtoCorrente.getQuantidade().toString();
			recebeValor = recebeValor.replace("0", "");
			recebeValor = recebeValor.replace(".", "");
			Long quantidade = Long.parseLong(recebeValor);

			if (quantidade < 2) {
				FacesUtil.adicionarMSGError(
						"O produto " + produtoCorrente.getDescricao() + " possui apenas uma unidade de vitrine");

			}
		}
	}

}
