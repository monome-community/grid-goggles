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
import processing.core.PApplet;
import java.awt.event.KeyEvent;

/**
 * A singleline input textfield, use arrow keys to go back and forth, use backspace to delete
 * characters. Using the up and down arrows lets you cycle through the history of the textfield.
 * 
 * @example controllers/ControlP5textfield
 * @nosuperclasses Controller Controller
 */
public class Textfield extends Controller {

	/*
	 * TODO needs a lot of work! has gone through massive amounts of little changes and adjustments.
	 * implement new fonts, current one is too small. make the text go to the left when cursor goes
	 * beyond right border. make textfield work for controlWindow
	 * 
	 * text-editor reference: http://www.cs.cmu.edu/~wjh/papers/byte.html via
	 * http://forum.processing.org/#Topic/25080000000412071
	 * 
	 * TODO pattern filter, see this post http://forum.processing.org/topic/controlip5
	 * -questions-regarding-textfield-dropdownlist-and-buttons
	 */

	protected ArrayList<String> myTextList = new ArrayList<String>();

	int myIndex = 1;

	int myPosition = 0;

	StringBuffer myTextline = new StringBuffer();

	private boolean isTexfieldActive = false;

	private boolean isPasswordMode = false;

	protected boolean isAutoClear = true;

	protected boolean isKeepFocus = false;

	/**
	 * 
	 * @param theControllerProperties ControllerProperties
	 */
	public Textfield(ControlP5 theControlP5, ControllerGroup theParent, String theName, String theDefaultValue, int theX, int theY, int theWidth, int theHeight) {
		super(theControlP5, theParent, theName, theX, theY, theWidth, theHeight);
		_myCaptionLabel = new Label(cp5, theName.toUpperCase(), 0, 0, color.getCaptionLabel());
		_myCaptionLabel.setFixedSize(false);
		_myBroadcastType = STRING;
		_myValueLabel.setWidth(width - 7);
		_myValueLabel.set("");
		_myValueLabel.setColor(color.getValueLabel());
		_myValueLabel.toUpperCase(false);
		_myValueLabel.setFixedSize(true);
		_myValueLabel.setFont(ControlP5.bitFont == ControlP5.standard58 ? ControlP5.standard56:ControlP5.bitFont);
	}

	/**
	 * 
	 * @param theValue float
	 */
	public Controller setValue(float theValue) {
		return this;
	}

	/**
	 * set the mode of the textfield to password mode, each character is shown as a "*" like e.g. in
	 * online password forms.
	 * 
	 * @param theFlag boolean
	 */
	public void setPasswordMode(boolean theFlag) {
		isPasswordMode = theFlag;
	}

	/**
	 * set the textfield's focus to true or false.
	 * 
	 * @param theFlag boolean
	 */
	public void setFocus(boolean theFlag) {
		if (theFlag == true) {
			mousePressed();
		} else {
			mouseReleasedOutside();
		}
	}

	/**
	 * use true as parameter to force the textfield to stay in focus. to go back to normal focus
	 * behavior, use false.
	 * 
	 * @param theFlag
	 */
	public void keepFocus(boolean theFlag) {
		isKeepFocus = theFlag;
		if (isKeepFocus) {
			setFocus(true);
		}
	}

	/**
	 * check if the textfield is active and in focus.
	 * 
	 * @return boolean
	 */
	public boolean isFocus() {
		return isTexfieldActive;
	}

	/**
	 * sets the value of the textfield and will broadcast the new string value immediately. what is
	 * the difference between setValue and setText? setValue does broadcast the value that has been
	 * set, setText does not broadcast the value, but only updates the content of a textfield. for
	 * further information about how setText works, see the setText documentation.
	 * 
	 * @param theValue String
	 */
	public Textfield setValue(String theValue) {
		myTextline = new StringBuffer(theValue);
		// myPosition = myTextline.length() - 1;
		_myStringValue = theValue;
		myPosition = myTextline.length();
		_myValueLabel.setWithCursorPosition(myTextline.toString(), myPosition);
		broadcast(_myBroadcastType);
		return this;
	}

