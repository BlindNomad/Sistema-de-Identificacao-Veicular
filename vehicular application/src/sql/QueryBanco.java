/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sql;

import java.sql.Connection;


/**
 *
 * @author thiagoj
 */
public class QueryBanco {
	
	private String query;
	
	private Connection conexao;

	public QueryBanco(Connection conexao) {
		this.conexao = conexao;
	}
	
	
}
