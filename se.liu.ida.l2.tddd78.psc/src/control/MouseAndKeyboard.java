package control;

import component.ShipComponent;
import component.weapon.WeaponComponent;
import game.Battlefield;
import game.Starship;
import game.Test;
import game.Workshop;
import graphics.displayers.GameDisplayer;
import graphics.displayers.MenuDisplayer;
import graphics.displayers.WorkshopDisplayer;
import weaponry.FiringOrder;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Mouse and Keyboard input for controlling a ship.
 */
public class MouseAndKeyboard extends JComponent
{

	private Battlefield battlefield;
	private Workshop workshop;
	private Starship controlledShip;
	private GameDisplayer gameDisplayer;
	private WorkshopDisplayer workshopDisplayer;
	private MenuDisplayer menuDisplayer;
	private WeaponComponent selectedWeapon;
	private ShipComponent selectedComponentInWorkshop;
	private JComponent playerController;


	public MouseAndKeyboard(final Battlefield battlefield, final Starship controlledShip, final GameDisplayer gameDisplayer, final
							WorkshopDisplayer workshopDisplayer, MenuDisplayer menuDisplayer, Workshop workshop) {
		this.battlefield = battlefield;
		this.controlledShip = controlledShip;
		this.gameDisplayer = gameDisplayer;
		this.workshopDisplayer = workshopDisplayer;
		this.menuDisplayer = menuDisplayer;
		this.workshop = workshop;

		playerController = this;

		selectedWeapon = null;
		selectedComponentInWorkshop = null;

		MouseAndKeyboardListener m = new MouseAndKeyboardListener();
		addMouseListener(m);
		addKeyListener(m);
		setFocusable(true);
		requestFocusInWindow();

	}

	private class MouseAndKeyboardListener extends MouseAdapter implements KeyListener
	{

		@Override public void mouseClicked(final MouseEvent e) {

			if(Test.gameMode == Test.Gamemode.WORKSHOP){
				ShipComponent clickedLocalComponent = workshop.getComponentAtSidebar(workshopDisplayer.getVirtualX(e.getX()), workshopDisplayer.getVirtualY(e.getY()));
				managePlacing(e, clickedLocalComponent);
				return;
			}


			ShipComponent clickedLocalComponent =
					controlledShip.getComponentAt(gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
			if (clickedLocalComponent != null) {
				if (e.isControlDown()) {
					managePower(e, clickedLocalComponent);
				} else if (e.isShiftDown()) {
					manageShielding(e, clickedLocalComponent);
				} else {
					manageActivation(e, clickedLocalComponent);
				}
			} else if (selectedWeapon != null) {
				ShipComponent clickedGlobalComponent =
						battlefield.getComponentAt(gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
				System.out.println("Found " + clickedGlobalComponent);
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (clickedGlobalComponent != null) {
						manageTargeting(e, clickedGlobalComponent);
					}

				} else if (e.getButton() == MouseEvent.BUTTON3) {
					selectedWeapon = null;
				}
			}
		}



		private void managePower(final MouseEvent e, ShipComponent clickedComponent) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				clickedComponent.increasePower();
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				clickedComponent.decreasePower();
			}
		}

		private void manageShielding(final MouseEvent e, ShipComponent clickedComponent) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				clickedComponent.increaseShielding();
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				clickedComponent.decreaseShielding();
			}
		}

		private void manageActivation(final MouseEvent e, ShipComponent clickedComponent) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (selectedWeapon == null) {
					clickedComponent.activate();

					if (clickedComponent instanceof WeaponComponent) {
						selectedWeapon = (WeaponComponent) clickedComponent;
					}
				} else {
					manageTargeting(e, clickedComponent);
				}

			} else if (e.getButton() == MouseEvent.BUTTON3) {
				if (selectedWeapon == null) {
					clickedComponent.deactivate();
				} else {
					selectedWeapon.deactivate();
					selectedWeapon = null;
				}
			}
		}

		private void managePlacing(final MouseEvent e, ShipComponent clickedShipComponent){
			if(e.getButton() == MouseEvent.BUTTON1){
				if(selectedComponentInWorkshop == null){
					selectedComponentInWorkshop = clickedShipComponent;
				}else{
					ShipComponent sc = null;
					try{
						sc = selectedComponentInWorkshop.clone();
					}catch(CloneNotSupportedException error){
						error.printStackTrace();
					}
					placeOnShip(sc, (int)workshopDisplayer.getVirtualX(e.getX()), (int)workshopDisplayer.getVirtualY(e.getY()) - 1);
				}
			}else if(e.getButton() == MouseEvent.BUTTON3){
				if(selectedComponentInWorkshop != null){
					selectedComponentInWorkshop = null;
				}else{
					placeOnShip(null, (int)workshopDisplayer.getVirtualX(e.getX()), (int)workshopDisplayer.getVirtualY(e.getY()) - 1);
				}

			}
		}


		private void placeOnShip(ShipComponent sc, int posX, int posY){
			workshop.getWorkingShip().setComponent(sc, posX, posY);
		}

		private void manageTargeting(final MouseEvent e, ShipComponent clickedComponent) {
			Point2D.Float originPos = controlledShip.getPositionOf(selectedWeapon);
			if (originPos != null) {
				Starship targetShip =
						battlefield.getShipAt(gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
				System.out.println("Target ship aquired");
				if (targetShip != null) {
					Point2D.Float targetPos = targetShip.getPositionOf(clickedComponent);

					System.out.println(clickedComponent);
					selectedWeapon.giveFiringOrder(
							new FiringOrder((float) originPos.getX(), (float) originPos.getY(), (float) targetPos.getX(),
											(float) targetPos.getY(), targetShip));
				}
			}
		}

		@Override public void keyPressed(final KeyEvent e) {
		}

		@Override public void keyReleased(final KeyEvent e) {
		}

		@Override public void keyTyped(final KeyEvent e) {

			if(e.getKeyChar() == 'c'){

				switch(Test.gameMode){
					case MENU:
						Test.changeGamemode(Test.Gamemode.WORKSHOP, gameDisplayer, workshopDisplayer, menuDisplayer, battlefield, workshop, controlledShip, playerController);
						break;
					case WORKSHOP:
						Test.changeGamemode(Test.Gamemode.BATTLE, gameDisplayer, workshopDisplayer, menuDisplayer, battlefield, workshop, controlledShip, playerController);
						break;
					case BATTLE:
						Test.changeGamemode(Test.Gamemode.MENU, gameDisplayer, workshopDisplayer, menuDisplayer, battlefield, workshop, controlledShip, playerController);
						break;

				}

			}

		}
	}
}

