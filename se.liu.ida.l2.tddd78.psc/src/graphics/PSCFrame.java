package graphics;

import game.ProjectSpaceComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * JFrame extension that displays and handles input for a game.
 */
public class PSCFrame extends JFrame
{

	public PSCFrame(ProjectSpaceComponent psc) throws HeadlessException {
		super("Project Space Component");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createMenus(psc);
	}

	private void createMenus(ProjectSpaceComponent psc) {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu gameMenu = new JMenu("Game");

		final JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		exit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		gameMenu.add(exit);

		final JMenuItem changeGamemode = new JMenuItem("Change Gamemode");
		changeGamemode.setMnemonic(KeyEvent.VK_G);
		changeGamemode.setAccelerator(KeyStroke.getKeyStroke("C"));
		changeGamemode.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				psc.changeGamemode();
			}
		});
		gameMenu.add(changeGamemode);

		menuBar.add(gameMenu);

		this.setJMenuBar(menuBar);
	}


}