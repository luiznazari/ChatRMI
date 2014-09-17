package chat.foda.pra.caralho.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryUtil extends ConexaoUtil {
	
	private static ResultSet result;
	
	private static final String createPessoa = "create table if not exists `chatFodaPraCaralho`.`pessoa`(codigo bigint not null primary key auto_increment, nome_completo varchar(45), cpf varchar(15), data_nascimento date)";
	
	private static final String createUsuario = "create table if not exists `chatFodaPraCaralho`.`usuario`(codigo bigint not null primary key auto_increment, email varchar(45) not null unique key, senha varchar(45) not null, nickname varchar(45), codPessoa bigint not null, foreign key (codpessoa) references pessoa(codigo))";
	
	/**
	 * Cria as tabelas da aplicação caso não existam.
	 */
	public static void criaBaseSeNaoExiste() {
		sql(createPessoa);
		sql(createUsuario);
	}
	
	/**
	 * Executa um comando em SQL que não necessita de parâmetros.
	 * 
	 * @param sql
	 */
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
	
	/**
	 * Executa um comando em SQL que necessite passagem de parâmetros mas não "retornam" como resultado.
	 * Exemplos: INSERT, UPDATE, SAVE
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static PreparedStatement sqlParam(String sql, String... params) {
		try {
			PreparedStatement prepared = conexao.prepareStatement(sql);
			
			for (int i = 1; i <= params.length; i++) {
				prepared.setString(i, params[i - 1]);
			}
			
			prepared.executeUpdate();
			conexao.commit();
			
			return prepared;
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
		
		return null;
	}
	
	/**
	 * Executa um comando em SQL que necessite passagem de parâmetros e retorna o Índice (código) do campo
	 * auto_increment. Utilizado para retornar o novo código salvo de uma entidade.
	 * Exemplos: INSERT, UPDATE, SAVE
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static Long sqlParamReturnKey(String sql, String... params) throws SQLException {
		PreparedStatement stmt = sqlParam(sql, params);
		return getUltimoAutoIncrementDaSessao(stmt, stmt.getResultSet());
	}
	
	/**
	 * Retorna o índex (código) do campo auto_increment da sessão do statement.
	 * 
	 * @param stmt
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	private static Long getUltimoAutoIncrementDaSessao(PreparedStatement stmt, ResultSet result) throws SQLException {
		result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
		
		if (result.next()) {
			return result.getLong(1);
		}
		return null;
	}
	
	/**
	 * Executa um comando em SQL que necessite passagem de parâmetros e retorna dados como resultado.
	 * Exemplos: SELECT
	 * 
	 * @param sql
	 * @param params
	 */
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
	
	/**
	 * Método que realiza a leitura do resultado de uma Query (SELECT). Retorna os valores respectivos às colunas
	 * passadas como parâmetro. Os nomes das colunas precisam ser iguais às da tabela do banco.
	 * 
	 * @param colunas
	 * @return
	 */
	public static HashMap<String, String> lerResult(String... colunas) {
		HashMap<String, String> valores = new HashMap<>();
		
		try {
			while (result.next()) {
				for (String s : colunas) {
					valores.put(s, result.getString(s));
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao recuperar colunas do ResultSet");
			e.printStackTrace();
			return null;
		}
		
		return valores;
	}
	
	/**
	 * Método semelhante ao lerResult. Lê e retorna uma lista de registros
	 * 
	 * @param colunas
	 * @return
	 */
	public static List<HashMap<String, String>> lerAllResult(String... colunas) {
		List<HashMap<String, String>> listaValores = new ArrayList<>();
		
		try {
			while (result.next()) {
				HashMap<String, String> valores = new HashMap<>();
				for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
					valores.put(colunas[i], result.getString(colunas[i]));
				}
				listaValores.add(valores);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao recuperar lista de colunas do ResultSet");
			e.printStackTrace();
			return null;
		}
		
		return listaValores;
	}
}
