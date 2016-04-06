package graphics.displayers;

import game.Menu;

import java.awt.*;

public class MenuDisplayer extends Displayer{

	Menu menu;

	public MenuDisplayer(Menu menu) {
		this.menu = menu;
	}

	@Override public Dimension getPreferredSize(){
		return new Dimension(200, 200);
	}
}
