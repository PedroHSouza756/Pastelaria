package model;

public class Refrigerante {
    private int id;
    private int idProduto;
    private String sabor;
    private int tamanhoMl;
    private double preco;

    public Refrigerante(int id, int idProduto, String sabor, int tamanhoMl, double preco) {
        this.id = id;
        this.idProduto = idProduto;
        this.sabor = sabor;
        this.tamanhoMl = tamanhoMl;
        this.preco = preco;
    }

    public String toString() {
        return sabor + " " + tamanhoMl + "ml - R$ " + preco; // Sabor e tamanho em ml
    }

    // gets
    public int getId() { return id; }
    public int getIdProduto() { return idProduto; }
    public String getSabor() { return sabor; }
    public int getTamanhoMl() { return tamanhoMl; }
    public double getPreco() { return preco; }
}
