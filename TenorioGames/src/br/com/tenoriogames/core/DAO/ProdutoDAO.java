package br.com.tenoriogames.core.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.tenoriogames.core.IDAO;
import br.com.tenoriogames.core.factory.Conexao;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Genero;
import br.com.tenoriogames.domain.Produto;

public class ProdutoDAO implements IDAO{

	public Long Salvar(EntidadeDominio entidade) throws SQLException {
		if(!(entidade instanceof Produto))
			return null;
		
		Produto produto = new Produto();
		produto = (Produto) entidade;
		Long retorno = null ;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO db_tenoriogames.tb_produtos(pro_codigo,pro_descricao,pro_peso,pro_plataforma,pro_preco,");
		sql.append("pro_quantidade,pro_setor,tb_fornecedor_forn_codigo,tb_genero_gen_codigo, pro_status)");
		sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?)");
		
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
		
		try {
			
			int i=0;
			pstm.setLong(++i, produto.getCodigo());
			pstm.setString(++i, produto.getDescricao());
			pstm.setDouble(++i,produto.getPeso());
			pstm.setString(++i, produto.getPlataforma());
			pstm.setBigDecimal(++i,produto.getPreco());
			pstm.setBigDecimal(++i,produto.getQuantidade());
			pstm.setString(++i, produto.getSetor());
			pstm.setLong(++i, produto.getFornecedor().getCodigo());
			pstm.setLong(++i, produto.getGenero().getCodigo());
			pstm.setString(++i, produto.getStatus());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		ResultSet rset =  pstm.getGeneratedKeys();
		while(rset.next()){
			rset.getInt(1);
			retorno=(long) rset.getInt(1);
		}
		return retorno;
		
		
	}
	
	
	
	public  List<EntidadeDominio> listar() {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_produtos;");			
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				
				Produto Produto = new Produto();
				Produto.setCodigo(rSet.getLong("pro_codigo"));
				Produto.setDescricao(rSet.getString("pro_descricao"));
				IDAO dao = new FornecedorDAO();
				Fornecedor fornecedor = (Fornecedor) dao.buscarPorCodigo(rSet.getLong("tb_fornecedor_forn_codigo"));
				dao =  (IDAO) new  GeneroDAO();
				Genero genero = (Genero) dao.buscarPorCodigo(rSet.getLong("tb_genero_gen_codigo"));
				Produto.setFornecedor(fornecedor);
				Produto.setGenero(genero);
				Produto.setPeso(rSet.getDouble("pro_peso"));
				Produto.setPlataforma(rSet.getString("pro_plataforma"));
				Produto.setPreco(rSet.getBigDecimal("pro_preco"));
				Produto.setQuantidade(rSet.getBigDecimal("pro_quantidade"));	
				Produto.setSetor(rSet.getString("pro_setor"));
				Produto.setStatus(rSet.getString("pro_status"));
				lista.add(Produto);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return lista;
	
	}
	
	
	
	public EntidadeDominio buscarPorCodigo(Long codigo) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_produtos");
		sql.append(" where tb_produtos.pro_codigo = ?");
		
		Produto Produto = new Produto();
		Connection con = Conexao.getConnection();
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, codigo);
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()){
				
				Produto.setCodigo(rSet.getLong("pro_codigo"));
				Produto.setDescricao(rSet.getString("pro_descricao"));
				IDAO dao = new FornecedorDAO();
				Fornecedor fornecedor = (Fornecedor) dao.buscarPorCodigo(rSet.getLong("tb_fornecedor_forn_codigo"));
				dao =  (IDAO) new GeneroDAO();
				Genero genero = (Genero) dao.buscarPorCodigo(rSet.getLong("tb_genero_gen_codigo"));
				Produto.setFornecedor(fornecedor);
				Produto.setGenero(genero);
				Produto.setPeso(rSet.getDouble("pro_peso"));
				Produto.setPlataforma(rSet.getString("pro_plataforma"));
				Produto.setPreco(rSet.getBigDecimal("pro_preco"));
				Produto.setQuantidade(rSet.getBigDecimal("pro_quantidade"));	
				Produto.setSetor(rSet.getString("pro_setor"));		
				Produto.setStatus(rSet.getString("pro_status"));	
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return Produto;
	}
	
	public void Excluir (EntidadeDominio entidade)	{
	
		 {				
				if(!(entidade instanceof Produto))
					return;
				
				Produto produto = new Produto();
				produto = (Produto) entidade;
				
				StringBuffer sql = new StringBuffer();
				sql.append("delete from db_tenoriogames.tb_produtos where pro_codigo = ?");
				
				Connection con = Conexao.getConnection();
				
				try {
					PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
					pstm.setLong(1, produto.getCodigo());
					pstm.executeUpdate();
					
				} catch (Exception e){
					e.printStackTrace();
					FacesUtil.adicionarMSGError((e.getMessage()));
				}
				
				
			
		 }
		
	}


	@Override
	public void Editar(EntidadeDominio entidade) throws SQLException {
		
		if(!(entidade instanceof Produto))
			return;
		
		Produto produto = new Produto();
		produto = (Produto) entidade;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE db_tenoriogames.tb_produtos set pro_descricao = ?,pro_peso = ?,pro_plataforma = ?,");
		sql.append("pro_preco = ?,pro_quantidade = ?,pro_setor= ? ,tb_fornecedor_forn_codigo = ?,tb_genero_gen_codigo= ?, pro_status=?");	
		sql.append(" WHERE pro_codigo=?");

		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			int i=0;
			pstm.setString(++i,produto.getDescricao());
			pstm.setDouble(++i,produto.getPeso());
			pstm.setString(++i,produto.getPlataforma());
			pstm.setBigDecimal(++i, produto.getPreco());
			pstm.setBigDecimal(++i,produto.getQuantidade());
			pstm.setString(++i,produto.getSetor());
			pstm.setLong(++i,produto.getFornecedor().getCodigo());
			pstm.setLong(++i,produto.getGenero().getCodigo());
			pstm.setString(++i, produto.getStatus());
			pstm.setLong(++i,produto.getCodigo());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
	}



	public EntidadeDominio consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
