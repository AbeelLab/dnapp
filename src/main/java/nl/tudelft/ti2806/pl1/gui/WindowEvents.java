/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * @author Maarten
 *
 */
public class WindowEvents implements ComponentListener {

	/**
	 * The window this listener receives events from.
	 */
	private Window window;

	/**
	 * Initializes a new window events listener.
	 * 
	 * @param w
	 *            The window this component listener listens to.
	 */
	public WindowEvents(final Window w) {
		this.window = w;
	}

	@Override
	public final void componentResized(final ComponentEvent e) {
		window.resized();
	}

	@Override
	public final void componentMoved(final ComponentEvent e) {
	}

	@Override
	public final void componentShown(final ComponentEvent e) {
	}

	@Override
	public final void componentHidden(final ComponentEvent e) {
	}

}
