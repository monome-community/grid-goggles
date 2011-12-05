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

/**
 * a textarea can be used to leave notes, it uses the controlP5 BitFont to render text. Scrollbars
 * will automaticaly be added when text extends the visible area. Textarea extends ControllerGroup,
 * for more methods available see the ControllerGroup documentation.
 * 
 * @example controllers/ControlP5textarea
 * @nosuperclasses ControllerGroup ControllerGroup
 */
public class Textarea extends ControllerGroup implements ControlListener {

	protected String _myText;

	protected Slider _myScrollbar;

	protected int _myWidth = 200;

	protected int _myHeight = 20;

	protected int _myColorBackground = 0x000000;

	protected boolean isColorBackground = false;

	protected float _myScrollValue = 0;

	protected boolean isScrollbarVisible = true;

	protected int _myBottomOffset = 4;

	private int _myScrollbarWidth = 5;

	/**
	 * 
	 * @param theControlP5 ControlP5
	 * @param theGroup ControllerGroup
	 * @param theName String
	 * @param theText String
	 * @param theX int
	 * @param theY int
	 * @param theW int
	 * @param theH int
	 */
	protected Textarea(ControlP5 theControlP5, ControllerGroup theGroup, String theName, String theText, int theX, int theY, int theW, int theH) {
		super(theControlP5, theGroup, theName, theX, theY);
		_myWidth = theW;
		_myHeight = theH;
		_myText = theText;
		setup();
	}

	/**
	 * 
	 * @param theText String
	 * @param theX int
	 * @param theY int
	 */
	public Textarea(String theText, int theX, int theY) {
		super(theX, theY);
		_myText = theText;
		setup();
	}

	private void setup() {
		_myValueLabel = new Label(cp5, _myText);
		if (_myValueLabel.getFont() instanceof Label.BitFontLabel) {
			_myValueLabel.setFont(BitFontRenderer.standard56);
		}
		_myValueLabel.setWidth((int) _myWidth);
		_myValueLabel.setHeight((int) _myHeight);
		_myValueLabel.setMultiline(true);
		_myValueLabel.toUpperCase(false);
		_myValueLabel.update();
		_myValueLabel.position.x = 2;
		_myValueLabel.position.y = 2;
		_myValueLabel.setColor(color.getValueLabel());
		addDrawable(_myValueLabel);

		_myScrollbar = new Slider(cp5, _myParent, getName() + "Scroller", 0, 1, 1, _myWidth - 5, 0, 5, _myHeight);
		_myScrollbar.init();
		_myScrollbar.setBroadcast(false);
		_myScrollbar.setSliderMode(Slider.FLEXIBLE);
		_myScrollbar.isMoveable = false;
		_myScrollbar.isLabelVisible = false;
		_myScrollbar.setParent(this);
		_myScrollbar.addListener(this);
		add(_myScrollbar);
		setWidth(_myWidth);
		setHeight(_myHeight);
		_myScrollbar.color.set(color);
		_myScrollbar.setColorBackground(0x00000000);
		_myScrollbar.setHandleSize(40);
	}

	/**
	 * 
	 * @param theEvent ControlEvent
	 */
	public void controlEvent(ControlEvent theEvent) {
		// reverse the value coming in from the scrollbar: 0 to 1 becomes -1 to
		// 0
		_myScrollValue = -(1 - theEvent.getValue());
		scroll();
	}

	public void hideScrollbar() {
		isScrollbarVisible = false;
		_myScrollbar.hide();
	}

	public void showScrollbar() {
		isScrollbarVisible = true;
		boolean isScrollbar = _myHeight < (_myValueLabel.getTextHeight() + _myValueLabel.getLineHeight());
		if (isScrollbar) {
			_myScrollbar.show();
		}
	}


	public boolean isScrollable() {
		return _myScrollbar.isVisible();
	}

	@Override
	public Textarea setColorBackground(int theColor) {
		_myColorBackground = theColor;
		isColorBackground = true;
		return this;
	}

	public void disableColorBackground() {
		isColorBackground = false;
	}

	public void enableColorBackground() {
		isColorBackground = true;
	}

	/**
	 * scroll the Textarea remotely. values must range from 0 to 1.
	 * 
	 * @param theValue
	 */
	public void scroll(float theValue) {
		_myScrollbar.setValue(1 - theValue);
	}

	/**
	 * private update method for the scrollbar.
	 */
	private void scroll() {
		_myScrollValue = PApplet.min(PApplet.max(-1, _myScrollValue), 0);
		float myLen = _myValueLabel.getTextHeight() + _myValueLabel.getLineHeight();
		float myOffset = 0;
		boolean isScrollbar = _myHeight < myLen;
		if (isScrollbar) {
			myOffset = _myScrollValue * (myLen - _myHeight + _myBottomOffset);
		}
		isScrollbar = (isScrollbarVisible) ? isScrollbar : false;
		_myScrollbar.setVisible(isScrollbar);
		_myValueLabel.setOffsetY((int) myOffset);
		_myValueLabel.setOffsetYratio((int) _myScrollValue);
		_myValueLabel.update();
	}

	@ControlP5.Invisible
	public void scrolled(int theStep) {
		int lines = (_myValueLabel.getTextHeight() / _myValueLabel.getLineHeight());
		float step = 1.0f / lines;
		scroll((1 - getScrollPosition()) + (theStep * step));
	}

