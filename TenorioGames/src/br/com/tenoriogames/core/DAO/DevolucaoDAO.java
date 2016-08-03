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
import br.com.tenoriogames.domain.Devolucao;
import br.com.tenoriogames.domain.EntidadeDominio;

public class DevolucaoDAO implements IDAO {

	@Override
	public Long Salvar(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof Devolucao))
			return null;

		Devolucao devolucao = new Devolucao();
		devolucao = (Devolucao) entidade;
		Long codigo = null;

		StringBuffer sql = new StringBuffer();

		sql.append(
				"INSERT INTO db_tenoriogames.tb_devolucao(dev_codigo,dev_status,usr_codigo,ven_codigo,prod_codigo,dev_valor) ");
		sql.append("VALUES (?,?,?,?,?,?)");
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);

		try {

			int i = 0;
			pstm.setLong(++i, devolucao.getCodigo());
			pstm.setString(++i, devolucao.getStatus());
			pstm.setLong(++i, devolucao.getUsuario().getCodigo());
			pstm.setLong(++i, devolucao.getVenda().getCodigo());
			pstm.setLong(++i, devolucao.getProduto().getCodigo());
			pstm.setBigDecimal(++i, devolucao.getValor());
			
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
		if (!(entidade instanceof Devolucao))
			return;

		Devolucao devolucao = new Devolucao();
		devolucao = (Devolucao) entidade;

		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE db_tenoriogames.tb_devolucao set dev_status = ? ");
		sql.append("WHERE dev_codigo=?");

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			int i = 0;
			pstm.setString(++i, devolucao.getStatus());
			pstm.setLong(++i, devolucao.getCodigo());
			pstm.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

	}

	@Override
	public void Excluir(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof Devolucao))
			return;

		Devolucao devolucao = new Devolucao();
		devolucao = (Devolucao) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("delete from db_tenoriogames.tb_devolucao where dev_codigo = ?");

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, devolucao.getCodigo());
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError((e.getMessage()));
		}

	}

	@Override
	public List<EntidadeDominio> listar() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM db_tenoriogames.tb_devolucao;");
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {

				Devolucao devolucao = new Devolucao();
				devolucao.setCodigo(rSet.getLong("dev_codigo"));
				devolucao.setStatus(rSet.getString("dev_status"));
				devolucao.getUsuario().setCodigo((rSet.getLong("usr_codigo")));
				devolucao.getVenda().setCodigo((rSet.getLong("ven_codigo")));
				devolucao.setValor((rSet.getBigDecimal("dev_valor")));
				devolucao.getProduto().setCodigo(rSet.getLong("prod_codigo"));
				lista.add(devolucao);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		System.out.println("lista    " + lista);

		return lista;
	}

	@Override
	public EntidadeDominio buscarPorCodigo(Long codigo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM db_tenoriogames.tb_devolucao");
		sql.append(" where tb_devolucao.dev_codigo =?");

		Devolucao devolucao = new Devolucao();

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, codigo);
			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {
				devolucao.setCodigo(rSet.getLong("dev_codigo"));
				devolucao.setStatus(rSet.getString("dev_status"));
				devolucao.getUsuario().setCodigo((rSet.getLong("usr_codigo")));
				devolucao.getVenda().setCodigo((rSet.getLong("ven_codigo")));
				devolucao.getProduto().setCodigo(rSet.getLong("prod_codigo"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		return devolucao;
	}


	@Override
	public EntidadeDominio consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
