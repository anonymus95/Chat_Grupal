package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import cliente.Cliente;

public class ManejadorComunicacion extends Thread {

    private Socket miCliente;
    private Servidor miServer;
    private DataOutputStream salidaACliente;
    private BufferedReader entradaDeCliente;
    Cliente c;
    public ManejadorComunicacion(Socket cliente, Servidor server) {
        this.miCliente = cliente;
        this.miServer = server;
        System.out.println(cliente.getLocalAddress().toString());
        try {
            entradaDeCliente = new BufferedReader(new InputStreamReader(miCliente.getInputStream()));
            salidaACliente = new DataOutputStream(miCliente.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        boolean activo = true;
        while (activo) {
            if (miCliente.isClosed()) {
                activo = false;
                continue;
            } else {
                try {
                    String mensaje = entradaDeCliente.readLine();
                    if (mensaje != null) {
                        System.out.println( mensaje);
                        miServer.enviarMensajeATodos(mensaje);
                    } else {

                    }
                } catch (IOException e) {
                    try {
                        miCliente.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
   
    public Socket getMiCliente() {
        return miCliente;
    }

    public void setMiCliente(Socket miCliente) {
        this.miCliente = miCliente;
    }

    public Servidor getMiServer() {
        return miServer;
    }

    public void setMiServer(Servidor miServer) {
        this.miServer = miServer;
    }

    public boolean enviarMensaje(String mensaje) {
        if (!miCliente.isClosed()) {
            try {
                salidaACliente.writeBytes( mensaje + "\n");
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}