package chat.foda.pra.caralho.telas;

import java.util.Scanner;

public class AutorizacaoBanco {

	private String usuario;
	private String senha;

	public AutorizacaoBanco() {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Usuario: ");
		this.usuario = in.nextLine();

		System.out.print("Senha: ");
		this.senha = in.nextLine();
		
		in.close();
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
