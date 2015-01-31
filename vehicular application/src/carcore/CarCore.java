/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carcore;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nucleo.Display;
import model.Condutor;
import model.Veiculo;
import nucleo.Gpio;
import nucleo.LeitorDeTag;

import org.json.JSONObject;
import org.json.zip.Huff;
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

		System.out.println("Testando I/O");

		Display d = new Display();
		d.setTipo(Display.type.TESTE);
		d.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			Logger.getLogger(CarCore.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println("Iniciando");
		d.setTipo(Display.type.INICIANDO);

		//JSONObject dados = new JSONObject();
		

		

		Conexao conexaoSql = new Conexao();

		while (true) {
			Veiculo veiculo = new Veiculo();
			Condutor condutor = new Condutor(conexaoSql.conectar());
			LeitorDeTag serial = new LeitorDeTag();
			serial.setArquivo("./tag.tag");
			serial.setSerial("");
			serial.start();
			d.setTipo(Display.type.AGUARDANDO);
			System.out.println("Aguardando");
			while (serial.getSerial() == "") {
			System.out.println("Valor Serial: " + serial.getSerial());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(CarCore.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			condutor.setCartao(serial.getSerial());
			

			System.out.println("Procurando condutor");
			if (!condutor.condutorPorCartao()) {

				veiculo.buscaCondutoresNuvem();

			} else if (!condutor.validadeDaInformacao()) {

				veiculo.buscarCondutorNuvem(condutor.getCartao());

			}

			if (condutor.getLiberado() == 1) {
				System.out.println("Condutor Habilitado");
				d.setTipo(Display.type.HABILITADO);

			} else {
				System.out.println("Condutor Desabilitado");
				d.setTipo(Display.type.DESABILITADO);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					Logger.getLogger(CarCore.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			serial.setHabilitado(false);
			LeitorDeTag desligar = new LeitorDeTag();
			desligar.setArquivo("./tag.tag");
			desligar.setSerial("ligado");
			desligar.start();
			System.out.println("Veiculo Habilitado = " + desligar.getSerial());
			String resposataEntrada = "0";
			while (!desligar.getSerial().equals("d1234") && condutor.getLiberado() == 1 && resposataEntrada.equals("0")) {
				System.out.println(desligar.getSerial() + " = " + condutor.getLiberado());
				Gpio entrada = new Gpio(8, Gpio.type.in);
				
				resposataEntrada = entrada.enable();
				System.out.println("Entrada = " + resposataEntrada);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(CarCore.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			d.setTipo(Display.type.REINICIAR);
			
			System.out.println("Veiculo desligado");
			desligar.setHabilitado(false);
			condutor = null;
			serial = null;
			veiculo = null;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(CarCore.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

}
