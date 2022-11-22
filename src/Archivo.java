

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

public class Archivo {
/**
 * Lee el un archivo y lo guarda en una variable de tipo String
 * @param fichero Es la direccion donde se encuetra el archivo o su nombre
 * @return Un String que tiene todo el archivo en el
 */
    public  String LeerArchivo(String fichero){
	    
		String informacion ="";
	    try {
	      FileReader fr = new FileReader(fichero);
	      BufferedReader br = new BufferedReader(fr);
	 
	      String linea;
	      while((linea = br.readLine()) != null)
	        informacion+= linea;
	      fr.close();
	    }
	    catch(Exception e) {
	    	System.out.println("El archivo esta vacio o no existe. Verifique su contenido.");
	    	
	    }
	    return informacion;
	  }

	public  boolean Leer(String fichero){
	    
		
	    try {
	      FileReader fr = new FileReader(fichero);
	      BufferedReader br = new BufferedReader(fr);
	 
	      String linea;
	      while((linea = br.readLine()) != null)
	        System.out.println(linea);
	    	 
	 
	      fr.close();
	    }
	    catch(Exception e) {
	    	System.out.println("El archivo esta vacio o no existe. Verifique su contenido.");
	    	return false;
	    }
	    return true;
	  }
	
	
	public  String[] LeerUnaLinea(String fichero){
		  
		
		
		String[] lineaString=null;
	
	    try {
	      FileReader fr = new FileReader(fichero);
	      BufferedReader br = new BufferedReader(fr);
	 
	      String linea;
	      while((linea = br.readLine()) != null)
	      { //System.out.println(linea);
	    	lineaString = linea.split(" ");
	      
	      } 
	 
	      
	      fr.close();
	      
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion leyendo fichero "+ fichero + ": " + e);
	    }
	    return lineaString;
	    
	  }
	public boolean ImprimirEn(String etiquetafichero, String Salida) {
		try{
			FileWriter fichero = new FileWriter(etiquetafichero, true);
			PrintWriter pw = new PrintWriter(fichero);
			pw.print(Salida);

			fichero.close();
		} catch (Exception e) {
			System.out.println("Excepcion leyendo fichero "+ etiquetafichero + ": " + e);
		}
		return false;
	}
	public boolean ImprimirEn(String etiquetafichero, Vector<String> vectorSalida) {
		try{
            FileWriter fichero = new FileWriter(etiquetafichero, true);
            //PrintWriter pw = new PrintWriter(fichero);

            for (int i = 0; i < vectorSalida.size(); i++)
            	//fichero.write(vectorSalida.get(i));

            fichero.close();
		} catch (Exception e) {
        	System.out.println("Excepcion leyendo fichero "+ etiquetafichero + ": " + e);
        }
		return false;
	}
	//Leer todo el texto
	/**
	 * Lee el archivo linea por linea y guarda en un arreglo de String cada linea, luego
	 * guarda ese arreglo de String en un vector 
	 * @param fichero direccion del archivo o el nombre
	 * @return Un vector que tiene arreglos de String en el
	 */
	public  Vector<String[]> LeerVector(String fichero){
		Vector<String[]> vectorTotal = new Vector<String[]>();
		String[] lineaString=null;
	    try {
	      FileReader fr = new FileReader(fichero);
	      BufferedReader br = new BufferedReader(fr);
	      String linea;
	      int c=0;
	      while((linea = br.readLine()) != null)
	      { //System.out.println(linea);
	    	  if(c<2)
	    		  lineaString = linea.split(" ");
	    	  else
	    		  lineaString = linea.split(",");
	    	  vectorTotal.add(lineaString);
	    	  lineaString = null;
	    	  c++;	  
	      } 
	      fr.close();
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion leyendo fichero "+ fichero + ": " + e);
	    }
	    return vectorTotal;
	  }
}
