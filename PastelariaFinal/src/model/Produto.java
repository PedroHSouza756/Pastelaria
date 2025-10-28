package model;

public class Produto {
    private int idProduto;
    private String tipo;

    public Produto(int idProduto, String tipo) {
        this.idProduto = idProduto;
        this.tipo = tipo;
    }

    // Gets
    public int getId() {return idProduto;}
    public String getNome() {return tipo;}

    @Override
    public String toString() {
        return tipo; // Mostra "Pastel" ou "Bebida" no combo
    }
}
