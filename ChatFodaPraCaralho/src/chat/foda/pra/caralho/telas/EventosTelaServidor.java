package chat.foda.pra.caralho.telas;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.clienteServidor.ServidorRmi;

public class EventosTelaServidor implements WindowListener {

	private ServidorRmi servidor;

	public EventosTelaServidor(TelaServidor telaServidor) {
		this.servidor = telaServidor.getServidor();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		try {
			this.servidor.getService().enviarMensagemParaTodosClientes("msg");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
