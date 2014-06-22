package untref.aydoo.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipFile;

import junit.framework.Assert;

import org.junit.Test;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Trayectoria;
import untref.aydoo.dtos.DatosBicicleta;
import untref.aydoo.servicio.ProcesadorEstadistico;
import untref.aydoo.servicio.ProcesadorEstadisticoImpl;


public class ProcesadorEstadisticoTest {
	
	private ProcesadorEstadistico procesador = new ProcesadorEstadisticoImpl();
	
	@Test
	public void testBicicletaUtilizadaMasVeces() throws IOException{
		String path = new File("src/docs/prueba2010.zip").getCanonicalPath();
		ZipFile zipFile = new ZipFile(path);
		Map<Bicicleta, DatosBicicleta> bicicletasMasUsadas = procesador.llenarMapaDeBicicletasUsadas(zipFile);

		bicicletasMasUsadas = procesador.obtenerBicicletasUtilizadasMasVeces(bicicletasMasUsadas);
		
		Bicicleta biciMasUsada = new Bicicleta();
		DatosBicicleta datosBiciMasUsada = new DatosBicicleta();
		
		for (Map.Entry<Bicicleta, DatosBicicleta> entry : bicicletasMasUsadas.entrySet())
		{
			biciMasUsada = entry.getKey();
			datosBiciMasUsada = entry.getValue();
		}
		
		Assert.assertEquals("403", biciMasUsada.getId());
		Assert.assertEquals(Integer.valueOf(4), datosBiciMasUsada.getCantidadVecesUsada());
	}
	
	@Test
	public void testBicicletaUtilizadaMenosVeces() throws IOException{
		String path = new File("src/docs/prueba2010.zip").getCanonicalPath();
		ZipFile zipFile = new ZipFile(path);
		Map<Bicicleta, DatosBicicleta> bicicletas = procesador.llenarMapaDeBicicletasUsadas(zipFile);

		bicicletas = procesador.obtenerBicicletaUtilizadaMenosVeces(bicicletas);
		
		Bicicleta biciMenosUsada = new Bicicleta();
		DatosBicicleta datosBiciMenosUsada = new DatosBicicleta();
		
		for (Map.Entry<Bicicleta, DatosBicicleta> entry : bicicletas.entrySet())
		{
			biciMenosUsada = entry.getKey();
			datosBiciMenosUsada = entry.getValue();
		}
		
		Assert.assertEquals("351", biciMenosUsada.getId());
		Assert.assertEquals(Integer.valueOf(1), datosBiciMenosUsada.getCantidadVecesUsada());
	}
	
	@Test
	public void testRecorridoMasRealizado() throws IOException{

		String path = new File("src/docs/prueba2010.zip").getCanonicalPath();
		ZipFile zipFile = new ZipFile(path);
		Map<Bicicleta, DatosBicicleta> bicicletas = procesador.llenarMapaDeBicicletasUsadas(zipFile);

		Map<Trayectoria,Integer> recorridosMasRealizados = procesador.obtenerRecorridoMasRealizado(bicicletas);
		
		Trayectoria trayectoria = null;
		Integer cantidadTrayectoriaMasRealizada = 0;
		for (Map.Entry<Trayectoria, Integer> entry : recorridosMasRealizados.entrySet())
		{
			trayectoria = entry.getKey();
			cantidadTrayectoriaMasRealizada = entry.getValue();
		}
		
		Assert.assertEquals("7", trayectoria.getEstacionOrigen().getId());
		Assert.assertEquals("3", trayectoria.getEstacionDestino().getId());
		Assert.assertEquals(Integer.valueOf(4), cantidadTrayectoriaMasRealizada);
		
	}
	
	@Test
	public void testTiempoPromedioDeUso() throws IOException{
		String path = new File("src/docs/prueba2010.zip").getCanonicalPath();
		ZipFile zipFile = new ZipFile(path);
		Map<Bicicleta, DatosBicicleta> bicicletas = procesador.llenarMapaDeBicicletasUsadas(zipFile);

		Integer promedio = procesador.getPromedioUso(bicicletas);
		Assert.assertEquals(Integer.valueOf(19), promedio);
	}
	
	@Test
	public void testCantidadDeArchivosEnZip() throws IOException{
//		String nombreArchivo = "C:" + File.separator + "Users" + File.separator + "Emanuel" + File.separator + "recorridos.zip";
//		List<File> listaArchivos = procesador.procesarCsvEnZip(nombreArchivo);
//		
//		Assert.assertEquals(2, listaArchivos.size());
	}
}
