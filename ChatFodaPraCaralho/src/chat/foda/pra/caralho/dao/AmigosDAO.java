package chat.foda.pra.caralho.dao;

import java.util.Set;

import chat.foda.pra.caralho.models.Usuario;

public interface AmigosDAO {
	
	void save(Long codUsuario, Long codAmigo);
	
	void deleteAll(Long codUsuario);
	
	void deleteOne(Long codUsuario, Long codAmigo);
	
	/**
	 * Retorna um usuário (amigo) com apenas o código, código e nome da pessoa
	 * 
	 * @param codUsuario
	 * @return
	 */
	Set<Usuario> findAllByCodUsuario(Long codUsuario);
	
}
