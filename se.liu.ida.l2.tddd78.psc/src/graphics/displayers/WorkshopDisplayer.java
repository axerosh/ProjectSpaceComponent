package graphics.displayers;

import game.Workshop;

import java.awt.*;

public class WorkshopDisplayer extends Displayer{

	final private Workshop workshop;

	public WorkshopDisplayer(Workshop workshop) {
		this.workshop = workshop;
	}

	@Override public Dimension getPreferredSize(){
		return new Dimension(400, 400);
	}
}
