package untref.aydoo.servicio;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.ho.yaml.Yaml;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Estacion;
import untref.aydoo.dominio.Trayectoria;
import untref.aydoo.dtos.DatosBicicleta;
import untref.aydoo.dtos.ExportYmlDTO;


public class ProcesadorEstadisticoImpl implements ProcesadorEstadistico{

	@Override
	public Map<Bicicleta,DatosBicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, DatosBicicleta> bicicletas) {
		Map<Bicicleta, DatosBicicleta> bicicletasAExportar = new HashMap<Bicicleta, DatosBicicleta>();
        int maxValorEnMapa=(this.obtenerVecesMasUsada(bicicletas));  // Esto devuelve el maximo dentro del mapa.
        for (Entry<Bicicleta, DatosBicicleta> entry : bicicletas.entrySet()) {
            if (entry.getValue().obtenerCantidadVecesUsada()==maxValorEnMapa) {
            	bicicletasAExportar.put(entry.getKey(), entry.getValue());
            }
        }
		return bicicletasAExportar;
	}

	private int obtenerVecesMasUsada(Map<Bicicleta, DatosBicicleta> bicicletas) {
		int cantidadMaxima=0;
		for(DatosBicicleta export : bicicletas.values()){
			if(cantidadMaxima < export.obtenerCantidadVecesUsada()){
				cantidadMaxima = export.obtenerCantidadVecesUsada();
			}
		}
		return cantidadMaxima;
	}

	@Override
	public Map<Bicicleta,DatosBicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, DatosBicicleta> bicicletas) {
		Map<Bicicleta, DatosBicicleta> bicicletasAExportar = new HashMap<Bicicleta, DatosBicicleta>();
        int minValorEnMapa = this.getMenosVecesUsada(bicicletas);  // Esto devuelve el menor valor dentro del mapa
        for (Entry<Bicicleta, DatosBicicleta> entry : bicicletas.entrySet()) {
            if (entry.getValue().obtenerCantidadVecesUsada()==minValorEnMapa) {
            	bicicletasAExportar.put(entry.getKey(),entry.getValue());
            }
        }
		return bicicletasAExportar;
	}

	private int getMenosVecesUsada(Map<Bicicleta, DatosBicicleta> bicicletas) {
		int cantidadMinima= bicicletas.values().iterator().next().obtenerCantidadVecesUsada();
		for(DatosBicicleta export : bicicletas.values()){
			if(cantidadMinima > export.obtenerCantidadVecesUsada()){
				cantidadMinima = export.obtenerCantidadVecesUsada();
			}
		}
		return cantidadMinima;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Trayectoria, Integer> obtenerRecorridoMasRealizado(Map<Bicicleta, 
			DatosBicicleta> bicicletasEnCsv) {
		Map<Trayectoria, Integer> recorridoADevolver = new HashMap<Trayectoria, Integer>();
		Map<Trayectoria, Integer> recorridoMasRealizado = new HashMap<Trayectoria, Integer>();
		for(DatosBicicleta entry : bicicletasEnCsv.values()){
			Map<Trayectoria, Integer> trayectoriasPorBicicleta = entry.obtenerTrayectoriasRealizadas();
			Iterator iterador = trayectoriasPorBicicleta.entrySet().iterator();
		    while (iterador.hasNext()) {
		    	Map.Entry paresClaveValor = (Map.Entry)iterador.next();
		    	if(!recorridoMasRealizado.containsKey(paresClaveValor.getKey())){
		    		recorridoMasRealizado.put((Trayectoria)paresClaveValor.getKey(), (Integer)paresClaveValor.getValue());
		    	}else{
		    		recorridoMasRealizado.put((Trayectoria)paresClaveValor.getKey(), (Integer)recorridoMasRealizado.get(paresClaveValor.getKey())+ (Integer)paresClaveValor.getValue());
		    	}
		    	iterador.remove();
		    }
		}
        
		int maxValorEnMapa=(Collections.max(recorridoMasRealizado.values()));
        for (Entry<Trayectoria, Integer> entry : recorridoMasRealizado.entrySet()) {
            if (entry.getValue()==maxValorEnMapa) {
            	recorridoADevolver.put(entry.getKey(), entry.getValue());
            }
        }
		return recorridoADevolver;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void comenzarAEscuchar(Path path) {
		
		comprobarPath(path);
		
		// Obtenemos el file system del path
		FileSystem fs = path.getFileSystem ();
		
		// Creamos un Watch Service que es el que va a quedarse escuchando
		// por un nuevo archivo en la carpeta (path) indicado.
		try{
			WatchService servicioEscuchador = fs.newWatchService(); 
			
			// Registramos el path en el servicio y esperamos solo por eventos de creacion,
			// es decir, por archivos nuevos que se alojen aqui.
			path.register(servicioEscuchador, ENTRY_CREATE);
			
			// Empezamos a escuchar con un loop infinito
			WatchKey key = null;
			while(true) {
				key = servicioEscuchador.take();
				
				Kind<?> objetoEscuchado = null;
				for(WatchEvent<?> eventoEscuchador : key.pollEvents()) {
					objetoEscuchado = eventoEscuchador.kind();
					if (OVERFLOW == objetoEscuchado) {
						continue; //loop
					} else if (ENTRY_CREATE == objetoEscuchado) {
						
						// Este path corresponde al nombre del archivo que se creo 
						Path nombreArchivo = ((WatchEvent<Path>) eventoEscuchador).context();
						
						// Suponemos que el archivo que se va a alojar es un ZIP pero lo valido.
						String pathArchivoZip = path.toString() + File.separator + nombreArchivo;
						if(nombreArchivo.toString().toLowerCase().endsWith(".zip")){
							this.procesarCsvEnZip(pathArchivoZip);
						}
					}
				}
				if(!key.reset()) {
					break;
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	// Comprueba que el path que se le pasa sea una carpeta valida
	private void comprobarPath(Path path) {
		try{
			Boolean esCarpetaValida = (Boolean) Files.getAttribute(path,
					"basic:isDirectory", NOFOLLOW_LINKS);
			if (!esCarpetaValida) {
				throw new IllegalArgumentException("El Path: " 
						+ path + " no es una carpeta");
			}
		} catch (IOException ioe) {
			// La carpeta no existe.
			ioe.printStackTrace();
		}
	}
	
	public Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadas(ZipFile zipFile){
		Map<Bicicleta, DatosBicicleta> bicicletas = new HashMap<Bicicleta, DatosBicicleta>();
		BufferedReader br = null;
		String lineaLeida = "";
		String cvsSpliteadoPor = ";";
	    
		Enumeration<? extends ZipEntry> archivosEnZip = zipFile.entries();
		
		while(archivosEnZip.hasMoreElements()){
			try {
				int contadorDeLineas=1;
				ZipEntry entry = archivosEnZip.nextElement();
			    InputStream stream = zipFile.getInputStream(entry);
			    InputStreamReader isr = new InputStreamReader(stream);
			    br = new BufferedReader(isr);
			    boolean esPrimeraLectura = true;
			    while ((lineaLeida = br.readLine()) != null) {
			    	if(esPrimeraLectura){
			    		lineaLeida = br.readLine();
			    		esPrimeraLectura = false;
			    	}
			    	contadorDeLineas++;
					String[] recorrido = lineaLeida.split(cvsSpliteadoPor);
					Bicicleta bicicleta = new Bicicleta();
					bicicleta.setId(recorrido[1]);
					DatosBicicleta datosBicicleta;
					if(recorrido.length == 9){
						if(!bicicletas.containsKey(bicicleta)){
							datosBicicleta = new DatosBicicleta();
							datosBicicleta.setearCantidadVecesUsada(1);
							datosBicicleta.setearTiempoDeUso(Integer.parseInt(recorrido[8]));
							
							datosBicicleta = agregarTrayectoriaAlExportDeDatosBicicleta(
									recorrido, datosBicicleta);
							
							bicicletas.put(bicicleta, datosBicicleta);
						}else{
							datosBicicleta = bicicletas.get(bicicleta);
							datosBicicleta.setearCantidadVecesUsada(datosBicicleta.obtenerCantidadVecesUsada()+1);
							datosBicicleta.setearTiempoDeUso(datosBicicleta.obtenerTiempoDeUso()+Integer.parseInt(recorrido[8]));
							
							datosBicicleta = agregarTrayectoriaAlExportDeDatosBicicleta(
									recorrido, datosBicicleta);
							
							
							bicicletas.put(bicicleta, datosBicicleta);
						}
					}else{
						System.out.println("La fila " + Integer.toString(contadorDeLineas) + 
								" del archivo " + entry.getName() + " posee un error y fue descartada para el analisis");
					}
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bicicletas;
	}

	private DatosBicicleta agregarTrayectoriaAlExportDeDatosBicicleta(String[] recorrido,
			DatosBicicleta datosBicicleta) {
		
		Trayectoria trayectoria = new Trayectoria();
		Estacion estacionOrigen = new Estacion();
		estacionOrigen.setId(recorrido[3]);
		estacionOrigen.setNombre(recorrido[4]);
		
		Estacion estacionDestino = new Estacion();
		estacionDestino.setId(recorrido[6]);
		estacionDestino.setNombre(recorrido[7]);
		
		trayectoria.setEstacionOrigen(estacionOrigen);
		trayectoria.setEstacionDestino(estacionDestino);
		
		if(!datosBicicleta.obtenerTrayectoriasRealizadas().containsKey(trayectoria)){
			datosBicicleta.obtenerTrayectoriasRealizadas().put(trayectoria, 1);
		}else{
			Map<Trayectoria,Integer> trayectorias = datosBicicleta.obtenerTrayectoriasRealizadas();
			trayectorias.put(trayectoria, trayectorias.get(trayectoria)+1);
		}
		return datosBicicleta;
	}

	@Override
	public void procesarCsvEnZip(String nombreArchivo) throws IOException {
	    ZipFile archivoZip = new ZipFile(nombreArchivo);
	    Map<Bicicleta, DatosBicicleta> bicicletasEnCsv = this.llenarMapaDeBicicletasUsadas(archivoZip);
	    ExportYmlDTO exportable = this.exportarYML(bicicletasEnCsv);
	    
		Yaml.dump(exportable, new File(nombreArchivo+".yml"));
	}

	@Override
	public void procesarDirectorio(Path pathAProcesar) throws ZipException, IOException {
		comprobarPath(pathAProcesar);
		File dir = new File(pathAProcesar.toString());
		File[] listaArchivosEnDirectorio = dir.listFiles();
		
		Map<Bicicleta,DatosBicicleta> datosEnDirectorio = 
			this.llenarMapaDeBicicletasUsadasEnDirectorio(listaArchivosEnDirectorio);
		ExportYmlDTO exportable = this.exportarYML(datosEnDirectorio);
		
		Yaml.dump(exportable, new File(pathAProcesar.toString() + File.separatorChar + "salida.yml"));
		System.out.println("El archivo se exporto correctamente");
	}

	public Integer obtenerPromedioUso(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv) {
		Integer cantidadTotalDeUso = 0;
		Integer cantidadDeFilasLeidas = 0;
		Collection<DatosBicicleta> collecionDeExports = bicicletasEnCsv.values();
		for(DatosBicicleta unExport : collecionDeExports){
			cantidadTotalDeUso += unExport.obtenerTiempoDeUso();
			cantidadDeFilasLeidas += unExport.obtenerCantidadVecesUsada();
		}
		Integer promedio = cantidadTotalDeUso / cantidadDeFilasLeidas;
		return promedio;
	}

	
	private Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadasEnDirectorio(File[] files) throws ZipException, IOException{
		Map<Bicicleta, DatosBicicleta> bicicletas = new HashMap<Bicicleta, DatosBicicleta>();
		BufferedReader br = null;
		String lineaLeida = "";
		String cvsSpliteadoPor = ";";
	    for(File unArchivo : files){
	    	ZipFile zipFile = new ZipFile(unArchivo);
	    	Enumeration<? extends ZipEntry> archivosEnZip = zipFile.entries();
		
			while(archivosEnZip.hasMoreElements()){
				try {
					ZipEntry archivoZip = archivosEnZip.nextElement();
				    InputStream stream = zipFile.getInputStream(archivoZip);
				    InputStreamReader isr = new InputStreamReader(stream);
				    br = new BufferedReader(isr);
					int contadorDeLineas=1;
				    boolean esPrimeraLeida = true;
				    while ((lineaLeida = br.readLine()) != null) {
						// Spliteamos por punto y coma.
				    	if(esPrimeraLeida){
				    		lineaLeida = br.readLine();
				    		esPrimeraLeida = false;
				    	}
				    	contadorDeLineas++;
						String[] recorrido = lineaLeida.split(cvsSpliteadoPor);
						Bicicleta bicicleta = new Bicicleta();
						bicicleta.setId(recorrido[1]);
						DatosBicicleta datosBicicleta;
						if(recorrido.length == 9){
							if(!bicicletas.containsKey(bicicleta)){
								datosBicicleta = new DatosBicicleta();
								datosBicicleta.setearCantidadVecesUsada(1);
								datosBicicleta.setearTiempoDeUso(Integer.parseInt(recorrido[8]));
								
								datosBicicleta = agregarTrayectoriaAlExportDeDatosBicicleta(
										recorrido, datosBicicleta);
								
								bicicletas.put(bicicleta, datosBicicleta);
							}else{
								datosBicicleta = bicicletas.get(bicicleta);
								datosBicicleta.setearCantidadVecesUsada(datosBicicleta.obtenerCantidadVecesUsada()+1);
								datosBicicleta.setearTiempoDeUso(datosBicicleta.obtenerTiempoDeUso()+Integer.parseInt(recorrido[8]));
								
								datosBicicleta = agregarTrayectoriaAlExportDeDatosBicicleta(
										recorrido, datosBicicleta);
								
								bicicletas.put(bicicleta, datosBicicleta);
							}
						}else{
							System.out.println("La fila " + Integer.toString(contadorDeLineas) + 
									" del archivo " + archivoZip.getName() + " posee un error y fue descartada para el analisis");
						}
				    }
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
	    }
		return bicicletas;
	}

	private ExportYmlDTO exportarYML(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv) throws FileNotFoundException {
		ExportYmlDTO exportYml = new ExportYmlDTO();
		Map<Bicicleta,DatosBicicleta> bicicletasMasUsadas = this.obtenerBicicletasUtilizadasMasVeces(bicicletasEnCsv);
		for (Map.Entry<Bicicleta, DatosBicicleta> entry : bicicletasMasUsadas.entrySet())
		{
			exportYml.addBicicletaMasUsada(entry.getKey());
		}
		
		Map<Bicicleta,DatosBicicleta> bicicletasMenosUsadas = this.obtenerBicicletaUtilizadaMenosVeces(bicicletasEnCsv);
		for (Map.Entry<Bicicleta, DatosBicicleta> entry : bicicletasMenosUsadas.entrySet())
		{
			exportYml.addBicicletaMenosUsada(entry.getKey());
		}
		Integer promedioUso = this.obtenerPromedioUso(bicicletasEnCsv);
		exportYml.setPromedioUso(promedioUso);
		
		Map<Trayectoria, Integer> recorridoMasRealizado = this.obtenerRecorridoMasRealizado(bicicletasEnCsv);
		for(Map.Entry<Trayectoria, Integer> entry : recorridoMasRealizado.entrySet()){
			exportYml.addTrayectoriaMasRealizada(entry.getKey());
		}
		
		return exportYml;
	}
}
