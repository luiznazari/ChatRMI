package chat.foda.pra.caralho.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import chat.foda.pra.caralho.telas.TelaAutorizacaoBanco;

public abstract class ConexaoUtil {

	protected static Connection conexao;

	static {
		if (!Log.existeLog()) {
			TelaAutorizacaoBanco auth = new TelaAutorizacaoBanco();
			Log.criarArqLog();
			Log.escrever(auth.getUsuario());
			Log.escrever(auth.getSenha());
		}

		String url = "jdbc:mysql://localhost/chatFodaPraCaralho", usuario = Log
				.ler()[0], senha = Log.ler()[1];

		try {
			conexao = DriverManager.getConnection(url, usuario, senha);
			conexao.setAutoCommit(false);
		} catch (SQLException e) {

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
