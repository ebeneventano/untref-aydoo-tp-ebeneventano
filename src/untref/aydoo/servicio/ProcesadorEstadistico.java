package untref.aydoo.servicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;
import untref.aydoo.dtos.DatosBicicleta;
import untref.aydoo.dtos.ExportYmlDTO;

public interface ProcesadorEstadistico {

	Map<Bicicleta,DatosBicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, DatosBicicleta> bicicletas);
	Map<Bicicleta,DatosBicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, DatosBicicleta> bicicletas);
	List<Recorrido> obtenerRecorridoMasRealizado();
	String obtenerTiempoPromedio(Bicicleta bicicleta);
	void comenzarAEscuchar(Path path);
	Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadas(ZipFile zipFile);
	void procesarCsvEnZip(String nombreArchivo) throws IOException;
	void procesarCsvEnZip(List<String> zips) throws IOException;
	void procesarDirectorio(Path pathProcesing);
}
