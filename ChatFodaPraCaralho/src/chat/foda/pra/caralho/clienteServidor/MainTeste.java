package chat.foda.pra.caralho.clienteServidor;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

public class MainTeste {
	
	public static void main(String[] args) {
		PessoaDAO pessoaDao = DaoFactory.get().pessoaDao();
		
		for (Pessoa p : pessoaDao.findAll()) {
			System.out.println(p.getCodigo() + " | " + p.getNomeCompleto());
		}
		
		UsuarioDAO usuarioDao = DaoFactory.get().usuarioDAO();
		
		for (Usuario u : usuarioDao.findAll()) {
			System.out.println(u.getCodigo() + " | " + u.getEmail() + " | " + u.getPessoa().getCodigo() + " | "
			        + u.getPessoa().getNomeCompleto());
		}
	}
	
}
