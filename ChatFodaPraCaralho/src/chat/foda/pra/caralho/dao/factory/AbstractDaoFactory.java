package chat.foda.pra.caralho.dao.factory;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;

public interface AbstractDaoFactory {
	
	PessoaDAO pessoaDao();
	
	UsuarioDAO usuarioDAO();
	
}
