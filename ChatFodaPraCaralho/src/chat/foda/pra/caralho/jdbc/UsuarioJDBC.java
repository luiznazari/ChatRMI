package chat.foda.pra.caralho.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

/**
 * @author Luiz Felipe Nazari
 */
public class UsuarioJDBC implements UsuarioDAO {
	
	private final String findAll = "select * from usuario where codigo > ?";
	
	private final String findByCodigo = "select * from usuario where codigo = ?";
	
	private final String findByEmail = "select * from usuario u where u.email = ?";
	
	private final String save = "insert into usuario(email, senha, nickname, codPessoa) values (?, ?, ?, ?)";
	
	private final String update = "update usuario set email = ?, senha = ?, nickname = ?, codPessoa = ? where codigo = ?";
	
	private final String deleteByCodigo = "delete from usuario where codigo = ?";
	
	private final PessoaDAO pessoaDAO;
	
	private final AmigosDAO amigosDAO;
	
	public UsuarioJDBC() {
		pessoaDAO = DaoFactory.get().pessoaDao();
		amigosDAO = DaoFactory.get().amigosDao();
	}
	
	@Override
	public Usuario save(Usuario usuario) {
		Pessoa pessoa = null;
		
		try {
			pessoa = pessoaDAO.save(usuario.getPessoa());
			
			Long codigo = QueryUtil.sqlParamReturnKey(save, usuario.getEmail(), usuario.getSenha(), usuario.getNickName(),
					usuario.getPessoa().getCodigo().toString());
			
			usuario.setCodigo(codigo);
		} catch (SQLException | NullPointerException e) {
			pessoaDAO.delete(pessoa);
		}
		
		return usuario;
	}
	
	@Override
	public void delete(Usuario usuario) {
		if (usuario.getCodigo() != null) {
			amigosDAO.deleteAll(usuario.getCodigo());
			
			QueryUtil.sqlParam(deleteByCodigo, usuario.getCodigo().toString());
			
			pessoaDAO.delete(usuario.getPessoa());
		}
		
	}
	
	@Override
	public void update(Usuario usuario) {
		QueryUtil.sqlParam(update, usuario.getEmail(), usuario.getSenha(), usuario.getNickName(), usuario.getPessoa().getCodigo()
				.toString(), usuario.getCodigo().toString());
		
		pessoaDAO.update(usuario.getPessoa());
	}
	
	@Override
	public List<Usuario> findAll() {
		List<Usuario> usuarios = new ArrayList<>();
		QueryUtil.queryParam(findAll, "0");
		
		List<HashMap<String, String>> listaValores = QueryUtil.lerAllResult("codigo", "email", "senha", "nickname", "codPessoa");
		for (HashMap<String, String> valores : listaValores) {
			usuarios.add(criaUsuario(valores));
		}
		
		return usuarios;
	}
	
	@Override
	public Usuario findOne(Long codigo) {
		QueryUtil.queryParam(findByCodigo, codigo.toString());
		
		HashMap<String, String> valores;
		
		valores = QueryUtil.lerResult("codigo", "email", "senha", "nickname", "codPessoa");
		if (valores.size() == 0) {
			return null;
		}
		
		return criaUsuario(valores);
	}
	
	@Override
	public Usuario findOneByEmail(String email) {
		QueryUtil.queryParam(findByEmail, email);
		
		HashMap<String, String> valores;
		
		valores = QueryUtil.lerResult("codigo", "email", "senha", "nickname", "codPessoa");
		if (valores.size() == 0) {
			return null;
		}
		
		return criaUsuario(valores);
	}
	
	private Usuario criaUsuario(HashMap<String, String> valores) {
		Pessoa pessoa = pessoaDAO.findOne(Long.valueOf(valores.get("codPessoa")));
		
		Long codUsuario = Long.valueOf(valores.get("codigo"));
		
		Usuario usuario = new Usuario(codUsuario);
		usuario.setEmail(valores.get("email"));
		usuario.setSenha(valores.get("senha"));
		usuario.setNickName(valores.get("nickname"));
		usuario.setPessoa(pessoa);
		
		usuario.setAmigos(amigosDAO.findAllByCodUsuario(codUsuario));
		
		return usuario;
	}
	
}
