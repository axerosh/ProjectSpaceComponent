package graphics;

import game.ProjectSpaceComponent;
import io.ShipIO;
import ship.Starship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * JFrame extension that displays and handles input for a game.
 */
public class PSCFrame extends JFrame
{

	private JMenu saveLoad;

	public PSCFrame(ProjectSpaceComponent psc) throws HeadlessException {
		super("Project Space Component");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createMenus(psc);
	}

	private void createMenus(ProjectSpaceComponent psc) {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu gameMenu = new JMenu("Game");

		saveLoad = new JMenu("Ship");

		final JMenuItem changeGamemode = new JMenuItem("Change Gamemode");
		changeGamemode.setMnemonic(KeyEvent.VK_G);
		changeGamemode.setAccelerator(KeyStroke.getKeyStroke("C"));
		changeGamemode.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				psc.changeGamemode();
			}
		});
		gameMenu.add(changeGamemode);

		final JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		exit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});



		final JMenuItem save = new JMenuItem("Save Ship");
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		save.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				String shipName = JOptionPane.showInputDialog("Please input a name");
				ShipIO.save(psc.getWorkshop().getWorkingShip(), shipName);
			}
		});
		saveLoad.add(save);


		final JMenuItem load = new JMenuItem("Load Ship");
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
		load.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				String shipName = JOptionPane.showInputDialog("Please input a name");

				List<Starship> starShips = new ArrayList<>();
				starShips.add(psc.getPlayerShip());
				starShips.add(psc.getWorkshop().getWorkingShip());
				ShipIO.loadToShips(shipName, starShips);

			}
		});

		saveLoad.add(load);
		gameMenu.add(exit);

		menuBar.add(gameMenu);
		menuBar.add(saveLoad);

		this.setJMenuBar(menuBar);
	}

	public JMenu getSaveLoad() {
		return saveLoad;
	}
}