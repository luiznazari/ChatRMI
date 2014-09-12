package chat.foda.pra.caralho.telas;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class EventosTelaServidor implements WindowListener {
	
	private TelaServidor telaServidor;
	
	public EventosTelaServidor(TelaServidor telaServidor) {
		this.telaServidor = telaServidor;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		try {
			Timer timer = new Timer();
			System.out.println();
			this.telaServidor.getServidor().getService()
			        .enviarMensagemParaTodosClientes("Avisos do Servidor: O servidor será encerrado dentro de 30 segundos.");
			this.telaServidor.escreverNoConsole("[" + LocalTime.now().toString()
			        + "] - Iniciada contagem para encerrar serviços.");
			
			timer.scheduleAtFixedRate(new TimerTask() {
				
				private int contador = 10;
				
				@Override
				public void run() {
					try {
						telaServidor.getServidor().getService()
						        .enviarMensagemParaTodosClientes("Avisos do Servidor: Encerrando em: " + contador);
						telaServidor.escreverNoConsole("[" + LocalTime.now().toString() + "] - Encerrando em: " + contador--);
						
						if (contador < 0) {
							telaServidor.getServidor().getService().encerrarServicos();
							System.exit(0);
						}
					} catch (RemoteException e) {
						e.printStackTrace();
						telaServidor.escreverNoConsole("Conexão - Erro ao finalizar.");
					}
				}
			}, 20000, 1000);
		} catch (RemoteException e1) {
			e1.printStackTrace();
			this.telaServidor.escreverNoConsole("Conexão - Erro ao finalizar.");
		}
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
