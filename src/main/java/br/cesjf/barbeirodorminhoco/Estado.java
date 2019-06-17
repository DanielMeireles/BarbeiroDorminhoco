package br.cesjf.barbeirodorminhoco;

public enum Estado {
    
    DORMINDO(" está dormindo"),
    CORTANDO(" está cortando o cabelo"),
    DESISTIU(" desistiu porque a barbearia está cheia"),
    CHEGOU(" chegou na barbearia"),
    TERMINOU(" terminou e está indo embora"),
    AGUARDANDO(" aguardando sua vez");
    
    private String descricao;
 
    Estado(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
    
}
