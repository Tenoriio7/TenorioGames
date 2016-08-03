package br.com.tenoriogames.core.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.tenoriogames.core.DAO.ItemDAO;
import br.com.tenoriogames.core.builder.GraficoPizza;
import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Item;


@javax.faces.bean.ManagedBean
@ViewScoped
public class GraficoLinhaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EntidadeDominio> listaItens = new ArrayList<>();
	private String dataInicio = null;
	private String dataFim = null;
	private String mesEscolhido = null;
	private BarChartModel barraVenda = null;
	private PieChartModel pizzaVenda = null;
	List<String> listaDescricaoProdutos = new ArrayList<>();

	private Map<String, String> hmMesesInicio = new HashMap<>();
	private Map<String, String> hmMesesFim = new HashMap<>();

	public GraficoLinhaBean() {
		hmMesesInicio.put("Janeiro", "2016-01-01");
		hmMesesInicio.put("Fevereiro", "2016-02-01");
		hmMesesInicio.put("Março", "2016-03-01");
		hmMesesInicio.put("Abril", "2016-04-01");
		hmMesesInicio.put("Maio", "2016-05-01");
		hmMesesInicio.put("Junho", "2016-06-01");
		hmMesesInicio.put("Julho", "2016-07-01");
		hmMesesInicio.put("Agosto", "2016-08-01");
		hmMesesInicio.put("Setembro", "2016-09-01");
		hmMesesInicio.put("Outubro", "2016-10-01");
		hmMesesInicio.put("Novembro", "2016-11-01");
		hmMesesInicio.put("Dezembro", "2016-12-01");

		hmMesesFim.put("Janeiro", "2016-01-31");
		hmMesesFim.put("Fevereiro", "2016-02-29");
		hmMesesFim.put("Março", "2016-03-31");
		hmMesesFim.put("Abril", "2016-04-30");
		hmMesesFim.put("Maio", "2016-05-31");
		hmMesesFim.put("Junho", "2016-06-30");
		hmMesesFim.put("Julho", "2016-07-31");
		hmMesesFim.put("Agosto", "2016-08-31");
		hmMesesFim.put("Setembro", "2016-09-30");
		hmMesesFim.put("Outubro", "2016-10-31");
		hmMesesFim.put("Novembro", "2016-11-30");
		hmMesesFim.put("Dezembro", "2016-12-31");
	}

	public List<String> getListaDescricaoProdutos() {
		return listaDescricaoProdutos;
	}

	public void setListaDescricaoProdutos(List<String> listaDescricaoProdutos) {
		this.listaDescricaoProdutos = listaDescricaoProdutos;
	}

	public PieChartModel getPizzaVenda() {
		return pizzaVenda;
	}

	public void setPizzaVenda(PieChartModel pizzaVenda) {
		this.pizzaVenda = pizzaVenda;
	}

	public List<EntidadeDominio> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<EntidadeDominio> listaItens) {
		this.listaItens = listaItens;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public BarChartModel getBarraVenda() {
		return barraVenda;
	}

	public String getMesEscolhido() {
		return mesEscolhido;
	}

	public void setMesEscolhido(String mesEscolhido) {
		this.mesEscolhido = mesEscolhido;
	}

	public Map<String, String> getHmMesesInicio() {
		return hmMesesInicio;
	}

	public void setHmMesesInicio(Map<String, String> hmMesesInicio) {
		this.hmMesesInicio = hmMesesInicio;
	}

	public Map<String, String> getHmMesesFim() {
		return hmMesesFim;
	}

	public void setHmMesesFim(Map<String, String> hmMesesFim) {
		this.hmMesesFim = hmMesesFim;
	}

	public void setBarraVenda(BarChartModel barraVenda) {
		this.barraVenda = barraVenda;
	}

	public void gerarGrafico() {
		pizzaVenda = new PieChartModel();
		barraVenda = new BarChartModel();
		dataInicio = hmMesesInicio.get(mesEscolhido);
		dataFim = hmMesesFim.get(mesEscolhido);
		listaItens = new Fachada().retornarItensVendidos(dataInicio, dataFim);

		for (EntidadeDominio itemAux : listaItens) {
			listaDescricaoProdutos.add(((Item) itemAux).getProduto().getDescricao());

		}

	}

	public void preencheValores() {
		
		if(listaItens.size() == 0){
			FacesUtil.adicionarMSGError("Não existem dados no intervalo informado");
			limparObjetos();
		}
		
		// Map<String, Integer> hasGrafico = new HashMap<>();
		//
		// // criando uma collection com apenas os destintos ( quantos
		// diferentes tem)
		// Set<String> listaDistintos = new HashSet<>(listaDescricaoProdutos);
		// List<String> listaProdutosOrdenados = new ArrayList<>();
		//
		// // transforma a set em uma lista de string para ser ordenado
		// for(String aux : listaDistintos){
		// listaProdutosOrdenados.add(aux);
		// }
		//
		// // ele classifica a lista por ordem alfabetica
		// Collections.sort(listaProdutosOrdenados);
		// Collections.sort(listaDescricaoProdutos);
		//
		// for(int i = 0 ; i < listaProdutosOrdenados.size();i++){
		// // count recebe a frequencia que existe de listaProdutosOrdenados na
		// listaDescricaoProdutos
		// int count = Collections.frequency(listaDescricaoProdutos,
		// listaProdutosOrdenados.get(i));
		// // popula o map inserindo qual produto e a quantidade que repete
		// hasGrafico.put(listaProdutosOrdenados.get(i), count);
		// }
		//
		// ChartSeries chart = null;
		// Set<String> chaves = hasGrafico.keySet();
		// for (int j = 0; j < listaDistintos.size(); j++) {
		// chart = new ChartSeries();
		// for (int i = 0; i < hasGrafico.size(); i++) {
		// chart.set(listaProdutosOrdenados.get(i),
		// hasGrafico.get(listaProdutosOrdenados.get(i)));
		// }
		//// for
		//// barraVenda.addSeries(chart);
		////
		
		pizzaVenda = GraficoPizza.gerarGrafico(listaDescricaoProdutos);
		pizzaVenda.setTitle("Grafico Pizza " + mesEscolhido);
		pizzaVenda.setShowDataLabels(true);
		pizzaVenda.setLegendPosition("w");

	}
	
	public void limparObjetos(){
		listaDescricaoProdutos.clear();
		listaItens.clear();
		pizzaVenda = null;
		barraVenda = null;
		mesEscolhido = new String();
		dataInicio = new String();
		dataFim = new String();
		
	}

}
