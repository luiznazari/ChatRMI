package chat.foda.pra.caralho.telas.eventos;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import chat.foda.pra.caralho.telas.TelaChatBuilder;

/**
 * @author Luiz Felipe Nazari
 */
public class EventosTelaChatBuilder implements InternalFrameListener {
	
	private final TelaChatBuilder tcBuilder;
	
	public EventosTelaChatBuilder(TelaChatBuilder tcBuilder) {
		this.tcBuilder = tcBuilder;
	}
	
	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		tcBuilder.getClient().fechaChat(tcBuilder);
	}
	
	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
	}
	
}
