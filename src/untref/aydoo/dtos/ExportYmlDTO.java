package untref.aydoo.dtos;

import java.util.ArrayList;
import java.util.List;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Trayectoria;

public class ExportYmlDTO {
	private List<Bicicleta> bicicletaMasUsada = new ArrayList<Bicicleta>();
	private List<Bicicleta> bicicletaMenosUsada = new ArrayList<Bicicleta>();
	private Integer promedioUso;
	private List<Trayectoria> mayorRecorridoRealizado = new ArrayList<Trayectoria>();
	
	public void setearBicicletaMasUsada(List<Bicicleta> bicicletaMasUsada) {
		this.bicicletaMasUsada = bicicletaMasUsada;
	}

	public List<Bicicleta> obtenerBicicletaMasUsada() {
		return bicicletaMasUsada;
	}

	public void setearPromedioUso(Integer promedioUso) {
		this.promedioUso = promedioUso;
	}
	
	public Integer obtenerPromedioUso() {
		return promedioUso;
	}
	
	public void setearMayorRecorridoRealizado(List<Trayectoria> mayorRecorridoRealizado) {
		this.mayorRecorridoRealizado = mayorRecorridoRealizado;
	}
	
	public List<Trayectoria> obtenerMayorRecorridoRealizado() {
		return mayorRecorridoRealizado;
	}
	
	public void setearBicicletaMenosUsada(List<Bicicleta> bicicletaMenosUsada) {
		this.bicicletaMenosUsada = bicicletaMenosUsada;
	}
	
	public List<Bicicleta> obtenerBicicletaMenosUsada() {
		return bicicletaMenosUsada;
	}
	
	public void agregarBicicletaMasUsada(Bicicleta bicicleta){
		this.bicicletaMasUsada.add(bicicleta);
	}
	
	public void agregarBicicletaMenosUsada(Bicicleta bicicleta){
		this.bicicletaMenosUsada.add(bicicleta);
	}
	
	public void agregarTrayectoriaMasRealizada(Trayectoria trayectoria){
		this.mayorRecorridoRealizado.add(trayectoria);
	}
}
