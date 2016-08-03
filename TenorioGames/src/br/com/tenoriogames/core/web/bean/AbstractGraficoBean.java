package br.com.tenoriogames.core.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Resultado;

public abstract class AbstractGraficoBean  implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected List<String> meses = new ArrayList<String>();
		protected Map<String, String> mesesInicio = new HashMap<String, String>();
		protected Map<String, String> mesesFim = new HashMap<String, String>();
		protected List<String> listaAnos = new ArrayList<String>();
		protected String ano;
		protected String tipoDados;

		public AbstractGraficoBean() {
			// adiciona todos os meses
			meses.add("Janeiro");
			meses.add("Fevereiro");
			meses.add("Março");
			meses.add("Abril");
			meses.add("Maio");
			meses.add("Junho");
			meses.add("Julho");
			meses.add("Agosto");
			meses.add("Setembro");
			meses.add("Outubro");
			meses.add("Novembro");
			meses.add("Dezembro");
			 
			// recebe -1 para garantir que começara do zero
			int i = -1;
			// Setando os hash com as datas inicio do meses
			mesesInicio.put(meses.get(++i), "-01-01");
			mesesInicio.put(meses.get(++i), "-02-01");
			mesesInicio.put(meses.get(++i), "-03-01");
			mesesInicio.put(meses.get(++i), "-04-01");
			mesesInicio.put(meses.get(++i), "-05-01");
			mesesInicio.put(meses.get(++i), "-06-01");
			mesesInicio.put(meses.get(++i), "-07-01");
			mesesInicio.put(meses.get(++i), "-08-01");
			mesesInicio.put(meses.get(++i), "-09-01");
			mesesInicio.put(meses.get(++i), "-10-01");
			mesesInicio.put(meses.get(++i), "-11-01");
			mesesInicio.put(meses.get(++i), "-12-01");
			
			// zera a variavel para utilizar a mesma
			i = -1;

			// Setando os hash com as datas finais dos meses
			mesesFim.put(meses.get(++i), "-01-31");
			mesesFim.put(meses.get(++i), "-02-29");
			mesesFim.put(meses.get(++i), "-03-31");
			mesesFim.put(meses.get(++i), "-04-30");
			mesesFim.put(meses.get(++i), "-05-31");
			mesesFim.put(meses.get(++i), "-06-30");
			mesesFim.put(meses.get(++i), "-07-31");
			mesesFim.put(meses.get(++i), "-08-31");
			mesesFim.put(meses.get(++i), "-09-30");
			mesesFim.put(meses.get(++i), "-10-31");
			mesesFim.put(meses.get(++i), "-11-30");
			mesesFim.put(meses.get(++i), "-12-31");

			// inicialia a lista de anos para ser exibida na view
		
			
		}

		public List<String> getMeses() {
			return meses;
		}

		public void setMeses(List<String> meses) {
			this.meses = meses;
		}

		public Map<String, String> getMesesInicio() {
			return mesesInicio;
		}

		public void setMesesInicio(Map<String, String> mesesInicio) {
			this.mesesInicio = mesesInicio;
		}

		public String getAno() {
			return ano;
		}

		public void setAno(String ano) {
			this.ano = ano;
		}

		public String getTipoDados() {
			return tipoDados;
		}

		public void setTipoDados(String tipoDados) {
			this.tipoDados = tipoDados;
		}

		public List<String> getListaAnos() {
			return listaAnos;
		}

		public void setListaAnos(List<String> listaAnos) {
			this.listaAnos = listaAnos;
		}

		public Map<String, String> getMesesFim() {
			return mesesFim;
		}

		public void setMesesFim(Map<String, String> mesesFim) {
			this.mesesFim = mesesFim;
		}

		
		
		protected abstract void limparGrafico();

	}
	

