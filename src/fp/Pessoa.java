package fp;

public class Pessoa {
	
	private int id;
	private String nome;
	private String receita;
	private double valorReceita;
	
	public Pessoa(String nome, String receita, double valorReceita) {
		this.nome = nome;
		this.receita = receita;
		this.valorReceita = valorReceita;
	}
	public Pessoa(int id, String nome, String receita, double valorReceita) {
		this.id = id;
		this.nome = nome;
		this.receita = receita;
		this.valorReceita = valorReceita;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getReceita() {
		return receita;
	}
	public void setReceita(String receita) {
		this.receita = receita;
	}
	public double getValorReceita() {
		return valorReceita;
	}
	public void setValorReceita(double valorReceita) {
		this.valorReceita = valorReceita;
	}
}