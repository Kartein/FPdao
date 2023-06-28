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

public class DaoPessoa {
	
	public boolean inserir(Pessoa pessoa) throws SQLException {
		
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into pessoa (nome, receita, valorReceita) values(? , ?, ?);";
		PreparedStatement ps = conexao.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, pessoa.getNome());
		ps.setString(2, pessoa.getReceita());
		ps.setDouble(3, pessoa.getValorReceita());

		int linhasAfetadas = ps.executeUpdate();
		
		ResultSet r = ps.getGeneratedKeys();
		
		if( r.next() ) {
			int id = r.getInt(1);	
			pessoa.setId(id);
		}	
		return (linhasAfetadas == 1 ? true : false);
	}	
	public boolean atualizarReceita(Pessoa pessoa) throws SQLException {
		return true;
	}
	public boolean atualizarValorReceita(Pessoa pessoa) throws SQLException {
		return true;
	}
	public boolean excluir(int id) throws SQLException {
		return true;
	}
	public Pessoa buscarPorId(int idBuscado) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from pessoa where id = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idBuscado);
		
		ResultSet result = ps.executeQuery();
		
		Pessoa pessoa = null;
		
		if( result.next() ) {
			int id = result.getInt("id");
			String nome = result.getString("nome");
			String receita = result.getString("receita");
			double valorReceita = result.getDouble("valorReceita");
			
			pessoa = new Pessoa(id, nome, receita, valorReceita);
		}
		return pessoa;
	}
	public Pessoa buscarPorReceita(String receita) throws SQLException {
		return null;
	}
	public List<Pessoa> buscarTodos() throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from pessoa";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet result = ps.executeQuery();
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		while( result.next() ) {
			int id = result.getInt("id");
			String nome = result.getString("nome");
			String receita = result.getString("receita");
			double valorReceita = result.getDouble("valorReceita");
			
			Pessoa pessoa = new Pessoa(id, nome, receita, valorReceita);
	
			pessoas.add(pessoa);
		}	
		return pessoas;
	}
}