package br.com.tenoriogames.core.factory;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.tenoriogames.core.util.FacesUtil;

public class Conexao {
	private static  Connection con =  null;
	
	private static final String url = "jdbc:mysql://localhost:3306/db_tenoriogames";
	private static final String usuario = "root";
	private static final String senha = "root";
	
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,usuario,senha);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return con;
	}


}
