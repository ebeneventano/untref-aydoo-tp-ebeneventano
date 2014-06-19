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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Recorrido;
import untref.aydoo.dtos.ExportYmlDTO;


public class ProcesadorEstadisticoImpl implements ProcesadorEstadistico{

	@Override
	public List<Bicicleta> obtenerBicicletasUtilizadasMasVeces(Map<Bicicleta, ExportYmlDTO> bicicletas) {
		List<Bicicleta> bicicletasUsadas = new ArrayList<Bicicleta>();
        int maxValueInMap=(this.getVecesMasUsada(bicicletas));  // This will return max value in the Hashmap
        for (Entry<Bicicleta, ExportYmlDTO> entry : bicicletas.entrySet()) {  // Itrate through hashmap
            if (entry.getValue().getCantidadVecesUsada()==maxValueInMap) {
            	bicicletasUsadas.add(entry.getKey());
            }
        }
		return bicicletasUsadas;
	}

	private int getVecesMasUsada(Map<Bicicleta, ExportYmlDTO> bicicletas) {
		int cantidadMaxima=0;
		for(ExportYmlDTO export : bicicletas.values()){
			if(cantidadMaxima < export.getCantidadVecesUsada()){
				cantidadMaxima = export.getCantidadVecesUsada();
			}
		}
		return cantidadMaxima;
	}

	@Override
	public List<Bicicleta> obtenerBicicletaUtilizadaMenosVeces(Map<Bicicleta, ExportYmlDTO> bicicletas) {
		List<Bicicleta> bicicletasUsadas = new ArrayList<Bicicleta>();
        int minValueInMap = this.getMenosVecesUsada(bicicletas);  // This will return max value in the Hashmap
        for (Entry<Bicicleta, ExportYmlDTO> entry : bicicletas.entrySet()) {  // Itrate through hashmap
            if (entry.getValue().getCantidadVecesUsada()==minValueInMap) {
            	bicicletasUsadas.add(entry.getKey());
            }
        }
		return bicicletasUsadas;
	}

	private int getMenosVecesUsada(Map<Bicicleta, ExportYmlDTO> bicicletas) {
		return 0;
	}

	@Override
	public List<Recorrido> obtenerRecorridoMasRealizado() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerTiempoPromedio(Bicicleta bicicleta) {
		// TODO Auto-generated method stub
		return null;
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
						this.procesarCsvEnZip(zipFilePath);
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
	
	public Map<Bicicleta,ExportYmlDTO> llenarMapaDeBicicletasUsadas(ZipFile zipFile){
		Map<Bicicleta, ExportYmlDTO> bicicletas = new HashMap<Bicicleta, ExportYmlDTO>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
	    
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		
		while(entries.hasMoreElements()){
			try {
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
					String[] recorrido = line.split(cvsSplitBy);
					Bicicleta bicicleta = new Bicicleta();
					bicicleta.setId(recorrido[1]);
					ExportYmlDTO export;
					if(!bicicletas.containsKey(bicicleta)){
						export = new ExportYmlDTO();
						export.setCantidadVecesUsada(1);
						export.setTiempoUso(Integer.parseInt(recorrido[8]));
						bicicletas.put(bicicleta, export);
					}else{
						export = bicicletas.get(bicicleta);
						export.setCantidadVecesUsada(export.getCantidadVecesUsada()+1);
						export.setTiempoUso(export.getTiempoUso()+Integer.parseInt(recorrido[8]));
						bicicletas.put(bicicleta, export);
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

	@Override
	public void procesarCsvEnZip(String nombreArchivo) throws IOException {
	    ZipFile zipFile = new ZipFile(nombreArchivo);
	    Map<Bicicleta, ExportYmlDTO> bicicletasEnCsv = this.llenarMapaDeBicicletasUsadas(zipFile);
	    this.exportarYML(bicicletasEnCsv);
	}
	
	@Override
	public void procesarCsvEnZip(List<String> zips) throws IOException {
	}

	private void exportarYML(Map<Bicicleta, ExportYmlDTO> bicicletasEnCsv) {
		List<Bicicleta> bicicletasMasUsadas = this.obtenerBicicletasUtilizadasMasVeces(bicicletasEnCsv);
		List<Bicicleta> bicicletasMenosUsadas = this.obtenerBicicletaUtilizadaMenosVeces(bicicletasEnCsv);
		bicicletasMasUsadas.size();
	}
}
