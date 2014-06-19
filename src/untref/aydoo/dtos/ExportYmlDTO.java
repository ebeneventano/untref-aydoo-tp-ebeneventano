package untref.aydoo.dtos;

public class ExportYmlDTO {
	
	private Integer cantidadVecesUsada;
	private Integer tiempoUso;
	private Integer mayorRecorridoRealizado;
	private Integer cantidadMayorRecorridoRealizado;
	
	public Integer getCantidadVecesUsada() {
		return cantidadVecesUsada;
	}
	public void setCantidadVecesUsada(Integer cantidadVecesUsada) {
		this.cantidadVecesUsada = cantidadVecesUsada;
	}
	public Integer getTiempoUso() {
		return tiempoUso;
	}
	public void setTiempoUso(Integer tiempoUso) {
		this.tiempoUso = tiempoUso;
	}
	public Integer getMayorRecorridoRealizado() {
		return mayorRecorridoRealizado;
	}
	public void setMayorRecorridoRealizado(Integer mayorRecorridoRealizado) {
		this.mayorRecorridoRealizado = mayorRecorridoRealizado;
	}
	public Integer getCantidadMayorRecorridoRealizado() {
		return cantidadMayorRecorridoRealizado;
	}
	public void setCantidadMayorRecorridoRealizado(
			Integer cantidadMayorRecorridoRealizado) {
		this.cantidadMayorRecorridoRealizado = cantidadMayorRecorridoRealizado;
	}
}
