package untref.aydoo.threads;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipException;

import untref.aydoo.servicio.ProcesadorEstadistico;
import untref.aydoo.servicio.ProcesadorEstadisticoImpl;

public class ThreadProcessor extends Thread {

	private String pathAEscuchar;
	
	
	public ThreadProcessor (String path){
		this.setPathAEscuchar(path);
	}
	
	@Override
	public void run() {
		Path pathProcesing = Paths.get(this.pathAEscuchar);
		ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
		try {
			procesador.procesarDirectorio(pathProcesing);
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void setPathAEscuchar(String pathAEscuchar) {
		this.pathAEscuchar = pathAEscuchar;
	}

	public String getPathAEscuchar() {
		return pathAEscuchar;
	}
}
