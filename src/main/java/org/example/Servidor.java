package org.example;

import org.example.BaseDatos.ConsultasFijas;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int PUERTO = 9069;
    private static final int MAXIMO_CONEXIONES = 3; // Limitar a 3 conexiones simultáneas
    private ServerSocket serverSocket;
    private ExecutorService colaEjecutor;

    public static void main(String[] args) throws IOException {
        //Cosa maligna
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }

    public void iniciar() throws IOException {
        serverSocket = new ServerSocket(PUERTO);
        colaEjecutor = Executors.newFixedThreadPool(MAXIMO_CONEXIONES);

        System.out.println("Servidor iniciado y esperando conexiones...");
        while (true) {
            try {
                // Aceptar una nueva conexión
                Socket socket = serverSocket.accept();
                System.out.println("Se intenta conectar el cliente "+ socket.getInetAddress());
                // Asignar la conexión a un hilo en el pool
                Thread hilo = new Thread(new ManejadorCliente(socket));
                colaEjecutor.submit(hilo);

            } catch (IOException e) {
                System.out.println("Error al aceptar conexión: " + e.getMessage());
            }
        }
    }
    private void sendFullMessage(Socket socket) throws IOException {
        // Enviar un mensaje al cliente indicando que el servidor está lleno
        try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())) {
            salida.writeObject("LLENO");
        }
    }

    public void cerrar() throws IOException {
        serverSocket.close();
        colaEjecutor.shutdown();
    }

    private void consultas(){
        ConsultasFijas con = new ConsultasFijas();

        System.out.println("Promedio de ingredientes.");
        List<Object[]> list = con.PromedioDeIngredientesPorPocion();
        for (Object[] pocion : list) {
            String escuela = ((Pociones.Escuela) pocion[0]).name().toLowerCase();
            Long promedio = (Long) pocion[1];
            System.out.println(escuela + " - " + promedio);
        }
        System.out.println("*-----------------------*\nIngredientes en mas de 3 pociones");

        List<Object[]> list1 = con.ingredientesEnMasDeTresPociones();
        for (Object[] ingrediente : list1){
            String ingred = (ingrediente[0]).toString();
            Long cantidad = (Long) ingrediente[1];
            System.out.println(ingred +" - "+cantidad);
        }

        System.out.println("*-----------------------*\nCantidad total de tipo de ingrediente utilizado");

        List<Object[]> list2 = con.totalTiposIngredientes();
        for (Object[] tipoing : list2){
            String tipo = ((Ingredientes.TiposIngrediente) tipoing[0]).name().toLowerCase();
            Long cantidad = (Long) tipoing[1];
            System.out.println(tipo+" - "+cantidad);
        }


        System.out.println("*-----------------------*\nPociones mas caras por tamaño");

        List<Pociones> list3 = con.pocionesPorTamanio(Pociones.Tamanio.GRANDE);
        for (Pociones pocTam : list3){
            System.out.println(pocTam.getNombrePocion()+" - "+pocTam.getPrecio());
        }

        System.out.println("*-----------------------*\ntop 10 pociones con mayor cantidad de ingredientes");
        List<Object[]> list4 = con.pocionesConMasIngredientes();
        for (Object[] poc : list4){
            String nombre = poc[0].toString();
            Long cantidad = ((Long) poc[1]);
            System.out.println(nombre+" - "+cantidad);
        }
    }

}