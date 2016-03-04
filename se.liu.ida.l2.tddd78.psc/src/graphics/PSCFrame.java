package graphics;

import game.BattleField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class PSCFrame extends JFrame{

	private BattleField arena;
	private GameDisplayer gc;

	public PSCFrame(BattleField arena, GameDisplayer gc) throws HeadlessException {
		super("Project Space Component");
		this.arena = arena;
		this.gc = gc;
		add(gc);
		pack();
		add(new MouseInputComponent());
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private class MouseInputComponent extends JComponent {

		public MouseInputComponent() {
			addMouseWheelListener(new PSCMouseWheelListener());
			addMouseListener(new PSCMouseListener());
		}

		private class PSCMouseWheelListener implements MouseWheelListener {

			@Override public void mouseWheelMoved(final MouseWheelEvent e) {
				arena.changeStatIndicatedAt(gc.getVirtualX(e.getX()), gc.getVirtualY(e.getY()), -e.getWheelRotation());
			}
		}

		private class PSCMouseListener extends MouseAdapter {

		}
	}
}
