package chat.foda.pra.caralho.modelo;

public abstract class Pessoa {

	private Integer codigo;

	private String cpf;
	private String email;
	private Integer idade;
	private String telefone;
	private String nomeCompleto;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getPrimeiroNome() {
		try {
			return this.nomeCompleto.substring(0, nomeCompleto.indexOf(" "));
		} catch (StringIndexOutOfBoundsException e) {
			return getNomeCompleto();
		}
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
}