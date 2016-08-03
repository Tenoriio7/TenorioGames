package br.com.tenoriogames.core.factory;

import java.sql.Connection;

public class TestaConexao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conexao = Conexao.getConnection();
	}

}
