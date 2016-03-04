package graphics;

import game.BattleField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class PSCFrame extends JFrame{

	private BattleField arena;
	private GameDisplayer gc;

	public PSCFrame(BattleField arena, GameDisplayer gc) throws HeadlessException {
		super("Project Space Component");
		this.arena = arena;
		this.gc = gc;
		add(gc);
		pack();
		add(new MouseAndKeyboardHandler());
		setVisible(true);
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
						arena.increasePowerOfShipAt(gc.getVirtualX(e.getX()), gc.getVirtualY(e.getY()));
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						arena.decreasePowerOfShipAt(gc.getVirtualX(e.getX()), gc.getVirtualY(e.getY()));
					}
				} else if (e.isShiftDown()) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						arena.increaseShieldingOfShipAt(gc.getVirtualX(e.getX()), gc.getVirtualY(e.getY()));
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						arena.decreaseShieldingOfShipAt(gc.getVirtualX(e.getX()), gc.getVirtualY(e.getY()));
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
