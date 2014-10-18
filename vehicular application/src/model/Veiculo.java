/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import comunicacao.EnviaDados;
import nucleo.DadosNuvem;
import org.json.JSONObject;

/**
 *
 * @author thiagoj
 */
public class Veiculo {
	
	private String placa;
	
	private String classe;

	public Veiculo() {
		
		this.placa = "LWX-7546";
		this.classe = "A";
		
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public boolean buscaCondutoresNuvem(){
		JSONObject dados = new JSONObject();
		EnviaDados enviar = new EnviaDados();
		
		enviar.setUrl("http://192.168.3.163/json");
		dados.put("placa", this.placa);
		dados.put("classe", this.classe);
		dados.put("codigo", 1);
		
		enviar.setParametros("dados = " + dados);
		JSONObject resposta = new JSONObject(enviar.execute());
		
		DadosNuvem nuvem = new DadosNuvem(resposta);
		return nuvem.execute();
	}
	
	
	public boolean buscarCondutorNuvem(String tag){
		JSONObject dados = new JSONObject();
		EnviaDados enviar = new EnviaDados();
		
		enviar.setUrl("http://192.168.3.163/json");
		dados.put("placa", this.placa);
		dados.put("classe", this.classe);
		dados.put("cartao", tag);
		dados.put("codigo", 2);
		
		enviar.setParametros("dados = " + dados);
		JSONObject resposta = new JSONObject(enviar.execute());
		
		DadosNuvem nuvem = new DadosNuvem(resposta);
		return nuvem.execute();
	}
	
	
}
