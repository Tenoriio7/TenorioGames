package br.com.tenoriogames.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraficoUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param listaDesordenada - Uma lista de String com repetiçoes ou distinta
	 * @return Uma lista distinta de String ordenada
	 */
	public static List<String> ordernarListaDistinta(List<String> listaDesordenada){
		
		// Criando uma collections com apenas os distintos
		Set<String> listaDistinta = new HashSet<String>(listaDesordenada);

		// criando uma lista que ira transforrmar a collection em uma lista de
		// String
		List<String> listaDistintaOrdenada = new ArrayList<String>();

		// tranforma a collection set em uma lista de String para ser ordenada
		for (String s : listaDistinta) {
			listaDistintaOrdenada.add(s);
		}
		
		// ordenando
		Collections.sort(listaDistintaOrdenada);

		return listaDistintaOrdenada;
		
	}
	
}