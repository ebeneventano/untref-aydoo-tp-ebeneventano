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
        int maxValueInMap=(this.getVecesMasUsada(bicicletas));  // This will return max value in the Hashmap
        for (Entry<Bicicleta, DatosBicicleta> entry : bicicletas.entrySet()) {  // Itrate through hashmap
            if (entry.getValue().getCantidadVecesUsada()==maxValueInMap) {
            	bicicletasAExportar.put(entry.getKey(), entry.getValue());
            }
        }
		return bicicletasAExportar;
	}

	private int getVecesMasUsada(Map<Bicicleta, DatosBicicleta> bicicletas) {
		int cantidadMaxima=0;
		for(DatosBicicleta export : bicicletas.values()){
			if(cantidadMaxima < export.getCantidadVecesUsada()){
				cantidadMaxima = export.getCantidadVecesUsada();
			}
		}
		return cantidadMaxima;
	}

	@Override
	public Map<Bicicleta,DatosBicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, DatosBicicleta> bicicletas) {
		Map<Bicicleta, DatosBicicleta> bicicletasAExportar = new HashMap<Bicicleta, DatosBicicleta>();
        int minValueInMap = this.getMenosVecesUsada(bicicletas);  // This will return max value in the Hashmap
        for (Entry<Bicicleta, DatosBicicleta> entry : bicicletas.entrySet()) {  // Itrate through hashmap
            if (entry.getValue().getCantidadVecesUsada()==minValueInMap) {
            	bicicletasAExportar.put(entry.getKey(),entry.getValue());
            }
        }
		return bicicletasAExportar;
	}

	private int getMenosVecesUsada(Map<Bicicleta, DatosBicicleta> bicicletas) {
		int cantidadMinima= bicicletas.values().iterator().next().getCantidadVecesUsada();
		for(DatosBicicleta export : bicicletas.values()){
			if(cantidadMinima > export.getCantidadVecesUsada()){
				cantidadMinima = export.getCantidadVecesUsada();
			}
		}
		return cantidadMinima;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Trayectoria, Integer> obtenerRecorridoMasRealizado(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv) {
		Map<Trayectoria, Integer> recorridoADevolver = new HashMap<Trayectoria, Integer>();
		Map<Trayectoria, Integer> recorridoMasRealizado = new HashMap<Trayectoria, Integer>();
		for(DatosBicicleta entry : bicicletasEnCsv.values()){
			Map<Trayectoria, Integer> trayectoriasPorBicicleta = entry.getTrayectoriasRealizadas();
			Iterator it = trayectoriasPorBicicleta.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry pairs = (Map.Entry)it.next();
		    	if(!recorridoMasRealizado.containsKey(pairs.getKey())){
		    		recorridoMasRealizado.put((Trayectoria)pairs.getKey(), (Integer)pairs.getValue());
		    	}else{
		    		recorridoMasRealizado.put((Trayectoria)pairs.getKey(), (Integer)recorridoMasRealizado.get(pairs.getKey())+ (Integer)pairs.getValue());
		    	}
		    	it.remove(); // avoids a ConcurrentModificationException
		    }
		}
        
		int maxValueInMap=(Collections.max(recorridoMasRealizado.values()));  // This will return max value in the Hashmap
        for (Entry<Trayectoria, Integer> entry : recorridoMasRealizado.entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==maxValueInMap) {
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
			WatchService service = fs.newWatchService(); 
			
			// Registramos el path en el servicio y esperamos solo por eventos de creacion,
			// es decir, por archivos nuevos que se alojen aqui.
			path.register(service, ENTRY_CREATE);
			
			// Empezamos a escuchar con un loop infinito
			WatchKey key = null;
			while(true) {
				key = service.take();
				
				// Dequeueing events
				Kind<?> kind = null;
				for(WatchEvent<?> watchEvent : key.pollEvents()) {
					// Get the type of the event
					kind = watchEvent.kind();
					if (OVERFLOW == kind) {
						continue; //loop
					} else if (ENTRY_CREATE == kind) {
						
						// Este path corresponde al nombre del archivo que se creo 
						Path newPath = ((WatchEvent<Path>) watchEvent).context();
						
						// Suponemos que el archivo que se va a alojar es un ZIP.
						String zipFilePath = path.toString() + File.separator + newPath;
						if(newPath.toString().toLowerCase().endsWith(".zip")){
							this.procesarCsvEnZip(zipFilePath);
						}
					}
				}
				if(!key.reset()) {
					break; //loop
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
			Boolean isFolder = (Boolean) Files.getAttribute(path,
					"basic:isDirectory", NOFOLLOW_LINKS);
			if (!isFolder) {
				throw new IllegalArgumentException("El Path: " 
						+ path + " no es una carpeta");
			}
		} catch (IOException ioe) {
			// Folder does not exists
			ioe.printStackTrace();
		}
	}
	
	public Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadas(ZipFile zipFile){
		Map<Bicicleta, DatosBicicleta> bicicletas = new HashMap<Bicicleta, DatosBicicleta>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
	    
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		
		while(entries.hasMoreElements()){
			try {
				int contadorDeLineas=1;
				ZipEntry entry = entries.nextElement();
			    InputStream stream = zipFile.getInputStream(entry);
			    InputStreamReader isr = new InputStreamReader(stream);
			    br = new BufferedReader(isr);
			    boolean firstRead = true;
			    while ((line = br.readLine()) != null) {
					// Spliteamos por punto y coma.
			    	if(firstRead){
			    		line = br.readLine();
			    		firstRead = false;
			    	}
			    	contadorDeLineas++;
					String[] recorrido = line.split(cvsSplitBy);
					Bicicleta bicicleta = new Bicicleta();
					bicicleta.setId(recorrido[1]);
					DatosBicicleta export;
					if(recorrido.length == 9){
						if(!bicicletas.containsKey(bicicleta)){
							export = new DatosBicicleta();
							export.setCantidadVecesUsada(1);
							export.setTiempoDeUso(Integer.parseInt(recorrido[8]));
							
							export = agregarTrayectoriaAlExportDeDatosBicicleta(
									recorrido, export);
							
							bicicletas.put(bicicleta, export);
						}else{
							export = bicicletas.get(bicicleta);
							export.setCantidadVecesUsada(export.getCantidadVecesUsada()+1);
							export.setTiempoDeUso(export.getTiempoDeUso()+Integer.parseInt(recorrido[8]));
							
							export = agregarTrayectoriaAlExportDeDatosBicicleta(
									recorrido, export);
							
							
							bicicletas.put(bicicleta, export);
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
			DatosBicicleta export) {
		
		Trayectoria trayectoria = new Trayectoria();
		Estacion estacionOrigen = new Estacion();
		estacionOrigen.setId(recorrido[3]);
		estacionOrigen.setNombre(recorrido[4]);
		
		Estacion estacionDestino = new Estacion();
		estacionDestino.setId(recorrido[6]);
		estacionDestino.setNombre(recorrido[7]);
		
		trayectoria.setEstacionOrigen(estacionOrigen);
		trayectoria.setEstacionDestino(estacionDestino);
		
		if(!export.getTrayectoriasRealizadas().containsKey(trayectoria)){
			export.getTrayectoriasRealizadas().put(trayectoria, 1);
		}else{
			Map<Trayectoria,Integer> trayectorias = export.getTrayectoriasRealizadas();
			trayectorias.put(trayectoria, trayectorias.get(trayectoria)+1);
		}
		return export;
	}

	@Override
	public void procesarCsvEnZip(String nombreArchivo) throws IOException {
	    ZipFile zipFile = new ZipFile(nombreArchivo);
	    Map<Bicicleta, DatosBicicleta> bicicletasEnCsv = this.llenarMapaDeBicicletasUsadas(zipFile);
	    ExportYmlDTO exportable = this.exportarYML(bicicletasEnCsv);
	    
		Yaml.dump(exportable, new File(nombreArchivo+".yml"));
	}

	@Override
	public void procesarDirectorio(Path pathProcesing) throws ZipException, IOException {
		comprobarPath(pathProcesing);
		File dir = new File(pathProcesing.toString());
		File[] directoryListing = dir.listFiles();
		
		Map<Bicicleta,DatosBicicleta> datosEnDirectorio = this.llenarMapaDeBicicletasUsadasEnDirectorio(directoryListing);
		ExportYmlDTO exportable = this.exportarYML(datosEnDirectorio);
		
		Yaml.dump(exportable, new File(pathProcesing.toString() + File.separatorChar + "salida.yml"));
	}

	public Integer getPromedioUso(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv) {
		Integer cantidadTotalDeUso = 0;
		Integer cantidadDeRows = 0;
		Collection<DatosBicicleta> collecionDeExports = bicicletasEnCsv.values();
		for(DatosBicicleta unExport : collecionDeExports){
			cantidadTotalDeUso += unExport.getTiempoDeUso();
			cantidadDeRows += unExport.getCantidadVecesUsada();
		}
		Integer promedio = cantidadTotalDeUso / cantidadDeRows;
		return promedio;
	}

	
	private Map<Bicicleta,DatosBicicleta> llenarMapaDeBicicletasUsadasEnDirectorio(File[] files) throws ZipException, IOException{
		Map<Bicicleta, DatosBicicleta> bicicletas = new HashMap<Bicicleta, DatosBicicleta>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
	    for(File unFile : files){
	    	ZipFile zipFile = new ZipFile(unFile);
	    	Enumeration<? extends ZipEntry> entries = zipFile.entries();
		
			while(entries.hasMoreElements()){
				try {
					ZipEntry entry = entries.nextElement();
				    InputStream stream = zipFile.getInputStream(entry);
				    InputStreamReader isr = new InputStreamReader(stream);
				    br = new BufferedReader(isr);
					int contadorDeLineas=1;
				    boolean firstRead = true;
				    while ((line = br.readLine()) != null) {
						// Spliteamos por punto y coma.
				    	if(firstRead){
				    		line = br.readLine();
				    		firstRead = false;
				    	}
				    	contadorDeLineas++;
						String[] recorrido = line.split(cvsSplitBy);
						Bicicleta bicicleta = new Bicicleta();
						bicicleta.setId(recorrido[1]);
						DatosBicicleta export;
						if(recorrido.length == 9){
							if(!bicicletas.containsKey(bicicleta)){
								export = new DatosBicicleta();
								export.setCantidadVecesUsada(1);
								export.setTiempoDeUso(Integer.parseInt(recorrido[8]));
								
								export = agregarTrayectoriaAlExportDeDatosBicicleta(
										recorrido, export);
								
								bicicletas.put(bicicleta, export);
							}else{
								export = bicicletas.get(bicicleta);
								export.setCantidadVecesUsada(export.getCantidadVecesUsada()+1);
								export.setTiempoDeUso(export.getTiempoDeUso()+Integer.parseInt(recorrido[8]));
								
								export = agregarTrayectoriaAlExportDeDatosBicicleta(
										recorrido, export);
								
								bicicletas.put(bicicleta, export);
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
	    }
		return bicicletas;
	}

	private ExportYmlDTO exportarYML(Map<Bicicleta, DatosBicicleta> bicicletasEnCsv) throws FileNotFoundException {
		ExportYmlDTO exportYml = new ExportYmlDTO();
		Map<Bicicleta,DatosBicicleta> bicicletasMasUsadas = this.obtenerBicicletasUtilizadasMasVeces(bicicletasEnCsv);
		for (Map.Entry<Bicicleta, DatosBicicleta> entry : bicicletasMasUsadas.entrySet())
		{
			exportYml.setBicicletaMasUsada(entry.getKey());
			exportYml.setCantidadVecesMasUsada(entry.getValue().getCantidadVecesUsada());
		}
		
		Map<Bicicleta,DatosBicicleta> bicicletasMenosUsadas = this.obtenerBicicletaUtilizadaMenosVeces(bicicletasEnCsv);
		for (Map.Entry<Bicicleta, DatosBicicleta> entry : bicicletasMenosUsadas.entrySet())
		{
			exportYml.setBicicletaMenosUsada(entry.getKey());
			exportYml.setCantidadVecesMenosUsada(entry.getValue().getCantidadVecesUsada());			
		}
		Integer promedioUso = this.getPromedioUso(bicicletasEnCsv);
		exportYml.setPromedioUso(promedioUso);
		
		Map<Trayectoria, Integer> recorridoMasRealizado = this.obtenerRecorridoMasRealizado(bicicletasEnCsv);
		for(Map.Entry<Trayectoria, Integer> entry : recorridoMasRealizado.entrySet()){
			exportYml.setMayorRecorridoRealizado(entry.getKey());
			exportYml.setCantidadMayorRecorridoRealizado(entry.getValue());
		}
		
		return exportYml;
	}
}
