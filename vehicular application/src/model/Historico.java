/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiagoj
 */
public class Historico {
	
	private int id;
	
	private int idCondutor;
	
	private String dataHora;
	
	private String tipoAcesso;
	
	private Connection conexao;

	public Historico(Connection conexao) {
		this.conexao = conexao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCondutor() {
		return idCondutor;
	}

	public void setIdCondutor(int idCondutor) {
		this.idCondutor = idCondutor;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(String tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}
	
	
	public boolean salvar(){
		String query;

		query = "INSERT INTO historico (id_condutor, data_hora, tipo_acesso) values (?, ?, ?)";

		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
			preparedStatement.setInt(1, this.idCondutor);
			preparedStatement.setString(2, this.dataHora);
			preparedStatement.setString(3, this.tipoAcesso);
			preparedStatement.executeUpdate();
			return true;

		} catch (SQLException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
		}

		return false;
	}
	
}
