package fp;

public class Despesas {
	
	private int id;
	private String descricao;
	private double valor;
	private double saldo;
	private Pessoa pessoa;
	
	public Despesas(String descricao, double valor, double saldo, Pessoa pessoa) {
		this.descricao = descricao;
		this.valor = valor;
		this.saldo = saldo;
		this.pessoa = pessoa;
	}
	public Despesas(int id, String descricao, double valor, double saldo, Pessoa pessoa) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.saldo = saldo;
		this.pessoa = pessoa;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}