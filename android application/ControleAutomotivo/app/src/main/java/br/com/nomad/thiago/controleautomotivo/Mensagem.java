package br.com.nomad.thiago.controleautomotivo;

import java.io.Serializable;

/**
 * Created by thiagoj on 16/10/14.
 */
public class Mensagem implements Serializable {

    public int condigo;

    public String mensagem;

    public Mensagem(int condigo, String mensagem) {
        this.condigo = condigo;
        this.mensagem = mensagem;
    }
}
