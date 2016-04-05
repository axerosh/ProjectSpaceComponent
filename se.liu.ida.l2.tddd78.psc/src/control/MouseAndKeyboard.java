package control;

import game.Battlefield;
import graphics.GameDisplayer;
import ship.Starship;
import ship.component.ShipComponent;
import ship.component.weapon.WeaponComponent;
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
	private Starship controlledShip;
	private GameDisplayer gameDisplayer;
	private WeaponComponent selectedWeapon;

	public MouseAndKeyboard(final Battlefield battlefield, final Starship controlledShip, final GameDisplayer gameDisplayer) {
		this.battlefield = battlefield;
		this.controlledShip = controlledShip;
		this.gameDisplayer = gameDisplayer;
		selectedWeapon = null;
		addMouseListener(new MouseAndKeyboardListener());
		addKeyListener(new MouseAndKeyboardListener());
	}

	private class MouseAndKeyboardListener extends MouseAdapter implements KeyListener
	{

		@Override public void mouseClicked(final MouseEvent e) {
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

		private void manageTargeting(final MouseEvent e, ShipComponent clickedComponent) {
			Point2D.Float originPos = controlledShip.getPositionOf(selectedWeapon);
			if (originPos != null) {
				Starship targetShip =
						battlefield.getShipAt(gameDisplayer.getVirtualX(e.getX()), gameDisplayer.getVirtualY(e.getY()));
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

		@Override public void keyTyped(final KeyEvent e) {}
	}
}

