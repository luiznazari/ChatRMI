package chat.foda.pra.caralho.jdbc;

import java.sql.SQLException;
import java.util.List;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

public class UsuarioJDBC implements UsuarioDAO {
	
	private final String findByCodigo = "select * from usuario where codigo = ?";
	
	private final String findByNomePessoa = "select * from usuario u join pessoa p on u.codpessoa = p.codigo where p.nome_completo = ?";
	
	private final String save = "insert into usuario(email, senha, nickname, codPessoa) values (?, ?, ?, ?)";
	
	private final String update = "update usuario set email = ?, senha = ?, nickname = ?, codPessoa = ? where codigo = ?";
	
	private final String deleteByCodigo = "delete from usuario where codigo = ?";
	
	private PessoaDAO pessoaDAO;
	
	public UsuarioJDBC() {
		pessoaDAO = DaoFactory.get().pessoaDao();
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Usuario findOne(Long codigo) {
		QueryUtil.queryParam(findByCodigo, codigo.toString());
		List<String> valores;
		
		valores = QueryUtil.lerResult("email", "senha", "nickname", "codpessoa");
		if (valores.size() == 0) {
			return null;
		}
		
		Pessoa pessoa = pessoaDAO.findOne(Long.valueOf(valores.get(3)));
		
		Usuario usuario = new Usuario(codigo);
		usuario.setEmail(valores.get(0));
		usuario.setSenha(valores.get(1));
		usuario.setNickName(valores.get(2));
		usuario.setPessoa(pessoa);
		
		return usuario;
	}
	
	@Override
	public Usuario findOneByNomePessoa(String nomePessoa) {
		QueryUtil.queryParam(findByNomePessoa, nomePessoa);
		List<String> valores;
		
		valores = QueryUtil.lerResult("codigo", "email", "senha", "nickname", "codpessoa");
		if (valores.size() == 0) {
			return null;
		}
		
		Pessoa pessoa = pessoaDAO.findOne(Long.valueOf(valores.get(4)));
		
		Usuario usuario = new Usuario(Long.valueOf(valores.get(0)));
		usuario.setEmail(valores.get(1));
		usuario.setSenha(valores.get(2));
		usuario.setNickName(valores.get(3));
		usuario.setPessoa(pessoa);
		
		return usuario;
	}
	
}