	/**
	 * setText does set the text of a textfield, but will not broadcast its value. use setText to
	 * force the textfield to change its text. you can call setText any time, nevertheless when
	 * autoClear is set to true (which is the default), it will NOT work when called from within
	 * controlEvent or within a method that has been identified by ControlP5 to forward messages to,
	 * when return has been pressed to confirm a textfield.<br />
	 * use setAutoClear(false) to enable setText to be executed for the above case. use
	 * yourTextfield.isAutoClear() to check if autoClear is true or false. <br />
	 * setText replaces the current/latest content of a textfield but does NOT overwrite the
	 * content. when scrolling through the list of textlines (use key up and down), the previous
	 * content that has been replaced will be put back into place again - since it has not been
	 * confirmed with return.
	 * 
	 * @param theValue
	 */
	public void setText(String theValue) {
		myTextline = new StringBuffer(theValue);
		_myStringValue = theValue;
		myPosition = myTextline.length();
		updateField();
	}

	/**
	 * {@inheritDoc}
	 */
	public Textfield update() {
		_myStringValue = myTextline.toString();
		return setValue(_myStringValue);
	}

	/**
	 * click the texfield to activate.
	 */
	protected void mousePressed() {
		boolean notyet = false;
		if (isActive && notyet == false) {
			Label.BitFontLabel bfl = (Label.BitFontLabel) _myValueLabel.getFont();
			int offset = 0;
			int m = BitFontRenderer.getWidth(myTextline.toString(), bfl, myPosition);
			offset = (m > _myValueLabel.getWidth()) ? _myValueLabel.getWidth() - m : 0;
			myPosition = BitFontRenderer.getPosition(myTextline.toString(), bfl, (int) (getControlWindow().mouseX - absolutePosition.x) - offset);
			updateField();
		}
		isTexfieldActive = isActive = true;
	}

	/**
	 * 
	 */
	protected void mouseReleasedOutside() {
		if (isKeepFocus == false) {
			isTexfieldActive = isActive = false;
		}
	}

	/**
	 * 
	 * @param theApplet PApplet
	 */
	public void draw(PApplet theApplet) {
		theApplet.fill(color.getBackground());
		theApplet.pushMatrix();
		theApplet.translate(position.x, position.y);
		theApplet.rect(0, 0, width, height);
		theApplet.noStroke();
		theApplet.fill(isTexfieldActive ? color.getActive() : color.getForeground());
		theApplet.rect(0, 0, width, 1);
		theApplet.rect(0, height - 1, width, 1);
		theApplet.rect(-1, 0, 1, height);
		theApplet.rect(width, 0, 1, height);
		theApplet.fill(255, 60);
		int yy = _myValueLabel.getHeight();
		if (isTexfieldActive) {
			theApplet.rect(cursorPosition + 2, 2, 5, height - 4);
		}
		
		_myValueLabel.draw(theApplet, 2, yy);
		_myCaptionLabel.draw(theApplet, 0, height + 4);
		theApplet.noFill();
		theApplet.popMatrix();
	}

	/**
	 * flip throught the texfield history with cursor keys UP and DOWN. go back and forth with
	 * cursor keys LEFT and RIGHT.
	 * 
	 * 
	 */
	public void keyEvent(KeyEvent theKeyEvent) {
		if (isUserInteraction && isTexfieldActive && isActive && theKeyEvent.getID() == KeyEvent.KEY_PRESSED) {
			if (cp5.keyHandler.keyCode == UP) {
				if (myTextList.size() > 0 && myIndex > 0) {
					myIndex--;
					myTextline = new StringBuffer((String) myTextList.get(myIndex));
					adjust();
				}
			} else if (cp5.keyHandler.keyCode == DOWN) {
				myIndex++;
				if (myIndex >= myTextList.size()) {
					myIndex = myTextList.size();
					myTextline = new StringBuffer();
				} else {
					myTextline = new StringBuffer((String) myTextList.get(myIndex));
				}
				adjust();
			} else if (cp5.keyHandler.keyCode == LEFT) {
				if (myPosition > 0) {
					myPosition--;
				}
			} else if (cp5.keyHandler.keyCode == RIGHT) {
				if (myPosition < myTextline.length()) {
					myPosition++;
				}
			} else if (cp5.keyHandler.keyCode == DELETE || cp5.keyHandler.keyCode == BACKSPACE) {
				if (myTextline.length() > 0) {
					if (myPosition > 0) {
						myTextline.deleteCharAt(myPosition - 1);
						myPosition--;
					}
				}
			} else if (cp5.keyHandler.keyCode == ENTER) {
				submit();
			} else if (cp5.keyHandler.keyCode != SHIFT && cp5.keyHandler.keyCode != ALT && cp5.keyHandler.keyCode != TAB && cp5.keyHandler.keyCode != CONTROL) {
				boolean a = true;
				if (_myValueLabel.getFont() instanceof Label.BitFontLabel) {
					if ((int) cp5.keyHandler.key > 31 && (int) cp5.keyHandler.key < 127) {
						a = true;
					} else {
						a = false;
					}
				}
				if (a) {
					myTextline.insert(myPosition, cp5.keyHandler.key);
					myPosition++;
				}
			}
			updateField();
		}
	}

