package br.cesjf.barbeirodorminhoco;

import java.util.concurrent.Semaphore;

public class BarbeiroDorminhoco {
    
    // Quantidade de cadeiras
    public static final int quantidadeCadeiras = 3;
    // Mutex para o próximo cliente que vai cortar o cabelo
    public static final Semaphore proximoCliente = new Semaphore(0);
    // Quantidade de clientes aguardando
    public static int aguardando = 0;
    

    public static void main(String[] args) {
        
        // Instanciação do barbeiro
        Barbeiro barbeiro = new Barbeiro(1);
        // Inicialização da Thread do barbeiro
        barbeiro.start();
        
        // Contador para o id de cliente
        int i = 0;
        
        // Loop para criação dos clientes
        while(true) {
            
            // Adiciona valor ao contador de id de cliente
            i++;
            
            // Instanciação do cliente
            Cliente cliente = new Cliente(i);
            // Inicialização da Thread do cliente
            cliente.start();
            
            // Executa o método que da uma pausa entre a criação de clientes
            pausa();
        }
        
    }
    
    // Definição do tempo de pausa entre a criação de clientes
    private static void pausa() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
}
