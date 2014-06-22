package untref.aydoo.dtos;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Trayectoria;

public class ExportYmlDTO {
	private Bicicleta bicicletaMasUsada;
	private Bicicleta bicicletaMenosUsada;
	private Integer cantidadVecesMasUsada;
	private Integer cantidadVecesMenosUsada;
	private Integer tiempoUso;
	private Trayectoria mayorRecorridoRealizado;
	private Integer cantidadMayorRecorridoRealizado;
	
	public Integer getCantidadVecesUsada() {
		return cantidadVecesMasUsada;
	}
	public void setCantidadVecesUsada(Integer cantidadVecesUsada) {
		this.cantidadVecesMasUsada = cantidadVecesUsada;
	}
	public Integer getTiempoUso() {
		return tiempoUso;
	}
	public void setTiempoUso(Integer tiempoUso) {
		this.tiempoUso = tiempoUso;
	}
	public Trayectoria getMayorRecorridoRealizado() {
		return mayorRecorridoRealizado;
	}
	public void setMayorRecorridoRealizado(Trayectoria mayorRecorridoRealizado) {
		this.mayorRecorridoRealizado = mayorRecorridoRealizado;
	}
	public Integer getCantidadMayorRecorridoRealizado() {
		return cantidadMayorRecorridoRealizado;
	}
	public void setCantidadMayorRecorridoRealizado(
			Integer cantidadMayorRecorridoRealizado) {
		this.cantidadMayorRecorridoRealizado = cantidadMayorRecorridoRealizado;
	}
	public void setBicicletaMasUsada(Bicicleta bicicletaMasUsada) {
		this.bicicletaMasUsada = bicicletaMasUsada;
	}
	public Bicicleta getBicicletaMasUsada() {
		return bicicletaMasUsada;
	}
	public void setBicicletaMenosUsada(Bicicleta bicicletaMenosUsada) {
		this.bicicletaMenosUsada = bicicletaMenosUsada;
	}
	public Bicicleta getBicicletaMenosUsada() {
		return bicicletaMenosUsada;
	}
	public void setCantidadVecesMenosUsada(Integer cantidadVecesMenosUsada) {
		this.cantidadVecesMenosUsada = cantidadVecesMenosUsada;
	}
	public Integer getCantidadVecesMenosUsada() {
		return cantidadVecesMenosUsada;
	}
}
