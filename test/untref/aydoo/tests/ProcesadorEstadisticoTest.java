package untref.aydoo.tests;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;
import untref.aydoo.servicio.ProcesadorEstadistico;
import untref.aydoo.servicio.ProcesadorEstadisticoImpl;


public class ProcesadorEstadisticoTest {
	
	private ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
	
	@Test
	public void comenzarAEscucharNuevoCSVFileEnDirectorio(){
//		Path folder = Paths.get(System.getProperty("user.home"));
//		procesador.comenzarAEscuchar(folder);
	}
	
	@Test
	public void testBicicletaUtilizadaMasVeces(){
//		String nombreArchivo = "C:" + File.separator + "Users" + File.separator + "Emanuel" + File.separator + "recorrido.csv";
//		procesador.llenarMapaDeBicicletasUsadas(nombreArchivo);
		List<Bicicleta> bicicletasMasUsadas = procesador.obtenerBicicletasUtilizadasMasVeces();
//
//		Assert.assertEquals("419", bicicletasMasUsadas.get(0).getId());
//		Assert.assertEquals("434", bicicletasMasUsadas.get(1).getId());

	}
	
	@Test
	public void testBicicletaUtilizadaMenosVeces(){
		String nombreArchivo = "C:" + File.separator + "Users" + File.separator + "Emanuel" + File.separator + "recorrido.csv";
		procesador.llenarMapaDeBicicletasUsadas(nombreArchivo);
		List<Bicicleta> bicicletasMenosUsadas = procesador.obtenerBicicletaUtilizadaMenosVeces();
		
		Assert.assertEquals("452", bicicletasMenosUsadas.get(0).getId());
		Assert.assertEquals("464", bicicletasMenosUsadas.get(1).getId());
	}
	
	@Test
	public void testRecorridoMasRealizado(){

		List<Recorrido> recorridosMasRealizados = procesador.obtenerRecorridoMasRealizado();
		
//		String origenId = recorridosMasRealizados.get(0).getTrayectoria().getEstacionOrigen().getId();
//		String destinoId = recorridosMasRealizados.get(0).getTrayectoria().getEstacionDestino().getId();
//
//		Assert.assertEquals(5, origenId);
//		Assert.assertEquals(3, destinoId);
		
	}
	
	@Test
	public void testTiempoPromedioDeUso(){
	}
	
	@Test
	public void testCantidadDeArchivosEnZip(){
		String nombreArchivo = "C:" + File.separator + "Users" + File.separator + "Emanuel" + File.separator + "recorridos.zip";
		List<Object> listaArchivos = procesador.procesarCsvEnZip(nombreArchivo);
		
		Assert.assertEquals(2, listaArchivos.size());
	}
}
