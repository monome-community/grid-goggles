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

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * ControllerGroup is an abstract class and is extended by class ControlGroup, Tab, or the ListBox.
 * 
 */
public abstract class ControllerGroup implements ControllerInterface, ControlP5Constants {

	protected PVector position;

	protected PVector positionBuffer;

	protected PVector absolutePosition;

	protected ControllerList controllers;

	protected ControlWindow _myControlWindow;

	protected ControlP5 cp5;

	protected ControllerGroup _myParent;

	protected String _myName;

	protected int _myId = -1;

	protected CColor color = new CColor();

	protected boolean isMousePressed = false;

	// only applies to the area of the title bar of a group
	protected boolean isInside = false;

	// applies to the area including controllers, currently only supported for listbox
	protected boolean isInsideGroup = false;

	protected boolean isVisible = true;

	protected boolean isOpen = true;

	protected boolean isMoveable = true;

	protected Label _myLabel;

	protected Label _myValueLabel;

	protected int _myWidth = 99;

	protected int _myHeight = 9;

	protected boolean isUpdate;

	protected List<ControlWindowCanvas> _myControlCanvas;

	protected float _myValue;

	protected String _myStringValue;

	protected float[] _myArrayValue;

	protected boolean isCollapse = true;

	protected int _myPickingColor = 0x6600ffff;

	protected PVector autoPosition = new PVector(10, 30, 0);

	protected float tempAutoPositionHeight = 0;

	protected float autoPositionOffsetX = 10;

	private String _myAddress = "";

	private boolean mouseover;

	/**
	 * 
	 * @param theControlP5 ControlP5
	 * @param theParent ControllerGroup
	 * @param theName String
	 * @param theX float
	 * @param theY float
	 * @exclude
	 */
	public ControllerGroup(ControlP5 theControlP5, ControllerGroup theParent, String theName, float theX, float theY) {
		position = new PVector(theX, theY, 0);
		cp5 = theControlP5;
		color.set((theParent == null) ? cp5.color : theParent.color);
		_myName = theName;
		controllers = new ControllerList();
		_myControlCanvas = new ArrayList<ControlWindowCanvas>();
		_myLabel = new Label(cp5, _myName);
		_myLabel.setColor(color.getCaptionLabel());
		setParent((theParent == null) ? this : theParent);
	}

