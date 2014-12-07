/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;
import nucleo.LeitorDeTag;


/**
 *
 * @author thiagoj
 */
public class Condutor{

	private int id;

	private String nome;

	private String numeroCarteira;

	private String validade;

	private int pontos;

	private int liberado;

	private String cartao;
	
	private String dataInfo;
	
	private int tempoVida;

	private Connection conexao;
	
	

	public Condutor(Connection conexao) {
		this.conexao = conexao;
		this.cartao = "";
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroCarteira() {
		return numeroCarteira;
	}

	public void setNumeroCarteira(String numeroCarteira) {
		this.numeroCarteira = numeroCarteira;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getLiberado() {
		return liberado;
	}

	public void setLiberado(int liberado) {
		this.liberado = liberado;
	}

	public String getCartao() {
		return cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}

	public String getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	public int getTempoVida() {
		return tempoVida;
	}

	public void setTempoVida(int tempoVida) {
		this.tempoVida = tempoVida;
	}
	
	
	

	/**
	 * Salva os dados no banco de dados.
	 * @return 
	 */
	public boolean salvar() {

		if (this.atualiza()) {
			this.apagar();
		}

		String query;

		query = "INSERT INTO condutor (id, nome, numero_carteira, validade, pontos, liberado, cartao, data_inf, tempo_vida) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
			preparedStatement.setInt(1, this.id);
			preparedStatement.setString(2, this.nome);
			preparedStatement.setString(3, this.numeroCarteira);
			preparedStatement.setString(4, this.validade);
			preparedStatement.setInt(5, this.pontos);
			preparedStatement.setInt(6, this.liberado);
			preparedStatement.setString(7, this.cartao);
			preparedStatement.setString(8, this.dataInfo);
			preparedStatement.setInt(9, this.tempoVida);
			preparedStatement.executeUpdate();
			return true;

		} catch (SQLException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
		}

		return false;

	}

	/**
	 * Verifica se o registro já existe.
	 *
	 * @return
	 */
	private boolean atualiza() {

		String query = "SELECT * FROM condutor WHERE id = ?";

		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
			preparedStatement.setInt(1, this.id);
			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;

			while (resultSet.next()) {
				++count;
			}

			if (count == 0) {
				return false;
			}

		} catch (SQLException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
		}

		return true;

	}

	/**
	 * Apaga os dados no banco de dados
	 * @return 
	 */
	public boolean apagar() {

		String query = "DELETE FROM condutor WHERE id = ?";

		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
			preparedStatement.setInt(1, this.id);

			preparedStatement.executeUpdate();
			return true;

		} catch (SQLException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(PreparedStatement.EXECUTE_FAILED);
		}

		return false;

	}

	/**
	 * Carrega os dados, do banco de dados.
	 * @return 
	 */
	public boolean Load() {

		String query = "SELECT * FROM condutor WHERE id = ?";

		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
			preparedStatement.setInt(1, this.id);
			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;

			while (resultSet.next()) {
				this.nome = resultSet.getString("nome");
				this.numeroCarteira = resultSet.getString("numero_carteira");
				this.validade = resultSet.getString("validade");
				this.pontos = resultSet.getInt("pontos");
				this.liberado = resultSet.getInt("liberado");
				this.cartao = resultSet.getString("cartao");
				this.dataInfo = resultSet.getString("data_inf");
				this.tempoVida = resultSet.getInt("tempo_vida");
				count++;
			}

			if (count == 0) {
				return false;
			}

		} catch (SQLException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
		}

		return true;

	}
	
	/**
	 * Carrega os dados a partir do numero do cartão.
	 * @return 
	 */
	public boolean condutorPorCartao(){
		
		String query = "SELECT * FROM condutor WHERE cartao = ?";

		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
			preparedStatement.setString(1, this.cartao);
			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;

			while (resultSet.next()) {
				this.id = resultSet.getInt("id");
				this.Load();
				count++;
			}

			if (count == 0) {
				System.out.println("Não encontrado o cartao.");
				return false;
			}

		} catch (SQLException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return true;
		
	}
	
	/**
	 * Verifica se a informação ainda é válida
	 * @return 
	 */
	public boolean validadeDaInformacao(){
		
		Date dataAtual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataBanco = null;
		try {
			dataBanco = sdf.parse(this.dataInfo);
			
		} catch (ParseException ex) {
			Logger.getLogger(Condutor.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		long diferencaMS = dataAtual.getTime() - dataBanco.getTime();
		long diferencaSegundos = diferencaMS / 1000;
		long diferencaDias = ((diferencaSegundos / 60) / 60) / 24;
		
		System.out.println("Dias de validade: " + diferencaDias);
		
		return diferencaDias < this.tempoVida;
		
	}

	
}
