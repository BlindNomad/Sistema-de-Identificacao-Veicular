/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nomad.thiago.controleautomotivo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author thiagoj
 */
public class EnviarDados {

    private String url;

    private String parametros;

    private String mensagem;

    private final String USER_AGENT = "Mozilla/5.0";

    public EnviarDados() {
    }

    public String execute() {

        try {
            URL urlDestino = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) urlDestino.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(this.parametros);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            System.out.println("\nEnviando POST para a url: " + this.url);
            System.out.println("Paramentros do post: " + this.parametros);
            System.out.println("Resposta do servidor: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            return response.toString();

        } catch (MalformedURLException ex) {
            Logger.getLogger(EnviarDados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnviarDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
