package chat.foda.pra.caralho.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Usuario extends Pessoa implements Serializable {
	private static final long serialVersionUID = -5591108295576221784L;
	
	private Integer codigo;
	private String senha;
	private String nickName;
	private Set<String> amigos;

	public Usuario(){		
	}
	
	public Usuario(Integer codigo){
		this.codigo = codigo;
	}
	
	public Usuario(String nomeCompleto, String senha) {
		this.setNomeCompleto(nomeCompleto);
		this.setSenha(senha);
	}
	
	public void adicionaAmigo(String nome) {
		if (this.amigos == null) {
			this.amigos = new HashSet<String>();
		}
		
		this.amigos.add(nome);
	}
	
	public boolean removeAmigo(String nome) {
		if (this.amigos == null) {
			return false;
		}
		
		this.amigos.remove(nome);
		return true;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Integer getCodigo() {
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
			return super.getPrimeiroNome();
		}
		
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Set<String> getAmigos() {
		return amigos;
	}

	public void setAmigos(Set<String> amigos) {
		this.amigos = amigos;
	}
}
