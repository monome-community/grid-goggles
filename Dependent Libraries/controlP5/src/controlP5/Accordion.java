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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * The Accordion is a list of ControlGroups which can be expanded and collapsed. Only one item can
 * be open at a time.
 * </p>
 * 
 * @see controlP5.ControllerGroup
 * @see controlP5.ControlGroup
 * @example controllers/ControlP5accordion
 */
public class Accordion extends ControlGroup {

	private List<ControlGroup> items;

	private int spacing = 1;

	private int minHeight = 100;

	private int itemheight;

	Accordion(ControlP5 theControlP5, Tab theTab, String theName, int theX, int theY, int theW) {
		super(theControlP5, theTab, theName, theX, theY, theW, 9);
		hideBar();
		items = new ArrayList<ControlGroup>();
	}

	/**
	 * Adds items of type ControlGroup to the Accordion, only ControlGroups can be added.
	 * 
	 * @exclude
	 * @param theGroup
	 * @return Accordion
	 */
	public Accordion addItem(ControlGroup theGroup) {
		theGroup.close();
		theGroup.moveTo(this);
		theGroup.activateEvent(true);
		theGroup.addListener(this);
		theGroup.setMoveable(false);
		if (theGroup.getBackgroundHeight() < minHeight) {
			theGroup.setBackgroundHeight(minHeight);
		}
		items.add(theGroup);
		updateItems();
		return this;
	}

	/**
	 * Removes a ControlGroup from the accordion AND from controlP5 remove(ControllerInterface
	 * theGroup) overwrites it's super method. if you want to remove a ControlGroup only from the
	 * accordion, use removeItem(ControlGroup).
	 * 
	 * @see controlP5.Accordion#removeItem(ControlGroup)
	 * @return ControllerInterface
	 */
	@Override
	public Accordion remove(ControllerInterface theGroup) {
		if (theGroup instanceof ControlGroup) {
			items.remove(theGroup);
			((ControlGroup) theGroup).removeListener(this);
			updateItems();
		}
		super.remove(theGroup);
		return this;
	}

	/**
	 * Removes a ControlGroup from the accordion and puts it back into the default tab of controlP5.
	 * if you dont have access to a ControlGroup via a variable, use
	 * controlP5.group("theNameOfTheGroup") which will return a
	 * 
	 * @return Accordion
	 */
	public Accordion removeItem(ControlGroup theGroup) {
		if (theGroup == null)
			return this;
		items.remove(theGroup);
		theGroup.removeListener(this);
		theGroup.moveTo(cp5.controlWindow);
		updateItems();
		return this;
	}

	/**
	 * UpdateItems is called when changes such as remove, change of height is performed on an
	 * accordion. updateItems() is called automatically for such cases, but by calling updateItems
	 * manually an update will be forced.
	 * 
	 * @return Accordion
	 */
	public Accordion updateItems() {
		int n = 0;
		for (ControlGroup cg : items) {
			n += cg.getBarHeight() + spacing;
			cg.setPosition(0, n);
			if (cg.isOpen()) {
				n += cg.getBackgroundHeight();
			}
		}
		return this;
	}

	/**
	 * Sets the minimum height of a collapsed item, default value is 100.
	 * 
	 * @param theHeight
	 * @return Accordion
	 */
	public Accordion setMinItemHeight(int theHeight) {
		minHeight = theHeight;
		for (ControlGroup cg : items) {
			if (cg.getBackgroundHeight() < minHeight) {
				cg.setBackgroundHeight(minHeight);
			}
		}
		updateItems();
		return this;
	}

	
	public int getMinItemHeight() {
		return minHeight;
	}

	public Accordion setItemHeight(int theHeight) {
		itemheight = theHeight;
		for (ControlGroup cg : items) {
			cg.setBackgroundHeight(itemheight);
		}
		updateItems();
		return this;
	}

	public int getItemHeight() {
		return itemheight;
	}

	@Override
	public Accordion setWidth(int theWidth) {
		super.setWidth(theWidth);
		for (ControlGroup cg : items) {
			cg.setWidth(theWidth);
		}
		return this;
	}

	/**
	 * @exclude
	 * {@inheritDoc}
	 */
	@Override
	@ControlP5.Invisible
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isGroup()) {
			int n = 0;
			for (ControlGroup cg : items) {
				n += cg.getBarHeight() + spacing;
				cg.setPosition(0, n);
				if (cg == theEvent.getGroup() && cg.isOpen()) {
					n += cg.getBackgroundHeight();
				} else {
					cg.close();
				}
			}
		}
	}
}
