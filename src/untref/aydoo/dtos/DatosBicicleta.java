package untref.aydoo.dtos;

import java.util.HashMap;
import java.util.Map;

import untref.aydoo.dominio.Trayectoria;

public class DatosBicicleta {
	
	private Integer cantidadVecesUsada;
	private Map<Trayectoria, Integer> trayectoriasRealizadas = new HashMap<Trayectoria,Integer>();
	private Integer tiempoDeUso;
	
	public void setearCantidadVecesUsada(Integer cantidadVecesUsada) {
		this.cantidadVecesUsada = cantidadVecesUsada;
	}
	public Integer obtenerCantidadVecesUsada() {
		return cantidadVecesUsada;
	}
	public void setearTiempoDeUso(Integer tiempoDeUso) {
		this.tiempoDeUso = tiempoDeUso;
	}
	public Integer obtenerTiempoDeUso() {
		return tiempoDeUso;
	}
	public void setearTrayectoriasRealizadas(Map<Trayectoria, Integer> trayectoriasRealizadas) {
		this.trayectoriasRealizadas = trayectoriasRealizadas;
	}
	public Map<Trayectoria, Integer> obtenerTrayectoriasRealizadas() {
		return trayectoriasRealizadas;
	}

}
