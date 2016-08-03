package br.com.tenoriogames.core.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.tenoriogames.core.IDAO;
import br.com.tenoriogames.core.factory.Conexao;
import br.com.tenoriogames.core.util.FacesUtil;
import br.com.tenoriogames.domain.Endereco;
import br.com.tenoriogames.domain.EntidadeDominio;
import br.com.tenoriogames.domain.Usuario;

public class UsuarioDAO implements IDAO {
	
	
	public Long Salvar(EntidadeDominio entidade) throws SQLException {
	
		if(!(entidade instanceof Usuario))
			return null;
		
		Usuario usuario = new Usuario();
		usuario = (Usuario) entidade;
		Long retorno = null ;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO db_tenoriogames.tb_usuario(usr_Codigo,usr_CPF,usr_Console,usr_Email,");
		sql.append("end_Bairro,end_CEP,");
		sql.append("end_Cidade,end_Estado,end_Numero,end_Rua,");
		sql.append("usr_Idade,usr_Nome,usr_RG,usr_Senha,usr_Sexo,usr_Sobrenome,usr_Status,usr_Telefone,dtCadastro)");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
		try {
			
			int i=0;
			pstm.setLong(++i, usuario.getCodigo());
			pstm.setString(++i,usuario.getCPF());
			pstm.setString(++i,usuario.getConsole());
			pstm.setString(++i,usuario.getEmail());
			pstm.setString(++i,usuario.getEndereco().getBairro());
			pstm.setString(++i, usuario.getEndereco().getCEP());
			pstm.setString(++i,usuario.getEndereco().getCidade());
			pstm.setString(++i,usuario.getEndereco().getEstado());
			pstm.setInt(++i,usuario.getEndereco().getNumero());
			pstm.setString(++i,usuario.getEndereco().getRua());
			pstm.setInt(++i,usuario.getIdade());
			pstm.setString(++i,usuario.getNome());
			pstm.setString(++i,usuario.getRG());
			pstm.setString(++i,usuario.getSenha());
			pstm.setString(++i,usuario.getSexo());
			pstm.setString(++i,usuario.getSobrenome());
			pstm.setString(++i,usuario.getStatus());
			pstm.setString(++i,usuario.getTelefone());
			SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
			String cadastro = stf.format(new Date());
			pstm.setString(++i, cadastro);
		
		
			pstm.executeUpdate();
			ResultSet rset =  pstm.getGeneratedKeys();
			while(rset.next()){
				rset.getInt(1);
				retorno=(long) rset.getInt(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return retorno;

	}

	
	public List<EntidadeDominio> listar() {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_usuario;");			
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				
				Usuario usuario = new Usuario();
				usuario.setCodigo(rSet.getLong("usr_Codigo"));
				usuario.setCPF(rSet.getString("usr_CPF"));
				usuario.setConsole(rSet.getString("usr_Console"));
				usuario.setEmail(rSet.getString("usr_Email"));
				
				Endereco endereco =  new Endereco();
				endereco.setBairro(rSet.getString("end_Bairro"));
				endereco.setCEP(rSet.getString("end_CEP"));
				endereco.setCidade(rSet.getString("end_Cidade"));
				endereco.setEstado(rSet.getString("end_Estado"));
				endereco.setNumero(rSet.getInt("end_Numero"));
				endereco.setRua(rSet.getString("end_Rua"));
				usuario.setEndereco(endereco);	
				usuario.setIdade(rSet.getInt("usr_Idade"));
				usuario.setNome(rSet.getString("usr_Nome"));
				usuario.setRG(rSet.getString("usr_RG"));
				usuario.setSenha(rSet.getString("usr_Senha"));
				usuario.setSexo(rSet.getString("usr_Sexo"));
				usuario.setSobrenome(rSet.getString("usr_Sobrenome"));
				usuario.setStatus(rSet.getString("usr_Status"));
				usuario.setTelefone(rSet.getString("usr_Telefone"));
				usuario.setDtCadastro(rSet.getDate("dtCadastro"));		
				
				lista.add(usuario);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return lista;
		
	}
	
	
	public Usuario buscarPorCodigo(Long codigo) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_usuario");
		sql.append(" where tb_usuario.usr_Codigo =?");
		
		
		Connection con = Conexao.getConnection();
		Usuario usuario = new Usuario();
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, codigo);
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()){
				
				usuario.setCodigo(rSet.getLong("usr_Codigo"));
				usuario.setCPF(rSet.getString("usr_CPF"));
				usuario.setConsole(rSet.getString("usr_Console"));
				usuario.setEmail(rSet.getString("usr_Email"));
				
				Endereco endereco =  new Endereco();
				endereco.setBairro(rSet.getString("end_Bairro"));
				endereco.setCEP(rSet.getString("end_CEP"));
				endereco.setCidade(rSet.getString("end_Cidade"));
				endereco.setEstado(rSet.getString("end_Estado"));
				endereco.setNumero(rSet.getInt("end_Numero"));
				endereco.setRua(rSet.getString("end_Rua"));
				usuario.setEndereco(endereco);	
				usuario.setIdade(rSet.getInt("usr_Idade"));
				usuario.setNome(rSet.getString("usr_Nome"));
				usuario.setRG(rSet.getString("usr_RG"));
				usuario.setSenha(rSet.getString("usr_Senha"));
				usuario.setSexo(rSet.getString("usr_Sexo"));
				usuario.setSobrenome(rSet.getString("usr_Sobrenome"));
				usuario.setStatus(rSet.getString("usr_Status"));
				usuario.setTelefone(rSet.getString("usr_Telefone"));
				usuario.setDtCadastro(rSet.getDate("dtCadastro"));				
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return usuario;
	

	}
	
	
	
	public void Excluir(EntidadeDominio entidade) {
		
		if(!(entidade instanceof Usuario))
			return;
		
		Usuario usuario = new Usuario();
		usuario = (Usuario) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from db_tenoriogames.tb_usuario where usr_Codigo = ?");
		
		Connection con = Conexao.getConnection();
		
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setLong(1, usuario.getCodigo());
			pstm.executeUpdate();
			FacesUtil.adicionarMSGInfo("Removido com sucesso!");
			
		} catch (Exception e){
			e.printStackTrace();
			FacesUtil.adicionarMSGError((e.getMessage()));
		}
		
		
		
	}
	
	
	
	
	public void Editar(EntidadeDominio entidade) throws SQLException {
		
		if(!(entidade instanceof Usuario))
			return;
		
		Usuario usuario = new Usuario();
		usuario = (Usuario) entidade;
		Long retorno = null ;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE db_tenoriogames.tb_usuario set usr_Codigo=?, usr_CPF=?, usr_Console =? , usr_Email = ?, ");
		sql.append("end_Bairro = ? ,end_CEP = ?, ");
		sql.append("end_Cidade = ?, end_Estado = ? , end_Numero = ? , end_Rua = ? , ");
		sql.append("usr_Idade = ?, usr_Nome = ? ,usr_RG = ? ,usr_Senha= ? , usr_Sexo= ? , usr_Sobrenome = ? , usr_Status = ? , usr_Telefone = ?, dtCadastro = ? ");
		
		Connection con = Conexao.getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
		try {
			
			int i=0;
			pstm.setLong(++i, usuario.getCodigo());
			pstm.setString(++i,usuario.getCPF());
			pstm.setString(++i,usuario.getConsole());
			pstm.setString(++i,usuario.getEmail());
			pstm.setString(++i,usuario.getEndereco().getBairro());
			pstm.setString(++i, usuario.getEndereco().getCEP());
			pstm.setString(++i,usuario.getEndereco().getCidade());
			pstm.setString(++i,usuario.getEndereco().getEstado());
			pstm.setInt(++i,usuario.getEndereco().getNumero());
			pstm.setString(++i,usuario.getEndereco().getRua());
			pstm.setInt(++i,usuario.getIdade());
			pstm.setString(++i,usuario.getNome());
			pstm.setString(++i,usuario.getRG());
			pstm.setString(++i,usuario.getSenha());
			pstm.setString(++i,usuario.getSexo());
			pstm.setString(++i,usuario.getSobrenome());
			pstm.setString(++i,usuario.getStatus());
			pstm.setString(++i,usuario.getTelefone());
			SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
			String cadastro = stf.format(new Date());
			pstm.setString(++i, cadastro);
		
		
			System.out.println(pstm.executeUpdate());
			pstm.executeUpdate();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		

	}
	
	public Usuario autenticar (String cpf, String senha){
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_usuario");
		sql.append(" WHERE tb_usuario.usr_CPF = ? AND tb_usuario.usr_Senha= ?");
		
		Usuario usuario = new  Usuario();
	
		Connection con = Conexao.getConnection();

		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setString(1, cpf);
			pstm.setString(2, senha);
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()){
				
				usuario.setCodigo(rSet.getLong("usr_Codigo"));
				usuario.setCPF(rSet.getString("usr_CPF"));
				usuario.setConsole(rSet.getString("usr_Console"));
				usuario.setEmail(rSet.getString("usr_Email"));
				
				Endereco endereco =  new Endereco();
				endereco.setBairro(rSet.getString("end_Bairro"));
				endereco.setCEP(rSet.getString("end_CEP"));
				endereco.setCidade(rSet.getString("end_Cidade"));
				endereco.setEstado(rSet.getString("end_Estado"));
				endereco.setNumero(rSet.getInt("end_Numero"));
				endereco.setRua(rSet.getString("end_Rua"));
				usuario.setEndereco(endereco);	
				usuario.setIdade(rSet.getInt("usr_Idade"));
				usuario.setNome(rSet.getString("usr_Nome"));
				usuario.setRG(rSet.getString("usr_RG"));
				usuario.setSenha(rSet.getString("usr_Senha"));
				usuario.setSexo(rSet.getString("usr_Sexo"));
				usuario.setSobrenome(rSet.getString("usr_Sobrenome"));
				usuario.setStatus(rSet.getString("usr_Status"));
				usuario.setTelefone(rSet.getString("usr_Telefone"));
				usuario.setDtCadastro(rSet.getDate("dtCadastro"));	
			
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return usuario;
	 
		
	}


	public EntidadeDominio consultar(EntidadeDominio entidade) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT * FROM db_tenoriogames.tb_usuario");
		sql.append(" where tb_usuario.usr_CPF=?");
		Usuario usuarioRecebe =  (Usuario) entidade;
		
		Connection con = Conexao.getConnection();
		Usuario usuario = new Usuario();
		try {
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql.toString());
			pstm.setString(1, usuarioRecebe.getCPF());
			ResultSet rSet = pstm.executeQuery();
			
			while(rSet.next()){
				
				usuario.setCodigo(rSet.getLong("usr_Codigo"));
				usuario.setCPF(rSet.getString("usr_CPF"));
				usuario.setConsole(rSet.getString("usr_Console"));
				usuario.setEmail(rSet.getString("usr_Email"));
				
				Endereco endereco =  new Endereco();
				endereco.setBairro(rSet.getString("end_Bairro"));
				endereco.setCEP(rSet.getString("end_CEP"));
				endereco.setCidade(rSet.getString("end_Cidade"));
				endereco.setEstado(rSet.getString("end_Estado"));
				endereco.setNumero(rSet.getInt("end_Numero"));
				endereco.setRua(rSet.getString("end_Rua"));
				usuario.setEndereco(endereco);	
				usuario.setIdade(rSet.getInt("usr_Idade"));
				usuario.setNome(rSet.getString("usr_Nome"));
				usuario.setRG(rSet.getString("usr_RG"));
				usuario.setSenha(rSet.getString("usr_Senha"));
				usuario.setSexo(rSet.getString("usr_Sexo"));
				usuario.setSobrenome(rSet.getString("usr_Sobrenome"));
				usuario.setStatus(rSet.getString("usr_Status"));
				usuario.setTelefone(rSet.getString("usr_Telefone"));
				usuario.setDtCadastro(rSet.getDate("dtCadastro"));				
				
				System.out.println(pstm);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			FacesUtil.adicionarMSGError(e.getMessage());
		}
		
		return usuario;
	
	}
	
	@Test
	public void test() throws SQLException{
		
		Usuario usuario  =  new Usuario();
		usuario =  buscarPorCodigo(8L);
		usuario .setNome("Joao");
		Editar(usuario);
		
	}
}

