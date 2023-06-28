package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.DaoDespesas;
import dao.DaoPessoa;
import fp.Despesas;
import fp.Pessoa;

public class Programa {

	private static DaoDespesas daoDespesas = new DaoDespesas();
	private static DaoPessoa daoPessoa = new DaoPessoa();
	
	public static void main(String[] args) throws SQLException {
		
		Scanner scanner = new Scanner(System.in);
		int op;
		
		do {
			System.out.println("Digite:");
			System.out.println("1 - Cadastrar pessoa");
			System.out.println("2 - Listar pessoa");
			System.out.println("3 - Cadastrar despesas");
			System.out.println("4 - Atualizar despesas");
			System.out.println("5 - Buscar despesas");
			System.out.println("6 - Excluir despesas");
			System.out.println("7 - Listar despesas");
			System.out.println("8 - Pesquisar despesas");
			System.out.println("9 - Listar despesas por pessoa");
			System.out.println("0 - Sair");
			
			op = Integer.parseInt( scanner.nextLine() );
			
			switch(op) {
				case 1:
					cadastrarPessoa();
					break;
				case 2:
					
					listarPessoa();
					break;
				case 3:
					cadastrarDespesas();
					break;
				case 4:
					atualizarDespesas();
					break;
				case 5:
					buscarDespesas();
					break;
				case 6:
					excluirDespesas();
					break;
				case 7:
					listarDespesas();
					break;
				case 8:
					pesquisarDespesas();
					break;
				case 9:
					listarDespesasPorPessoa();
					break;
				case 0:
					System.out.println("Código Encerrado");
					break;
				default:
					System.out.println("Opção inválida!");
			}		
		}while(op != 0);
	}
	public static void cadastrarPessoa() throws SQLException{
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome da pessoa: ");
		String nome = scanner.nextLine();
		
		System.out.println("Digite o nome da receita(salário, bolsa família, etc): ");
		String receita = scanner.nextLine();
		
		System.out.println("Informe o valor da receita: ");
		double valorReceita = scanner.nextDouble();

		Pessoa pessoa = new Pessoa(nome, receita, valorReceita);

		System.out.println( daoPessoa.inserir( pessoa ) ? "Cadastro Concluído!" : "Falha do cadastro...");

		System.out.println("Pessoa cadastrado sob o ID " + pessoa.getId());
	}
	public static void listarPessoa() throws SQLException {
		
		System.out.println("\n----- Listar Pessoas -----\n");
		
		List<Pessoa> pes = daoPessoa.buscarTodos();
		
		for(Pessoa p : pes) {
			System.out.println("ID: " + p.getId());
			System.out.println("Nome: " + p.getNome());
			System.out.println("Receita: " + p.getReceita());
			System.out.println("Valor da Receita: " + p.getValorReceita());
		}
	}
	public static void cadastrarDespesas() throws SQLException {
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite a despesa: ");
		String descricao = scanner.nextLine();
		
		System.out.println("Informe o valor da despesa: ");
		double valor = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Informe o ID da Pessoa: ");
		int idPes = Integer.parseInt(scanner.nextLine());

		Pessoa p = daoPessoa.buscarPorId(idPes);
		
		if(p != null) {
			Despesas d = new Despesas(descricao, valor, valor, p);
	
			System.out.println( daoDespesas.inserir( d ) ? "Cadastro Finalizado!" : "Falha ao cadastrar...");
	
			System.out.println("Despesa cadastrada sob o ID " + d.getId());
		}else {
			System.out.println("Não existe pessoa com o ID informado!");
		}
	}
	public static void atualizarDespesas() throws SQLException{
		
		System.out.println("----- Atualizando Despesas -----");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Informe o ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		Despesas despesas = daoDespesas.buscar(id);
		
		if(despesas != null) {
			
			System.out.println("Descrição atual da despesa: " + despesas.getDescricao());
			System.out.println("Informe a nova descrição ou pressione enter:");
			
			String desc = scanner.nextLine();
			
			if(desc.length() > 0) {
				despesas.setDescricao(desc);
			}
			
			System.out.println("Valor atual da despesa: " + despesas.getValor());
			System.out.println("Informe o novo valor ou pressione enter:");
			
			String priori = scanner.nextLine();
			
			if(priori.length() > 0) {
				despesas.setValor( Integer.parseInt(priori) );
			}
			
			if( daoDespesas.atualizar(despesas) ) {
				System.out.println("Despesa atualizada!");
			}else {
				System.out.println("Houve um erro ao atualizar.");
			}
			
		}else {
			System.out.println("Erro ao localizar despesa. A depesa "+ id +" existe?");
		}
	}
	public static void buscarDespesas() throws SQLException {
		
		System.out.println("\n----- Buscando Despesa por ID -----");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Informe o ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		Despesas d = daoDespesas.buscar(id);
		
		if(d != null) {
			System.out.println("ID: " + d.getId());
			System.out.println("Descrição: " + d.getDescricao());
			System.out.println("Valor: " + d.getValor());
			System.out.println("Pessoa: " + d.getPessoa().getNome() +"\n");
		}else {
			System.out.println("Despesa não existe...");
		}
	}
	public static void excluirDespesas() throws SQLException{
		
		System.out.println("\n----- Excluindo Despesas por ID -----");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Informe o ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		boolean r = daoDespesas.excluir(id);
		
		if( r ) {
			System.out.println("Despesa excluída com sucesso!");
		}else {
			System.out.println("Houve um erro ao excluir. A depesa "+ id +" existe?");
		}
	}
	public static void listarDespesas() throws SQLException {
		
		System.out.println("\n----- Listar Despesas -----\n");
		
		List<Despesas> finpas = daoDespesas.buscarTodas();

		Scanner scanner = new Scanner(System.in);
		
		for(Despesas d : finpas) {
			System.out.println("ID: " + d.getId());
			System.out.println("Descrição: " + d.getDescricao());
			System.out.println("Valor: " + d.getValor());
			System.out.println("Pessoa: " + d.getPessoa().getNome() +"\n");

			scanner.nextLine();
		}
	}
	public static void pesquisarDespesas() throws SQLException {
		
		System.out.println("\n----- Buscando Despesas por Descrição -----");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Informe o descrição: ");
		String pesquisa = scanner.nextLine();

		List<Despesas> finpas = daoDespesas.pesquisarPorDescricao(pesquisa);
		
		for(Despesas d : finpas) {
			System.out.println("ID: " + d.getId());
			System.out.println("Descrição: " + d.getDescricao());
			System.out.println("Valor: " + d.getValor());
			System.out.println("Pessoa: " + d.getPessoa().getNome() +"\n");
			
			scanner.nextLine();
		}
	}
	public static void listarDespesasPorPessoa() throws SQLException{
		
		System.out.println("\n----- Listar Despesas por Pessoa -----\n");
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome da pessoa: ");
		String nome = scanner.nextLine();
		
		List<Despesas> finpas = daoDespesas.tarefasPorPessoa(nome);
		
		for(Despesas d : finpas) {
			System.out.println("ID: " + d.getId());
			System.out.println("Descrição: " + d.getDescricao());
			System.out.println("Valor: " + d.getValor());
			System.out.println("Pessoa: " + d.getPessoa().getNome() +"\n");
			
			scanner.nextLine();
		}
	}
}