	protected ControllerGroup(int theX, int theY) {
		position = new PVector(theX, theY, 0);
		controllers = new ControllerList();
		_myControlCanvas = new ArrayList<ControlWindowCanvas>();
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	public void init() {
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	@Override
	public ControllerInterface getParent() {
		return _myParent;
	}

	void setParent(ControllerGroup theParent) {
		if (_myParent != null && _myParent != this) {
			_myParent.remove(this);
		}

		_myParent = theParent;

		if (_myParent != this) {
			_myParent.add(this);
		}
		absolutePosition = new PVector(position.x, position.y);

		absolutePosition.add(_myParent.absolutePosition);

		positionBuffer = new PVector(position.x, position.y);

		_myControlWindow = _myParent.getWindow();

		for (int i = 0; i < controllers.size(); i++) {
			if (controllers.get(i) instanceof Controller) {
				((Controller) controllers.get(i))._myControlWindow = _myControlWindow;
			} else {
				((ControllerGroup) controllers.get(i))._myControlWindow = _myControlWindow;
			}
		}
		if (_myControlWindow != null) {
			setMouseOver(false);
		}
	}

	/**
	 * @param theGroup ControllerGroup
	 * @return ControllerGroup
	 */
	public final ControllerGroup setGroup(ControllerGroup theGroup) {
		setParent(theGroup);
		return this;
	}

	/**
	 * @param theName String
	 * @return ControllerGroup
	 */
	public final ControllerGroup setGroup(String theName) {
		setParent(cp5.getGroup(theName));
		return this;
	}

	/**
	 * @param theGroup ControlGroup
	 * @param theTab Tab
	 * @param theControlWindow ControlWindow
	 * @return ControllerGroup
	 */
	public final ControllerGroup moveTo(ControllerGroup theGroup, Tab theTab, ControlWindow theControlWindow) {
		if (theGroup != null) {
			setGroup(theGroup);
			return this;
		}

		if (theControlWindow == null) {
			theControlWindow = cp5.controlWindow;
		}

		setTab(theControlWindow, theTab.getName());
		return this;
	}

	public final ControllerGroup moveTo(ControllerGroup theGroup) {
		moveTo(theGroup, null, null);
		return this;
	}

	public final ControllerGroup moveTo(Tab theTab) {
		moveTo(null, theTab, theTab.getWindow());
		return this;
	}

	public ControllerGroup moveTo(PApplet thePApplet) {
		moveTo(cp5.controlWindow);
		return this;
	}

	public ControllerGroup moveTo(ControlWindow theControlWindow) {
		moveTo(null, theControlWindow.getTab("default"), theControlWindow);
		return this;
	}

	public final ControllerGroup moveTo(String theTabName) {
		moveTo(null, cp5.controlWindow.getTab(theTabName), cp5.controlWindow);
		return this;
	}

	public final ControllerGroup moveTo(String theTabName, ControlWindow theControlWindow) {
		moveTo(null, theControlWindow.getTab(theTabName), theControlWindow);
		return this;
	}

	public final ControllerGroup moveTo(ControlWindow theControlWindow, String theTabName) {
		moveTo(null, theControlWindow.getTab(theTabName), theControlWindow);
		return this;
	}

	public final ControllerGroup moveTo(Tab theTab, ControlWindow theControlWindow) {
		moveTo(null, theTab, theControlWindow);
		return this;
	}

	/**
	 * @param theName String
	 * @return ControllerGroup
	 */
	public final ControllerGroup setTab(String theName) {
		setParent(cp5.getTab(theName));
		return this;
	}

	public final ControllerGroup setTab(ControlWindow theWindow, String theName) {
		setParent(cp5.getTab(theWindow, theName));
		return this;
	}

	/**
	 * @param theTab Tab
	 * @return ControllerGroup
	 */
	public final ControllerGroup setTab(Tab theTab) {
		setParent(theTab);
		return this;
	}

	/**
	 * @return Tab
	 */
	public Tab getTab() {
		if (this instanceof Tab) {
			return (Tab) this;
		}
		if (_myParent instanceof Tab) {
			return (Tab) _myParent;
		}
		return _myParent.getTab();
	}

	protected void updateFont(ControlFont theControlFont) {
		_myLabel.updateFont(theControlFont);
		if (_myValueLabel != null) {
			_myValueLabel.updateFont(theControlFont);
		}
		for (int i = 0; i < controllers.size(); i++) {
			if (controllers.get(i) instanceof Controller) {
				((Controller) controllers.get(i)).updateFont(theControlFont);
			} else {
				((ControllerGroup) controllers.get(i)).updateFont(theControlFont);
			}
		}
	}

	@ControlP5.Invisible
	public PVector getAbsolutePosition() {
		return new PVector(absolutePosition.x, absolutePosition.y);
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	public ControllerGroup setAbsolutePosition(PVector thePVector) {
		absolutePosition.set(thePVector.x, thePVector.y, thePVector.z);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public PVector getPosition() {
		return new PVector(position.x, position.y);
	}

	/**
	 * set the position of this controller.
	 * 
	 * @param theX float
	 * @param theY float
	 */
	public ControllerGroup setPosition(float theX, float theY) {
		position.set((int) theX, (int) theY, 0);
		positionBuffer.set(position);
		updateAbsolutePosition();
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setPosition(PVector thePVector) {
		setPosition(thePVector.x, thePVector.y);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup updateAbsolutePosition() {
		absolutePosition.set(position);
		absolutePosition.add(_myParent.getAbsolutePosition());
		for (int i = 0; i < controllers.size(); i++) {
			controllers.get(i).updateAbsolutePosition();
		}
		return this;
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	public void continuousUpdateEvents() {
		if (controllers.size() <= 0) {
			return;
		}
		for (int i = controllers.size() - 1; i >= 0; i--) {
			((ControllerInterface) controllers.get(i)).continuousUpdateEvents();
		}
	}

	/**
	 * @exclude
	 * @return ControllerGroup
	 */
	public ControllerGroup update() {
		if (controllers.size() <= 0) {
			return this;
		}
		for (int i = controllers.size() - 1; i >= 0; i--) {
			if (((ControllerInterface) controllers.get(i)).isUpdate()) {
				((ControllerInterface) controllers.get(i)).update();
			}
		}
		return this;
	}

	/**
	 * enables or disables the update function of a controller.
	 * 
	 * @param theFlag boolean
	 * @return ControllerGroup
	 */
	@Override
	public ControllerGroup setUpdate(boolean theFlag) {
		isUpdate = theFlag;
		for (int i = 0; i < controllers.size(); i++) {
			((ControllerInterface) controllers.get(i)).setUpdate(theFlag);
		}
		return this;
	}

	/**
	 * checks the update status of a controller.
	 * 
	 * @return boolean
	 */
	public boolean isUpdate() {
		return isUpdate;
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	public ControllerGroup updateEvents() {

		if (isOpen) {
			for (int i = controllers.size() - 1; i >= 0; i--) {
				((ControllerInterface) controllers.get(i)).updateEvents();
			}
		}
		if (isVisible) {

			if ((isMousePressed == _myControlWindow.mouselock)) {
				if (isMousePressed && cp5.keyHandler.isAltDown && isMoveable) {
					if (!cp5.isMoveable) {
						positionBuffer.x += _myControlWindow.mouseX - _myControlWindow.pmouseX;
						positionBuffer.y += _myControlWindow.mouseY - _myControlWindow.pmouseY;
						if (cp5.keyHandler.isShiftDown) {
							position.x = ((int) (positionBuffer.x) / 10) * 10;
							position.y = ((int) (positionBuffer.y) / 10) * 10;
						} else {
							position.set(positionBuffer);
						}
						updateAbsolutePosition();
					}
				} else {
					if (isInside) {
						setMouseOver(true);
					}
					if (inside()) {
						if (!isInside) {
							isInside = true;
							onEnter();
							setMouseOver(true);
						}
					} else {
						if (isInside && !isMousePressed) {
							onLeave();
							isInside = false;
							setMouseOver(false);
						}
					}
				}
			}
		}
		return this;
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	public ControllerGroup updateInternalEvents(PApplet theApplet) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return boolean
	 */
	public boolean isMouseOver() {
		mouseover = isInside || isInsideGroup;
		return mouseover;
	}

	public ControllerGroup setMouseOver(boolean theFlag) {

		mouseover = theFlag;
		if (!mouseover) {
			isInside = false;
			isInsideGroup = false;
			_myControlWindow.removeMouseOverFor(this);
			for (int i = controllers.size() - 1; i >= 0; i--) {
				controllers.get(i).setMouseOver(false);
			}
		} else {
			// TODO since inside can be either isInside or isInsideGroup, there are 2 options here,
			// which i am not sure how to handle them yet.
			_myControlWindow.setMouseOverController(this);
		}
		return this;
	}

	/**
	 * @exclude
	 * @param theApplet PApplet
	 */
	@ControlP5.Invisible
	public final void draw(PApplet theApplet) {

		if (isVisible) {
			theApplet.pushMatrix();
			theApplet.translate(position.x, position.y);
			preDraw(theApplet);
			drawControllers(theApplet);
			postDraw(theApplet);
			if (_myValueLabel != null) {
				_myValueLabel.draw(theApplet);
			}
			theApplet.popMatrix();
		}
	}

	protected void drawControllers(PApplet theApplet) {
		if (isOpen) {

			for (ControlWindowCanvas cc : _myControlCanvas) {
				if (cc.mode() == ControlWindowCanvas.PRE) {
					cc.draw(theApplet);
				}
			}
			for (ControllerInterface ci : controllers.get()) {
				if (ci.isVisible()) {
					ci.updateInternalEvents(theApplet);
					ci.draw(theApplet);
				}
			}

			for (CDrawable cd : controllers.getDrawables()) {
				cd.draw(theApplet);
			}

			for (ControlWindowCanvas cc : _myControlCanvas) {
				if (cc.mode() == ControlWindowCanvas.POST) {
					cc.draw(theApplet);
				}
			}
		}
	}

	protected void preDraw(PApplet theApplet) {
	}

	protected void postDraw(PApplet theApplet) {
	}

	/**
	 * Adds a canvas to a controllerGroup such as a tab or group. Use processing's draw methods to
	 * add visual content.
	 * 
	 * @param theCanvas
	 * @return ControlWindowCanvas
	 */
	public ControlWindowCanvas addCanvas(ControlWindowCanvas theCanvas) {
		_myControlCanvas.add(theCanvas);
		theCanvas.setup(cp5.papplet);
		return theCanvas;
	}

	/**
	 * Removes a canvas from a controller group.
	 * 
	 * @param theCanvas
	 * @return ControllerGroup
	 */
	public ControllerGroup removeCanvas(ControlWindowCanvas theCanvas) {
		_myControlCanvas.remove(theCanvas);
		return this;
	}

	/**
	 * Adds a controller to the group, but use Controller.setGroup() instead.
	 * 
	 * @param theElement ControllerInterface
	 * @return ControllerGroup
	 */
	public ControllerGroup add(ControllerInterface theElement) {
		controllers.add(theElement);
		return this;
	}

	/**
	 * Removes a controller from the group, but use Controller.setGroup() instead.
	 * 
	 * @param theElement ControllerInterface
	 * @return ControllerGroup
	 */

	public ControllerGroup remove(ControllerInterface theElement) {
		if (theElement != null) {
			theElement.setMouseOver(false);
		}
		controllers.remove(theElement);
		return this;
	}

	/**
	 * @param theElement CDrawable
	 * @return ControllerGroup
	 */
	@ControlP5.Invisible
	public ControllerGroup addDrawable(CDrawable theElement) {
		controllers.addDrawable(theElement);
		return this;
	}

	/**
	 * @param theElement CDrawable
	 * @return ControllerGroup
	 */
	public ControllerGroup remove(CDrawable theElement) {
		controllers.removeDrawable(theElement);
		return this;
	}

	/**
	 * removes the group from controlP5.
	 */
	public void remove() {
		_myControlWindow.removeMouseOverFor(this);
		if (_myParent != null) {
			_myParent.remove(this);
		}
		if (cp5 != null) {
			cp5.remove(this);
		}

		for (int i = controllers.size() - 1; i >= 0; i--) {
			controllers.get(i).remove();
		}
		controllers.clear();
		controllers.clearDrawable();
		controllers = new ControllerList();
		if (this instanceof Tab) {
			_myControlWindow.removeTab((Tab) this);
		}
	}

	/**
	 * @return String
	 */
	public String getName() {
		return _myName;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getAddress() {
		return _myAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControllerGroup setAddress(String theAddress) {
		if (_myAddress.isEmpty()) {
			_myAddress = theAddress;
		}
		return this;
	}

	/**
	 * @return ControlWindow
	 */
	public ControlWindow getWindow() {
		return _myControlWindow;
	}

	/**
	 * @exclude
	 * @param theEvent KeyEvent
	 */
	@ControlP5.Invisible
	public void keyEvent(KeyEvent theEvent) {
		for (int i = 0; i < controllers.size(); i++) {
			((ControllerInterface) controllers.get(i)).keyEvent(theEvent);
		}
	}

	/**
	 * @exclude
	 * @param theStatus boolean
	 * @return boolean
	 */
	public boolean setMousePressed(boolean theStatus) {
		if (!isVisible) {
			return false;
		}
		for (int i = controllers.size() - 1; i >= 0; i--) {
			if (((ControllerInterface) controllers.get(i)).setMousePressed(theStatus)) {
				return true;
			}
		}
		if (theStatus == true) {
			if (isInside) {
				isMousePressed = true;
				mousePressed();
				return true;
			}
		} else {
			if (isMousePressed == true) {
				isMousePressed = false;
				mouseReleased();
			}
		}
		return false;
	}

	void mousePressed() {
	}

	void mouseReleased() {
	}

	void onEnter() {
	}

	void onLeave() {
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setId(int theId) {
		_myId = theId;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() {
		return _myId;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setColor(CColor theColor) {
		for (ControllerInterface ci : controllers.get()) {
			ci.setColor(theColor);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setColorActive(int theColor) {
		color.setActive(theColor);
		for (ControllerInterface ci : controllers.get()) {
			ci.setColorActive(theColor);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setColorForeground(int theColor) {
		color.setForeground(theColor);
		for (ControllerInterface ci : controllers.get()) {
			ci.setColorForeground(theColor);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setColorBackground(int theColor) {
		color.setBackground(theColor);
		for (ControllerInterface ci : controllers.get()) {
			ci.setColorBackground(theColor);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setColorLabel(int theColor) {
		color.setCaptionLabel(theColor);
		if (_myLabel != null) {
			_myLabel.setColor(color.getCaptionLabel());
		}
		for (ControllerInterface ci : controllers.get()) {
			ci.setColorLabel(theColor);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setColorValue(int theColor) {
		color.setValueLabel(theColor);
		if (_myValueLabel != null) {
			_myValueLabel.setColor(color.getValueLabel());
		}
		for (ControllerInterface ci : controllers.get()) {
			ci.setColorValue(theColor);
		}
		return this;
	}

	/**
	 * @param theLabel String
	 * @return ControllerGroup
	 */
	public ControllerGroup setLabel(String theLabel) {
		_myLabel.setFixedSize(false);
		_myLabel.set(theLabel);
		_myLabel.setFixedSize(true);
		return this;
	}

	/**
	 * @return boolean
	 */
	public boolean isVisible() {
		if (_myParent != null && _myParent != this) {
			if (getParent().isVisible() == false) {
				return false;
			}
		}
		return isVisible;
	}

	/**
	 * @param theFlag boolean
	 * @return ControllerGroup
	 */
	public ControllerGroup setVisible(boolean theFlag) {
		isVisible = theFlag;
		return this;
	}

	public ControllerGroup hide() {
		isVisible = false;
		return this;
	}

	public ControllerGroup show() {
		isVisible = true;
		return this;
	}

	/**
	 * set the moveable status of the group, when false, the group can't be moved.
	 * 
	 * @param theFlag boolean
	 * @return ControllerGroup
	 */
	public ControllerGroup setMoveable(boolean theFlag) {
		isMoveable = theFlag;
		return this;
	}

	public boolean isMoveable() {
		return isMoveable;
	}

	public ControllerGroup setOpen(boolean theFlag) {
		isOpen = theFlag;
		return this;
	}

	/**
	 * @return boolean
	 */
	public boolean isOpen() {
		return isOpen;
	}

	public ControllerGroup open() {
		setOpen(true);
		return this;
	}

	public ControllerGroup close() {
		setOpen(false);
		return this;
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@ControlP5.Invisible
	public int getPickingColor() {
		return _myPickingColor;
	}

	/**
	 * {@inheritDoc}
	 */
	public CColor getColor() {
		return color;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setValue(float theValue) {
		_myValue = theValue;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getValue() {
		return _myValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getStringValue() {
		return _myStringValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup setStringValue(String theValue) {
		_myStringValue = theValue;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public float[] getArrayValue() {
		return _myArrayValue;
	}

	/**
	 * @param theArray
	 * @return ControllerGroup
	 */
	public ControllerGroup setArrayValue(float[] theArray) {
		_myArrayValue = theArray;
		return this;
	}

	public Controller getController(String theController) {
		return cp5.getController(theController);
	}

	public Label getCaptionLabel() {
		return _myLabel;
	}

	public Label getValueLabel() {
		return _myValueLabel;
	}

	/**
	 * @return ControllerGroup
	 */
	public ControllerGroup enableCollapse() {
		isCollapse = true;
		return this;
	}

	/**
	 * @return ControllerGroup
	 */
	public ControllerGroup disableCollapse() {
		isCollapse = false;
		return this;
	}

	public boolean isCollapse() {
		return isCollapse;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getWidth() {
		return _myWidth;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getHeight() {
		return _myHeight;
	}

	/**
	 * @param theWidth
	 * @return ControllerGroup
	 */
	public ControllerGroup setWidth(int theWidth) {
		_myWidth = theWidth;
		return this;
	}

	/**
	 * @param theHeight
	 * @return ControllerGroup
	 */
	public ControllerGroup setHeight(int theHeight) {
		_myHeight = theHeight;
		return this;
	}

	protected boolean inside() {
		return (_myControlWindow.mouseX > position.x + _myParent.absolutePosition.x && _myControlWindow.mouseX < position.x + _myParent.absolutePosition.x + _myWidth
				&& _myControlWindow.mouseY > position.y + _myParent.absolutePosition.y - _myHeight && _myControlWindow.mouseY < position.y + _myParent.absolutePosition.y);
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerProperty getProperty(String thePropertyName) {
		return cp5.getProperties().getProperty(this, thePropertyName);
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerProperty getProperty(String theSetter, String theGetter) {
		return cp5.getProperties().getProperty(this, theSetter, theGetter);
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup registerProperty(String thePropertyName) {
		cp5.getProperties().register(this, thePropertyName);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup registerProperty(String theSetter, String theGetter) {
		cp5.getProperties().register(this, theSetter, theGetter);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup removeProperty(String thePropertyName) {
		cp5.getProperties().remove(this, thePropertyName);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ControllerGroup removeProperty(String theSetter, String theGetter) {
		cp5.getProperties().remove(this, theSetter, theGetter);
		return this;
	}

	@Override
	public String toString() {
		return getName() + " [" + getClass().getSimpleName() + "]";
	}

	public String getInfo() {
		return "type:\tControllerGroup" + "\nname:\t" + _myName + "\n" + "label:\t" + _myLabel.getText() + "\n" + "id:\t" + _myId + "\n" + "value:\t" + _myValue + "\n"
				+ "arrayvalue:\t" + ControlP5IOHandler.arrayToString(_myArrayValue) + "\n" + "position:\t" + position + "\n" + "absolute:\t" + absolutePosition + "\n" + "width:\t"
				+ getWidth() + "\n" + "height:\t" + getHeight() + "\n" + "color:\t" + getColor() + "\n" + "visible:\t" + isVisible + "\n" + "moveable:\t" + isMoveable + "\n";
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public PVector absolutePosition() {
		return getAbsolutePosition();
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public PVector position() {
		return getPosition();
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public CColor color() {
		return color;
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public float value() {
		return _myValue;
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public String stringValue() {
		return getStringValue();
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public float[] arrayValue() {
		return getArrayValue();
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public String name() {
		return _myName;
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public int id() {
		return _myId;
	}

	/**
	 * @exclude
	 */
	@Deprecated
	public Controller controller(String theController) {
		return cp5.getController(theController);
	}

	/**
	 * @exclude
	 */
	@ControlP5.Invisible
	@Deprecated
	public ControllerInterface parent() {
		return _myParent;
	}

	/**
	 * @exclude
	 * @deprecated
	 */
	@Deprecated
	public Label captionLabel() {
		return _myLabel;
	}

	/**
	 * @exclude
	 * @deprecated
	 */
	@Deprecated
	public Label valueLabel() {
		return _myValueLabel;
	}

}
