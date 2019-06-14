package br.cesjf.barbeirodorminhoco;

import java.util.concurrent.Semaphore;

public class BarbeiroDorminhoco {
    
    // Declaração de variáveis e objetos
    public static final int quantidadeCadeiras = 3;
    public static final Semaphore clientes = new Semaphore(0);
    public static final Semaphore mutex = new Semaphore(1);
    public static int aquardando = 0;
    public static int clienteCortando;

    public static void main(String[] args) {
        
        // Instanciação do barbeiro e inicialização de sua Thread
        Barbeiro barbeiro = new Barbeiro(1);
        barbeiro.start();
        
        // Contador para o id de cliente
        int i = 0;
        
        // Cliação dos clientes
        while(true) {
            
            // Instanciação do cliente e inicialização de sua Thread
            Cliente cliente = new Cliente(i);
            cliente.start();
            
            // Adiciona valor ao contador
            i++;
            
            // Executa o método que da uma pausa entre as criações de clientes
            pausa();
        }
        
    }
    
    // Definição do tempo entre a geração dos clientes
    private static void pausa() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 6000));
        } catch (InterruptedException e) {}
    }
    
}
