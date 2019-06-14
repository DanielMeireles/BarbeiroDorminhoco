package br.cesjf.barbeirodorminhoco;

import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread {
    
    // Declaração de variáveis e objetos
    public final int id;
    public static Estado estado;
    public static final Semaphore semaforo = new Semaphore(1);
    public static final Semaphore mutex = new Semaphore(1);
    
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
                    // Altera o estado
                    estado = Estado.DORMINDO;
                    // Executa o método de impressão
                    imprime();
                }
                // Acessa o semáforo de clientes
                BarbeiroDorminhoco.clientes.acquire();
                // Exclusão mútua - Começa a executar
                BarbeiroDorminhoco.mutex.acquire();
                // Faz a subtração no contador de clientes aguardando
                BarbeiroDorminhoco.aquardando--;
                // Altera o estado
                estado = Estado.CORTANDO;
                // Exclusão mútua - Termina a execução
                BarbeiroDorminhoco.mutex.release();
                // Executa o método que faz a impressão
                imprime();
                // Executa o método que faz uma pausa na execução enquanto está cortando
                cortando();
                // Libera o semáforo do barbeiro
                Barbeiro.semaforo.release();
                // Libera o semáforo do barbeiro
                Barbeiro.semaforo.release();
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
        if(estado == Estado.CORTANDO) {
            System.out.println("O barbeiro" + estado.getDescricao() + " do cliente " + BarbeiroDorminhoco.clienteCortando);
        } else {
            System.out.print("O barbeiro" + estado.getDescricao());
        }
    }
    
}
