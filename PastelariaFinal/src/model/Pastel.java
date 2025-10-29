package model;

public class Pastel {
    private int idPasteis;
    private String sabor;
    private double preco;

    public Pastel(int idPasteis, String sabor, double preco) { // cria um constructor que necessita do Id
        this.idPasteis = idPasteis;
        this.sabor = sabor;
        this.preco = preco;
    }

    public Pastel(String sabor, double preco) { // Cria um constructor que nao precisa do Id
        this.sabor = sabor;
        this.preco = preco;
    }

    // Temos os gets (Nao usamos os sets)
    public int getIdPasteis() { return idPasteis; }
    public String getSabor() { return sabor; }
    public double getPreco() { return preco; }


    //Print do pastel e seu preço
    @Override
    public String toString() {
        return sabor + " - R$ " + String.format("%.2f", preco);
    }
}
