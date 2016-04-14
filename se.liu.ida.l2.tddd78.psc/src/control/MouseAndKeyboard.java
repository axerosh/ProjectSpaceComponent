package control;

import game.BattleSpace;
import game.ProjectSpaceComponent;
import game.Test;
import game.Workshop;
import graphics.displayers.BattleSpaceDisplayer;
import graphics.displayers.MenuDisplayer;
import graphics.displayers.WorkshopDisplayer;
import ship.Starship;
import ship.component.ShipComponent;
import ship.component.weapon.WeaponComponent;
import weaponry.FiringOrder;
import game.ProjectSpaceComponent.Gamemode;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Mouse and Keyboard input for controlling a ship.
 */
public class MouseAndKeyboard extends JComponent {

	private Starship controlledShip;
	/*private BattleSpace battleSpace;
	private Workshop workshop;
	private BattleSpaceDisplayer battleSpaceDisplayer;
	private WorkshopDisplayer workshopDisplayer;
	private MenuDisplayer menuDisplayer;*/
	private ProjectSpaceComponent psc;
	private WeaponComponent selectedWeapon;
	private ShipComponent selectedComponentInWorkshop;

	private Gamemode gamemode;


	public MouseAndKeyboard(final ProjectSpaceComponent psc, final Gamemode gamemode) {/*final BattleSpace battleSpace, final BattleSpaceDisplayer battleSpaceDisplayer, final
							WorkshopDisplayer workshopDisplayer, final MenuDisplayer menuDisplayer,final Workshop workshop, final Gamemode gamemode) {
							this.battleSpace = battleSpace;
		this.battleSpaceDisplayer = battleSpaceDisplayer;
		this.workshopDisplayer = workshopDisplayer;
		this.menuDisplayer = menuDisplayer;
		this.workshop = workshop;*/
		this.gamemode = gamemode;
		this.psc = psc;

		selectedWeapon = null;
		selectedComponentInWorkshop = null;

		MouseAndKeyboardListener m = new MouseAndKeyboardListener();
		addMouseListener(m);
		addKeyListener(m);
		setFocusable(true);
		requestFocusInWindow();

	}

	public void setControlledShip(final Starship controlledShip) {
		this.controlledShip = controlledShip;
	}

	public void setGamemode(final Gamemode gamemode) {
		this.gamemode = gamemode;
	}

	private class MouseAndKeyboardListener extends MouseAdapter implements KeyListener
	{

		@Override public void mouseClicked(final MouseEvent e) {
			if (controlledShip == null) {
				return;
			}

			if (gamemode == Gamemode.WORKSHOP) {
				WorkshopDisplayer workshopDisplayer = psc.getWorkshopDisplayer();
				ShipComponent clickedLocalComponent = psc.getWorkshop().getComponentAtSidebar(
						workshopDisplayer.getVirtualX(e.getX()), workshopDisplayer.getVirtualY(e.getY()));
				managePlacing(e, clickedLocalComponent);
				return;
			}

			BattleSpaceDisplayer battleSpaceDisplayer = psc.getBattleSpaceDisplayer();
			ShipComponent clickedLocalComponent =
					controlledShip.getComponentAt(battleSpaceDisplayer.getVirtualX(e.getX()), battleSpaceDisplayer.getVirtualY(e.getY()));
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
						psc.getBattleSpace().getComponentAt(battleSpaceDisplayer.getVirtualX(e.getX()), battleSpaceDisplayer
								.getVirtualY(e.getY()));

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
					WorkshopDisplayer workshopDisplayer = psc.getWorkshopDisplayer();
					placeOnShip(sc, (int)workshopDisplayer.getVirtualX(e.getX()), (int)workshopDisplayer.getVirtualY(e.getY())
																				  - psc.getWorkshop().getTopBarHeight());
				}
			}else if(e.getButton() == MouseEvent.BUTTON3){
				if(selectedComponentInWorkshop != null){
					selectedComponentInWorkshop = null;
				}else{
					WorkshopDisplayer workshopDisplayer = psc.getWorkshopDisplayer();
					placeOnShip(null, (int)workshopDisplayer.getVirtualX(e.getX()), (int)workshopDisplayer.getVirtualY(e.getY())
																					- psc.getWorkshop().getTopBarHeight());
				}

			}
		}


		private void placeOnShip(ShipComponent sc, int posX, int posY){
			psc.getWorkshop().getWorkingShip().setComponent(sc, posX, posY);
		}

		private void manageTargeting(final MouseEvent e, ShipComponent clickedComponent) {
			Point2D.Float originPos = controlledShip.getPositionOf(selectedWeapon);
			if (originPos != null) {
				BattleSpaceDisplayer battleSpaceDisplayer = psc.getBattleSpaceDisplayer();
				Starship targetShip =
						psc.getBattleSpace().getShipAt(battleSpaceDisplayer.getVirtualX(e.getX()), battleSpaceDisplayer.getVirtualY(e.getY()));
				if (targetShip != null) {
					Point2D.Float targetPos = targetShip.getPositionOf(clickedComponent);

					System.out.println(clickedComponent);
					selectedWeapon.giveFiringOrder(
							new FiringOrder((float) originPos.getX(), (float) originPos.getY(), (float) targetPos.getX(),
											(float) targetPos.getY(), targetShip));
				}
			}
		}

		@Override public void keyPressed(final KeyEvent e) {}

		@Override public void keyReleased(final KeyEvent e) {}

		@Override public void keyTyped(final KeyEvent e) {

			if(e.getKeyChar() == 'c'){

				switch (psc.getGamemode()) {
					case MENU:
						//Test.changeGamemode(Gamemode.WORKSHOP, battleSpaceDisplayer, workshopDisplayer, menuDisplayer,
						//					battleSpace, workshop, controlledShip, playerController);
						psc.changeGamemode(Gamemode.WORKSHOP);
						break;
					case WORKSHOP:
						//Test.changeGamemode(Gamemode.BATTLE, battleSpaceDisplayer, workshopDisplayer, menuDisplayer, battleSpace, workshop, controlledShip, playerController);
						psc.changeGamemode(Gamemode.BATTLE);
						break;
					case BATTLE:
						//Test.changeGamemode(Gamemode.MENU, battleSpaceDisplayer, workshopDisplayer, menuDisplayer, battleSpace, workshop, controlledShip, playerController);
						psc.changeGamemode(Gamemode.MENU);
						break;

				}

			}

		}
	}
}

