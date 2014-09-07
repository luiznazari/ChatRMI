package chat.foda.pra.caralho.dao.factory;

import chat.foda.pra.caralho.dao.PessoaDAO;

public class DaoFactory {
	
	private static DaoFactory factory;
	
	private static AbstractDaoFactory daoFactory;
	
	public static DaoFactory get() {
		if (factory == null) {
			factory = new DaoFactory();
		}
		
		daoFactory = new JDBCDaoFactory();
		return factory;
	}
	
	public PessoaDAO pessoaDao() {
		return daoFactory.pessoaDao();
	}
	
}
