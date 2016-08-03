package br.com.tenoriogames.core.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.tenoriogames.core.util.GraficoUtil;

public class GraficoLinha {
	
	// crio a variavel linha que será alimentada para gerar  o grafico
	private static LineChartModel graficoRetorno = null;

	/**
	 * 
	 * @param dadosTotais No Map o primeiro parametro String  que recebera  os  meses , na seguqnei o map possui String que é o produto ou console
	 *  o parametro Integer é a quantidade que se repete
	 * @param intervaloPesquisa Mes inicio e mes fim , porém esta padronizado de Janeiro Dezembro
	 * @param listaDesordenada Lista Todas as String  que é retorno do SQL
	 * @param eixoX  eixo X do plano Cartesiano
	 * @param eixoY  eixo X do plano Cartesiano
	 * @return
	 */
	public static LineChartModel gerarGraficoLine(Map<String, List<Map<String, Integer>>> dadosTotais,
			List<String> intervaloPesquisa, List<String> listaDesordenada, String eixoX, String eixoY) {
		
		// instanciando 
		graficoRetorno = new LineChartModel();
		// variavel que possui  o valor de cada linha do grafico ( ex produto X , produto Y , console X , console Y)
		LineChartSeries lineChartSeries = null;

		// ordena a lista e atribui para ela mesma 
		listaDesordenada = GraficoUtil.ordernarListaDistinta(listaDesordenada);

		// ira receber a maior quantidade entre todos o meses para controlar a maior escala do eixo Y
		int maiorQuantidade = 0;

		// Cria as Linhas do Grafico
		for (int j = 0; j < listaDesordenada.size(); j++) {
			
			// esee  map possui todos os valores em um determinado mês ele possui todos os valores independente se console ou jogo
			List<Map<String, Integer>> listaMaps = null;
			
			// o unitario do selecionado ( se console ou jogo)
			// a String é a indentificação e o integer o quanto se repete
			// Exemplo fifa 15 10 unidades
			Map<String, Integer> mapUnitario = null;
			
			// linha gerada no grafico
			lineChartSeries = new LineChartSeries();

			// coloca o nome da legenda, pois cada indice é um produto por estar ordenado
			lineChartSeries.setLabel(listaDesordenada.get(j));

			
			// verifica em todos os meses aquele que foi selecionado
			for (int i = 0; i < intervaloPesquisa.size(); i++) {
				// instancia
				listaMaps = new ArrayList<Map<String, Integer>>();
				//instancia
				mapUnitario = new HashMap<String, Integer>();
				
				// pega de todos dados recebidos apenas o do mes selecionado que esta no indice i
				listaMaps = dadosTotais.get(intervaloPesquisa.get(i));

				// para cada mes informado sera adicionado o valor encontrado
				// como ja foi selecionado o mes e o produto 
				for (int k = 0; k < listaMaps.size(); k++) {
					// o map unitario selecionado recebe  o valor do indice
					mapUnitario = listaMaps.get(k);
					
					if (mapUnitario.containsKey(listaDesordenada.get(j))) {
						if (mapUnitario.containsKey(lineChartSeries.getLabel())) {
							// se nao possui uma linha, cria uma com  o mes e  o unitario
							lineChartSeries.set(intervaloPesquisa.get(i), mapUnitario.get(listaDesordenada.get(j)));
						}

						// verifica o maior dado para formar o eixo y + 2
						if (maiorQuantidade < mapUnitario.get(listaDesordenada.get(j)))
							maiorQuantidade = mapUnitario.get(listaDesordenada.get(j));

						// s se não encontrou dado naquele mes recebera o valor
						// 0
					} else {
						lineChartSeries.set(intervaloPesquisa.get(i), 0);
					}
				}

			}

			graficoRetorno.addSeries(lineChartSeries);
		}

		graficoRetorno.getAxes().put(AxisType.X, new CategoryAxis(eixoX));
		Axis yAxis = graficoRetorno.getAxis(AxisType.Y);
		yAxis.setLabel(eixoY);
		yAxis.setMin(-1);
		yAxis.setMax(maiorQuantidade);
		yAxis.setTickFormat("%i");
		graficoRetorno.setLegendPosition("e");
		graficoRetorno.setShowPointLabels(true);
		graficoRetorno.setAnimate(true);
		graficoRetorno.setZoom(true);

		return graficoRetorno;
	}

}


