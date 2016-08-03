package br.com.tenoriogames.core.DAO;

import java.math.BigDecimal;
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
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.Devolucao;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Venda;

public class VendaDAO implements IDAO  {

	public Long  Salvar(EntidadeDominio entidade) throws SQLException {
		
		
		Venda venda = new Venda();
		venda = (Venda) entidade;
		Long codigo = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO db_tenoriogames.tb_vendas(ven_codigo,ven_horario,ven_peso,ven_valor_total,tb_usuario_usr_codigo,");
		sql.append("ven_status)");
		sql.append("VALUES (?,?,?,?,?,?)");
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
		
		try {
			SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
			String vendaHorario = stf.format(venda.getHorario());
			
			int i=0;
			pstm.setLong(++i, venda.getCodigo());
			pstm.setString(++i, vendaHorario);
			pstm.setDouble(++i,venda.getPeso());
			pstm.setBigDecimal(++i,venda.getValor());
			pstm.setLong(++i, venda.getUsuario().getCodigo());
			pstm.setString(++i,venda.getStatus());
			pstm.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
			return null;
		}
		ResultSet rset =  pstm.getGeneratedKeys();
		while(rset.next()){
			 codigo = (long) rset.getInt(1);
			
		}
		
		codigo = Long.parseLong(codigo.toString());
		return codigo;
		
	}

	public void Editar(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof Venda))
			return;

		Venda venda = new Venda();
		venda = (Venda) entidade;

		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE db_tenoriogames.tb_vendas set ven_status = ? ");
		sql.append("WHERE ven_codigo=?");

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			int i = 0;
			pstm.setString(++i, venda.getStatus());
			pstm.setLong(++i, venda.getCodigo());
			pstm.executeUpdate();

		

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		
	}


	public void Excluir(EntidadeDominio entidade) throws SQLException {
		if (!(entidade instanceof Devolucao))
			return;

		Venda venda = new Venda();
		venda = (Venda) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("delete from db_tenoriogames.tb_vendas where ven_codigo = ?");

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, venda.getCodigo());
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError((e.getMessage()));
		}

	
		
	}


	public List<EntidadeDominio> listar() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM db_tenoriogames.tb_vendas");
		sql.append(" order by db_tenoriogames.tb_vendas.ven_codigo desc ;");
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {

				Venda venda = new Venda();
				venda.setCodigo(rSet.getLong("ven_codigo"));
				venda.setHorario(rSet.getDate("ven_horario"));
				venda.setPeso(rSet.getDouble("ven_peso"));
				venda.setValor(rSet.getBigDecimal("ven_valor_total"));
				venda.getUsuario().setCodigo((rSet.getLong("tb_usuario_usr_codigo")));
				venda.setStatus((rSet.getString("ven_status")));
				lista.add(venda);
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
		sql.append("SELECT * FROM db_tenoriogames.tb_vendas");
		sql.append(" where tb_vendas.ven_codigo =?");

		Venda venda = new Venda();

		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, codigo);
			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {
				venda.setCodigo(rSet.getLong("ven_codigo"));
				venda.setHorario(rSet.getDate("ven_horario"));
				venda.setPeso(rSet.getDouble("ven_peso"));
				venda.setValor(rSet.getBigDecimal("ven_valor_total"));
				venda.getUsuario().setCodigo((rSet.getLong("tb_usuario_usr_codigo")));
				venda.setStatus((rSet.getString("ven_status")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}

		return venda;
	
	}
	
	@Test
	public  void testar() throws SQLException{
		Venda venda =  new Venda();
		
		venda.setCodigo(69L);
		venda.setHorario( new java.util.Date());
		venda.setPeso(1D);
		venda.setStatus("normal");
		UsuarioDAO dao = new UsuarioDAO();
		venda.setUsuario(dao.buscarPorCodigo(1L));
		venda.setValor(new BigDecimal(1));
		System.out.println(Salvar(venda));
		
		
	}

	@Override
	public EntidadeDominio consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
		
}
