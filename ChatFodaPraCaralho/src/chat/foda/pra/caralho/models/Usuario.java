package chat.foda.pra.caralho.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import chat.foda.pra.caralho.jdbc.Entidade;

public class Usuario extends Pessoa implements Serializable, Entidade {
	private static final long serialVersionUID = -5591108295576221784L;
	
	private Long codigo;
	
	private Pessoa pessoa;
	
	private String senha;
	
	private String nickName;
	
	private Set<String> amigos;
	
	public Usuario() {}
	
	public Usuario(Long codigo) {
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
	
	public Set<String> getAmigos() {
		return amigos;
	}
	
	public void setAmigos(Set<String> amigos) {
		this.amigos = amigos;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
}
