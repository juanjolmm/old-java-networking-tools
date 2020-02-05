import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;
public class Ip extends JFrame implements ActionListener
{
	private int bitsSubredes;
	private int bitsEstaciones;
	private String IP=null;
	private String tipo=null;
	private JLabel esta=new JLabel("Estaciones  ");
	private JLabel subre=new JLabel("Subredes     ");
	private JTextField estaciones=new JTextField(6);
	private JTextField subredes=new JTextField(6);
	private JPanel panGeneral=new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panResultados=new JPanel();
	private JPanel panInicio=new JPanel(new BorderLayout());
	private JPanel panInicio1=new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panInicio2=new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JButton boton=new JButton("START");
	private JLabel resultadoTipo=new JLabel("  TIPO DE IP : ");
	private JLabel resultadoRed=new JLabel("  BITS SUBREDES : ");
	private JLabel resultadoEstaciones=new JLabel("  BITS ESTACIONES : ");
	private JLabel mascaraRed=new JLabel("  MASCARA DE RED : ");
	private JLabel mascaraSubred=new JLabel("  MASCARA DE SUBRED :                             ");
	private JLabel direccionIP=new JLabel("  DIRECCION RESERVADA :                             ");
	private JList listado=new JList();
	private JScrollPane panList=new JScrollPane(listado);
	public Ip()
	{
		super("Redes de Area Local");
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(0);}});
		boton.addActionListener(this);
		this.setSize(740,400);
		panInicio.setBorder(BorderFactory.createTitledBorder("  Nº de Elementos"));
		panList.setBorder(BorderFactory.createTitledBorder("  Ips de Subredes"));
		panResultados.setBorder(BorderFactory.createTitledBorder("  Resultados"));
		panResultados.setLayout(new BoxLayout(panResultados, BoxLayout.Y_AXIS));
		this.getContentPane().setLayout(new BorderLayout());
		panInicio1.add(esta);
		panInicio1.add(estaciones);
		panInicio2.add(subre);
		panInicio2.add(subredes);
		panInicio.add("North",panInicio1);
		panInicio.add("Center",panInicio2);
		panInicio.add("South",panList);
		panResultados.add(resultadoTipo);
		panResultados.add(resultadoRed);
		panResultados.add(resultadoEstaciones);
		panResultados.add(direccionIP);
		panResultados.add(mascaraRed);
		panResultados.add(mascaraSubred);
		panGeneral.add(panInicio);
		panGeneral.add(panResultados);
		this.getContentPane().add("North",panGeneral);
		this.getContentPane().add("South",boton);
	}
	public void actionPerformed(ActionEvent ev)
	{
		compruebaTipoIp(Integer.parseInt(estaciones.getText()), Integer.parseInt(subredes.getText()));
		int cuantos=bitsSubredes+bitsEstaciones;
		if(cuantos<=8)
		{
			tipo="C";
			resultadoTipo.setText("  TIPO DE IP : "+tipo);
			mascaraRed.setText("  MASCARA DE RED : 255.255.255.0");
			IP="198.168.0.0";
			direccionIP.setText("  DIRECCION RESERVADA : "+IP);
			bitsEstaciones+=(8-cuantos)/2;
			bitsSubredes+=8-bitsEstaciones-bitsSubredes;
			resultadoRed.setText("  BITS SUBREDES : "+String.valueOf(bitsSubredes));
			resultadoEstaciones.setText("  BITS ESTACIONES : "+String.valueOf(bitsEstaciones));
			averiguaSubred(bitsSubredes,"c");
			recogeSubredes(IP,tipo,bitsSubredes,Integer.parseInt(subredes.getText()));
		}
		else if(cuantos>8 && cuantos<=16)
		{
			tipo="B";		
			resultadoTipo.setText("  TIPO DE IP : "+tipo);
			mascaraRed.setText("  MASCARA DE RED : 255.255.0.0");
			IP="172.16.0.0";
			direccionIP.setText("  DIRECCION RESERVADA : "+IP);
			bitsEstaciones+=(16-cuantos)/2;
			bitsSubredes+=16-bitsEstaciones-bitsSubredes;
			resultadoRed.setText("  BITS SUBREDES : "+String.valueOf(bitsSubredes));
			resultadoEstaciones.setText("  BITS ESTACIONES : "+String.valueOf(bitsEstaciones));
			averiguaSubred(bitsSubredes,"b");
			recogeSubredes(IP,tipo,bitsSubredes,Integer.parseInt(subredes.getText()));
		}
		else if(cuantos>16 && cuantos<=24)
		{
			tipo="A";
			resultadoTipo.setText("  TIPO DE IP : "+tipo);
			mascaraRed.setText("  MASCARA DE RED : 255.0.0.0");
			IP="10.0.0.0";
			direccionIP.setText("  DIRECCION RESERVADA : "+IP);
			bitsEstaciones+=(24-cuantos)/2;
			bitsSubredes+=24-bitsEstaciones-bitsSubredes;
			resultadoRed.setText("  BITS SUBREDES : "+String.valueOf(bitsSubredes));
			resultadoEstaciones.setText("  BITS ESTACIONES : "+String.valueOf(bitsEstaciones));
			averiguaSubred(bitsSubredes,"a");
			recogeSubredes(IP,tipo,bitsSubredes,Integer.parseInt(subredes.getText()));
		}
		else
		{
			resultadoTipo.setText("Se necesitan varias IPs");
		}
	}
	public void averiguaSubred(int bitsRed,String tipo)
	{
		if(tipo.equals("a"))
		{
			String subRed="";
			int aCero=24-bitsRed;
			for(int i=0;i<bitsRed;i++)
			{
				subRed+="1";
			}
			for(int i=0;i<aCero;i++)
			{
				subRed+="0";
			}
			String ip1=String.valueOf(Conversion.aDecimal(subRed.substring(0,8)));
			String ip2=String.valueOf(Conversion.aDecimal(subRed.substring(8,16)));
			String ip3=String.valueOf(Conversion.aDecimal(subRed.substring(16,subRed.length())));
			mascaraSubred.setText("  MASCARA DE SUBRED : 255."+ip1+"."+ip2+"."+ip3);
		}
		else if(tipo.equals("b"))
		{
			String subRed="";
			int aCero=16-bitsRed;
			for(int i=0;i<bitsRed;i++)
			{
				subRed+="1";
			}
			for(int i=0;i<aCero;i++)
			{
				subRed+="0";
			}
			String ip1=String.valueOf(Conversion.aDecimal(subRed.substring(0,8)));
			String ip2=String.valueOf(Conversion.aDecimal(subRed.substring(8,subRed.length())));
			mascaraSubred.setText("  MASCARA DE SUBRED : 255.255."+ip1+"."+ip2);
		}
		else if(tipo.equals("c"))
		{
			String subRed="";
			int aCero=8-bitsRed;
			for(int i=0;i<bitsRed;i++)
			{
				subRed+="1";
			}
			for(int i=0;i<aCero;i++)
			{
				subRed+="0";
			}
			String ip1=String.valueOf(Conversion.aDecimal(subRed));
			mascaraSubred.setText("  MASCARA DE SUBRED : 255.255.255"+ip1);
		}
	}
	public void compruebaTipoIp(int estaciones,int subredes)
	{
		int estacionesTotales=1;
		for(bitsEstaciones=1;bitsEstaciones<33;bitsEstaciones++)
		{
			estacionesTotales*=2;
			if(estacionesTotales-2>=estaciones)
			{
				break;
			}
		}
		int subredesTotales=1;
		for(bitsSubredes=1;bitsSubredes<33;bitsSubredes++)
		{
			subredesTotales*=2;
			if(subredesTotales-1>=subredes)
			{
				break;
			}
		}
	}
	public void recogeSubredes(String Ip, String tipoIP, int bitsSub,int subR)
	{
		String []listadoIP=new String[subR+1];
		int bitsTotales;
		if(tipoIP.equals("A"))
		{
			bitsTotales=24;
			for(int i=0;i<=subR;i++)
			{
				if(i==0)
				{
					listadoIP[0]=":::::::::::::::::::::::LISTADO DE SUBREDES::::::::::::::::::::::";
				}
				else
				{
					String resto="";
					if(i==1)
					{
						resto="1";
					}
					else
					{
						resto=Conversion.aBinario(i);
					}
					if(resto.length()<bitsSub)
					{
						String ceros="";
						int cuantosCer=bitsSub-resto.length();
						for(int e=0;e<cuantosCer;e++)
						{
							ceros+="0";
						}
						resto=ceros+resto;
					}
					String ceros2="";
					int cuantosCer2=bitsTotales-resto.length();
					for(int j=0;j<cuantosCer2;j++)
					{
						ceros2+="0";
					}
					resto+=ceros2;
					listadoIP[i]=Ip.substring(0,3)+String.valueOf(Conversion.aDecimal(resto.substring(0,8)))+"."+String.valueOf(Conversion.aDecimal(resto.substring(8,16)))+"."+String.valueOf(Conversion.aDecimal(resto.substring(16,resto.length())));
					listado.setListData(listadoIP);
				}
			}
		}
		else if(tipoIP.equals("B"))
		{
			bitsTotales=16;
			for(int i=0;i<=subR;i++)
			{
				if(i==0)
				{
					listadoIP[0]=":::::::::::::::::::::::LISTADO DE SUBREDES::::::::::::::::::::::";
				}
				else
				{
					String resto="";
					if(i==1)
					{
						resto="1";
					}
					else
					{
						resto=Conversion.aBinario(i);
					}
					if(resto.length()<bitsSub)
					{
						String ceros="";
						int cuantosCer=bitsSub-resto.length();
						for(int e=0;e<cuantosCer;e++)
						{
							ceros+="0";
						}
						resto=ceros+resto;
					}
					String ceros2="";
					int cuantosCer2=bitsTotales-resto.length();
					for(int j=0;j<cuantosCer2;j++)
					{
						ceros2+="0";
					}
					resto+=ceros2;
					listadoIP[i]=Ip.substring(0,7)+String.valueOf(Conversion.aDecimal(resto.substring(0,8)))+"."+String.valueOf(Conversion.aDecimal(resto.substring(8,resto.length())));
					listado.setListData(listadoIP);
				}
			}
		}
		else if(tipoIP.equals("C"))
		{
			bitsTotales=8;
			for(int i=0;i<=subR;i++)
			{
				if(i==0)
				{
					listadoIP[0]=":::::::::::::::::::::::LISTADO DE SUBREDES::::::::::::::::::::::";
				}
				else
				{
					String resto="";
					if(i==1)
					{
						resto="1";
					}
					else
					{
						resto=Conversion.aBinario(i);
					}
					if(resto.length()<bitsSub)
					{
						String ceros="";
						int cuantosCer=bitsSub-resto.length();
						for(int e=0;e<cuantosCer;e++)
						{
							ceros+="0";
						}
						resto=ceros+resto;
					}
					String ceros2="";
					int cuantosCer2=bitsTotales-resto.length();
					for(int j=0;j<cuantosCer2;j++)
					{
						ceros2+="0";
					}
					resto+=ceros2;
					listadoIP[i]=Ip.substring(0,10)+String.valueOf(Conversion.aDecimal(resto.substring(0,resto.length())));
					listado.setListData(listadoIP);
				}
			}
		}
	}
	public static void main(String []args)
	{
		Ip cual=new Ip();
		cual.show();
	}
}