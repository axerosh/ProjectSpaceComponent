package graphics;

import game.Battlefield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PSCFrame extends JFrame {

	private Battlefield arena;
	private GameDisplayer gameDisplayer;

	public PSCFrame(Battlefield arena, GameDisplayer gameDisplayer) throws HeadlessException {
		super("Project Space Component");
		this.arena = arena;
		this.gameDisplayer = gameDisplayer;
		add(gameDisplayer);
		pack();
		add(new MouseAndKeyboardHandler());
		setVisible(true);
		gameDisplayer.repaint();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private class MouseAndKeyboardHandler extends JComponent{

		protected MouseAndKeyboardHandler() {
			addMouseListener(new MouseAndKeyboardListener());
			addKeyListener(new MouseAndKeyboardListener());
		}

		private class MouseAndKeyboardListener extends MouseAdapter implements KeyListener {

			@Override public void mouseClicked(final MouseEvent e) {
				if (e.isControlDown()) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						arena.increasePowerOfShipAt(gameDisplayer.getVirtualX(e.getX()),
													gameDisplayer.getVirtualY(e.getY()));
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						arena.decreasePowerOfShipAt(gameDisplayer.getVirtualX(e.getX()),
													gameDisplayer.getVirtualY(e.getY()));
					}
				} else if (e.isShiftDown()) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						arena.increaseShieldingOfShipAt(gameDisplayer.getVirtualX(e.getX()),
														gameDisplayer.getVirtualY(e.getY()));
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						arena.decreaseShieldingOfShipAt(gameDisplayer.getVirtualX(e.getX()),
														gameDisplayer.getVirtualY(e.getY()));
					}
				}
			}

			@Override public void keyPressed(final KeyEvent e) {

			}

			@Override public void keyReleased(final KeyEvent e) {

			}

			@Override public void keyTyped(final KeyEvent e) {

			}
		}
	}

}
