package chat.foda.pra.caralho.dao.factory;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.jdbc.AmigosJDBC;
import chat.foda.pra.caralho.jdbc.PessoaJDBC;
import chat.foda.pra.caralho.jdbc.UsuarioJDBC;

public class JDBCDaoFactory implements AbstractDaoFactory {
	
	@Override
	public PessoaDAO pessoaDao() {
		return new PessoaJDBC();
	}
	
	@Override
	public UsuarioDAO usuarioDao() {
		return new UsuarioJDBC();
	}
	
	@Override
	public AmigosDAO amigosDao() {
		return new AmigosJDBC();
	}
	
}
