package chat.foda.pra.caralho.testes.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

public class UsuarioCRUDTeste {
	
	private static UsuarioDAO usuarioDAO;
	
	private static PessoaDAO pessoaDAO;
	
	private static AmigosDAO amigosDAO;
	
	private Usuario usuario;
	
	private Usuario usuarioAmigo1;
	
	private Usuario usuarioAmigo2;
	
	private Pessoa pessoa;
	
	@BeforeClass
	public static void criaConexaoBanco() {
		usuarioDAO = DaoFactory.get().usuarioDao();
		pessoaDAO = DaoFactory.get().pessoaDao();
		amigosDAO = DaoFactory.get().amigosDao();
	}
	
	@Before
	public void criaAmbiente() {
		pessoa = new Pessoa();
		pessoa.setNomeCompleto("Pessoa Teste 1");
		pessoa.setDataNascimento(LocalDate.now().minusYears(1));
		
		usuario = new Usuario();
		usuario.setSenha("123");
		usuario.setNickName("Nick Teste 1");
		usuario.setEmail("usuario1_@teste.com");
		usuario.setPessoa(pessoa);
		
		Pessoa pessoaAmiga1 = new Pessoa();
		pessoaAmiga1.setNomeCompleto("Pessoa Amiga Teste 1");
		
		usuarioAmigo1 = new Usuario();
		usuarioAmigo1.setEmail("usuarioAmigo1_@teste.com");
		usuarioAmigo1.setSenha("123");
		usuarioAmigo1.setPessoa(pessoaAmiga1);
		
		usuarioDAO.save(usuarioAmigo1);
		
		usuario.adicionaAmigo(usuarioAmigo1);
	}
	
	public void criaOutroAmigo() {
		Pessoa pessoaAmiga2 = new Pessoa();
		pessoaAmiga2.setNomeCompleto("Pessoa Amiga Teste 2");
		
		usuarioAmigo2 = new Usuario();
		usuarioAmigo2.setEmail("usuarioAmigo2_@teste.com");
		usuarioAmigo2.setSenha("123");
		usuarioAmigo2.setPessoa(pessoaAmiga2);
		
		usuarioDAO.save(usuarioAmigo2);
		
		usuario.adicionaAmigo(usuarioAmigo2);
	}
	
	@After
	public void removeAmbiente() {
		usuarioDAO.delete(usuario);
		usuarioDAO.delete(usuarioAmigo1);
		if (usuarioAmigo2 != null) {
			usuarioDAO.delete(usuarioAmigo2);
		}
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
	
	@Test
	public void deletaPessoaEUsuarioEAmigos() {
		usuarioDAO.save(usuario);
		amigosDAO.save(usuario.getCodigo(), usuarioAmigo1.getCodigo());
		
		usuarioDAO.delete(usuario);
		
		assertNull(pessoaDAO.findOne(usuario.getPessoa().getCodigo()));
		assertNull(usuarioDAO.findOne(usuario.getCodigo()));
		assertEquals(0, amigosDAO.findAllByCodUsuario(usuario.getCodigo()).size());
	}
	
	@Test
	public void salvaERetornaAmigos() {
		usuarioDAO.save(usuario);
		criaOutroAmigo();
		amigosDAO.save(usuario.getCodigo(), usuarioAmigo1.getCodigo());
		amigosDAO.save(usuario.getCodigo(), usuarioAmigo2.getCodigo());
		
		Usuario usuarioDoBanco = usuarioDAO.findOne(usuario.getCodigo());
		
		assertEquals(2, usuarioDoBanco.getAmigos().size());
		
		Map<Long, Usuario> amigosDoUsuarioDoBanco = new HashMap<>();
		// Precisa ter um for, pois o HashSet não tem um .get
		for (Usuario u : usuarioDoBanco.getAmigos()) {
			amigosDoUsuarioDoBanco.put(u.getCodigo(), u);
		}
		
		Usuario toTest = amigosDoUsuarioDoBanco.get(usuarioAmigo1.getCodigo());
		assertEquals(usuarioAmigo1.getCodigo(), toTest.getCodigo());
		assertEquals(usuarioAmigo1.getPessoa().getCodigo(), toTest.getPessoa().getCodigo());
		assertEquals(usuarioAmigo1.getPessoa().getNomeCompleto(), toTest.getPessoa().getNomeCompleto());
		
		toTest = amigosDoUsuarioDoBanco.get(usuarioAmigo2.getCodigo());
		assertEquals(usuarioAmigo2.getCodigo(), toTest.getCodigo());
		assertEquals(usuarioAmigo2.getPessoa().getCodigo(), toTest.getPessoa().getCodigo());
		assertEquals(usuarioAmigo2.getPessoa().getNomeCompleto(), toTest.getPessoa().getNomeCompleto());
	}
}
