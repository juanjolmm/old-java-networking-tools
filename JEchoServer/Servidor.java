import java.net.*;
import java.io.*;
public class Servidor
{
	public static void main(String []args) throws Exception
	{
		System.out.println("Servidor de lectura y escritura. PORT 335");
		ServerSocket server=new ServerSocket(335);
		System.out.println("Esperando conexion...");
		Socket port=server.accept();
		HiloLector hl=new HiloLector(port);
		HiloEscritor he=new HiloEscritor(port);
		hl.start();
		he.start();
	}
}
class HiloLector extends Thread
{
	public Socket socket;
	public InputStream is;
	public int vuelta;
	public HiloLector(Socket s)throws Exception
	{
		socket=s;
		is=socket.getInputStream();
		vuelta=0;
	}
	public void run()
	{	
		try
		{
			while(vuelta!=-1)
			{
				vuelta=is.read();
				System.out.print((char)vuelta);
			}
			System.out.println();
		}
		catch(Exception ex)
		{	
			System.out.println(ex);
		}
	}
}
class HiloEscritor extends Thread 
{
	public Socket socket;
	public PrintStream ps;
	public int vuelta;
	public BufferedReader teclado;
	public String texto;
	public HiloEscritor(Socket s)throws Exception
	{
		socket=s;
		teclado=new BufferedReader(new InputStreamReader(System.in));
		ps=new PrintStream(socket.getOutputStream());
	}
	public void run()
	{
		try
		{
			texto=" ";
			while(!texto.equalsIgnoreCase("fin"))
			{
				texto=teclado.readLine();
				ps.println(texto);
			}
		}
		catch(Exception ex)
		{	
			System.out.println(ex);
		}
	}
}