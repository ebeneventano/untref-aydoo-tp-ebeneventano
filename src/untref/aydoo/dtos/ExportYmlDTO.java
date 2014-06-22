package untref.aydoo.dtos;

import untref.aydoo.dominio.Bicicleta;
import untref.aydoo.dominio.Trayectoria;

public class ExportYmlDTO {
	private Bicicleta bicicletaMasUsada;
	private Integer cantidadVecesMasUsada;
	private Bicicleta bicicletaMenosUsada;
	private Integer cantidadVecesMenosUsada;
	private Integer promedioUso;
	private Trayectoria mayorRecorridoRealizado;
	private Integer cantidadMayorRecorridoRealizado;
	
	public void setBicicletaMasUsada(Bicicleta bicicletaMasUsada) {
		this.bicicletaMasUsada = bicicletaMasUsada;
	}

	public Bicicleta getBicicletaMasUsada() {
		return bicicletaMasUsada;
	}

	public void setCantidadVecesMasUsada(Integer cantidadVecesMasUsada) {
		this.cantidadVecesMasUsada = cantidadVecesMasUsada;
	}

	public Integer getCantidadVecesMasUsada() {
		return cantidadVecesMasUsada;
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

	public void setPromedioUso(Integer promedioUso) {
		this.promedioUso = promedioUso;
	}

	public Integer getPromedioUso() {
		return promedioUso;
	}

	public void setMayorRecorridoRealizado(Trayectoria mayorRecorridoRealizado) {
		this.mayorRecorridoRealizado = mayorRecorridoRealizado;
	}

	public Trayectoria getMayorRecorridoRealizado() {
		return mayorRecorridoRealizado;
	}
	
	public void setCantidadMayorRecorridoRealizado(
			Integer cantidadMayorRecorridoRealizado) {
		this.cantidadMayorRecorridoRealizado = cantidadMayorRecorridoRealizado;
	}

	public Integer getCantidadMayorRecorridoRealizado() {
		return cantidadMayorRecorridoRealizado;
	}
}
