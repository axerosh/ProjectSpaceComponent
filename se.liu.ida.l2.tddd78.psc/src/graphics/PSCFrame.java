package graphics;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;

/**
 * JFrame extension that displays and handles input for a game.
 */
public class PSCFrame extends JFrame {

	public PSCFrame() throws HeadlessException {
		super("Project Space Component");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}