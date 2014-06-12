package chat.foda.pra.caralho.bancoDados;

import chat.foda.pra.caralho.modelo.Usuario;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class GerenciadorDoBanco {

	private ObjectContainer db;
	private String nomeBanco;

	// nomeBanco sem extensão
	public void abrir(String nomeBanco) {
		this.nomeBanco = nomeBanco;
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				nomeBanco + ".db4o");
		this.sair();
	}

	private void abrir() {
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				this.nomeBanco);
	}

	public ObjectSet<Usuario> getListaUsuarios() {
		this.abrir();
		ObjectSet<Usuario> lista = this.db.query(Usuario.class);
		this.sair();
		return lista;
	}

	public Usuario getUsuario(Integer codigo) {
		ObjectSet<Usuario> lista = getListaUsuarios();
		for (Usuario o : lista) {
			if (o.getCodigo().equals(codigo)) {
				return o;
			}
		}

		return null;
	}

	public Usuario getUsuario(String apelido) {
		ObjectSet<Usuario> lista = getListaUsuarios();
		for (Usuario o : lista) {
			if (o.getNickName().equals(apelido)) {
				return o;
			}
		}

		return null;
	}

	public void salvar(Object objeto) {
		this.abrir();
		this.db.store(objeto);
		this.sair();
	}

	public Integer getQuantidadeUsuarios() {
		return this.getListaUsuarios().size();
	}

	public void sair() {
		this.db.close();
	}


	public static void main(String[] args) {
		Usuario u1 = new Usuario("Alessandro", "123");
		Usuario u2 = new Usuario("dfsf", "321");

		GerenciadorDoBanco b = new GerenciadorDoBanco();
		b.abrir("teste");
		b.salvar(u1);
		b.getListaUsuarios();
		b.getQuantidadeUsuarios();
	}
}
