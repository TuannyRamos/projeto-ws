package br.com.projetows.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tuanny
 */
public class Cidade {
    
    private int id;
    private String nome;
    private String uf;
    private Date atualizacao;
    private List<Previsao> previsoes;

    
    public Cidade() {
        this.previsoes = new ArrayList<>();
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(Date atualizacao) {
        this.atualizacao = atualizacao;
    }

    public List<Previsao> getPrevisoes() {
        return previsoes;
    }

    public void setPrevisoes(List<Previsao> previsoes) {
        this.previsoes = previsoes;
    }
}
