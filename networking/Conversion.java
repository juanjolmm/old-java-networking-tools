public class Conversion
{
	public static int aDecimal(String binario)
	{
		int cantidad=0;
		int var;
		int progreso;
		int multipli=1;
		int largo=binario.length();
		for(int i=0;i<largo;i++)
		{
			var=largo-(i+1);
			if(var==0)
			{
				multipli=1;
			}
			else if(var==1)
			{
				multipli=2;
			}
			else
			{
				for(int e=0;e<var;e++)
				{
					multipli*=2;
				}
			}
			progreso=Integer.parseInt(binario.substring(i,i+1))*multipli;
			cantidad+=progreso;
			multipli=1;
		}
		return cantidad;
	}
	public static String aBinario(int decimal)
	{
		String binario="";
		int resto;
		int result;
		do
		{
			result=decimal/2;
			binario+=String.valueOf(decimal%2);
			decimal=result;
		}
		while(result!=1);
		binario=binario+String.valueOf(result);
		char [] cadena=binario.toCharArray();
		char cadena2[]=new char[cadena.length];
		for(int i=0;i<cadena.length;i++)
		{
			cadena2[i]=cadena[cadena.length-(i+1)];
		}
		String total=new String(cadena2);
		return total;
	}
}