package br.com.tenoriogames.core.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.tenoriogames.core.IDAO;
import br.com.tenoriogames.core.factory.Conexao;
import br.com.tenoriogames.core.impl.controle.Fachada;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.web.bean.GraficoLinhaBean;
import br.com.tenoriogames.domain.Devolucao;
import br.com.tenoriogames.domain.Endereco;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Genero;
import br.com.tenoriogames.domain.Item;
import br.com.tenoriogames.domain.Produto;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;

public class ItemDAO implements IDAO {
	public Long Salvar(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof Item))
			return null;

		Item item = new Item();
		item = (Item) entidade;
		Long codigo = null;

		StringBuffer sql = new StringBuffer();

		sql.append(
				"INSERT INTO db_tenoriogames.tb_item(ite_codigo,ite_quantidade,ite_valor_parcial,tb_produtos_pro_codigo,tb_vendas_ven_codigo,ite_prod_descricao) ");
		sql.append("VALUES (?,?,?,?,?,?)");
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);

		try {

			int i = 0;
			pstm.setLong(++i, item.getCodigo());
			System.out.println();
			pstm.setInt(++i, item.getQuantidade());
			pstm.setBigDecimal(++i, item.getValor());
			pstm.setLong(++i, item.getProduto().getCodigo());
			pstm.setLong(++i, item.getVenda().getCodigo());
			pstm.setString(++i, item.getProduto().getDescricao());

			pstm.executeUpdate();

			FacesUtil.adicionarMSGInfo("Inserido com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		ResultSet rset =  pstm.getGeneratedKeys();
		while(rset.next()){
			 codigo = (long) rset.getInt(1);
			
		}
		
		codigo = Long.parseLong(codigo.toString());
		return codigo;
	}

@Override
	public void Editar(EntidadeDominio entidade) throws SQLException {
		

	}


	public void Excluir(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	
	public List<EntidadeDominio> listar() {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_item;");			
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				
				Item item = new Item();
				item.setCodigo(rSet.getLong("ite_codigo"));
				item.setQuantidade(rSet.getInt("ite_quantidade"));
				item.setValor(rSet.getBigDecimal("ite_valor_parcial"));
				item.getProduto().setCodigo(rSet.getLong("tb_produtos_pro_codigo"));
				item.getVenda().setCodigo(rSet.getLong("tb_vendas_ven_codigo"));
				item.setPrdDescricao(rSet.getString("ite_prod_descricao"));		
				lista.add(item);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return lista;
	}

	@Override
	public EntidadeDominio buscarPorCodigo(Long codigo) {
		// TODO Auto-generated method stub
		return null;
	}



	public static void main(String[] args) {
		//
		// List<Item> lista = retornaItensVendidos("2016-04-29", "2016-05-29");
		// System.out.println(lista);
		GraficoLinhaBean aux = new GraficoLinhaBean();
		aux.gerarGrafico();
	}

	public static List<EntidadeDominio> retornaItensVendidos(String dataInicio, String dataFim) {

		Date datainicio = null;
		Date dataifinal = null;
		SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			datainicio = (Date) stf.parse(dataInicio);
			dataifinal = (Date) stf.parse(dataFim);
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<EntidadeDominio> listaItens = new ArrayList<>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tb_vendas ");
		sql.append("inner join tb_item on tb_vendas.ven_codigo = tb_item.tb_vendas_ven_codigo ");
		sql.append("inner join tb_produtos on tb_produtos.pro_codigo = tb_item.tb_produtos_pro_codigo ");
		sql.append("where tb_vendas.ven_horario between ? and ?");

		Connection con = Conexao.getConnection();
		try {
			java.sql.PreparedStatement statement = con.prepareStatement(sql.toString());

			statement.setString(1, dataInicio);
			statement.setString(2, dataFim);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Item item = new Item();

				Produto produto = new Produto();
				produto.setCodigo(resultSet.getLong("pro_codigo"));
				produto.setDescricao(resultSet.getString("pro_descricao"));
				produto.setPeso(resultSet.getDouble("pro_peso"));
				produto.setPlataforma(resultSet.getString("pro_plataforma"));
				produto.setPreco(resultSet.getBigDecimal("pro_preco"));
				produto.setQuantidade(resultSet.getBigDecimal("pro_quantidade"));
				produto.setSetor(resultSet.getString("pro_setor"));

				Venda vendaAux = new Venda();
				vendaAux.setCodigo(resultSet.getLong("ven_codigo"));
				vendaAux.setHorario(resultSet.getDate("ven_horario"));
				vendaAux.setPeso(resultSet.getDouble("ven_peso"));

				Usuario usuario = new Usuario();
				usuario.setCodigo(resultSet.getLong("tb_usuario_usr_codigo"));
				vendaAux.setUsuario(usuario);
				vendaAux.setValor(resultSet.getBigDecimal("ven_valor_total"));
				item.setVenda(vendaAux);
				item.setProduto(produto);

				item.setCodigo(resultSet.getLong("ite_codigo"));
				item.setValor(resultSet.getBigDecimal("ite_valor_parcial"));
				item.setQuantidade(resultSet.getInt("ite_quantidade"));
				listaItens.add(item);
				System.out.println(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaItens;

	}
	
	
	
	@Test
	public  void SQL(){
		List<EntidadeDominio> lista =  retornaItensVendidos("2016-01-01", "2016-01-31");
		System.out.println(lista);
	}

	@Override
	public EntidadeDominio consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
