package graphics.displayers;

import game.Menu;

import java.awt.*;

public class MenuDisplayer extends Displayer{

	Menu menu;

	public MenuDisplayer(Menu menu, float scale, int width, int height) {
		super(scale, width, height);
		this.menu = menu;
	}


}
