/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nucleo;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Condutor;
import model.Historico;
import org.json.JSONArray;
import org.json.JSONObject;
import sql.Conexao;

/**
 *
 * @author thiagoj
 */
public class DadosNuvem {

	private JSONObject dados;

	private Connection conexao;

	public DadosNuvem(JSONObject dados) {
		this.dados = dados;
		Conexao conexaoSql = new Conexao();
		this.conexao = conexaoSql.conectar();
	}

	/**
	 * Coloca os dados no banco de dados de acordo com o código que chega por JSOn
	 * @return 
	 */
	public boolean execute() {

		int codigo = this.dados.getInt("codigo");

		switch (codigo) {
			case 200:
				condutoresAtualizar(this.dados);
				return true;
				
			case 201:
				condutorAtualizar(this.dados);
				return true;

			default:
				return false;
		}

		

	}

	/**
	 * Atualiza os dados de um vetor de condutores
	 * @param dados
	 * @return 
	 */
	private boolean condutoresAtualizar(JSONObject dados) {

		Historico historico = new Historico(this.conexao);

		JSONArray condutores = dados.getJSONArray("mensagem");

		for (int i = 0; i < condutores.length(); i++) {
			JSONObject c = condutores.getJSONObject(i);
			Condutor condutor = new Condutor(this.conexao);
			condutor.setId(c.getInt("id"));
			condutor.setNome(c.getString("nome"));
			condutor.setCartao(c.getString("cartao"));
			condutor.setLiberado(c.getInt("liberado"));
			condutor.setNumeroCarteira(c.getString("nCarteira"));
			condutor.setPontos(c.getInt("pontos"));
			condutor.setValidade(c.getString("validade"));
			condutor.setTempoVida(c.getInt("tempoVida"));
			Date data = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			condutor.setDataInfo(sdf.format(data));
			condutor.salvar();
		}

		return true;

	}

	/**
	 * Atualiza os dados de 1 condutor
	 * @param dados
	 * @return 
	 */
	private boolean condutorAtualizar(JSONObject dados) {

		JSONObject c = dados.getJSONObject("mensagem");
		Condutor condutor = new Condutor(this.conexao);
		condutor.setId(c.getInt("id"));
		condutor.setNome(c.getString("nome"));
		condutor.setCartao(c.getString("cartao"));
		condutor.setLiberado(c.getInt("liberado"));
		condutor.setNumeroCarteira(c.getString("nCarteira"));
		condutor.setPontos(c.getInt("pontos"));
		condutor.setValidade(c.getString("validade"));
		condutor.setTempoVida(c.getInt("tempoVida"));
		Date data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		condutor.setDataInfo(sdf.format(data));
		condutor.salvar();
		
		return true;
	}
}
