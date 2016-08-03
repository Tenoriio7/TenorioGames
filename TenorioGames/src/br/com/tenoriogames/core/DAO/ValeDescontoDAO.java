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
import br.com.tenoriogames.domain.ValeDesconto;

public class ValeDescontoDAO implements IDAO {

	@Override
	public Long Salvar(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof ValeDesconto))
			return null;

		ValeDesconto desconto = new ValeDesconto();
		desconto = (ValeDesconto) entidade;
		Long codigo = null;

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO db_tenoriogames.tb_valedesconto(vd_codigo,vd_valor,usr_codigo,vd_status) ");

		sql.append("VALUES (?,?,?,?)");
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);

		try {

			int i = 0;
			pstm.setLong(++i, desconto.getCodigo());
			pstm.setBigDecimal(++i, desconto.getValor());
			pstm.setLong(++i, desconto.getUsuario().getCodigo());
			pstm.setString(++i, desconto.getStatus());
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
		if (!(entidade instanceof ValeDesconto))
			return;

		ValeDesconto desconto = new ValeDesconto();
		desconto = (ValeDesconto) entidade;
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE db_tenoriogames.tb_valedesconto set vd_status=? ");
		sql.append("WHERE vd_codigo= ?");

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			int i = 0;
			pstm.setString(++i, desconto.getStatus());
			pstm.setLong(++i, desconto.getCodigo());
			pstm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

	}

	public void Excluir(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof ValeDesconto))
			return;

		ValeDesconto desconto = new ValeDesconto();
		desconto = (ValeDesconto) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("delete from db_tenoriogames.tb_valedesconto where vd_codigo = ?");

		Connection con = Conexao.getConnection();

		try {
			int i = 0;
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(++i, desconto.getCodigo());
			pstm.setBigDecimal(++i, desconto.getValor());
			pstm.setLong(++i, desconto.getUsuario().getCodigo());
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError((e.getMessage()));
		}

	}

	@Override
	public List<EntidadeDominio> listar() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM db_tenoriogames.tb_valedesconto;");
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {

				ValeDesconto desconto = new ValeDesconto();
				desconto.setCodigo(rSet.getLong("vd_codigo"));
				desconto.setValor(rSet.getBigDecimal("vd_valor"));
				desconto.getUsuario().setCodigo(rSet.getLong("usr_codigo"));
				desconto.setStatus(rSet.getString("vd_status"));
				lista.add(desconto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		return lista;

	}

	@Override
	public EntidadeDominio buscarPorCodigo(Long codigo) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM db_tenoriogames.tb_valedesconto");
		sql.append(" where tb_valedesconto.vd_codigo =?");

		Connection con = Conexao.getConnection();
		ValeDesconto desconto = new ValeDesconto();
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, codigo);
			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {
				desconto.setCodigo(rSet.getLong("vd_codigo"));
				desconto.setValor(rSet.getBigDecimal("vd_valor"));
				desconto.getUsuario().setCodigo(rSet.getLong("usr_cod"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		return desconto;

	}

	@Override
	public EntidadeDominio consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
