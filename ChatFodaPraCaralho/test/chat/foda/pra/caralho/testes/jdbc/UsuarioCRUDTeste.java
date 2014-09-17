package chat.foda.pra.caralho.testes.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

public class UsuarioCRUDTeste {
	
	private static UsuarioDAO usuarioDAO;
	
	private static PessoaDAO pessoaDAO;
	
	private Usuario usuario;
	
	private Pessoa pessoa;
	
	@BeforeClass
	public static void criaConexaoBanco() {
		usuarioDAO = DaoFactory.get().usuarioDAO();
		pessoaDAO = DaoFactory.get().pessoaDao();
	}
	
	@Before
	public void criaAmbiente() {
		pessoa = new Pessoa();
		pessoa.setNomeCompleto("Pessoa Teste 1");
		pessoa.setCpf("123456");
		pessoa.setDataNascimento(LocalDate.now().minusYears(1));
		
		usuario = new Usuario();
		usuario.setSenha("123");
		usuario.setNickName("Nick Teste 1");
		usuario.setEmail("usuario1_@teste.com");
		usuario.setPessoa(pessoa);
	}
	
	@After
	public void removeAmbiente() {
		usuarioDAO.delete(usuario);
	}
	
	/**
	 * Método para testar a inserção de um novo usuário no banco
	 * Se ocorrer erro no processo, não pode salvar os dados do Usuário nem da Pessoa relacionada ao usuário
	 */
	@Test
	public void salvaPessoaSemSalvarUsuario() {
		usuario.setEmail(null); // Vai causar erro ao salvar usuário após salvar pessoa
		
		usuarioDAO.save(usuario);
		
		assertNull(usuario.getCodigo());
		assertNull(pessoaDAO.findOne(usuario.getPessoa().getCodigo()));
	}
	
	/**
	 * Salva pessoa e usuário corretamente no banco de dados
	 */
	@Test
	public void salvaPessoaEUsuario() {
		usuarioDAO.save(usuario);
		
		assertNotNull(usuario.getCodigo());
		assertNotNull(usuario.getPessoa().getCodigo());
	}
	
	@Test
	public void deletaPessoaEUsuario() {
		usuarioDAO.save(usuario);
		
		usuarioDAO.delete(usuario);
		
		assertNull(pessoaDAO.findOne(pessoa.getCodigo()));
		assertNull(usuarioDAO.findOne(usuario.getCodigo()));
	}
}
