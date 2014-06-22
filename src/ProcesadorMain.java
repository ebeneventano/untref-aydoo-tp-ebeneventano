import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import untref.aydoo.servicio.ProcesadorEstadistico;
import untref.aydoo.servicio.ProcesadorEstadisticoImpl;


public class ProcesadorMain {
	
	
	public static void main(String[] args) throws IOException {
		
		
		
//		Thread t1 = new Thread(new Runnable() {
//			public void run(){
//				System.out.println("Indique la carpeta que desea escuchar por nuevos archivos");
//				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//				String pathAEscuchar;
//				try {
//					pathAEscuchar = br.readLine();
//					Path pathListening = Paths.get(pathAEscuchar);
//					ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
//					procesador.comenzarAEscuchar(pathListening);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});  
//		t1.start();
		
		
//		Thread t2 = new Thread(new Runnable() {
//		     public void run(){
//		    	 System.out.println("Indique la carpeta que desea procesar todos los archivos");
//		    	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		    	 br = new BufferedReader(new InputStreamReader(System.in));
//		    	 String pathAProcesar;
//				try {
//					pathAProcesar = br.readLine();
//					Path pathProcesing = Paths.get(pathAProcesar);
//					ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
//					procesador.procesarDirectorio(pathProcesing);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		     }
//		 });  
//		t2.start();
		
	}

}
