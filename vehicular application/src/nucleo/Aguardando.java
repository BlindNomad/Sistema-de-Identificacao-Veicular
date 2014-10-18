/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nucleo;

import nucleo.Gpio;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiagoj
 */
public class Aguardando extends Thread {

	private boolean habilitado;

	private int pinoLend;

	private int intervalo;

	public Aguardando(boolean habilitado, int pinoLend, int intervalo) {
		this.habilitado = habilitado;
		this.pinoLend = pinoLend;
		this.intervalo = intervalo;
	}

	@Override
	public void run() {
		Gpio pino = new Gpio(this.pinoLend, Gpio.type.out);
		try {
			while (this.habilitado) {
				pino.enable();
				Thread.sleep(this.intervalo);
				pino.disable();
				Thread.sleep(this.intervalo);
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(Aguardando.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getPinoLend() {
		return pinoLend;
	}

	public void setPinoLend(int pinoLend) {
		this.pinoLend = pinoLend;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
	
	

}
