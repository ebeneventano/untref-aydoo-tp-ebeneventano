package untref.aydoo.dominio;

public class Bicicleta {
		
		private String id;

		public void setearId(String id) {
			this.id = id;
		}

		public String obtenerId() {
			return id;
		}
		
	    @Override
	    public boolean equals(Object o) {
	        if(o instanceof Bicicleta){
	            Bicicleta other = (Bicicleta) o;
	            return id.equals(other.obtenerId());
	        }

	        return false;
	    }

	    @Override
	    public int hashCode() {
	        return id.hashCode();
	    }

}
