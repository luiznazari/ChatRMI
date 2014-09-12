package chat.foda.pra.caralho.models;

import java.io.Serializable;

import java.time.LocalDate;

import chat.foda.pra.caralho.jdbc.Entidade;

public class Pessoa implements Entidade, Serializable {
	private static final long serialVersionUID = 6514736075986784511L;
	
	private Long codigo;
	
	private String cpf;
	
	private LocalDate dataNascimento;
	
	private String nomeCompleto;
	
	public Pessoa() {}
	
	public Pessoa(Long codigo) {
		this.codigo = codigo;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getPrimeiroNome() {
		try {
			return this.nomeCompleto.substring(0, nomeCompleto.indexOf(" "));
		} catch (StringIndexOutOfBoundsException | NullPointerException e) {
			return getNomeCompleto();
		}
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
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