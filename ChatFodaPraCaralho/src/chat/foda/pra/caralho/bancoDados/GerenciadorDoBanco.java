package chat.foda.pra.caralho.bancoDados;

import java.util.ArrayList;

import chat.foda.pra.caralho.modelo.Usuario;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class GerenciadorDoBanco {

	private ObjectContainer db;

	// nomeBanco sem extensão
	public void abrir(String nomeBanco) {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), nomeBanco
				+ ".db4o");
	}

	public ObjectSet<Usuario> getListaUsuarios() {
		return db.query(Usuario.class);
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

	public void armazenar(Object objeto) {
		db.store(objeto);
	}

	public void sair() {
		db.close();
	}

}
