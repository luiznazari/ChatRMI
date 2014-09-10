package chat.foda.pra.caralho.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryUtil extends ConexaoUtil {
	
	private static ResultSet result;
	
	private static final String createSchema = "create schema if not exists `chatFodaPraCaralho`";
	
	private static final String createPessoa = "create table if not exists `chatFodaPraCaralho`.`pessoa`(codigo bigint not null primary key auto_increment, nome_completo varchar(45), cpf varchar(15), email varchar(45) not null unique key, data_nascimento date)";
	
	private static final String createUsuario = "create table if not exists `chatFodaPraCaralho`.`usuario`(codigo bigint not null primary key auto_increment, senha varchar(45) not null, nickname varchar(45), codPessoa bigint not null, foreign key (codpessoa) references pessoa(codigo))";
	
	public static void criaBaseSeNaoExiste() {
		sql(createSchema);
		sql(createPessoa);
		sql(createUsuario);
	}
	
	public static void sql(String sql) {
		try {
			conexao.createStatement().execute(sql);
			conexao.commit();
		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao dar ROLLBACK");
				e1.printStackTrace();
			}
			System.err.println("Erro ao executar SQL: " + sql + "\n");
			e.printStackTrace();
		}
	}
	
	public static void sqlParam(String sql, String... params) {
		try {
			PreparedStatement prepared = conexao.prepareStatement(sql);
			
			for (int i = 1; i <= params.length; i++) {
				prepared.setString(i, params[i - 1]);
			}
			
			prepared.executeUpdate();
			conexao.commit();
		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao dar ROLLBACK");
				e1.printStackTrace();
			}
			System.err.println("Erro ao executar SQL: " + sql + "\n");
			e.printStackTrace();
		}
	}
	
	// Usa statements com parāmetros
	public static void queryParam(String sql, String... params) {
		try {
			PreparedStatement prepared = conexao.prepareStatement(sql);
			
			for (int i = 1; i <= params.length; i++) {
				prepared.setString(i, params[i - 1]);
			}
			
			result = prepared.executeQuery();
		} catch (SQLException e) {
			System.err.println("Erro ao executar Query: " + sql + "\n");
			e.printStackTrace();
		}
	}
	
	public static List<String> lerResult(String... colunas) {
		List<String> valores = new ArrayList<>();
		
		try {
			while (result.next()) {
				for (String s : colunas) {
					valores.add(result.getString(s));
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao recuperar colunas do ResultSet");
			e.printStackTrace();
			return null;
		}
		
		return valores;
	}
	
}
