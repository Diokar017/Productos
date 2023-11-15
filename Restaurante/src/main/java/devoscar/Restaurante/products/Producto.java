package devoscar.Restaurante.products;

public class Producto {
    private Long id;
    private String nomPlato;
    private double precio;
    private boolean gluten = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPlato() {
        return nomPlato;
    }

    public String setNomPlato(String nomPlato) {
        this.nomPlato = nomPlato;
        return nomPlato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean getGluten() {
        return gluten;
    }

    public void setGluten(Boolean gluten) {
        this.gluten = gluten;
    }

    public Producto(Long id, String nomPlato, double precio, Boolean gluten) {
        this.id = id;
        this.nomPlato = nomPlato;
        this.precio = precio;
        this.gluten = gluten;
    }


}
