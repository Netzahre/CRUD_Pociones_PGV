package org.example;

import org.example.BaseDatos.ConsultasFijas;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static final int PUERTO = 69;

    public Main() throws IOException{}

    public static void main(String[] args) {
//        try{
//            ServerSocket socketServidor = new ServerSocket(PUERTO);
//
//        } catch (IOException var3){
//            IOException ioe = var3;
//            ioe.printStackTrace();
//        }
        ConsultasFijas con = new ConsultasFijas();

        List test = con.PromedioDeIngredientesPorPocion();
//        for (Pociones pocion : con.PromedioDeIngredientesPorPocion()) {
//            String escuela = pocion.getNombrePocion();  // Nombre de la escuela
//
//            System.out.println("Escuela: " + escuela);
//
//        }

        for (Object linea : test) {
            for (int i = 0; i < test.size(); i++) {
                System.out.println(linea[i]);
            }
        }

    }




}