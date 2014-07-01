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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((estacionDestino == null) ? 0 : estacionDestino.hashCode());
		result = prime * result
				+ ((estacionOrigen == null) ? 0 : estacionOrigen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trayectoria other = (Trayectoria) obj;
		if (estacionDestino == null) {
			if (other.estacionDestino != null)
				return false;
		} else if (!estacionDestino.equals(other.estacionDestino))
			return false;
		if (estacionOrigen == null) {
			if (other.estacionOrigen != null)
				return false;
		} else if (!estacionOrigen.equals(other.estacionOrigen))
			return false;
		return true;
	}
	
}
