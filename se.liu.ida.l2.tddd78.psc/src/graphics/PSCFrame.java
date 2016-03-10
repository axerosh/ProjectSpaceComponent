package graphics;

import game.Battlefield;
import ship_components.ShipComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * JFrame extension that displays and handles input for a game.
 */
public class PSCFrame extends JFrame
{

	private Battlefield battlefield;
	private GameDisplayer gameDisplayer;
	private ShipComponent selectedComponent;

	public PSCFrame(Battlefield battlefield, GameDisplayer gameDisplayer) throws HeadlessException {
		super("Project Space Component");
		this.battlefield = battlefield;
		this.gameDisplayer = gameDisplayer;
		selectedComponent = null;
		add(gameDisplayer);
		pack();
		add(new MouseAndKeyboardHandler());
		setVisible(true);
		gameDisplayer.repaint();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private class MouseAndKeyboardHandler extends JComponent
	{

		protected MouseAndKeyboardHandler() {
			addMouseListener(new MouseAndKeyboardListener());
			addKeyListener(new MouseAndKeyboardListener());
		}

		private class MouseAndKeyboardListener extends MouseAdapter implements KeyListener
		{

			@Override public void mouseClicked(final MouseEvent e) {
				ShipComponent clickedComponent =
						battlefield.getComponentAt(gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
				if (clickedComponent != null) {
					if (e.isControlDown()) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							clickedComponent.increasePower();
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							clickedComponent.decreasePower();
						}
					} else if (e.isShiftDown()) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							clickedComponent.increaseShielding();
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							clickedComponent.decreaseShielding();
						}
					} else {
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (selectedComponent == null) {
								clickedComponent.activate();
								if (clickedComponent.needsTarget()) {
									selectedComponent = clickedComponent;
								}
							} else {
								//selectedComponent.activateWithTarget(battlefield.get)
							}

						} else if (e.getButton() == MouseEvent.BUTTON3) {
							if (selectedComponent == null) {
								clickedComponent.deactivate();
							} else {
								selectedComponent = null;
							}
						}
					}
				}
			}

			@Override public void keyPressed(final KeyEvent e) {}

			@Override public void keyReleased(final KeyEvent e) {}

			@Override public void keyTyped(final KeyEvent e) {}
		}
	}

}
