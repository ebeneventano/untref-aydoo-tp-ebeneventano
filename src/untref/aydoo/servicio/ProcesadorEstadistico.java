package untref.aydoo.servicio;

import java.nio.file.Path;
import java.util.List;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;

public interface ProcesadorEstadistico {

	List<Bicicleta> obtenerBicicletasUtilizadasMasVeces();
	List<Bicicleta> obtenerBicicletaUtilizadaMenosVeces();
	List<Recorrido> obtenerRecorridoMasRealizado();
	String obtenerTiempoPromedio(Bicicleta bicicleta);
	void comenzarAEscuchar(Path path);
	void llenarMapaDeBicicletasUsadas(String csvFile);

}
