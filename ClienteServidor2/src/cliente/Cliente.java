package cliente;


/**
 * Trabajo : Chat Grupal
 * Integrantes:
 * 				Julián Andres Osorio Marin - 1094948817
 * 				Mauricio Pareja Urbano - 9730895
 * 				Julián David Tintinago - 1094951704
 * 
 * Docente:	Andres Herrera
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

public class Cliente 
{
	//hacer una interfaz
	public static int PORT = 3400;
	public static String SERVER_LOCATION = "localhost";
	
	private static Socket socketCliente;
	
	private static DataOutputStream salidaDatos;
	private BufferedReader entradaDatos;
	

	static ClienteGUI c; 
	
	public static void main(String[] args) {
		c = new ClienteGUI();
	}
	
	
	public Cliente() 
	{
		
		try {
			socketCliente = new Socket(Cliente.SERVER_LOCATION, Cliente.PORT);
			entradaDatos = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salidaDatos = new DataOutputStream(socketCliente.getOutputStream());
			//se crea un hilo que se va a ejecutar en paralelo
			
			Thread escuchador = new Thread(new Runnable() {
				
				@Override
				public void run() 
				{
					while(true)
					{
						try {
							if(!socketCliente.isClosed() && entradaDatos.ready())
							{
								String mensaje = entradaDatos.readLine();
								
								c.agregarLinea(mensaje+"\n");
								//System.out.println("Mensaje entrante> " + mensaje);
								
							}
						} catch (IOException e) {
							
							e.printStackTrace();
							break;//rompe el hilo
						}
					}
				}
			});
			escuchador.start(); //arrancamos el hilo
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
	}
	
	
	/**
	 * Este metodo le envia los mensajes al servidor
	 * @param mensaje
	 * @throws IOException
	 */
	
	public  void prueba(String mensaje) throws IOException
	{
		
		if(!socketCliente.isClosed() && !mensaje.equals(""))
		{	
			salidaDatos.writeBytes(mensaje+'\n');
		}
	}
	



	

	

	
}
