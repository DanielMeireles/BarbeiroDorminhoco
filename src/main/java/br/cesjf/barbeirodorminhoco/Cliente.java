package br.cesjf.barbeirodorminhoco;

public class Cliente extends Thread {
    
    // Declaração de variáveis e objetos
    private final int id;
    private static Estado estado;
    
    // Construtor
    public Cliente(int id){
        this.id = id;
        estado = Estado.CHEGOU;
    }
    
    @Override
    public void run() {
        try {
            
            // Variável que controla quando a Thread deve ser finalizada
            boolean ativo = true;
            
            // Loop que permanece em execução enquanto a variável ativo é verdadeira
            while(ativo) {
                
                switch(estado) {
                    case CHEGOU:
                        // Executa o método que faz a impressão
                        imprime();
                        // Verifica se as cadeiras estão cheias, se não estiverem o cliente aguarda, caso contrário desiste
                        if(BarbeiroDorminhoco.aguardando < BarbeiroDorminhoco.quantidadeCadeiras) {
                            // Altera o estado para aguardando
                            estado = Estado.AGUARDANDO;
                        } else {
                            // Altera o estado para desistiu
                            estado = Estado.DESISTIU;
                        }
                        break;
                    
                    case AGUARDANDO:
                        // Executa o método que faz a impressão
                        imprime();
                        // Acrescenta o cliente ao contador de clientes aguardando
                        BarbeiroDorminhoco.aguardando++;
                        // Tenta fazer o acesso a exclusão mutua do barbeiro
                        Barbeiro.barbeiro.acquire();
                        // Ao ser liberado altera o estado para cortando
                        estado = Estado.CORTANDO;
                        break;
                    
                    case CORTANDO:
                        // Executa o método que faz a impressão
                        imprime();
                        // Subtrai o cliente do contador de clientes aguardando
                        BarbeiroDorminhoco.aguardando--;
                        // Libera o cliente´para o barbeiro fazer o corte
                        BarbeiroDorminhoco.proximoCliente.release();
                        // Tenta acessar o mutex que controla quando o barbeiro terminou o corte
                        Barbeiro.cortando.acquire();
                        // Ao finalizar altera o estado para terminou
                        estado = Estado.TERMINOU;
                        break;
                    
                    case TERMINOU:
                        // Executa o método que faz a impressão
                        imprime();
                        // Libera o semáforo do barbeiro para que faça o próximo corte
                        Barbeiro.barbeiro.release();
                        // Altera a varável ativo para que a Thread seja finalizada
                        ativo = false;
                        break;
                        
                    case DESISTIU:
                        // Executa o método que faz a impressão
                        imprime();
                        // Altera a varável ativo para que a Thread seja finalizada
                        ativo = false;
                        break;
                    
                }
                
            }
            
        } catch (InterruptedException ex) {}
    }
    
    // Método que faz a impressão
    private void imprime() {
        System.out.println("O cliente " + this.id + estado.getDescricao());
    }
    
}