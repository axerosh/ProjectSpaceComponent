package game;

import graphics.GameDisplayer;
import ship_components.ShipComponent;
import ship_components.weapon_components.WeaponComponent;
import weaponry.FiringOrder;

import java.awt.geom.Point2D;
import javax.swing.JComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseAndKeyboard extends JComponent {

	private Battlefield battlefield;
	private Starship controlledShip;
	private GameDisplayer gameDisplayer;
	private WeaponComponent selectedWeapon;

	public MouseAndKeyboard(final Battlefield battlefield, final Starship controlledShip, final GameDisplayer gameDisplayer) {
		this.battlefield = battlefield;
		this.controlledShip = controlledShip;
		this.gameDisplayer = gameDisplayer;
		addMouseListener(new MouseAndKeyboardListener());
		addKeyListener(new MouseAndKeyboardListener());
	}

	private class MouseAndKeyboardListener extends MouseAdapter implements KeyListener {

		@Override public void mouseClicked(final MouseEvent e) {
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
			} else {
				ShipComponent clickedGlobalComponent = battlefield.getComponentAt(gameDisplayer.getVirtualX(e.getX()),
																				  gameDisplayer.getVirtualY(e.getY()));
				manageTargeting(e, clickedGlobalComponent );
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
					selectedWeapon = null;
				}
			}
		}

		private void manageTargeting(final MouseEvent e, ShipComponent clickedComponent) {
			Point2D.Float originPos = controlledShip.getPositionOf(selectedWeapon);
			if (originPos != null) {
				Starship targetShip = battlefield.getShipAt(gameDisplayer.getVirtualX(e.getX()),
															gameDisplayer.getVirtualY(e.getY()));
				Point2D.Float targetPos = targetShip.getPositionOf(clickedComponent);
				System.out.println(clickedComponent);
				selectedWeapon.giveFiringOrder(new FiringOrder((float) originPos.getX(), (float) originPos.getY(),
															   (float) targetPos.getX(), (float) targetPos.getY(),
															   targetShip));
			}

		}

		@Override public void keyPressed(final KeyEvent e) {}

		@Override public void keyReleased(final KeyEvent e) {}

		@Override public void keyTyped(final KeyEvent e) {}
	}
}

