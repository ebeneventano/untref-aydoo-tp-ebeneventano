import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import untref.aydoo.threads.ThreadListener;
import untref.aydoo.threads.ThreadProcessor;


public class ProcesadorMain {
	
	
	public static void main(String[] args) throws IOException {
		
		
		int opcion = -1;
		while(opcion != 0){
			System.out.println("Seleccine una opcion: ");
			System.out.println("1. Escuchar un directorio");
			System.out.println("2. Procesar un directorio");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			opcion = Integer.valueOf(br.readLine());
			if(opcion == 1){
				BufferedReader bufferListener = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Ingrese la ruta del directorio que desea escuchar");
				ThreadListener thread = new ThreadListener(bufferListener.readLine());
				thread.start();
			}else if(opcion == 2){
				BufferedReader bufferListener = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Ingrese la ruta del directorio que desea procesar");
				ThreadProcessor thread = new ThreadProcessor(bufferListener.readLine());
				thread.start();
			}else{
				System.out.println("Opcion incorrecto, vuelva a ingresar otra opcion");
			}
		}
	}
}
