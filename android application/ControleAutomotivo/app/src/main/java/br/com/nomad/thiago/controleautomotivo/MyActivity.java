package br.com.nomad.thiago.controleautomotivo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyActivity extends Activity {

    private EditText usuario;

    private EditText senha;

    private EditText teste;

    private TextView statusV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        usuario = (EditText) findViewById(R.id.eTUsuario);
        senha = (EditText) findViewById(R.id.eTSenha);
        statusV = (TextView) findViewById(R.id.tVStatus);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void status(final String mensagem){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusV.setText(mensagem);
            }
        });



    }


    public void btClick(View view){
        Runnable r = new Runnable() {



            @Override
            public void run() {
                JSONObject j = new JSONObject();

                try {
                    j.put("codigo", 3);
                    j.put("usuario", usuario.getText().toString());
                    j.put("senha", Sha1.sha1(senha.getText().toString()));

                    EnviarDados enviarDados = new EnviarDados();
                    enviarDados.setUrl("http://192.168.254.114/json");
                    enviarDados.setParametros("dados=" + j);
                    JSONObject recebimento = new JSONObject(enviarDados.execute());

                    if ((recebimento.getInt("codigo") == 300)) {
                        JSONArray jPlacas = recebimento.getJSONArray("mensagem");
                        status("Autenticado");
                        ClientTcp tcp = new ClientTcp(888);
                        tcp.setDispositivos(jPlacas);
                        tcp.start();
                    }
                    else{

                        status("Não autenticado");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
    }
}
