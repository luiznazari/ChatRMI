package chat.foda.pra.caralho.testes.integracao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import chat.foda.pra.caralho.jdbc.ConexaoUtil;

public class ConexaoUtilTeste {
	
	@Test
	public void deveConectarAoMySql() throws SQLException {
		Connection connection = ConexaoUtil.getConexao();
		assertNotNull(connection);
		assertTrue(connection.isValid(0)); // 0 -> Tempo de espera
	}
	
}
