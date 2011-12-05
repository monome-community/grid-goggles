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
 * A multiple-choice radioButton. items are added to a checkBox and can be organized in rows and
 * columns. items of a checkBox are of type Toggle.
 * 
 * @example controllers/ControlP5checkBox
 * 
 * @see controlP5.Toggle
 * 
 */
public class CheckBox extends RadioButton {

	/**
	 * A CheckBox should only be added to controlP5 by using controlP5.addCheckBox()
	 * 
	 * @exclude
	 * @param theControlP5
	 * @param theParent
	 * @param theName
	 * @param theX
	 * @param theY
	 */
	public CheckBox(final ControlP5 theControlP5, final ControllerGroup theParent, final String theName, final int theX, final int theY) {
		super(theControlP5, theParent, theName, theX, theY);
		isMultipleChoice = true;
	}

	public final void activateAll() {
		int n = _myRadioToggles.size();
		for (int i = 0; i < n; i++) {
			_myRadioToggles.get(i).activate();
		}
		updateValues();
	}

	/**
	 * Activates a single checkbox item by index
	 */
	public final void activate(int theIndex) {
		if (theIndex < _myRadioToggles.size()) {
			_myRadioToggles.get(theIndex).activate();
			updateValues();
		}
	}

	/**
	 * deactivate a single checkbox item by index
	 */
	public final void deactivate(int theIndex) {
		if (theIndex < _myRadioToggles.size()) {
			_myRadioToggles.get(theIndex).deactivate();
			updateValues();
		}
	}

	/**
	 * toggle a single checkbox item by index
	 */
	@Override
	public final void toggle(int theIndex) {
		if (theIndex < _myRadioToggles.size()) {
			Toggle t = _myRadioToggles.get(theIndex);
			if (t.getState() == true) {
				t.deactivate();
			} else {
				t.activate();
			}
			updateValues();
		}
	}

	/**
	 * deactivate a single checkbox item by name
	 */
	public final void toggle(String theName) {
		int n = _myRadioToggles.size();
		for (int i = 0; i < n; i++) {
			Toggle t = _myRadioToggles.get(i);
			if (theName.equals(t.getName())) {
				if (t.getState() == true) {
					t.deactivate();
				} else {
					t.activate();
				}
				updateValues();
				return;
			}
		}
	}

	/**
	 * Activates a single checkbox item by name
	 */
	public final void activate(String theName) {
		int n = _myRadioToggles.size();
		for (int i = 0; i < n; i++) {
			Toggle t = _myRadioToggles.get(i);
			if (theName.equals(t.getName())) {
				t.activate();
				updateValues();
				return;
			}
		}
	}

	/**
	 * Deactivates a single checkbox item by name
	 */
	public final void deactivate(String theName) {
		int n = _myRadioToggles.size();
		for (int i = 0; i < n; i++) {
			Toggle t = _myRadioToggles.get(i);
			if (theName.equals(t.getName())) {
				t.deactivate();
				updateValues();
				return;
			}
		}
	}

	private final void updateValues() {
		_myValue = -1;
		updateValues(true);
	}

	/**
	 * Sets the value for all CheckBox items according to the values of the array passed on. 0 will
	 * turn off an item, any other value will turn it on.
	 */
	@Override
	public CheckBox setArrayValue(float[] theArray) {
		for (int i = 0; i < theArray.length; i++) {
			if (_myArrayValue[i] != theArray[i]) {
				if (theArray[i] == 0) {
					_myRadioToggles.get(i).deactivate();
				} else {
					_myRadioToggles.get(i).activate();
				}
			}
		}
		super.setArrayValue(theArray);
		return this;
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@Override
	public String getInfo() {
		return "type:\tCheckBox\n" + super.getInfo();
	}

	/**
	 * @exclude {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
