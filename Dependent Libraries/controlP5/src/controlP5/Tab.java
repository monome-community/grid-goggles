package controlP5;

/**
 * controlP5 is a processing gui library.
 *
 *  2006-2011 by Andreas Schlegel
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 * @author 		Andreas Schlegel (http://www.sojamo.de)
 * @modified	11/13/2011
 * @version		0.6.12
 *
 */

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Tabs are used to organize controllers. Tabs are arranged horizontally from
 * the top-left corner by default, Tab extends ControllerGroup, for more
 * available methods see the ControllerGroup documentation. Reposition tabs with
 * {@link controlP5.ControlWindow#setPositionOfTabs(int, int)}
 * 
 * @example controllers/ControlP5tab
 * @nosuperclasses ControllerGroup ControllerGroup
 */
public class Tab extends ControllerGroup {

	protected int _myOffsetX = -1000;

	protected int _myOffsetY = -1000;

	protected boolean isActive = false;

	protected boolean isEventActive = false;

	protected float _myValue = 0;

	protected String _myStringValue = "";

	protected int _myRightBorder = 4;

	/**
	 * 
	 * @param theControlP5 ControlP5
	 * @param theControlWindow ControlWindow
	 * @param theName String
	 */
	public Tab(ControlP5 theControlP5, ControlWindow theControlWindow, String theName) {
		super(theControlP5, null, theName, 0, 0);
		_myControlWindow = theControlWindow;

		position = new PVector();
		absolutePosition = new PVector();
		isMoveable = false;
		isEventActive = theControlP5.isTabEventsActive;
		_myHeight = 16;
		_myLabel.update();
		_myWidth = _myLabel.getWidth() + _myRightBorder;
		width();
	}

	protected void setOffset(int theValueX, int theValueY) {
		_myOffsetX = theValueX;
		_myOffsetY = theValueY;
	}

	protected int height() {
		return _myHeight;
	}

	protected boolean updateLabel() {
		isInside = inside();
		return _myControlWindow.getTabs().size() > 2;
	}

	protected void drawLabel(PApplet theApplet) {
		theApplet.pushMatrix();
		theApplet.fill(isInside ? color.getForeground() : color.getBackground());
		if (isActive) {
			theApplet.fill(color.getActive());
		}
		theApplet.rect(_myOffsetX, _myOffsetY, _myWidth - 1 + _myRightBorder, _myHeight);
		_myLabel.draw(theApplet, _myOffsetX + 4, _myOffsetY + 5);
		theApplet.popMatrix();
	}

	/**
	 * set the label of the group. TODO overwriting COntrollerGroup.setLabel to
	 * set the Width of a tab after renaming. this should be temporary and fixed
	 * in the future.
	 * 
	 * @param theLabel String
	 * @return Tab
	 */
	public Tab setLabel(String theLabel) {
		_myLabel.setFixedSize(false);
		_myLabel.set(theLabel);
		_myLabel.setFixedSize(true);
		setWidth(_myLabel.getWidth());
		return this;
	}

	protected int width() {
		return _myWidth + _myRightBorder;
	}

	/**
	 * @param theWidth
	 * @return
	 */
	public Tab setWidth(int theWidth) {
		_myWidth = theWidth + _myRightBorder;
		return this;
	}

	public Tab setHeight(int theHeight) {
		_myHeight = theHeight;
		return this;
	}

	protected boolean inside() {
		return (_myControlWindow.mouseX > _myOffsetX && _myControlWindow.mouseX < _myOffsetX + _myWidth + _myRightBorder && _myControlWindow.mouseY > _myOffsetY && _myControlWindow.mouseY < _myOffsetY
				+ _myHeight);
	}

	/**
	 * {@inheritDoc}
	 */
	@ControlP5.Invisible
	public void mousePressed() {
		_myControlWindow.activateTab(this);
		if (isEventActive) {
			cp5.getControlBroadcaster().broadcast(new ControlEvent(this), ControlP5Constants.METHOD);
		}
	}

	/**
	 * Activates a tab.
	 * 
	 * @param theFlag boolean
	 */
	public Tab setActive(boolean theFlag) {
		isActive = theFlag;
		return this;
	}

	/**
	 * checks if a tab is active.
	 * 
	 * @return boolean
	 */
	protected boolean isActive() {
		return isActive;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tab moveTo(ControlWindow theWindow) {
		_myControlWindow.removeTab(this);
		setTab(theWindow, getName());
		return this;
	}

	/**
	 * activates or deactivates the Event status of a tab, When activated a tab
	 * will send a controlEvent to the main application. By default this is
	 * disabled.
	 * 
	 * @param theFlag boolean
	 * @return Tab
	 */
	public Tab activateEvent(boolean theFlag) {
		isEventActive = theFlag;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStringValue() {
		return _myStringValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getValue() {
		return _myValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tab setValue(float theValue) {
		_myValue = theValue;
		return this;
	}

	@Deprecated
	public float value() {
		return _myValue;
	}

	@Deprecated
	public String stringValue() {
		return _myStringValue;
	}

}
