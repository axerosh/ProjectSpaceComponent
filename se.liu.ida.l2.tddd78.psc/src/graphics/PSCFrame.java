package graphics;

import game.BattleField;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;

public class PSCFrame extends JFrame{

	private BattleField arena;
	private GameComponent gc;

	public PSCFrame(BattleField arena, GameComponent gc) throws HeadlessException {
		super("Project Space Component");
		this.arena = arena;
		this.gc = gc;
		add(gc);
		pack();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addMouseWheelListener(new PSCMouseWheelListener());
		addMouseListener(new PSCMouseListener());
	}

	private class PSCMouseWheelListener implements MouseWheelListener {

		@Override public void mouseWheelMoved(final MouseWheelEvent e) {
			System.out.println("\nWheel moved!");
			Point mp  = getMousePos();
			if (e.getWheelRotation() < 0) {
				arena.activateWithCursor(gc.getVirtualX(mp.x), gc.getVirtualY(mp.y));
			} else if (e.getWheelRotation() > 0){
				arena.deactivateWithCursor(gc.getVirtualX(mp.x), gc.getVirtualY(mp.y));
			}
		}
	}

	private class PSCMouseListener implements MouseListener {

		@Override public void mouseClicked(final MouseEvent e) {

		}

		@Override public void mousePressed(final MouseEvent e) {

		}

		@Override public void mouseReleased(final MouseEvent e) {

		}

		@Override public void mouseEntered(final MouseEvent e) {

		}

		@Override public void mouseExited(final MouseEvent e) {

		}
	}

	private Point getMousePos() {
		return MouseInfo.getPointerInfo().getLocation();
	}
}
