package chat.foda.pra.caralho.telas;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;

public class EventosJanela implements WindowListener {

	private ClienteRmi cliente;
	
	public EventosJanela(ClienteRmi cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		try {
			cliente.getService().logout(cliente.getUsuarioLogado());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
