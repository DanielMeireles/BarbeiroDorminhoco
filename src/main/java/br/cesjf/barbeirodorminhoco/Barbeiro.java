package br.cesjf.barbeirodorminhoco;

import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread {
    
    // Declaração de variáveis e objetos
    public final int id;
    public static Estado estado;
    public static final Semaphore semaforo = new Semaphore(1);
    
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
                
                // Verificação se existe cliente aguardando para controlar o estado do barbeiro
                if(BarbeiroDorminhoco.aquardando == 0){
                    estado = Estado.DORMINDO;
                } else {
                    estado = Estado.CORTANDO;
                }
                
                // Executa o método de impressão
                imprime();
                
                // Acessa o semáforo de clientes
                BarbeiroDorminhoco.clientes.acquire();
                
            }
        } catch (InterruptedException ex) {}
        
    }
    
    // Método que faz a impressão
    public void imprime() {
        System.out.println("O barbeiro" + estado.getDescricao());
    }
    
}
