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
public class Display extends Thread {

	/**
	 * Estados das saídas
	 */
	public enum type {

		INICIANDO, AGUARDANDO, HABILITADO, DESABILITADO, FALHA, TESTE, REINICIAR
	}

	/**
	 * Se a thread está executando
	 */
	private boolean habilitado;

	/**
	 * Led de aviso de bloqueio
	 */
	private Gpio estadoBloqueio;

	/**
	 * Led de aviso de liberado
	 */
	private Gpio estadoLiberado;

	/**
	 * Saída para o veículo
	 */
	private Gpio sinalVeiculo1;

	/**
	 * Não usado
	 */
	private Gpio sinalVeiculo2;

	

	private type tipo;

	public Display() {
		this.habilitado = true;
		this.estadoBloqueio = new Gpio(3, Gpio.type.out);
		this.estadoLiberado = new Gpio(0, Gpio.type.out);
		this.sinalVeiculo1 = new Gpio(4, Gpio.type.out);
		this.sinalVeiculo2 = new Gpio(5, Gpio.type.out);
		
	}

	@Override
	public void run() {
		try {
			while (this.habilitado) {
				if (this.tipo == type.INICIANDO) {
					this.estadoBloqueio.enable();
					Thread.sleep(500);
					this.estadoBloqueio.disable();
					Thread.sleep(500);
				}
				
				if (this.tipo == type.AGUARDANDO){
					this.estadoLiberado.enable();
					Thread.sleep(500);
					this.estadoLiberado.disable();
					Thread.sleep(500);
				}
				if (this.tipo == type.HABILITADO) {
					this.estadoLiberado.enable();
					this.estadoBloqueio.disable();
					this.sinalVeiculo1.enable();
					Thread.sleep(500);
				}

				if (this.tipo == type.DESABILITADO) {
					this.estadoBloqueio.enable();
					this.estadoLiberado.disable();
					this.sinalVeiculo1.disable();
					Thread.sleep(500);
				}
				
				if (this.tipo == type.REINICIAR){
					this.estadoBloqueio.disable();
					this.estadoLiberado.disable();
					this.sinalVeiculo1.disable();
					Thread.sleep(500);
				}
				
				if (this.tipo == type.TESTE){
					this.teste();
				}
				
				if (this.tipo == type.FALHA){
					this.falha();
				}

			}
		} catch (InterruptedException ex) {
			Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void teste() {
		try {
			this.estadoBloqueio.enable();
			Thread.sleep(200);
			this.estadoBloqueio.disable();
			this.estadoLiberado.enable();
			Thread.sleep(200);
			this.estadoLiberado.disable();
			this.sinalVeiculo1.enable();
			Thread.sleep(200);
			this.sinalVeiculo1.disable();
			this.sinalVeiculo2.enable();
			Thread.sleep(200);
			this.sinalVeiculo2.disable();
		} catch (InterruptedException ex) {
			Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void falha(){
		try {
			this.estadoBloqueio.enable();
			this.estadoLiberado.disable();
			Thread.sleep(500);
			this.estadoBloqueio.disable();
			this.estadoLiberado.enable();
			Thread.sleep(500);
			
			
		} catch (InterruptedException ex) {
			Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public type getTipo() {
		return tipo;
	}

	public void setTipo(type tipo) {
		this.tipo = tipo;
	}

}
