package untref.aydoo.servicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Trayectoria;
import untref.aydoo.dtos.DatosBicicleta;

public interface ProcesadorEstadistico {
	
	/**
	 * Pasado un mapa de todas las bicicletas en los distintos csv
	 * devuelvue un mapa con las bicicletas que se utilizaron mas veces.
	 */
	Map<Bicicleta,DatosBicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, DatosBicicleta> bicicletas);
	
	/**
	 * Pasado un mapa de todas las bicicletas en los distintos csv
	 * devuelvue un mapa con las bicicletas que se utilizaron menos veces.
	 */
	Map<Bicicleta,DatosBicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, DatosBicicleta> bicicletas);
	
	/**
	 * Pasado un mapa de todas las bicicletas en los distintos csv
	 * devuelvue un mapa con las trayectorias que se utilizaron mas veces.
	 */
	Map<Trayectoria, Integer> obtenerRecorridoMasRealizado(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv);
	
	/**
	 * Pasado un path comienza a escuchar por nuevos archivos en ese directorio.
	 */
	void comenzarAEscuchar(Path path);
	
	/**
	 * Recorre una sola vez todos los csv involucrados y devuelve un mapa con todas las bicicletas como key
	 * y como value una entidad DatosBicicletas donde se va calculando distinta informacion necesaria para
	 * la exportacion.
	 */
	Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadas(ZipFile zipFile);
	
	/**
	 * Procesa un zip nuevo en un directorio y llama al metodo que llena el mapa de bicicletas para hacer
	 * luego la exportacion correspondiente
	 */
	void procesarCsvEnZip(String nombreArchivo) throws IOException;
	
	/**
	 * Procesa un directorio completo
	 */
	void procesarDirectorio(Path pathProcesing) throws ZipException, IOException;
	
	Integer getPromedioUso(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv);
}
