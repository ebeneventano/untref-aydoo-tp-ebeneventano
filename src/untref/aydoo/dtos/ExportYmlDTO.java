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
//	private Integer cantidadVecesMasUsada;
//	private Integer cantidadVecesMenosUsada;
//	private Integer cantidadMayorRecorridoRealizado;
	
	public void setBicicletaMasUsada(List<Bicicleta> bicicletaMasUsada) {
		this.bicicletaMasUsada = bicicletaMasUsada;
	}

	public List<Bicicleta> getBicicletaMasUsada() {
		return bicicletaMasUsada;
	}

	public void setPromedioUso(Integer promedioUso) {
		this.promedioUso = promedioUso;
	}
	
	public Integer getPromedioUso() {
		return promedioUso;
	}
	
	public void setMayorRecorridoRealizado(List<Trayectoria> mayorRecorridoRealizado) {
		this.mayorRecorridoRealizado = mayorRecorridoRealizado;
	}
	
	public List<Trayectoria> getMayorRecorridoRealizado() {
		return mayorRecorridoRealizado;
	}
	
	public void setBicicletaMenosUsada(List<Bicicleta> bicicletaMenosUsada) {
		this.bicicletaMenosUsada = bicicletaMenosUsada;
	}
	
	public List<Bicicleta> getBicicletaMenosUsada() {
		return bicicletaMenosUsada;
	}
	
	public void addBicicletaMasUsada(Bicicleta bicicleta){
		this.bicicletaMasUsada.add(bicicleta);
	}
	
	public void addBicicletaMenosUsada(Bicicleta bicicleta){
		this.bicicletaMenosUsada.add(bicicleta);
	}
	
	public void addTrayectoriaMasRealizada(Trayectoria trayectoria){
		this.mayorRecorridoRealizado.add(trayectoria);
	}

//	public void setCantidadVecesMasUsada(Integer cantidadVecesMasUsada) {
//		this.cantidadVecesMasUsada = cantidadVecesMasUsada;
//	}
//
//	public Integer getCantidadVecesMasUsada() {
//		return cantidadVecesMasUsada;
//	}
//	public void setCantidadVecesMenosUsada(Integer cantidadVecesMenosUsada) {
//		this.cantidadVecesMenosUsada = cantidadVecesMenosUsada;
//	}
//
//	public Integer getCantidadVecesMenosUsada() {
//		return cantidadVecesMenosUsada;
//	}
//	public void setCantidadMayorRecorridoRealizado(
//			Integer cantidadMayorRecorridoRealizado) {
//		this.cantidadMayorRecorridoRealizado = cantidadMayorRecorridoRealizado;
//	}
//
//	public Integer getCantidadMayorRecorridoRealizado() {
//		return cantidadMayorRecorridoRealizado;
//	}
}
