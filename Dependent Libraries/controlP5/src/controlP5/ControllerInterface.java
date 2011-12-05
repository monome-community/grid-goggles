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

import processing.core.PApplet;
import processing.core.PVector;

/**
 * 
 * The ControllerInterface is inherited by all ControllerGroup and Controller
 * classes.
 * 
 */
public interface ControllerInterface {

	
	@ControlP5.Invisible
	public void init();

	public int getWidth();

	public int getHeight();

	public ControllerInterface setValue(float theValue);

	public float getValue();

	public ControllerInterface setStringValue(String theValue);

	public String getStringValue();

	public float[] getArrayValue();

	public int getId();

	public PVector getPosition();

	@ControlP5.Invisible
	public ControllerInterface setPosition(float theX, float theY);

	@ControlP5.Invisible
	public ControllerInterface setPosition(PVector thePVector);

	public PVector getAbsolutePosition();

	public ControllerInterface setAbsolutePosition(PVector thePVector);

	public ControllerInterface updateAbsolutePosition();

	public ControllerInterface getParent();

	public ControllerInterface update();

	public ControllerInterface setUpdate(boolean theFlag);

	public boolean isUpdate();

	@ControlP5.Invisible
	public ControllerInterface updateEvents();

	@ControlP5.Invisible
	public void continuousUpdateEvents();

	/**
	 * a method for putting input events like e.g. mouse or keyboard events and
	 * queries. this has been taken out of the draw method for better
	 * overwriting capability.
	 * 
	 * 
	 */
	@ControlP5.Invisible
	public ControllerInterface updateInternalEvents(PApplet theApplet);

	@ControlP5.Invisible
	public void draw(PApplet theApplet);

	public ControllerInterface add(ControllerInterface theElement);

	public ControllerInterface remove(ControllerInterface theElement);

	public void remove();

	public String getName();

	public String getAddress();

	public ControlWindow getWindow();

	public Tab getTab();

	public boolean setMousePressed(boolean theStatus);

	@ControlP5.Invisible
	public void keyEvent(KeyEvent theEvent);

	@ControlP5.Invisible
	public ControllerInterface setAddress(String theAddress);

	public ControllerInterface setId(int theValue);

	public ControllerInterface setLabel(String theString);

	public ControllerInterface setColorActive(int theColor);

	public ControllerInterface setColorForeground(int theColor);

	public ControllerInterface setColorBackground(int theColor);

	public ControllerInterface setColorLabel(int theColor);

	public ControllerInterface setColorValue(int theColor);
	
	public ControllerInterface setColor(CColor theColor);
	
	public CColor getColor();
	
	public ControllerInterface show();

	public ControllerInterface hide();

	public boolean isVisible();

	public ControllerInterface moveTo(ControllerGroup theGroup, Tab theTab, ControlWindow theWindow);

	public ControllerInterface moveTo(ControllerGroup theGroup);


	@ControlP5.Invisible
	public int getPickingColor();

	@ControlP5.Invisible
	public ControllerInterface parent();

	public ControllerProperty getProperty(String thePropertyName);

	public ControllerProperty getProperty(String theSetter, String theGetter);

	public ControllerInterface registerProperty(String thePropertyName);

	public ControllerInterface registerProperty(String theSetter, String theGetter);

	public ControllerInterface removeProperty(String thePropertyName);

	public ControllerInterface removeProperty(String theSetter, String theGetter);

	public boolean isMouseOver();
	
	public ControllerInterface setMouseOver(boolean theFlag);
	
	/**
	 * @exclude
	 * @deprecated
	 */
	@Deprecated
	public String name();
	
	/**
	 * @exclude
	 * @deprecated
	 */
	@Deprecated
	public String stringValue();
	
	/**
	 * @exclude
	 * @deprecated
	 */
	@Deprecated
	public int id();

}
