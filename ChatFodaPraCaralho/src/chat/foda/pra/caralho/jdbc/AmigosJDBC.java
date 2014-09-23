package chat.foda.pra.caralho.jdbc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;

public class AmigosJDBC implements AmigosDAO {
	
	private final String save = "insert into amigos values (?, ?)";
	
	private final String deleteAll = "delete from amigos where codUsuario = ?";
	
	private final String deleteOne = "delete from amigos where codUsuario = ? and codAmigo = ?";
	
	private final String findAllByCodUsuario = "select u.codigo, u.codPessoa , p.nome_completo from usuario u inner join pessoa p on u.codPessoa = p.codigo where u.codigo in (select codAmigo from amigos where codUsuario = ?)";
	
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
	public Set<Usuario> findAllByCodUsuario(Long codUsuario) {
		Set<Usuario> amigos = new HashSet<>();
		
		QueryUtil.queryParam(findAllByCodUsuario, codUsuario.toString());
		
		List<HashMap<String, String>> listaValores = QueryUtil.lerAllResult("codigo", "codPessoa", "nome_completo");
		for (HashMap<String, String> val : listaValores) {
			amigos.add(criaAmigo(val));
		}
		
		return amigos;
	}
	
	private Usuario criaAmigo(HashMap<String, String> valores) {
		Usuario usuario = new Usuario(Long.valueOf(valores.get("codigo")));
		usuario.setPessoa(new Pessoa(Long.valueOf(valores.get("codPessoa"))));
		usuario.getPessoa().setNomeCompleto(valores.get("nome_completo"));
		
		return usuario;
	}
	
}
