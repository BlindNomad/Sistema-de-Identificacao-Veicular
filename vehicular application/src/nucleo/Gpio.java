/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nucleo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiagoj
 */
public class Gpio {
	
	public enum type{
		in, out
	}
	
	private int pino;
	
	private type tipo;

	public Gpio(int pino, type tipo) {
		this.pino = pino;
		this.tipo = tipo;
		if (this.tipo == type.out){
			try {
				System.out.println("Configurando");
				Process p = Runtime.getRuntime().exec("gpio mode " + this.pino +" out");
				System.out.println("gpio mode " + this.pino +" out");
				p.waitFor();
			} catch (IOException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}
	
	public int enable(){
		if (this.tipo == type.out){
			try {
				Process p = Runtime.getRuntime().exec("gpio write " + this.pino + " 1");
				System.out.println("gpio write " + this.pino + " 1");
				p.waitFor();
			} catch (IOException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
				
		
		return 1;
	}
	
	public void disable(){
		try {
			Process p = Runtime.getRuntime().exec("gpio write " + this.pino + " 0");
			System.out.println("gpio write " + this.pino + " 0");
			p.waitFor();
		} catch (IOException ex) {
			Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	
}
