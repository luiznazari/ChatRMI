package chat.foda.pra.caralho.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConexaoUtil {
	
	protected static Connection conexao;
	
	static {
		String url = "jdbc:mysql://localhost/chatFodaPraCaralho";
		String usuario = "root";
		String senha = "meuessequeele";
		
		try {
			conexao = DriverManager.getConnection(url, usuario, senha);
			conexao.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Erro ao criar conexão");
			e.printStackTrace();
		}
	}
	
	public static void fechaConexao() {
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro ao fechar conexão");
			e.printStackTrace();
		}
	}
	
	public static Connection getConexao() {
		return conexao;
	}
	
}
