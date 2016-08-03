package br.com.tenoriogames.core.impl.controle;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tenoriogames.core.IDAO;
import br.com.tenoriogames.core.IStrategy;
import br.com.tenoriogames.core.DAO.FornecedorDAO;
import br.com.tenoriogames.core.DAO.ItemDAO;
import br.com.tenoriogames.core.DAO.ProdutoDAO;
import br.com.tenoriogames.core.DAO.UsuarioDAO;
import br.com.tenoriogames.core.DAO.VendaDAO;
import br.com.tenoriogames.core.impl.negocio.ValidadorCnpj;
import br.com.tenoriogames.core.impl.negocio.ValidadorDadosObrigatoriosFornecedor;
import br.com.tenoriogames.core.impl.negocio.ValidadorDadosObrigatoriosVenda;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.web.bean.redirecionaBean;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Item;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.Resultado;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;

public class FachadaVenda {

	/**
	 * Mapa de DAOS, será indexado pelo nome da entidade O valor é uma instância
	 * do DAO para uma dada entidade;
	 */
	private static Map<String, IDAO> daos;

	/**
	 * Mapa para conter as regras de negócio de todas operações por entidade; O
	 * valor é um mapa que de regras de negócio indexado pela operação
	 */
	private static Map<String, Map<String, List<IStrategy>>> rns;

	private static Resultado resultado;

	public FachadaVenda() {
		/* Intânciando o Map de DAOS */
		daos = new HashMap<String, IDAO>();
		/* Intânciando o Map de Regras de Negócio */
		rns = new HashMap<String, Map<String, List<IStrategy>>>();

		/* Criando instâncias dos DAOs a serem utilizados*/
		FornecedorDAO forDAO = new FornecedorDAO();
		UsuarioDAO usrDAO = new UsuarioDAO();
		ProdutoDAO proDAO = new ProdutoDAO();
		VendaDAO venDAO = new VendaDAO();
		ItemDAO itemDAO = new ItemDAO();
		
		/* Adicionando cada dao no MAP indexando pelo nome da classe */
		daos.put(Fornecedor.class.getName(), (IDAO) forDAO);		
		daos.put(Produto.class.getName(), (IDAO) proDAO);
		daos.put(Usuario.class.getName(), (IDAO) usrDAO);
		daos.put(Venda.class.getName(), (IDAO)venDAO);
		daos.put(Item.class.getName(), (IDAO)itemDAO);

		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosFornecedor vrDadosObrigatoriosFornecedor = new ValidadorDadosObrigatoriosFornecedor();
		ValidadorCnpj vCnpj = new ValidadorCnpj();
		ValidadorDadosObrigatoriosVenda dadosObrigatoriosVenda = new ValidadorDadosObrigatoriosVenda();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor
		 * quando a operação for Salvar
		 */
		List<IStrategy> rnsSalvarVenda = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação Salvar do
		 * fornecedor
		 */
		rnsSalvarVenda.add(vrDadosObrigatoriosFornecedor);
		rnsSalvarVenda.add(vCnpj);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio
		 * específica por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsVenda = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação Salvar no mapa do fornecedor
		 * (lista criada na linha 70)
		 */
		rnsVenda.put("Salvar", rnsSalvarVenda);

		/*
		 * Adiciona o mapa(criado na linha 79) com as regras indexadas pelas
		 * operações no mapa geral indexado pelo nome da entidade
		 */
		rns.put(Fornecedor.class.getName(), rnsVenda);
		
	}
	public static Long salvarVenda(Venda venda) throws SQLException {
		Long codigo = 0L;

		
			VendaDAO dao = new VendaDAO();
			try {
				codigo=dao.Salvar(venda);
				FacesUtil.adicionarMSGInfo("Salvo com sucesso");
			} catch (RuntimeException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");

			}
			System.out.println(codigo);
		return codigo;
	}
	
	
	

	public static void alterarGenerico(EntidadeDominio entidade) {
		String nmClasse = entidade.getClass().getName();
		IDAO dao = daos.get(nmClasse);
		try {
			dao.Editar(entidade);
			FacesUtil.adicionarMSGInfo("Editado com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			

		}

		
	}

	public Resultado excluir(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "Excluir");

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				dao.Excluir(entidade);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);

				FacesUtil.adicionarMSGInfo("Excluido com sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");

			}
		} else {
			resultado.setMsg(msg);

		}

		return resultado;
	}


	public List<EntidadeDominio> listarGenerico(EntidadeDominio entidade) {
		String nmClasse = entidade.getClass().getName();
		IDAO  dao= daos.get(nmClasse);
		List<EntidadeDominio> lista =  new ArrayList<>();
			lista=dao.listar();
		return lista;
	}
	

	
	
	

	private static String executarRegras(EntidadeDominio entidade, String operacao) {
		String nmClasse = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();

		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);

		if (regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);

			if (regras != null) {
				for (IStrategy s : regras) {
					String m = s.processar(entidade);

					if (m != null) {
						msg.append(m);
						msg.append("\n");
					}
				}
			}

		}

		if (msg.length() > 0)
			return msg.toString();
		else
			return null;
	}

}
