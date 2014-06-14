package untref.aydoo.dominio;

import java.util.Date;

public class Trayectoria {
	
	private Estacion estacionOrigen;
	private Estacion estacionDestino;
	private Date fechaOrigen;
	private Date fechaDestino;
	public Estacion getEstacionOrigen() {
		return estacionOrigen;
	}
	
	public void setEstacionOrigen(Estacion estacionOrigen) {
		this.estacionOrigen = estacionOrigen;
	}
	
	public Estacion getEstacionDestino() {
		return estacionDestino;
	}
	
	public void setEstacionDestino(Estacion estacionDestino) {
		this.estacionDestino = estacionDestino;
	}
	
	public Date getFechaOrigen() {
		return fechaOrigen;
	}
	
	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}
	
	public Date getFechaDestino() {
		return fechaDestino;
	}
	
	public void setFechaDestino(Date fechaDestino) {
		this.fechaDestino = fechaDestino;
	}
}
