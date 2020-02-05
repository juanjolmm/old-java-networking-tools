import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Escaner extends JFrame implements ActionListener
{
	private Socket socket;
	private JLabel separaIP1=new JLabel("-");
	private JLabel separaIP2=new JLabel("-");
	private JLabel separaIP3=new JLabel("-");
	private JLabel desde=new JLabel("Puerto desde:");
	private JLabel hasta=new JLabel("Puerto hasta:");
	private JTextField host=new JTextField(20);
	private JTextField pDesde=new JTextField(3);
	private JTextField pHasta=new JTextField(3);
	private JTextField ip1=new JTextField(3);
	private JTextField ip2=new JTextField(3);
	private JTextField ip3=new JTextField(3);
	private JTextField ip4=new JTextField(3);
	private JRadioButton nombre=new JRadioButton("Nombre Host",true);
	private JRadioButton ip=new JRadioButton("IP",false);
	private ButtonGroup grupo=new ButtonGroup();
	private JTextArea area=new JTextArea();
	private JScrollPane scroll=new JScrollPane(area);
	private JPanel panel1=new JPanel(new FlowLayout());
	private JPanel panel2=new JPanel(new BorderLayout());
	private JPanel panel3=new JPanel(new FlowLayout());
	private JPanel panel4=new JPanel(new BorderLayout());
	private JPanel panel5=new JPanel(new FlowLayout());
	private JButton boton=new JButton("ESCANEAR");
	private boolean porHost=true;
	private HiloEscaneador he;
	public Escaner()
	{
		super("PORT SCANER");
		this.setSize(300,400);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
		grupo.add(nombre);
		grupo.add(ip);
		panel1.add(nombre);
		panel1.add(ip);
		ip.addActionListener(this);
		nombre.addActionListener(this);
		panel3.add(host);
		panel5.add(desde);
		panel5.add(pDesde);
		panel5.add(hasta);
		panel5.add(pHasta);
		panel4.add("North",panel1);
		panel4.add("Center",panel3);
		panel4.add("South",panel5);
		this.getContentPane().add("North",panel4);
		panel2.add(scroll);
		this.getContentPane().add(panel2);
		this.getContentPane().add("South",boton);
		area.setEditable(false);
		boton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ev)
	{
		Object cual=ev.getSource();
		if(cual==ip)
		{
			panel3.remove(host);
			panel3.add(ip1);
			panel3.add(separaIP1);
			panel3.add(ip2);
			panel3.add(separaIP2);
			panel3.add(ip3);
			panel3.add(separaIP3);
			panel3.add(ip4);
			porHost=false;
			SwingUtilities.updateComponentTreeUI(this);
		}
		else if(cual==nombre)
		{
			panel3.remove(ip1);
			panel3.remove(ip2);
			panel3.remove(ip3);
			panel3.remove(ip4);
			panel3.remove(separaIP1);
			panel3.remove(separaIP2);
			panel3.remove(separaIP3);
			panel3.add(host);
			porHost=true;
			SwingUtilities.updateComponentTreeUI(this);
		}
		else if(cual==boton)
		{
			area.setText("");
			int portH=Integer.parseInt(pHasta.getText());
			int portD=Integer.parseInt(pDesde.getText());
			String servidor=host.getText();
			if(porHost)
			{
				he=new HiloEscaneador(portD,portH,servidor,this);
				he.start();
			}
			else
			{
				try
				{
					byte dire[]=new byte[4];
					dire[0]=(byte)Integer.parseInt(ip1.getText());
					dire[1]=(byte)Integer.parseInt(ip2.getText());
					dire[2]=(byte)Integer.parseInt(ip3.getText());
					dire[3]=(byte)Integer.parseInt(ip4.getText());
					System.out.println(dire[0]+"."+dire[1]+"."+dire[2]+"."+dire[3]);
					InetAddress dirIP=InetAddress.getByAddress(dire);
					he=new HiloEscaneador(portD,portH,dirIP,this);
					he.start();
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
			}
		}
		
	}
	public void setArea(String s)
	{
		area.setText(area.getText()+s);

	}
	public void setAreaFont()
	{
		area.setFont(new Font("Serif", Font.ITALIC, 16));
	}
	public static void main(String []args)
	{
		Escaner escaner=new Escaner();
		escaner.show();
	}
}
class HiloEscaneador extends Thread
{
	private Socket socket;
	private int portD;
	private int portH;
	private String servidor;
	private Escaner escan;
	private InetAddress inet;
	private boolean porIP=false;
	public HiloEscaneador(int portDesde,int portHasta,String host,Escaner escaner)
	{
		escan=escaner;
		portD=portDesde;
		portH=portHasta;
		servidor=host;
	}
	public HiloEscaneador(int portDesde,int portHasta,InetAddress address,Escaner escaner)
	{
		escan=escaner;
		portD=portDesde;
		portH=portHasta;
		inet=address;
		porIP=true;
	}
	public void run()
	{
		if(!porIP)
		{
			for(int i=portD;i<=portH;i++)
			{
				try
				{
					escan.setArea("* COMPROBANDO PUERTO "+i+"...\n");
					escan.setAreaFont();
					socket=new Socket(servidor,i);
					socket.close();
					escan.setArea("* PUERTO "+i+" ------- ABIERTO\n");
				}
				catch(Exception e)
				{
					escan.setArea("* PUERTO "+i+" ------- CERRADO\n");
				}
			}
		}
		else
		{
			for(int i=portD;i<=portH;i++)
			{
				try
				{
					escan.setArea("* COMPROBANDO PUERTO "+i+"...\n");
					socket=new Socket(inet,i);
					socket.close();
					escan.setArea("* PUERTO "+i+" ------- ABIERTO\n");
				}
				catch(Exception e)
				{
					escan.setArea("* PUERTO "+i+" ------- CERRADO\n");
				}
			}
		}
	}
}