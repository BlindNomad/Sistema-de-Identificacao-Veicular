/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nucleo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiagoj
 */
public class LeitorDeTag extends Thread {

	

	private String serial;

	private boolean habilitado;

	private String arquivo;

	

	@Override
	public void run() {

		this.habilitado = true;
		this.serial = "";

		while (this.habilitado) {
			try {
				Thread.sleep(1000);
				File file = new File(this.arquivo);
				if (file.exists()) {
					FileInputStream stream = new FileInputStream(this.arquivo);
					InputStreamReader reader = new InputStreamReader(stream);
					BufferedReader br = new BufferedReader(reader);
					this.serial = br.readLine();
					System.out.println("Lido: " + this.serial);
					file.delete();

				}

			} catch (InterruptedException ex) {
				Logger.getLogger(LeitorDeTag.class.getName()).log(Level.SEVERE, null, ex);
			} catch (FileNotFoundException ex) {
				Logger.getLogger(LeitorDeTag.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(LeitorDeTag.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

}
