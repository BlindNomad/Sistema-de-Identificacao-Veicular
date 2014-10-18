/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carcore;

import comunicacao.BlueComunication;
import nucleo.Aguardando;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Condutor;
import model.Veiculo;

import org.json.JSONObject;
import sql.Conexao;

/**
 *
 * @author thiagoj
 */
public class CarCore {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here

		Aguardando w = new Aguardando(true, 3, 500);
		w.start();

		BlueComunication bt = new BlueComunication();
		bt.start();


//		JSONObject dados = new JSONObject();
//		Veiculo veiculo = new Veiculo();
//
//
//		
//		Conexao conexaoSql = new Conexao("192.168.3.163", "root", "thjs0212", "car_core");
//
//		Condutor condutor = new Condutor(conexaoSql.conectar());
//		condutor.setCartao("123456789");
//		if (!condutor.condutorPorCartao()) {
//			
//			veiculo.buscaCondutoresNuvem();
//			
//		}else if (!condutor.validadeDaInformacao()) {
//			
//			veiculo.buscarCondutorNuvem(condutor.getCartao());
//			
//		}
		

	}

}
