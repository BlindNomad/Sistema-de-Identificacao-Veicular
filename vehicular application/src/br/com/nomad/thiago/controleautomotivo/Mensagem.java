/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.nomad.thiago.controleautomotivo;

import java.io.Serializable;

/**
 *
 * @author thiagoj
 */
public class Mensagem implements Serializable {

    public int condigo;

    public String mensagem;

    public Mensagem(int condigo, String mensagem) {
        this.condigo = condigo;
        this.mensagem = mensagem;
    }
}