package br.cesjf.barbeirodorminhoco;

import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread {
    
    // Declaração de variáveis e objetos
    public final int id;
    public static Estado estado;
    // Semáforo do b arbeiro
    public static final Semaphore barbeiro = new Semaphore(1);
    // Mutex que controla quando o barbeiro terminou o corte para o cliente permanecer com o estado sincronizado
    public static final Semaphore cortando = new Semaphore(0);
    
    // Construtor
    public Barbeiro(int id){
        this.id = id;
        estado = Estado.DORMINDO;
    }
    
    @Override
    public void run() {
        
        try {
            
            // Mensagem inicial
            System.out.println("Barbearia aberta");
            
            while(true) {
                
                switch(estado){
                    
                    case DORMINDO:
                        // Executa o método de impressão
                        imprime();
                        // Acessa o mutex de próximo cliente
                        BarbeiroDorminhoco.proximoCliente.acquire();
                        // Altera o estado para cortando
                        estado = Estado.CORTANDO;
                        break;
                        
                    case CORTANDO:
                        // Executa o método que faz uma pausa na execução enquanto está efetuando o corte
                        cortando();
                        // Altera o estado para terminou
                        estado = Estado.TERMINOU;
                        break;
                    
                    case TERMINOU:
                        // Impressão
                        System.out.println("O barbeiro está livre para o próximo cliente");
                        // Verifica se existe algum cliente aguardando para definir o próximo estado
                        if(BarbeiroDorminhoco.aguardando == 0) {
                            // Altera o estado
                            estado = Estado.DORMINDO;
                        } else {
                            // Altera o estado
                            estado = Estado.CORTANDO;
                        }
                        // Libera o mutex que controla quando o barbeiro terminou o corte para que o cliente esta sincronizado
                        Barbeiro.cortando.release();
                        break;
                        
                }
                
            }
            
        } catch (InterruptedException ex) {}
        
    }
    
    // Definição do tempo que o cliente fica cortando o cabelo
    private void cortando() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 10000));
        } catch (InterruptedException e) {}
    }
    
    // Método que faz a impressão
    public void imprime() {
        System.out.println("O barbeiro" + estado.getDescricao());
    }
    
}
