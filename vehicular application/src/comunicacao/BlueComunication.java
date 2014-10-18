/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacao;

import java.io.DataInputStream;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;



/**
 *
 * @author thiagoj
 */
public class BlueComunication extends Thread {

	public final UUID uuid = new UUID("1101", true); //it can be generated randomly
	public final String name = "Echo Server";                       //the name of the service
	public final String url = "btspp://localhost:" + uuid + ";name=" + name + ";authenticate=false;encrypt=false;";
	LocalDevice local = null;
	StreamConnectionNotifier server = null;
	StreamConnection conn = null;

	public BlueComunication() {

	}

	public void run() {
		try {
			System.out.println("Setting device to be discoverable...");
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			System.out.println("Start advertising service...");
			server = (StreamConnectionNotifier) Connector.open(url);
			System.out.println("Waiting for incoming connection...");
			conn = server.acceptAndOpen();
			System.out.println("Client Connected...");
			DataInputStream din = new DataInputStream(conn.openInputStream());
			while (true) {
				String cmd = "";
				char c;
				while (((c = din.readChar()) > 0) && (c != '\n')) {
					cmd = cmd + c;
				}
				System.out.println("Received " + cmd);
			}

		} catch (Exception e) {
			System.out.println("Exception Occured: " + e.toString());
		}

	}

}
