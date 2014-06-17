package untref.aydoo.servicio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;

public interface ProcesadorEstadistico {

	List<Bicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, Integer> bicicletas);
	List<Bicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, Integer> bicicletas);
	List<Recorrido> obtenerRecorridoMasRealizado();
	String obtenerTiempoPromedio(Bicicleta bicicleta);
	void comenzarAEscuchar(Path path);
	Map<Bicicleta,Integer> llenarMapaDeBicicletasUsadas(InputStreamReader csvFile);
	List<File> procesarCsvEnZip(String nombreArchivo) throws IOException;
	List<File> procesarCsvEnZip(List<String> zips) throws IOException;
}
