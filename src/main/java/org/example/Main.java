package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static final int PUERTO = 69;

    public Main() throws IOException{}

    public static void main(String[] args) {
        try{
            ServerSocket socketServidor = new ServerSocket(PUERTO);

        } catch (IOException var3){
            IOException ioe = var3;
            ioe.printStackTrace();
        }
    }
}