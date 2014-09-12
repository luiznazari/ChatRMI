package chat.foda.pra.caralho.dao;

import java.util.List;

import chat.foda.pra.caralho.jdbc.Entidade;

public interface CrudDAO<T extends Entidade> {
	
	T save(T entidade);
	
	void delete(T entidade);
	
	void update(T entidade);
	
	List<T> findAll();
	
	T findOne(Long codigo);
	
}
