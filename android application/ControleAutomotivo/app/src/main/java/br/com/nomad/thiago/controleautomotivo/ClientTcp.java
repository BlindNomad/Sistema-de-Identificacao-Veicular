package br.com.nomad.thiago.controleautomotivo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Created by thiagoj on 14/10/14.
 */
public class ClientTcp extends Thread{

    private int porta;

    private String endereco;

    private JSONArray dispositivos;

    private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

    private BluetoothAdapter mAdapter;

    private ObjectOutputStream saida;

    private ObjectInputStream entrada;




    public ClientTcp(int porta) {
        this.porta = porta;
        this.endereco = endereco;
    }

    public void run(){
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mAdapter == null){
            System.out.println("Não suporta bluetooth!");
        }else{

            if(!mAdapter.isEnabled()){
                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

//                startActivityForResult(i, 1);
            }else {

                mAdapter.startDiscovery();

                Set<BluetoothDevice> list = mAdapter.getBondedDevices();
                for (BluetoothDevice bd : list) {
                    System.out.println(bd.getName());
                    for (int i = 0; i < this.dispositivos.length(); i++){
                        try {
                            if(bd.getName().contains(this.dispositivos.getString(i))){
                                System.out.println(this.dispositivos.getString(i));
                                this.con(bd);
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                }
            }

        }


    }

    private void con(BluetoothDevice device){
        try {
            BluetoothSocket socket;
            socket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
            mAdapter.cancelDiscovery();
            socket.connect();
            System.out.println("Conectado");
            this.entrada = new ObjectInputStream(socket.getInputStream());
            Mensagem mensagem;
            mensagem = (Mensagem) this.entrada.readObject();
            System.out.println(mensagem.mensagem);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(JSONArray dispositivos) {
        this.dispositivos = dispositivos;
    }
}
