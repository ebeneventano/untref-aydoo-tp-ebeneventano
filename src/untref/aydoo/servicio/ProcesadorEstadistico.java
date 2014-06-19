package untref.aydoo.servicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;

public interface ProcesadorEstadistico {

	List<Bicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, Integer> bicicletas);
	List<Bicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, Integer> bicicletas);
	List<Recorrido> obtenerRecorridoMasRealizado();
	String obtenerTiempoPromedio(Bicicleta bicicleta);
	void comenzarAEscuchar(Path path);
	Map<Bicicleta,Integer> llenarMapaDeBicicletasUsadas(ZipFile zipFile);
	void procesarCsvEnZip(String nombreArchivo) throws IOException;
	void procesarCsvEnZip(List<String> zips) throws IOException;
}
