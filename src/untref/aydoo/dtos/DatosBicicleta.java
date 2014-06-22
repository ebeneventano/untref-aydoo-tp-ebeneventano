package untref.aydoo.dtos;

import java.util.HashMap;
import java.util.Map;

import untref.aydoo.dominio.Trayectoria;

public class DatosBicicleta {
	
	private Integer cantidadVecesUsada;
	private Map<Trayectoria, Integer> trayectoriasRealizadas = new HashMap<Trayectoria,Integer>();
	private Integer tiempoDeUso;
	
	public void setCantidadVecesUsada(Integer cantidadVecesUsada) {
		this.cantidadVecesUsada = cantidadVecesUsada;
	}
	public Integer getCantidadVecesUsada() {
		return cantidadVecesUsada;
	}
	public void setTiempoDeUso(Integer tiempoDeUso) {
		this.tiempoDeUso = tiempoDeUso;
	}
	public Integer getTiempoDeUso() {
		return tiempoDeUso;
	}
	public void setTrayectoriasRealizadas(Map<Trayectoria, Integer> trayectoriasRealizadas) {
		this.trayectoriasRealizadas = trayectoriasRealizadas;
	}
	public Map<Trayectoria, Integer> getTrayectoriasRealizadas() {
		return trayectoriasRealizadas;
	}

}
