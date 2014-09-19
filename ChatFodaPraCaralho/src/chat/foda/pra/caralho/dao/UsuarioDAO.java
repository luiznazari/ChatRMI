package chat.foda.pra.caralho.dao;

import chat.foda.pra.caralho.models.Usuario;

public interface UsuarioDAO extends CrudDAO<Usuario> {
	
	Usuario findOneByEmail(String email);
	
}
