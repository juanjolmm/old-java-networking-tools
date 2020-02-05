import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Inventa extends JFrame implements ActionListener
{
	private JLabel dirIP=new JLabel("DIRECCION DE PARTIDA");
	private JLabel port=new JLabel("PUERTO");
	private JTextField puerto=new JTextField(4);
	private JTextField ip1=new JTextField(3);
	private JTextField ip2=new JTextField(3);
	private JTextField ip3=new JTextField(3);
	private JTextField ip4=new JTextField(3);
	private JPanel panelIP=new JPanel(new FlowLayout());
	private JPanel panelPORT=new JPanel(new FlowLayout());
	private JPanel panel=new JPanel();
	private JButton boton=new JButton("BUSCAR");
	private boolean parar;
	private NuevoHilo hilo;
	public Inventa()
	{
		super("Comprueba Puerto...");
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(0);}});
		parar=false;
		panelIP.add(dirIP);
		panelIP.add(ip1);
		panelIP.add(ip2);
		panelIP.add(ip3);
		panelIP.add(ip4);
		panelPORT.add(port);
		panelPORT.add(puerto);
		boton.addActionListener(this);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(panelIP);
		panel.add(panelPORT);
		this.getContentPane().add(panel);
		this.getContentPane().add("South",boton);
		this.pack();
		this.setResizable(false);
	}
	public void actionPerformed(ActionEvent ev)
	{
		if(!parar)
		{
			boton.setText("PARAR");
			int puertoC1=Integer.parseInt(puerto.getText());
			byte [] ips1=new byte[4];
			ips1[0]=(byte)Integer.parseInt(ip1.getText());
			ips1[1]=(byte)Integer.parseInt(ip2.getText());
			ips1[2]=(byte)Integer.parseInt(ip3.getText());
			ips1[3]=(byte)Integer.parseInt(ip4.getText());
			int a1=Integer.parseInt(ip1.getText());
			int b1=Integer.parseInt(ip2.getText());
			int c1=Integer.parseInt(ip3.getText());
			int d1=Integer.parseInt(ip4.getText());
			Muestra vent=new Muestra("Dir IP: "+a1+"."+b1+"."+c1+"."+d1+" en el puerto "+puertoC1);
			vent.show();
			hilo=new NuevoHilo(ips1, a1, b1, c1, d1, puertoC1, vent);
			hilo.start();
			hilo.setSigue(true);
			parar=true;
		}
		else if(parar)
		{
			boton.setText("BUSCAR");
			hilo.setSigue(false);
			parar=false;
		}
		
	}
	public static void main(String[]args)
	{
		Inventa ventana=new Inventa();
		ventana.show();
	}
}

class NuevoHilo extends Thread
{
	private byte [] ips;
	private int a;
	private int b;
	private int c;
	private int d;
	private int puertoC;
	private boolean sigue;
	private Muestra vent1;
	public NuevoHilo(byte [] ips2, int a2, int b2, int c2, int d2, int puertoC2, Muestra vent)
	{
		ips=ips2;
		a=a2;
		b=b2;
		c=c2;
		d=d2;
		puertoC=puertoC2;
		vent1=vent;
		sigue=true;
	}
	public void run()
	{
		while(sigue)
		{
			if(d<256)
			{
				try
				{
					InetAddress direccion=InetAddress.getByAddress(ips);
					Socket socket=new Socket(direccion,puertoC);
					socket.close();
					vent1.tomaTexto("Se ha producido la conexion en "+a+"."+b+"."+c+"."+d+" en el puerto "+puertoC+"\n");
				}
				catch(Exception ex)
				{
					vent1.tomaTexto("No se ha producido la conexion en "+a+"."+b+"."+c+"."+d+" en el puerto "+puertoC+"\n");
				}
				ips[3]++;
				d++;
			}
			else if(d==256)
			{
				if(c<256)
				{
					c++;
					d=0;
				}
				else
				{
					if(b<256)
					{
						b++;
						c=0;
						d=0;
					}
					else
					{
						if(a<256)
						{
							a++;
							b=0;
							c=0;
							d=0;
						}
					}
				}
			}
			else
			{
				break;
			}
		}
	}
	public void setSigue(boolean valor)
	{
		sigue=valor;
	}
}


class Muestra extends JFrame
{
	private JTextArea texto=new JTextArea();
	private JScrollPane panel=new JScrollPane(texto);
	public Muestra(String dir)
	{
		super(dir);
		texto.setEditable(false);
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){dispose();}});
		this.getContentPane().add(panel);
		this.setSize(450,450);
	}
	public void tomaTexto(String textoT)
	{
		texto.append(textoT);
	}
}