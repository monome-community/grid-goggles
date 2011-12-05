package controlP5;

/**
 * controlP5 is a processing gui library.
 * 
 * 2006-2011 by Andreas Schlegel
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * @author Andreas Schlegel (http://www.sojamo.de)
 * @modified 11/13/2011
 * @version 0.6.12
 * 
 */

/**
 * Used by the ListBox class.
 */
public class ListBoxItem {

	protected String name;

	protected String text;

	protected int value;

	protected boolean isActive;

	protected CColor color;

	protected int id = -1;

	protected ListBox parent;

	private boolean toUpperCase = true;

	protected ListBoxItem(ListBox theListBox, String theName, int theValue) {
		name = theName;
		text = theName;
		value = theValue;
		parent = theListBox;
		color = new CColor(theListBox.color);
	}

	public CColor getColor() {
		return color;
	}

	public void setColor(CColor theColor) {
		color.set(theColor);
		parent.updateListBoxItems();
	}

	public void setColorActive(int theColor) {
		color.setActive(theColor);
		parent.updateListBoxItems();
	}

	public void setColorForeground(int theColor) {
		color.setForeground(theColor);
		parent.updateListBoxItems();
	}

	public void setColorBackground(int theColor) {
		color.setBackground(theColor);
		parent.updateListBoxItems();
	}

	public void setColorLabel(int theColor) {
		color.setCaptionLabel(theColor);
		parent.updateListBoxItems();
	}

	/**
	 * set the id of a listboxitem
	 * 
	 * @param theId
	 */
	public void setId(int theId) {
		id = theId;
	}

	/**
	 * returns the id of a listboxitem.
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * change text text of a lsitboxitem's label.
	 * 
	 * @param theText
	 */
	public ListBoxItem setText(String theText) {
		text = theText;
		parent.updateListBoxItems();
		return this;
	}

	/**
	 * returns the text displayed for this listboxitem. use setText(String) to
	 * apply changes.
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * returns the name of the listboxitem. use
	 * ListBox.itme(ListBoxItem.getName()) to access a listboxitem by name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the value of the listboxitem.
	 * 
	 * @return int
	 */
	public int getValue() {
		return value;
	}

	/**
	 * TODO no effect yet.
	 * 
	 * @return
	 */
	public boolean isActive() {
		ControlP5.logger().info("no effect for isActive, please dont use yet.");
		return isActive;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "\ntype:\t" + this.getClass() + "\nname:\t" + name + "\n" + "label:\t" + text + "\n" + "id:\t" + id + "\n"
				+ "value:\t" + value + "\n" + "color:\t" + getColor() + "\n";
	}

	/**
	 * by default the text of a listboxitem is set to uppercase, use
	 * toUpperCase(false) to make changes.
	 * 
	 * @param theFlag
	 */
	public void toUpperCase(boolean theFlag) {
		toUpperCase = theFlag;
		parent.updateListBoxItems();
	}

	/**
	 * returns the uppercase status of this listboxitem.
	 * 
	 * @return
	 */
	public boolean getToUpperCase() {
		return toUpperCase;
	}
}