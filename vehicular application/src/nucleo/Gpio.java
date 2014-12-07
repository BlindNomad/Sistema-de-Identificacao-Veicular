/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nucleo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiagoj
 */
public class Gpio {

	private static final String driver = "/usr/local/bin/gpio";

	public enum type {

		in, out
	}

	private int pino;

	private type tipo;

	public Gpio(int pino, type tipo) {
		this.pino = pino;
		this.tipo = tipo;
		if (this.tipo == type.out) {
			try {
				//System.out.println("Configurando");
				Process p = Runtime.getRuntime().exec(this.driver + " mode " + this.pino + " out");
				//System.out.println("gpio mode " + this.pino +" out");
				p.waitFor();
			} catch (IOException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			Process p;
			try {
				p = Runtime.getRuntime().exec(this.driver + " mode " + this.pino + " input");
				p.waitFor();
				System.out.println(this.driver + " mode " + this.pino + " out");
			} catch (IOException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	/**
	 * Escreve ou lê na saída
	 * @return 
	 */
	public String enable() {
		if (this.tipo == type.out) {
			try {
				Process p = Runtime.getRuntime().exec(this.driver + " write " + this.pino + " 1");
				//System.out.println("gpio write " + this.pino + " 1");
				p.waitFor();
			} catch (IOException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			try {
				String lido;
				Process p = Runtime.getRuntime().exec(this.driver + " read " + this.pino);
				System.out.println(this.driver + " read " + this.pino);
				BufferedReader buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
				lido = buff.readLine();
				System.out.println("lido: " + lido);
				p.waitFor();
				
				return lido;
			} catch (IOException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return "0";
	}

	/**
	 * Desliga a saída
	 */
	public void disable() {
		try {

			Process p = Runtime.getRuntime().exec(this.driver + " write " + this.pino + " 0");
			//System.out.println("gpio write " + this.pino + " 0");
			p.waitFor();
		} catch (IOException ex) {
			Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
