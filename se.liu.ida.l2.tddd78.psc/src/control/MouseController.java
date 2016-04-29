package control;

import game.ProjectSpaceComponent;
import game.ProjectSpaceComponent.Gamemode;
import graphics.Displayer;
import ship.Starship;
import ship.component.ShipComponent;
import ship.component.weapon.FiringOrder;
import ship.component.weapon.WeaponComponent;

import javax.swing.JComponent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * Mouse input for controlling induvidiall ShipComponent in a Starship
 * A ProjectSpaceComponent is used to get information about what is on the screen
 */
public class MouseController extends JComponent {

	private Starship controlledShip;
	private ProjectSpaceComponent psc;
	private WeaponComponent selectedWeapon;
	private ShipComponent selectedComponentInWorkshop;

	private Gamemode gamemode;


	public MouseController(final ProjectSpaceComponent psc, final Gamemode gamemode) {

		this.gamemode = gamemode;
		this.psc = psc;

		selectedWeapon = null;
		selectedComponentInWorkshop = null;
		controlledShip = null;

		MouseListener m = new PSCMouseListener();
		addMouseListener(m);

	}

	public void setControlledShip(final Starship controlledShip) {
		this.controlledShip = controlledShip;
	}

	public void setGamemode(final Gamemode gamemode) {
		this.gamemode = gamemode;
	}

	private class PSCMouseListener extends MouseAdapter {

		@Override public void mouseClicked(final MouseEvent e) {
			if (controlledShip == null) {
				return;
			}

			Displayer gameDisplayer = psc.getGameDisplayer();

			if (gamemode == Gamemode.WORKSHOP) {
				ShipComponent clickedLocalComponent = psc.getWorkshop().getComponentAtSidebar(
						gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
				managePlacing(e, clickedLocalComponent);
				return;
			}

			ShipComponent clickedLocalComponent = controlledShip.getComponentAt(gameDisplayer.getVirtualX(e.getX()),
																				gameDisplayer.getVirtualY(e.getY()));
			if (clickedLocalComponent != null) {
				if (e.isControlDown()) {
					managePower(e, clickedLocalComponent);
				} else if (e.isShiftDown()) {
					manageShielding(e, clickedLocalComponent);
				} else {
					manageActivation(e, clickedLocalComponent);
				}
			} else if (selectedWeapon != null) {
				ShipComponent clickedGlobalComponent = psc.getBattleSpace().getComponentAt(gameDisplayer.getVirtualX(e.getX()),
																						   gameDisplayer.getVirtualY(e.getY
																								   ()));

				if (e.getButton() == MouseEvent.BUTTON1) {
					if (clickedGlobalComponent != null) {
						manageTargeting(e, clickedGlobalComponent);
					}

				} else if (e.getButton() == MouseEvent.BUTTON3) {
					selectedWeapon.setSelected(false);
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
					if (clickedComponent instanceof WeaponComponent) {
						selectedWeapon = (WeaponComponent) clickedComponent;
						clickedComponent.setSelected(true);
					} else {
						clickedComponent.setActive(true);
					}
				} else {
					manageTargeting(e, clickedComponent);
				}

			} else if (e.getButton() == MouseEvent.BUTTON3) {
				if (selectedWeapon == null) {
					clickedComponent.setActive(false);
				} else {
					selectedWeapon.setSelected(false);
					selectedWeapon = null;
				}
			}
		}

		private void managePlacing(final MouseEvent e, ShipComponent clickedShipComponent) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (clickedShipComponent != null) {
					if (selectedComponentInWorkshop != null) {
						selectedComponentInWorkshop.setSelected(false);
					}
					clickedShipComponent.setSelected(true);
					selectedComponentInWorkshop = clickedShipComponent;
				} else if (selectedComponentInWorkshop != null) {
					ShipComponent sc = selectedComponentInWorkshop.copy();
					Displayer gameDisplayer = psc.getGameDisplayer();
					placeOnShip(sc, gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				Displayer gameDisplayer = psc.getGameDisplayer();
				placeOnShip(null, gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
			}
		}


		private void placeOnShip(ShipComponent sc, float posX, float posY) {
			Starship workingShip = psc.getWorkshop().getWorkingShip();
			if (workingShip.contains(posX, posY)) {
				workingShip.setComponentExternal(sc, posX, posY);
			}
		}

		private void manageTargeting(final MouseEvent e, ShipComponent clickedComponent) {
			Point2D.Float originPos = controlledShip.getPositionOf(selectedWeapon);
			if (originPos != null) {
				Displayer gameDisplayer = psc.getGameDisplayer();
				Starship targetShip = psc.getBattleSpace().getShipAt(gameDisplayer.getVirtualX(e.getX()),
																	 gameDisplayer.getVirtualY(e.getY()));
				if (targetShip != null) {
					Point2D.Float targetPos = targetShip.getPositionOf(clickedComponent);
					selectedWeapon.setFiringOrder(
							new FiringOrder((float) originPos.getX(), (float) originPos.getY(), (float) targetPos.getX(),
											(float) targetPos.getY(), targetShip));
				}
			}
		}
	}
}

