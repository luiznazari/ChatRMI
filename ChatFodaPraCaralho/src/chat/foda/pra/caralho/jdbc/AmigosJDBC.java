package chat.foda.pra.caralho.jdbc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

/**
 * @author Luiz Felipe Nazari
 */
public class AmigosJDBC implements AmigosDAO {
	
	private final String save = "insert into amigos values (?, ?)";
	
	private final String deleteAll = "delete from amigos where codUsuario = ?";
	
	private final String deleteOne = "delete from amigos where codUsuario = ? and codAmigo = ?";
	
	private final String findOne = "select u.codigo, u.nickname, u.codPessoa , p.nome_completo from usuario u inner join pessoa p on u.codPessoa = p.codigo where u.codigo = ?";
	
	private final String findAllByCodUsuario = "select u.codigo, u.nickname, u.codPessoa , p.nome_completo from usuario u inner join pessoa p on u.codPessoa = p.codigo where u.codigo in (select codAmigo from amigos where codUsuario = ?)";
	
	private final String findAllDesconhecidos = "select u.codigo, u.nickname, u.codPessoa , p.nome_completo from usuario u inner join pessoa p on u.codPessoa = p.codigo where u.codigo not in (select codAmigo from amigos where codUsuario = ?) and u.codigo != ?";
	
	@Override
	public void save(Long codUsuario, Long codAmigo) {
		QueryUtil.sqlParam(save, codUsuario.toString(), codAmigo.toString());
	}
	
	@Override
	public void deleteAll(Long codUsuario) {
		QueryUtil.sqlParam(deleteAll, codUsuario.toString());
		
	}
	
	@Override
	public void deleteOne(Long codUsuario, Long codAmigo) {
		QueryUtil.sqlParam(deleteOne, codUsuario.toString(), codAmigo.toString());
	}
	
	@Override
	public Usuario findOne(Long codAmigo) {
		QueryUtil.queryParam(findOne, codAmigo.toString());
		
		return criaAmigo(QueryUtil.lerResult("codigo", "nickname", "codPessoa", "nome_completo"));
	}
	
	@Override
	public Set<Usuario> findAllByCodUsuario(Long codUsuario) {
		return criaSetAmigos(findAllByCodUsuario, codUsuario.toString());
	}
	
	@Override
	public Set<Usuario> findAllDesconhecidosByCodUsiario(Long codUsuario) {
		return criaSetAmigos(findAllDesconhecidos, codUsuario.toString(), codUsuario.toString());
	}
	
	private Set<Usuario> criaSetAmigos(String sql, String... param) {
		Set<Usuario> amigos = new HashSet<>();
		
		QueryUtil.queryParam(sql, param);
		
		List<HashMap<String, String>> listaValores = QueryUtil.lerAllResult("codigo", "nickname", "codPessoa", "nome_completo");
		for (HashMap<String, String> val : listaValores) {
			amigos.add(criaAmigo(val));
		}
		
		return amigos;
	}
	
	private Usuario criaAmigo(HashMap<String, String> valores) {
		Usuario usuario = new Usuario(Long.valueOf(valores.get("codigo")));
		usuario.setNickName(valores.get("nickname"));
		usuario.setPessoa(new Pessoa(Long.valueOf(valores.get("codPessoa"))));
		usuario.getPessoa().setNomeCompleto(valores.get("nome_completo"));
		
		return usuario;
	}
	
}
