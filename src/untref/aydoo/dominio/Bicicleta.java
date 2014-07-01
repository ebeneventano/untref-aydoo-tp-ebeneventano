package untref.aydoo.dominio;

public class Bicicleta {
		
		private String id;

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
		
	    @Override
	    public boolean equals(Object o) {
	        if(o instanceof Bicicleta){
	            Bicicleta other = (Bicicleta) o;
	            return id.equals(other.getId());
	        }

	        return false;
	    }

	    @Override
	    public int hashCode() {
	        return id.hashCode();
	    }

}
