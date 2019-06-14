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
            // Exclusão mútua - Começa a executar
            BarbeiroDorminhoco.mutex.acquire();
            // Executa o método que faz a impressão
            imprime();
            // Verifica se as cadeiras já estão cheias
            if(BarbeiroDorminhoco.aquardando < BarbeiroDorminhoco.quantidadeCadeiras+1) {
                // Faz a adição no contador de clientes aguardando
                BarbeiroDorminhoco.aquardando++;
                // Exclusão mútua - Termina a execução
                BarbeiroDorminhoco.mutex.release();
                // Acessa o semáforo do barbeiro
                Barbeiro.semaforo.acquire();
                // Acessa o semáforo de cliente
                BarbeiroDorminhoco.clientes.release();
                // Altera o estado
                estado = Estado.CORTANDO;
                BarbeiroDorminhoco.clienteCortando = id+1;
                // Acessa o semáforo do barbeiro
                Barbeiro.semaforo.acquire();
                // Altera o estado
                estado = Estado.TERMINOU;
                // Executa o método que faz a impressão
                imprime();
            } else {
                // Exclusão mútua - Termina a execução
                BarbeiroDorminhoco.mutex.release();
                // Altera o estado
                estado = Estado.DESISTIU;
                // Executa o método que faz a impressão
                imprime();
            }
            // No fim da execução elimina a Thread
            Thread.currentThread().isInterrupted();
        } catch (InterruptedException ex) {}
    }
    
    // Definição do tempo que o cliente fica cortando o cabelo
    private void cortando() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 10000));
        } catch (InterruptedException e) {}
    }
    
    // Método que faz a impressão
    private void imprime() {
        System.out.println("O cliente " + (id + 1) + estado.getDescricao());
    }
    
}