package br.com.tenoriogames.core.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.primefaces.model.chart.PieChartModel;

public class GraficoPizza {
	public static PieChartModel gerarGrafico(List<String> listaStringDesordenada) {
		PieChartModel graficoRetorno = new PieChartModel();

		// Criando uma collections com apenas os distintos
		Set<String> reservasDistintas = new HashSet<String>(listaStringDesordenada);

		// criando uma lista que ira transforrmar a collection em uma lista de
		// String
		List<String> listaDistintaOrdenada = new ArrayList<String>();

		// tranforma a collection set em uma lista de String para ser ordenada
		for (String s : reservasDistintas) {
			listaDistintaOrdenada.add(s);
		}

		// classifica a lista por ordem alfabetica
		Collections.sort(listaDistintaOrdenada);

		// HashMap que ira receber <Nome, qtde>
		HashMap<String, Integer> graficoHash = new HashMap<String, Integer>();

		Collections.sort(listaStringDesordenada);
		for (int i = 0; i < listaDistintaOrdenada.size(); i++) {
			int count = Collections.frequency(listaStringDesordenada, listaDistintaOrdenada.get(i));
			graficoHash.put(listaDistintaOrdenada.get(i), count);
		}

		// Grafico recebe os dados para ser exibido
		for (int i = 0; i < graficoHash.size(); i++) {
			graficoRetorno.set(listaDistintaOrdenada.get(i), graficoHash.get(listaDistintaOrdenada.get(i)));
		}

		return graficoRetorno;
	}

}
