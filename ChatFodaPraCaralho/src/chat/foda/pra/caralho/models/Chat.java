package chat.foda.pra.caralho.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import chat.foda.pra.caralho.jdbc.Entidade;

public class Chat implements Entidade, Serializable {
	private static final long serialVersionUID = -6861975820907169849L;
	
	private Long codigo;
	
	private Set<Usuario> usuarios;
	
	public Chat() {
		
	}
	
	public Chat(Long codigo) {
		this.codigo = codigo;
	}
	
	public void adicionaUsuario(Usuario usuario) {
		if (this.usuarios == null) {
			usuarios = new HashSet<Usuario>();
		}
		
		usuarios.add(usuario);
	}
	
	public boolean removeUsuario(Usuario usuario) {
		if (this.usuarios == null) {
			return false;
		}
		
		usuarios.remove(usuario);
		return true;
	}
	
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@Override
	public Long getCodigo() {
		return codigo;
	}
	
	@Override
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
}
