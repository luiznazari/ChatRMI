package chat.foda.pra.caralho.dao.factory;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;

/**
 * @author Luiz Felipe Nazari
 */
public interface AbstractDaoFactory {
	
	PessoaDAO pessoaDao();
	
	UsuarioDAO usuarioDao();
	
	AmigosDAO amigosDao();
	
}
