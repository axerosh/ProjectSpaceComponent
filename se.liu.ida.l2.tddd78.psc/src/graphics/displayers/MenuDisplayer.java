package graphics.displayers;

import game.Menu;

public class MenuDisplayer extends Displayer{

	private Menu menu;

	public MenuDisplayer(Menu menu, float scale, int width, int height) {
		super(scale, width, height);
		this.menu = menu;
	}


}
