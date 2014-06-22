package untref.aydoo.servicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Trayectoria;
import untref.aydoo.dtos.DatosBicicleta;

public interface ProcesadorEstadistico {

	Map<Bicicleta,DatosBicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, DatosBicicleta> bicicletas);
	Map<Bicicleta,DatosBicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, DatosBicicleta> bicicletas);
	Map<Trayectoria, Integer> obtenerRecorridoMasRealizado(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv);
	void comenzarAEscuchar(Path path);
	Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadas(ZipFile zipFile);
	void procesarCsvEnZip(String nombreArchivo) throws IOException;
	void procesarCsvEnZip(List<String> zips) throws IOException;
	void procesarDirectorio(Path pathProcesing) throws ZipException, IOException;
}
