package untref.aydoo.servicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;
import untref.aydoo.dtos.ExportYmlDTO;

public interface ProcesadorEstadistico {

	Map<Bicicleta,ExportYmlDTO> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, ExportYmlDTO> bicicletas);
	Map<Bicicleta,ExportYmlDTO> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, ExportYmlDTO> bicicletas);
	List<Recorrido> obtenerRecorridoMasRealizado();
	String obtenerTiempoPromedio(Bicicleta bicicleta);
	void comenzarAEscuchar(Path path);
	Map<Bicicleta,ExportYmlDTO> llenarMapaDeBicicletasUsadas(ZipFile zipFile);
	void procesarCsvEnZip(String nombreArchivo) throws IOException;
	void procesarCsvEnZip(List<String> zips) throws IOException;
}
