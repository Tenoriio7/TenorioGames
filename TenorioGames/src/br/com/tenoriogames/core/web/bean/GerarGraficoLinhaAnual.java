package br.com.tenoriogames.core.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.LineChartModel;

import br.com.tenoriogames.core.DAO.ItemDAO;
import br.com.tenoriogames.core.builder.GraficoLinha;
import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Item;

@ManagedBean
@ViewScoped
public class GerarGraficoLinhaAnual extends AbstractGraficoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LineChartModel lineModel;
	// essa variavel define qual tipo de dados no grafico
	private String tipoDeDadosGraficos;
	private List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
	private List<String> listaString = new ArrayList<String>();
	private Map<String, Integer> dados = new HashMap<String, Integer>();
	// intervalo a ser perquisado
	private List<String> seriesPesquisada = new ArrayList<String>();
	private String stDataInicio;
	private String stDataFinal;
	private Item item = new Item();
	
	// todos os dados necessários para o grafico
	private Map<String, List<Map<String, Integer>>> dadosTotais = new HashMap<String, List<Map<String, Integer>>>();

	public GerarGraficoLinhaAnual() {

	}

	
	public List<EntidadeDominio> getLista() {
		return lista;
	}

	public void setLista(List<EntidadeDominio> lista) {
		this.lista = lista;
	}

	public List<String> getSeriesPesquisada() {
		return seriesPesquisada;
	}

	public void setSeriesPesquisada(List<String> seriesPesquisada) {
		this.seriesPesquisada = seriesPesquisada;
	}

	public List<String> getListaString() {
		return listaString;
	}

	public void setListaString(List<String> listaString) {
		this.listaString = listaString;
	}

	

	public Map<String, Integer> getDados() {
		return dados;
	}

	public void setDados(Map<String, Integer> dados) {
		this.dados = dados;
	}

	public String getTipoDeDadosGraficos() {
		return tipoDeDadosGraficos;
	}

	public void setTipoDeDadosGraficos(String tipoDeDadosGraficos) {
		this.tipoDeDadosGraficos = tipoDeDadosGraficos;
	}
	
	public LineChartModel getLineModel() {
		return lineModel;
	}
	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	public Map<String, List<Map<String, Integer>>> getDadosTotais() {
		return dadosTotais;
	}

	public void setDadosTotais(Map<String, List<Map<String, Integer>>> dadosTotais) {
		this.dadosTotais = dadosTotais;
	}


	
	public void gerarGraficoPersonalizado() {

		// Verifica se foi escolhido o ano
		if (ano == null) {
			FacesUtil.adicionarMSGInfo("Escolha o ano");
			return;
		}
		
		
		List<Map<String, Integer>> listaMaps = null;
		
		
		// for do tamanho de meses
		for (int i = 0; i < meses.size(); i++) {

			listaMaps = new ArrayList<Map<String, Integer>>();
			
			//String de data inicial
			stDataFinal = null;
			//String de data inicial
			stDataInicio = null;

			// Recebe a data ja armazenda e mais o ano escolhido na view
			stDataInicio =ano + mesesInicio.get(meses.get(i));
			stDataFinal = ano + mesesFim.get(meses.get(i));
		
			
			// recebe os items  relacionado ao mes pesquisado
			lista =  Fachada.retornarItensVendidos(stDataInicio, stDataFinal);

			for (EntidadeDominio iAux : lista) {
				item = new Item();
				item = (Item) iAux;
				// aqui ele ira identificar qual tipo de grafico ele ira pesquisar , se é de produto ou genero
				// através do switch do retornarStringPesquisada
				listaString.add(retornarStringPesquisada());
				seriesPesquisada.add(retornarStringPesquisada());
			}

			for (String stringAux : listaString) {
				//verifica se dados ja contem aquela 
				if (!dados.containsKey(stringAux)) {
					//se nao contem adiciona
					dados.put(stringAux, 1);
				} else {
					// se já contem adiciona mais um
					dados.put(stringAux, dados.get(stringAux) + 1);
				}

			}
			 // adiciona ao maps   o mes e a quantidade do mês.
			listaMaps.add(dados);

			// Map recebe o mes e quantidade daquele mes
			dadosTotais.put(meses.get(i), listaMaps);
			// limpar a lista de String para receber novos dados
			listaString.clear();
			dados = new HashMap<String, Integer>();
			

		}
		lineModel =new LineChartModel();
	}

		


	/**
	 * Chama o grafico para ser exibido na view
	 */
	public void exibirGraficoPersonalizado() {
		
		// para saber se possui valor no grafico.
		boolean temValor = false;

		// verifica se algum mes retornou resultado
		for (int i = 0; i < meses.size(); i++) {
			if (dadosTotais.get(meses.get(i)).size() > 0) {
				temValor = true;
			}
		}
		
		// Não houve dados disponiveis para gerar o gráfico
		if (!temValor) {
			FacesUtil.adicionarMSGError("Não houve há Dados disponiveis para o Intervalo solicitado");
			limparGrafico();
			org.primefaces.context.RequestContext.getCurrentInstance().update("pnlGrafico pnlComandos pnlTipoGrafico"
					+ "pnlGraficoLinha pnlEscolha outDataPersonalizada outEscolha");
			return;
		}
		
		
		// aqui estou passando todos meus dados, os meses , 
		lineModel = GraficoLinha.gerarGraficoLine(dadosTotais, meses, seriesPesquisada, tipoDados, "Quantidade");
		lineModel.setTitle("Grafico de Análise Anual");

	}

	private String retornarStringPesquisada() {
		// Verificando qual foi o dado relacionado ao grafico que o usuario
		// deseja verificar

		switch (tipoDados) {
		case "Jogos":
			return item.getProduto().getDescricao();

		case "Console":
			return item.getProduto().getPlataforma();

		}

		return null;
	}

	/**
	 * Limpa o Grafico Personalizado da VIew
	 */
	@Override
	public void limparGrafico() {

		lineModel = null;
		lista.clear();
		listaString.clear();
		dados = new HashMap<String, Integer>();
		seriesPesquisada.clear();
		tipoDeDadosGraficos = null;
		ano = null;
		tipoDados = null;
		dadosTotais.clear();
		item =  new  Item();

	}



}