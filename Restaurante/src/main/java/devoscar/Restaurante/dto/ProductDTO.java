package devoscar.Restaurante.dto;
public class ProductDTO {

    private Long id;
    private String nomPlato;
    private double precio;
    private boolean gluten;

    public ProductDTO(Long id, String nomPlato, double precio, boolean gluten) {
        this.id = id;
        this.nomPlato = nomPlato;
        this.precio = precio;
        this.gluten = gluten;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPlato() {
        return nomPlato;
    }

    public void setNomPlato(String nomPlato) {
        this.nomPlato = nomPlato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }


    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nomPlato='" + nomPlato + '\'' +
                ", precio=" + precio +
                ", gluten=" + gluten +
                '}';
    }
}
