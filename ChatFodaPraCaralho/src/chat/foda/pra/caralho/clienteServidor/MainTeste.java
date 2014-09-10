package chat.foda.pra.caralho.clienteServidor;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;

public class MainTeste {
	
	public static void main(String[] args) {
		PessoaDAO dao = DaoFactory.get().pessoaDao();
		
		Pessoa pessoa = dao.findOne(7l);
		pessoa.getCodigo();
		
	}
	
}
