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


/**
 *@exmaple controllers/ControlP5dropdownList
 */
public class DropdownList extends ListBox {

	protected DropdownList(ControlP5 theControlP5, ControllerGroup theGroup, String theName, int theX, int theY, int theW, int theH) {
		super(theControlP5, theGroup, theName, theX, theY, theW, theH);
		actAsPulldownMenu(true);
	}

	/**
	 * @exclude
	 * {@inheritDoc}
	 */
	@Override
	public String getStringValue() {
		return getCaptionLabel().toString();
	}
	
	
	@Override
	public ControllerGroup setValue(float theValue) {
		for (ListBoxItem l : items) {
			if ((l.value == theValue)) {
				_myValue = l.value;
				setLabel(l.name);
				cp5.getControlBroadcaster().broadcast(new ControlEvent(this), ControlP5Constants.FLOAT);
				break;
			}
		}
		return this;
	}

	@Override
	public float getValue() {
		return _myValue;
	}

	/**
	 * @deprecated
	 * @exclude
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	public String stringValue() {
		return captionLabel().toString();
	}
}
