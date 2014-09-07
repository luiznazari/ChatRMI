package chat.foda.pra.caralho.dao.factory;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.jdbc.PessoaJDBC;
import chat.foda.pra.caralho.jdbc.UsuarioJDBC;

public class JDBCDaoFactory implements AbstractDaoFactory {
	
	@Override
	public PessoaDAO pessoaDao() {
		return new PessoaJDBC();
	}
	
	@Override
	public UsuarioDAO usuarioDAO() {
		return new UsuarioJDBC();
	}
	
}
