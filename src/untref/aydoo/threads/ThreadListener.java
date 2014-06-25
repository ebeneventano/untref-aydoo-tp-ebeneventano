package untref.aydoo.threads;

import java.nio.file.Path;
import java.nio.file.Paths;

import untref.aydoo.servicio.ProcesadorEstadistico;
import untref.aydoo.servicio.ProcesadorEstadisticoImpl;

public class ThreadListener extends Thread{

	private String pathAEscuchar;
	
	
	public ThreadListener (String path){
		this.setPathAEscuchar(path);
	}
	
	@Override
	public void run() {
		Path pathListening = Paths.get(this.pathAEscuchar);
		ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
		procesador.comenzarAEscuchar(pathListening);		
	}

	public void setPathAEscuchar(String pathAEscuchar) {
		this.pathAEscuchar = pathAEscuchar;
	}

	public String getPathAEscuchar() {
		return pathAEscuchar;
	}
}
