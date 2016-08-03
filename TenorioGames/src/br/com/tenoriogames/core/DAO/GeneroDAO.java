package br.com.tenoriogames.core.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.tenoriogames.core.IDAO;
import br.com.tenoriogames.core.factory.Conexao;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.core.util.HibernateUtil;
import br.com.tenoriogames.domain.Endereco;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;
import br.com.tenoriogames.domain.Genero;

public class GeneroDAO implements IDAO {
	
	@Test
	public Long Salvar(EntidadeDominio entidade) throws SQLException {
		if(!(entidade instanceof Genero))
			return null;
		
		Genero genero = new Genero();
		genero = (Genero) entidade;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO db_tenoriogames.tb_genero(gen_codigo,gen_descricao) ");
		
		sql.append("VALUES (?,?)");		
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
		
		try {
			
			int i=0;
			pstm.setLong(++i, genero.getCodigo());
			pstm.setString(++i,genero.getDescricao());
			pstm.executeUpdate();
			
			FacesUtil.adicionarMSGInfo("Inserido com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		ResultSet rset =  pstm.getGeneratedKeys();
		return rset.getLong(1);

	}

	public List<EntidadeDominio> listar() {
			
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_genero;");			
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				
				Genero genero = new Genero();
				genero.setCodigo(rSet.getLong("gen_codigo"));
				genero.setDescricao(rSet.getString("gen_descricao"));			
				lista.add(genero);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return lista;
		
	
	}
	
	@Test
	public EntidadeDominio buscarPorCodigo(Long codigo) {

		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_genero");
		sql.append(" where tb_genero.gen_codigo =?");
		
		Genero genero = new  Genero();
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, codigo);
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()){
				genero.setCodigo(rSet.getLong("gen_codigo"));	
				genero.setDescricao(rSet.getString("gen_descricao"));
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return genero;
 
	}
	
	
	public void Excluir(EntidadeDominio entidade) {
		
		if(!(entidade instanceof Genero))
			return;
		
		Genero genero = new Genero();
		genero = (Genero) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from db_tenoriogames.tb_genero where gen_codigo = ?");
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, genero.getCodigo());
			pstm.executeUpdate();
			
		} catch (Exception e){
			e.printStackTrace();
			FacesUtil.adicionarMSGError((e.getMessage()));
		}
		
		
	
}
	
	
	
	@Test
	public void Editar(EntidadeDominio entidade) {
		if(!(entidade instanceof Genero))
			return;
		
		Genero genero = new Genero();
		genero = (Genero) entidade;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE db_tenoriogames.tb_genero set gen_codigo =? ,gen_descricao = ?");	
		sql.append("WHERE gen_codigo=?");

		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			int i=0;
			pstm.setLong(++i,genero.getCodigo());
			pstm.setString(++i,genero.getDescricao());
			pstm.setLong(++i,genero.getCodigo());
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
