package chat.foda.pra.caralho.bancoDados;

import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oRecoverableException;

import chat.foda.pra.caralho.modelo.Usuario;

public class GerenciadorDoBanco {

	private ObjectContainer db;
	private String nomeBanco;

	public GerenciadorDoBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco + ".db4o";
	}
	
	public void abrir() {
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), this.nomeBanco);
	}

	public void fechar() {
		this.db.close();
	}
	
	public ObjectSet<Usuario> getListaUsuarios() {
		try {
			ObjectSet<Usuario> lista = db.query(Usuario.class);	
			return lista;
		} catch(Db4oRecoverableException e) {
			return null;
		}
	}

	public Usuario getUsuario(Integer codigo) {
		try {
			Usuario usuario = new Usuario(codigo);
			ObjectSet<Usuario> usuarioBanco = db.queryByExample(usuario);
			return usuarioBanco.get(0);
		} catch(Db4oRecoverableException e) {
			return null;
		}
	}

	public Usuario getUsuario(String nome) {
		try {
			Usuario usuario = new Usuario();
			usuario.setNomeCompleto(nome);
			ObjectSet<Usuario> usuarioBanco = db.queryByExample(usuario);
			return usuarioBanco.get(0);
		} catch(Db4oRecoverableException e) {
			return null;
		}
	}

	public void salvar(Object objeto) {
		this.db.store(objeto);
	}
	
	public void remover(Object objeto) {
		this.db.delete(objeto);
	}
	
	public Integer getQuantidadeUsuarios() {
		return this.getListaUsuarios().size();
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}
	
	public void imprimeDadosUsuario(Usuario usuario) {
		System.out.println("Nome: " + usuario.getNomeCompleto() + " | Senha: " + usuario.getSenha());
		System.out.println("Número de amigos: "+ usuario.getAmigos().size());
	}
	
	public void imprimeDadosUsuarios() {
		for (Usuario usuario : getListaUsuarios()) {
			System.out.println("Nome: " + usuario.getNomeCompleto() + " | Senha: " + usuario.getSenha());
			try {
				System.out.println("Número de amigos: "+ usuario.getAmigos().size());
				for (String nome : usuario.getAmigos()) {
					System.out.print(nome + " ");
				}
				System.out.println("\n");
			} catch (NullPointerException e) {
				System.out.println("Número de amigos: " + 0 + "\n");
			}
		}
	}
	
	public void populaBanco() {		
		Usuario u1 = new Usuario("Luiz", "123");
		Usuario u2 = new Usuario("Alessandro", "123");
		Usuario u3 = new Usuario("Usuario001", "123");
		Usuario u4 = new Usuario("Usuario002", "123");
		
		u1.adicionaAmigo(u2.getNomeCompleto());
		u2.adicionaAmigo(u1.getNomeCompleto());
		u4.adicionaAmigo(u1.getNomeCompleto());
		u4.adicionaAmigo(u2.getNomeCompleto());
		u4.adicionaAmigo(u3.getNomeCompleto());
		
		salvar(u1);
		salvar(u2);
		salvar(u3);
		salvar(u4);
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("0 - BancoTeste | 1 - BancoDeDados");
		Integer escolha = Integer.valueOf(scan.nextLine());
		switch (escolha) {
			case 0: {
				GerenciadorDoBanco bancoTeste = new GerenciadorDoBanco("BancoTeste");
				
				bancoTeste.abrir();
				//bancoTeste.populaBanco();
				Usuario u1 = bancoTeste.getUsuario("Luiz");
				Usuario u2 = bancoTeste.getUsuario("Alessandro");				
				Usuario u3 = bancoTeste.getUsuario("Usuario001");
				Usuario u4 = bancoTeste.getUsuario("Usuario002");
				bancoTeste.fechar();
				
				bancoTeste.abrir();
				bancoTeste.imprimeDadosUsuarios();
				bancoTeste.fechar();
				
				break;
			}
			case 1: {
				GerenciadorDoBanco b = new GerenciadorDoBanco("BancoDeDados");
				b.abrir();
				//b.populaBanco();
				b.imprimeDadosUsuarios();
				b.fechar();
				break;
			}
		}
		
		scan.close();
	}
}