	private float cursorPosition = 0;

	private float actualCursorPosition = 0;

	private void updateField() {
		if ((_myValueLabel.getFont() instanceof Label.BitFontLabel) == false) {
			ControlFont cf = ((Label.ControlFontLabel) _myValueLabel.getFont()).getFont();
			_myControlWindow.papplet().textFont(cf.getPFont(), _myValueLabel.getFontSize());
			
			int start = 0;

			char[] chrs = myTextline.toString().toCharArray();
			actualCursorPosition = _myControlWindow.papplet().textWidth(chrs, 0, myPosition);
			if (actualCursorPosition > getWidth()) {
				for (int i = 0; i < myPosition; i++) {
					if (_myControlWindow.papplet().textWidth(chrs, i, myPosition - i) <= getWidth()) {
						start = i;
						break;
					}
				}
			}
			cursorPosition = PApplet.min(actualCursorPosition, _myValueLabel.getWidth() - 2);
			if (start > 0) {
				_myValueLabel.setText(myTextline.toString().substring(start, myPosition));
			} else {
				int end = myTextline.length();
				for (int i = 0; i < myTextline.length(); i++) {
					if (_myControlWindow.papplet().textWidth(chrs, 0, i) >= getWidth()) {
						end = i - 1;
						break;
					}
				}
				_myValueLabel.setText(myTextline.toString().substring(0, end));
			}
			return;
		}
		
		Label.BitFontLabel bfl = (Label.BitFontLabel) _myValueLabel.getFont();
		
		actualCursorPosition = BitFontRenderer.getWidth(myTextline.toString(), bfl, myPosition);
		if (isPasswordMode) {
			String myPasswordTextline = "";
			for (int i = 0; i < myTextline.length(); i++) {
				myPasswordTextline += "*";
			}
			_myValueLabel.setWithCursorPosition(myPasswordTextline, myPosition);
		} else {
			
			int m = BitFontRenderer.getWidth(myTextline.toString(), bfl, myPosition);
			int offset = (m > _myValueLabel.getWidth()) ? _myValueLabel.getWidth() - m : 0;
			cursorPosition = PApplet.min(actualCursorPosition, _myValueLabel.getWidth() - 2);
			_myValueLabel.setWithCursorPosition(myTextline.toString(), myPosition, offset);
		}
	}

	/**
	 * get the current text of the textfield.
	 * 
	 * @return String
	 */
	public String getText() {
		return myTextline.toString();
	}

	/**
	 * returns a string array that lists all text lines that have been confirmed with a return.
	 * 
	 * @return
	 */
	public String[] getTextList() {
		String[] s = new String[myTextList.size()];
		myTextList.toArray(s);
		return s;
	}

	/**
	 * clear the current content of the textfield.
	 */
	public void clear() {
		myTextline = new StringBuffer();
		myIndex = myTextList.size();
		myPosition = 0;
		updateField();
	}

	protected void checkClear() {
		if (isAutoClear) {
			myTextline = new StringBuffer();
			myIndex = myTextList.size();
			myPosition = 0;
			updateField();
		}
	}

	/**
	 * use setAutoClear(false) to not clear the content of the textfield after confirming with
	 * return.
	 * 
	 * @param theFlag
	 */
	public void setAutoClear(boolean theFlag) {
		isAutoClear = theFlag;
	}

	/**
	 * returns the current state of the autoClear flag.
	 * 
	 * @return
	 */
	public boolean isAutoClear() {
		return isAutoClear;
	}

	/**
	 * make the controller execute a return event. submit the current content of the texfield.
	 * 
	 */
	public void submit() {
		if (myTextline.length() > 0) {
			myTextList.add(myTextline.toString());
			update();
			checkClear();
		}
	}

	protected void adjust() {
		myPosition = myTextline.length();
		if (myPosition < 0) {
			myPosition = 0;
		}
	}

}
