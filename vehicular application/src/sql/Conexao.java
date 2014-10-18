/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiagoj
 */
public class Conexao {
	
	private String ipBanco;
	
	private String usuario;
	
	private String senha;
	
	private String banco;

	
	/**
	 * Contrutor da classe
	 * @param ipBanco IP do banco de dados
	 * @param usuario Usuário de conexao
	 * @param senha Senha de conexao
	 * @param banco Banco de dados acessado.
	 */
	public Conexao(String ipBanco, String usuario, String senha, String banco) {
		this.ipBanco = ipBanco;
		this.usuario = usuario;
		this.senha = senha;
		this.banco = banco;
	}
	
	/**
	 * Efetua a conexão com o banco
	 * @return Retorna a conexao.
	 */
	public Connection conectar(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexao = DriverManager.getConnection("jdbc:mysql://" + this.ipBanco + "/" + this.banco + "?user=" + this.usuario + "&password=" + this.senha);
			return conexao;
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
		
	}
	
	
	
}
