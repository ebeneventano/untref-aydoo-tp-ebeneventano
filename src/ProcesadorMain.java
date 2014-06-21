import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import untref.aydoo.servicio.ProcesadorEstadistico;
import untref.aydoo.servicio.ProcesadorEstadisticoImpl;


public class ProcesadorMain {
	
	
	public static void main(String[] args) throws IOException {
		ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
		
		System.out.println("Indique la carpeta que desea escuchar por nuevos archivos");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathAEscuchar = br.readLine();
		Path pathListening = Paths.get(pathAEscuchar);
		
		System.out.println("Indique la carpeta que desea procesar todos los archivos");
		br = new BufferedReader(new InputStreamReader(System.in));
		String pathAProcesar = br.readLine();
		Path pathProcesing = Paths.get(pathAProcesar);

//		procesador.comenzarAEscuchar(pathListening);
//		procesador.procesarDirectorio(pathProcesing);
		
	}

}
