package chat.foda.pra.caralho.jdbc;

import java.util.List;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

public class UsuarioJDBC implements UsuarioDAO {
	
	private final String findAll = "select * from usuario";
	
	private final String findByCodigo = "select * from usuario where codigo = ?";
	
	private final String findByNomePessoa = "select * from usuario u join pessoa p on u.codpessoa = p.codigo where p.nomeCompleto = ?";
	
	private final String save = "insert into usuario(senha, nickname, codPessoa) values (?, ?, ?)";
	
	private final String update = "update pessoa set senha = ?, nickname = ?, codPessoa = ? where codigo = ?";
	
	private final String deleteByCodigo = "delete from usuario where codigo = ?";
	
	private PessoaDAO pessoaDAO;
	
	public UsuarioJDBC() {
		pessoaDAO = DaoFactory.get().pessoaDao();
	}
	
	@Override
	public void save(Usuario entidade) {
		QueryUtil.sqlParam(save, entidade.getSenha(), entidade.getNickName(), entidade.getPessoa().getCodigo().toString());
		
		pessoaDAO.save(entidade.getPessoa());
	}
	
	@Override
	public void delete(Usuario entidade) {
		QueryUtil.sqlParam(deleteByCodigo, entidade.getCodigo().toString());
		
		pessoaDAO.delete(entidade.getPessoa());
		
	}
	
	@Override
	public void update(Usuario entidade) {
		QueryUtil.sqlParam(update, entidade.getSenha(), entidade.getNickName(), entidade.getPessoa().getCodigo().toString(),
		        entidade.getCodigo().toString());
		
		pessoaDAO.update(entidade.getPessoa());
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
		
		valores = QueryUtil.lerResult("senha", "nickname", "codpessoa");
		if (valores.size() == 0) {
			return null;
		}
		
		Pessoa pessoa = pessoaDAO.findOne(Long.valueOf(valores.get(2)));
		
		Usuario usuario = new Usuario(codigo);
		usuario.setSenha(valores.get(0));
		usuario.setNickName(valores.get(1));
		usuario.setPessoa(pessoa);
		
		return usuario;
	}
	
	@Override
	public Usuario findOneByNomePessoa(String nomePessoa) {
		QueryUtil.queryParam(findByNomePessoa, nomePessoa);
		List<String> valores;
		
		valores = QueryUtil.lerResult("codigo", "senha", "nickname", "codpessoa");
		if (valores.size() == 0) {
			return null;
		}
		
		Pessoa pessoa = pessoaDAO.findOne(Long.valueOf(valores.get(3)));
		
		Usuario usuario = new Usuario(Long.valueOf(valores.get(0)));
		usuario.setSenha(valores.get(1));
		usuario.setNickName(valores.get(2));
		usuario.setPessoa(pessoa);
		
		return usuario;
	}
	
}
