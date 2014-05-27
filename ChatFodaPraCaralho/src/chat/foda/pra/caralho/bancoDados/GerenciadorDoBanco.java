package chat.foda.pra.caralho.bancoDados;

import chat.foda.pra.caralho.clienteServidor.ClienteOffLine;
import chat.foda.pra.caralho.modelo.Chat;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class GerenciadorDoBanco {
	
	private final ObjectContainer bancoDeDados = Db4oEmbedded.openFile(
			Db4oEmbedded.newConfiguration(), "bancoDeDados.db4o");

	public void salva(Object object) {
		bancoDeDados.store(object);
	}

	public ObjectSet<Chat> listarChats() {
		return bancoDeDados.query(Chat.class);
	}
	
	public ObjectSet<ClienteOffLine> listarClientes() {
		return bancoDeDados.query(ClienteOffLine.class);
	}

	public void sair() {
		bancoDeDados.close();
	}

	public Chat getChatPorCodigo(Integer codigo) {
		Chat chat = new Chat();
		chat.setCodigo(codigo);
		return (Chat) bancoDeDados.queryByExample(chat).get(0);
	}

	public static void main(String[] args) {
		GerenciadorDoBanco main = new GerenciadorDoBanco();
		try {
			for(Chat c : main.listarChats()) {
				System.out.println(c.getCodigo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			main.sair();
		} finally {
			main.sair();
		}
	}
}
