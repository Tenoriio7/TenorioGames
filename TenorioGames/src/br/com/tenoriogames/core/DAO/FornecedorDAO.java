package br.com.tenoriogames.core.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.tenoriogames.core.IDAO;
import br.com.tenoriogames.core.factory.Conexao;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.Endereco;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Fornecedor;

public class FornecedorDAO implements IDAO {

	
public Long Salvar(EntidadeDominio entidade) throws SQLException {
		if(!(entidade instanceof Fornecedor))
			return null;
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor = (Fornecedor) entidade;
		Long codigo = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO db_tenoriogames.tb_fornecedor(forn_codigo,forn_CNPJ,forn_Telefone,end_Bairro,end_CEP,");
		sql.append("end_Cidade,end_Estado,end_Numero,end_Rua,forn_nome_fantasia,");
		sql.append("forn_razao_social)");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");		
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
		
		try {
			
			int i=0;
			pstm.setLong(++i, fornecedor.getCodigo());
			pstm.setString(++i,fornecedor.getCNPJ());
			pstm.setString(++i,fornecedor.getTelefone());
			pstm.setString(++i,fornecedor.getEndereco().getBairro());
			pstm.setString(++i, fornecedor.getEndereco().getCEP());
			pstm.setString(++i,fornecedor.getEndereco().getCidade());
			pstm.setString(++i,fornecedor.getEndereco().getEstado());
			pstm.setInt(++i,fornecedor.getEndereco().getNumero());
			pstm.setString(++i,fornecedor.getEndereco().getRua());
			pstm.setString(++i,fornecedor.getNomeFantasia());
			pstm.setString(++i,fornecedor.getRazaoSocial());
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


	@Test
	public List<EntidadeDominio> listar()  {
			
			StringBuffer sql = new StringBuffer(); 
			sql.append("SELECT * FROM db_tenoriogames.tb_fornecedor;");			
			List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
			
			Connection con = Conexao.getConnection();
			
			try {
				PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
				ResultSet rSet = pstm.executeQuery();
				
				while(rSet.next()) {
					
					Fornecedor fornecedorRetorno = new Fornecedor();
					fornecedorRetorno.setCodigo(rSet.getLong("forn_codigo"));
					fornecedorRetorno.setCNPJ(rSet.getString("forn_CNPJ"));
					fornecedorRetorno.setNomeFantasia(rSet.getString("forn_nome_fantasia"));
					fornecedorRetorno.setNomeFantasia((rSet.getString("forn_razao_social")));
					Endereco endereco =  new Endereco();
					endereco.setBairro(rSet.getString("end_Bairro"));
					endereco.setCEP(rSet.getString("end_CEP"));
					endereco.setCidade(rSet.getString("end_Cidade"));
					endereco.setEstado(rSet.getString("end_Estado"));
					endereco.setNumero(rSet.getInt("end_Numero"));
					endereco.setRua(rSet.getString("end_Rua"));
					fornecedorRetorno.setEndereco(endereco);					
					lista.add(fornecedorRetorno);
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				FacesUtil.adicionarMSGError(e.getMessage());
			}
			
			
			return lista;
		
	}

	
	@Test
	public EntidadeDominio buscarPorCodigo(Long codigo) { {
			

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM db_tenoriogames.tb_fornecedor");
			sql.append(" where tb_fornecedor.forn_codigo =?");

			Fornecedor fornecedorRetorno = new Fornecedor();

			Connection con = Conexao.getConnection();

			try {
				PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
				pstm.setLong(1, codigo);
				ResultSet rSet = pstm.executeQuery();

				while (rSet.next()) {
					fornecedorRetorno.setCodigo(rSet.getLong("forn_codigo"));
					fornecedorRetorno.setCNPJ(rSet.getString("forn_CNPJ"));
					fornecedorRetorno.setNomeFantasia(rSet.getString("forn_nome_fantasia"));
					fornecedorRetorno.setRazaoSocial((rSet.getString("forn_razao_social")));
					fornecedorRetorno.setTelefone((rSet.getString("forn_Telefone")));
					Endereco endereco = new Endereco();
					endereco.setBairro(rSet.getString("end_Bairro"));
					endereco.setCEP(rSet.getString("end_CEP"));
					endereco.setCidade(rSet.getString("end_Cidade"));
					endereco.setEstado(rSet.getString("end_Estado"));
					endereco.setNumero(rSet.getInt("end_Numero"));
					endereco.setRua(rSet.getString("end_Rua"));
					fornecedorRetorno.setEndereco(endereco);

				}

			} catch (SQLException e) {
				e.printStackTrace();
				FacesUtil.adicionarMSGError(e.getMessage());
			}

			return fornecedorRetorno;
		}

	}

	@Test
	public void Excluir(EntidadeDominio entidade) {
			
			if(!(entidade instanceof Fornecedor))
				return;
			
			Fornecedor fornecedor = new Fornecedor();
			fornecedor = (Fornecedor) entidade;
			
			StringBuffer sql = new StringBuffer();
			sql.append("delete from db_tenoriogames.tb_fornecedor where forn_codigo = ?");
			
			Connection con = Conexao.getConnection();
			
			try {
				PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
				pstm.setLong(1, fornecedor.getCodigo());
				pstm.executeUpdate();
				
			} catch (Exception e){
				e.printStackTrace();
				FacesUtil.adicionarMSGError((e.getMessage()));
			}
			
			
		
	}



	@Test
	public void Editar(EntidadeDominio entidade) {
		if(!(entidade instanceof Fornecedor))
			return;
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor = (Fornecedor) entidade;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE db_tenoriogames.tb_fornecedor set forn_CNPJ =? ,forn_Telefone = ?,end_Bairro = ?,end_CEP = ?,");
		sql.append("end_Cidade = ?,end_Estado = ?,end_Numero= ? ,end_Rua = ?,forn_nome_fantasia= ?,");
		sql.append("forn_razao_social =? ");	
		sql.append("WHERE forn_codigo=?");

		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			int i=0;
			pstm.setString(++i,fornecedor.getCNPJ());
			pstm.setString(++i,fornecedor.getTelefone());
			pstm.setString(++i,fornecedor.getEndereco().getBairro());
			pstm.setString(++i, fornecedor.getEndereco().getCEP());
			pstm.setString(++i,fornecedor.getEndereco().getCidade());
			pstm.setString(++i,fornecedor.getEndereco().getEstado());
			pstm.setInt(++i,fornecedor.getEndereco().getNumero());
			pstm.setString(++i,fornecedor.getEndereco().getRua());
			pstm.setString(++i,fornecedor.getNomeFantasia());
			pstm.setString(++i,fornecedor.getRazaoSocial());
			pstm.setLong(++i, fornecedor.getCodigo());
			System.out.println(pstm);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
	}


	@Override
	public EntidadeDominio consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}
		






}
