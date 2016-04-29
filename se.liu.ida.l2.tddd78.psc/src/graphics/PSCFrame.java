package graphics;

import game.ProjectSpaceComponent;
import io.ShipIO;
import ship.Starship;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

/**
 * JFrame extension that holds a menu to manipulate a game session of ProjectSpaceComponent.
 *
 * @see JFrame
 */
public class PSCFrame extends JFrame {

	private JMenu saveAndLoadMenu;

	public PSCFrame(ProjectSpaceComponent psc) throws HeadlessException {
		super("Project Space Component");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createMenus(psc);
	}

	private void createMenus(ProjectSpaceComponent psc) {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu gameMenu = new JMenu("Game");

		saveAndLoadMenu = new JMenu("Ship");

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
		saveAndLoadMenu.add(save);


		final JMenuItem load = new JMenuItem("Load Ship");
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
		load.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				String shipName = JOptionPane.showInputDialog("Please input a name");

				Collection<Starship> starShips = new ArrayList<>();
				starShips.add(psc.getPlayerShip());
				starShips.add(psc.getWorkshop().getWorkingShip());
				ShipIO.loadToShips(shipName, starShips);

			}
		});

		saveAndLoadMenu.add(load);
		gameMenu.add(exit);

		menuBar.add(gameMenu);
		menuBar.add(saveAndLoadMenu);

		this.setJMenuBar(menuBar);
	}

	public JMenu getSaveAndLoadMenu() {
		return saveAndLoadMenu;
	}
}