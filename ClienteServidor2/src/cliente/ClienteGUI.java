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
import java.io.IOException;

import javax.swing.*;

public class ClienteGUI  extends JFrame implements ActionListener
{
	private JTextArea jta;
	private JScrollPane jsp;
	private JButton jbtn;
	private JTextField jtf;
	private JLabel jlab;
	private Cliente cliente;
	private String mensaje="";
	private JLabel jlnombre;
	private JTextField jtNombre;
	private JButton btnEntrar;
	
	
	//constructor
	public ClienteGUI() 
	{
		//se inicializan componentes que se agregaran en la interfaz
		jlnombre = new JLabel("Ingrese su nombre");
		jlnombre.setBounds(90, 10, 200, 30);
		
		jtNombre = new  JTextField();
		jtNombre.setBounds(50, 40, 200, 25);
		
		jta = new JTextArea(20,20);
	
		jbtn = new JButton("Enviar");
		
		jtf = new JTextField(20);
		jlab = new JLabel("Ingrese texto y luego oprima cambiar");
		
		jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(90, 70, 100, 25);
			
		jbtn.addActionListener(this);
		jtf.addActionListener(this);
		jtNombre.addActionListener(this);
		jta.setEditable(false);
		
		btnEntrar.addActionListener(this);
		
		add(jlnombre);
		add(jtNombre);
		add(btnEntrar);
		
		
		setTitle("Cliente");
		setSize(300, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String nombre = jtNombre.getText();
		
		/**
		 * este condicional crea el cliente cuando el usuario
		 * ingrese su nombre.
		 */
		if((btnEntrar == e.getSource() || jtNombre == e.getSource())&& !nombre.equals("") )
		{
			cliente = new Cliente();
			jlnombre.setVisible(false);
			jtNombre.setVisible(false);
			btnEntrar.setVisible(false);
			
			setSize(300,430);
			setLayout(new FlowLayout());
			
			
			add(jsp);
			add(jtf);
			add(jbtn);
			setTitle(nombre);
		}
		
		/**
		 * Este condicional solo funciona cuando un cliente envia un mensaje
		 */
		else if(jbtn == e.getSource() || jtf == e.getSource())
		{
			mensaje = jtf.getText();
			
			if(!mensaje.equals(""))
			{
				try {
					cliente.prueba(nombre + "-->" +mensaje);
					jtf.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Por favor llene los campos");
			}

		}
		else
		{
			JOptionPane.showMessageDialog(null, "Ingrese el nombre de usuario");
		}
	}
	
	
	/**
	 * Este metodo lo que hace es agrega los mensaje al textArea de los diferente clientes 
	 * conectados, incluyendo al mismo
	 * @param linea
	 */
	public void agregarLinea(String linea)
	{
		jta.append(linea);
	}
	
	
}
