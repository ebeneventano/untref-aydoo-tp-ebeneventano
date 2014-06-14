package untref.aydoo.dominio;

public class Recorrido {
	
	private Usuario usuario;
	private Bicicleta bicicleta;
	private Trayectoria trayectoria;
	private String tiempoUso;

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Bicicleta getBicicleta() {
		return bicicleta;
	}
	
	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}
	
	public Trayectoria getTrayectoria() {
		return trayectoria;
	}
	
	public void setTrayectoria(Trayectoria trayectoria) {
		this.trayectoria = trayectoria;
	}
	
	public String getTiempoUso() {
		return tiempoUso;
	}
	
	public void setTiempoUso(String tiempoUso) {
		this.tiempoUso = tiempoUso;
	}
}