	@ControlP5.Invisible
	public float getScrollPosition() {
		return _myScrollbar.getValue();
	}

	/**
	 * set the width of the textarea.
	 * 
	 * @param theValue int
	 */
	public Textarea setWidth(int theValue) {
		theValue = (theValue < 10) ? 10 : theValue;
		_myWidth = theValue;
		_myValueLabel.setWidth(_myWidth - _myScrollbarWidth - 10);
		_myScrollValue = (float) (_myHeight) / (float) (_myValueLabel.getTextHeight());
		_myScrollbar.setHeight(_myHeight + _myValueLabel.getStyle().paddingTop + _myValueLabel.getStyle().paddingBottom);
		return this;
	}

	/**
	 * set the height of the textarea.
	 * 
	 * @param theValue int
	 */
	public Textarea setHeight(int theValue) {
		theValue = (theValue < 10) ? 10 : theValue;
		_myHeight = theValue;
		_myValueLabel.setHeight(_myHeight - 2);
		_myScrollValue = (float) (_myHeight) / (float) (_myValueLabel.getTextHeight());
		_myScrollbar.setHeight(_myHeight + _myValueLabel.getStyle().paddingTop + _myValueLabel.getStyle().paddingBottom);
		return this;
	}

	/**
	 * set the lineheight of the textarea.
	 * 
	 * @param theLineHeight int
	 */
	public void setLineHeight(int theLineHeight) {
		_myValueLabel.setLineHeight(theLineHeight);
		scroll();
	}

	/**
	 * set the text color of the textarea.
	 * 
	 * @param theColor int
	 */
	public void setColor(int theColor) {
		_myValueLabel.setColor(theColor, true);
	}

	/**
	 * returns the instance of the textarea's label.
	 * 
	 * @return
	 */
	public Label getValueLabel() {
		return _myValueLabel;
	}

	/**
	 * set the text of the textarea.
	 * 
	 * @param theText String
	 */
	public void setText(String theText) {
		_myValueLabel.set(theText, true);
		_myScrollValue = (float) (_myHeight) / (float) (_myValueLabel.getTextHeight());
		_myScrollbar.setHeight(_myHeight + _myValueLabel.getStyle().paddingTop + _myValueLabel.getStyle().paddingBottom);
	}

	/**
	 * returns the text content of the textarea.
	 * 
	 * @return String
	 */
	public String getText() {
		return getStringValue();
	}

	public String text() {
		return getText();
	}

	protected void preDraw(PApplet theApplet) {
		if (_myScrollbar.isVisible() || isColorBackground) {
			_myScrollbar.getPosition().x = _myWidth - _myScrollbarWidth + _myValueLabel.getStyle().paddingLeft + _myValueLabel.getStyle().paddingRight;
			if (!isColorBackground) {
				theApplet.noFill();
			} else {
				int a = _myColorBackground >> 24 & 0xff;
				theApplet.fill(_myColorBackground, a > 0 ? a : 255);
			}
			int ww = _myWidth + _myValueLabel.getStyle().paddingLeft + _myValueLabel.getStyle().paddingRight;
			int hh = _myHeight + _myValueLabel.getStyle().paddingTop + _myValueLabel.getStyle().paddingBottom;
			theApplet.rect(0, 0, ww, hh);

		}
	}

	// !!! add padding to the box.
	// padding and margin doesnt work nicely with textarea yet!
	protected boolean inside() {
		return (_myControlWindow.mouseX > position.x + _myParent.absolutePosition.x && _myControlWindow.mouseX < position.x + _myParent.absolutePosition.x + _myWidth
				&& _myControlWindow.mouseY > position.y + _myParent.absolutePosition.y && _myControlWindow.mouseY < position.y + _myParent.absolutePosition.y + _myHeight);
	}

	public String getStringValue() {
		return _myValueLabel.toString();
	}

	/**
	 * @param theFont
	 * @return Textarea
	 */
	public Textarea setFont(ControlFont theFont) {
		_myValueLabel.setFont(theFont);
		return this;
	}

	/**
	 * @param theFontIndex
	 * @return Textarea
	 */
	public Textarea setFont(int theFontIndex) {
		_myValueLabel.setFont(theFontIndex);
		return this;
	}

	/**
	 * @param theColor
	 * @return Textarea
	 */
	public Textarea setScrollBackground(int theColor) {
		_myScrollbar.setColorBackground(theColor);
		return this;
	}

	/**
	 * @param theColor
	 * @return Textarea
	 */
	public Textarea setScrollForeground(int theColor) {
		_myScrollbar.setColorForeground(theColor);
		return this;
	}

	/**
	 * @param theColor
	 * @return Textarea
	 */
	public Textarea setScrollActive(int theColor) {
		_myScrollbar.setColorActive(theColor);
		return this;
	}

	/**
	 * @param theColor
	 * @return Textarea
	 */
	public Textarea setBorderColor(int theColor) {
		color.setBackground(theColor);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getValue() {
		return 0;
	}

	@Deprecated
	public float value() {
		return 0;
	}

	@Deprecated
	public String stringValue() {
		return getStringValue();
	}

	@Deprecated
	public Label valueLabel() {
		return getValueLabel();
	}
	
	/**
	 * @exclude
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public boolean isScrollbarVisible() {
		return isScrollbarVisible;
	}
}

// @todo linebreaking algorithm.
// http://www.leverkruid.eu/GKPLinebreaking/index.html
// found at http://www.texone.org/?p=43

