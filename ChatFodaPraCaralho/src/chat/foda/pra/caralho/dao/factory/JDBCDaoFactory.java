package chat.foda.pra.caralho.dao.factory;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.jdbc.PessoaJDBC;

public class JDBCDaoFactory implements AbstractDaoFactory {
	
	@Override
	public PessoaDAO pessoaDao() {
		return new PessoaJDBC();
	}
	
}
