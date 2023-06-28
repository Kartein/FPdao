package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fp.Despesas;
import fp.Pessoa;

public class DaoDespesas {
	
	public boolean inserir(Despesas despesas) throws SQLException {
				
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into despesas (descricao, valor, saldo, pessoa_id) values(? , ? , ?, ?);";
		PreparedStatement ps = conexao.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, despesas.getDescricao());
		ps.setDouble(2, despesas.getValor());
		ps.setDouble(3, despesas.getSaldo());
		ps.setInt(4, despesas.getPessoa().getId());

		int linhasAfetadas = ps.executeUpdate();
		
		ResultSet r = ps.getGeneratedKeys();
		
		if( r.next() ) {
			int id = r.getInt(1);	
			despesas.setId(id);
		}	
		return (linhasAfetadas == 1 ? true : false);
	}
	public boolean atualizar(Despesas despesas) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "update despesas set descricao = ?, valor = ?, saldo = ? where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, despesas.getDescricao());
		ps.setDouble(2, despesas.getValor());
		ps.setDouble(3, despesas.getSaldo());
		ps.setInt(4, despesas.getPessoa().getId());
		
		if( ps.executeUpdate() == 1) {
			return true;
		}else {
			return false;
		}
	}
	public boolean excluir(int id) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "delete from despesas where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		if( ps.executeUpdate() == 1) {
			return true;
		}else {
			return false;
		}
	}
	public Despesas buscar(int idBuscado) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from despesas where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, idBuscado);
		
		ResultSet result = ps.executeQuery();
		
		Despesas despesas = null;
		
		if( result.next() ) {
			int id = result.getInt("id");
			String descricao = result.getString("descricao");
			double valor = result.getDouble("valor");
			double saldo = result.getDouble("saldo");
			int idPessoa = result.getInt("pessoa_id");
			
			DaoPessoa daoPes = new DaoPessoa();
			Pessoa p = daoPes.buscarPorId(idPessoa);
			
			despesas = new Despesas(id, descricao, valor, saldo, p);
		}
		return despesas;
	}
	public List<Despesas> buscarTodas() throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from despesas";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet result = ps.executeQuery();
		
		List<Despesas> despesa = new ArrayList<Despesas>();
		
		DaoPessoa daoPes = new DaoPessoa();

		while( result.next() ) {
			int id = result.getInt("id");
			String descricao = result.getString("descricao");
			double valor = result.getDouble("valor");
			double saldo = result.getDouble("saldo");
			int idPessoa = result.getInt("pessoa_id");
			
			Pessoa p = daoPes.buscarPorId(idPessoa);
			
			Despesas d = new Despesas(id, descricao, valor, saldo, p);
	
			despesa.add(d);
		}
		return despesa;
	}
	public List<Despesas> pesquisarPorDescricao(String texto) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from despesas where descricao like ? ";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+texto+"%");
		
		ResultSet result = ps.executeQuery();
		
		List<Despesas> despesa = new ArrayList<Despesas>();
		
		DaoPessoa daoPes = new DaoPessoa();
		
		while( result.next() ) {
			int id = result.getInt("id");
			String descricao = result.getString("descricao");
			double valor = result.getDouble("valor");
			double saldo = result.getDouble("saldo");
			int idPessoa = result.getInt("pessoa_id");
			
			Pessoa p = daoPes.buscarPorId(idPessoa);
			Despesas d = new Despesas(id, descricao, valor, saldo, p);
	
			despesa.add(d);
		}
		return despesa;
	}
	public List<Despesas> tarefasPorPessoa(String receita) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from despesas left join pessoa on despesas.pessoa_id = pessoa.id where pessoa.nome = ?;";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, receita);
		
		ResultSet result = ps.executeQuery();
		
		List<Despesas> despesa = new ArrayList<Despesas>();
		
		if( result.next() ) {			
			int idPes = result.getInt("pessoa_id");
			String nome = result.getString("nome");
			String receita1 = result.getString("receita");
			double valorReceita = result.getDouble("valorReceita");
			
			Pessoa pessoa = new Pessoa(idPes, nome, receita1, valorReceita);
			
			do {
				int id = result.getInt("id");
				String descricao = result.getString("descricao");
				double valor = result.getDouble("valor");
				double saldo = result.getDouble("saldo");
				int idPessoa = result.getInt("pessoa_id");
				
				Despesas d = new Despesas(id, descricao, valor, saldo, pessoa);
		
				despesa.add(d);
				
			}while(result.next());
		}	
		return despesa;
	}
}