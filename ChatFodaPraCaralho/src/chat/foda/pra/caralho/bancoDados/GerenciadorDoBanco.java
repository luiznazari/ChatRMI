package chat.foda.pra.caralho.bancoDados;

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
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				this.nomeBanco);
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

	public Integer getQuantidadeUsuarios() {
		return this.getListaUsuarios().size();
	}

	public void fechar() {
		this.db.close();
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public static void main(String[] args) {
		//GerenciadorDoBanco b = new GerenciadorDoBanco("BancoTeste");
		GerenciadorDoBanco b = new GerenciadorDoBanco("BancoDeDados");
		b.abrir();
		for (Usuario u : b.getListaUsuarios()) {
			System.out.println("nome: " + u.getNomeCompleto() + " senha: " + u.getSenha());
		}
		b.fechar();
	}
}
