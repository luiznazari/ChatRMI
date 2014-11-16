package chat.foda.pra.caralho.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import chat.foda.pra.caralho.jdbc.Entidade;

public class Usuario implements Serializable, Entidade {
	private static final long serialVersionUID = -5591108295576221784L;
	
	private Long codigo;
	
	private Pessoa pessoa;
	
	private String senha;
	
	private String nickName;
	
	private String email;
	
	private Set<Usuario> amigos;
	
	public Usuario() {}
	
	public Usuario(Long codigo) {
		this.codigo = codigo;
	}
	
	public Usuario(Long codigo, String nickname) {
		this.codigo = codigo;
		this.nickName = nickname;
	}
	
	public void adicionaAmigo(Usuario usuario) {
		if (this.amigos == null) {
			this.amigos = new HashSet<Usuario>();
		}
		
		this.amigos.add(usuario);
	}
	
	public boolean removeAmigo(Usuario usuario) {
		if (this.amigos == null) {
			return false;
		}
		
		this.amigos.remove(usuario);
		return true;
	}
	
	@Override
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public Long getCodigo() {
		return codigo;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNickName() {
		if (this.nickName == null) {
			return pessoa.getPrimeiroNome();
		}
		
		return this.nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Set<Usuario> getAmigos() {
		if (amigos == null) {
			amigos = new HashSet<Usuario>();
		}
		return amigos;
	}
	
	public void setAmigos(Set<Usuario> amigos) {
		this.amigos = amigos;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return this.getNickName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Usuario)) {
			return false;
		}
		
		Usuario toCompare = ( Usuario ) obj;
		
		return this.getCodigo().equals(toCompare.getCodigo());
	}
	
	@Override
	public int hashCode() {
		return 31 * codigo.hashCode();
	}
}
