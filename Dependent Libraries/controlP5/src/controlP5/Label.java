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
import processing.core.PImage;
import processing.core.PVector;

/**
 * A text custom label using controlP5's BitFonts or PFont based ControlFonts.
 * 
 * 
 * @see controlP5.ControlFont
 * @example controllers/ControlP5Textlabel
 * 
 */
public class Label implements CDrawable {

	private String _myText;

	private String _myPrevText;

	private int _myWidth;

	private int _myHeight;

	private boolean isFixedSize;

	private int _myColor = 0xffffffff;

	private int _myCursorPosition = -1;

	private int _myOffsetX;

	private int _myOffsetY;

	private float _myOffsetYratio;

	private FontLabel _myFont;

	private boolean isToUpperCase = true;

	private int _myLineHeight;

	private int _myFontSize;

	private int _myLetterSpacing = 0;

	private boolean isMultiline;

	private ControllerStyle _myControllerStyle;

	private boolean isVisible = true;

	public PVector position = new PVector(0, 0, 0);

	private int textHeight; // actual height of text, necessary for textarea

	private boolean forceupdate;

	private boolean isColorBackground;

	private int _myColorBackground = 0xffffffff;

	private ControlP5 cp5;

	public Label(ControlP5 theControlP5, String theText) {
		init(theControlP5, theText, 0, 0, _myColor);
	}

	public Label(ControlP5 theControlP5, final String theText, final int theWidth, final int theHeight, final int theColor) {
		init(theControlP5, theText, theWidth, theHeight, theColor);
	}

	private void init(ControlP5 theControlP5, String theText, int theWidth, int theHeight, int theColor) {
		cp5 = theControlP5;
		_myWidth = theWidth;
		_myHeight = theHeight;
		_myText = theText;
		_myColor = theColor;
		if (cp5.isControlFont && cp5.getFont() != null) {
			setFont(cp5.getFont());
		} else {
			int font = ControlP5.bitFont;
			setFont(font);
			setLineHeight(BitFontRenderer.getHeight(font));
			int[] dim = BitFontRenderer.getDimension(theText, (BitFontLabel) _myFont);
			setWidth((theWidth != 0) ? theWidth : dim[0]);
			setHeight((theHeight != 0) ? theHeight : dim[1]);
			((BitFontLabel) _myFont).init();
			setFixedSize(true);

		}
		set(theText);
		_myControllerStyle = new ControllerStyle();
	}

	public void update() {
		set(_myText);
	}

	public Label setFont(ControlFont theFont) {
		_myFont = new ControlFontLabel(theFont);
		_myLineHeight = theFont.getLineHeight();
		_myFontSize = theFont.getFontSize();
		_myFont.update();
		return this;
	}

	public Label setFont(int theBitFontIndex) {
		if (!BitFontRenderer.fonts.containsKey(theBitFontIndex)) {
			return this;
		}
		_myFont = new BitFontLabel(theBitFontIndex);
		forceupdate = true;
		_myFont.update();
		update();
		return this;
	}

	public Label setFontSize(int theSize) {
		_myFontSize = theSize;
		return this;
	}

	public int getFontSize() {
		return _myFontSize;
	}

	public FontLabel getFont() {
		return _myFont;
	}

	protected void updateFont(ControlFont theControlFont) {
		setFont(theControlFont);
	}

	/**
	 * draw a textlabel at a given location xy.
	 * 
	 * @param theApplet PApplet
	 * @param theX int
	 * @param theY int
	 */
	public void draw(final PApplet theApplet, final int theX, final int theY) {
		if (isVisible) {
			theApplet.pushMatrix();
			theApplet.translate(_myControllerStyle.marginLeft, _myControllerStyle.marginTop);
			theApplet.translate(theX, theY);

			if (isColorBackground) {
				int a = _myColorBackground >> 24 & 0xff;
				int r = _myColorBackground >> 16 & 0xff;
				int g = _myColorBackground >> 8 & 0xff;
				int b = _myColorBackground >> 0 & 0xff;

				float ww = getStyle().paddingRight + getStyle().paddingLeft;
				if (getStyle().backgroundWidth > -1) {
					ww += _myControllerStyle.backgroundWidth;
				} else {
					ww += _myFont.getWidth();
				}
				float hh = getStyle().paddingBottom + getStyle().paddingTop;
				if (getStyle().backgroundHeight > -1) {
					hh += getStyle().backgroundHeight;
				} else {
					hh += _myFont.getHeight();
				}
				theApplet.fill(r, g, b, a);
				theApplet.rect(0, 0, ww, hh);
			}
			_myFont.draw(theApplet);
			theApplet.popMatrix();
		}
	}

	/**
	 * draw a textlabel.
	 */
	@Override
	public void draw(PApplet theApplet) {
		draw(theApplet, (int) position.x, (int) position.y);
	}

	protected void draw(final PApplet theApplet, final int theX, final int theY, final int theColor) {
		_myColor = theColor;
		draw(theApplet, theX, theY);
	}

	public String getText() {
		return _myText;
	}

	public int getLineHeight() {
		return _myLineHeight;
	}

	public Label setLineHeight(int theLineHeight) {
		_myLineHeight = theLineHeight;
		forceupdate = true;
		_myFont.update();
		return this;
	}

	public int getWidth() {
		return _myWidth;
	}

	public int getHeight() {
		return _myHeight;
	}

	public Label setWidth(final int theWidth) {
		_myWidth = theWidth;
		forceupdate = true;
		_myFont.update();
		return this;
	}

	public Label setHeight(final int theHeight) {
		_myHeight = theHeight;
		forceupdate = true;
		_myFont.update();
		return this;
	}

	public int getTextHeight() {
		return textHeight;
	}

	public int getColor() {
		return _myColor;
	}

	public Label setColor(int theColor) {
		_myColor = theColor;
		update();
		return this;
	}

	public Label setColor(int theColor, boolean theFixedSizeFlag) {
		setFixedSize(theFixedSizeFlag);
		setColor(theColor);
		return this;
	}

	public Label setColorBackground(int theColor) {
		enableColorBackground();
		_myColorBackground = theColor;
		return this;
	}

	public Label disableColorBackground() {
		isColorBackground = false;
		return this;
	}

	public Label enableColorBackground() {
		isColorBackground = true;
		return this;
	}

	public Label setMultiline(boolean theFlag) {
		isMultiline = theFlag;
		return this;
	}

	public Label setLetterSpacing(int theValue) {
		_myLetterSpacing = theValue;
		set(_myText);
		return this;
	}

	/**
	 * set the text of the label. when setting the text, the fixedSize flag will be temporarily
	 * overwritten and set to false. after the text has been set, the fixedSize flag is set back to
	 * its previous value. use set(String, true) to set text for a fixed size area.
	 * 
	 * @param theText
	 */
	public Label set(final String theText) {
		boolean myFixedSize = isFixedSize();
		setFixedSize(false);
		set(theText, _myColor, _myCursorPosition, 0);
		setFixedSize(myFixedSize);
		return this;
	}

	public Label setText(final String theText) {
		return set(theText);
	}

	public Label set(final String theText, boolean theFlag) {
		boolean myFixedSize = isFixedSize();
		setFixedSize(theFlag);
		set(theText, _myColor, _myCursorPosition, 0);
		setFixedSize(myFixedSize);
		return this;
	}

	protected Label set(String theText, final int theColor, final int theCursorPosition, final int theOffsetX) {
		theText = (theText == null) ? "" : theText;
		_myOffsetX = theOffsetX;
		_myCursorPosition = theCursorPosition;
		_myPrevText = _myText;
		_myText = theText;
		// this conflicts with setting the captionLabel with
		// controller.captionLabel().set("blabla");
		_myFont.adjust(theColor, this);
		return this;
	}

	public void setWithCursorPosition(final String theText, final int theCursorPosition) {
		setWithCursorPosition(theText, theCursorPosition, 0);
	}

	public void setWithCursorPosition(final String theText, final int theCursorPosition, final int theOffsetX) {
		_myOffsetX = theOffsetX;
		forceupdate = true;
		set(theText, _myColor, theCursorPosition, _myOffsetX);
	}

	public boolean isFixedSize() {
		return isFixedSize;
	}

	public Label setOffsetX(int theOffset) {
		_myOffsetX = theOffset;
		return this;
	}

	public Label setOffsetY(int theOffset) {
		_myOffsetY = theOffset;
		return this;
	}

	public Label setOffsetYratio(float theRatio) {
		_myOffsetYratio = theRatio;
		return this;
	}

	public int getOffsetX() {
		return _myOffsetX;
	}

	public int getOffsetY() {
		return _myOffsetY;
	}

	public Label toUpperCase(final boolean theFlag) {
		isToUpperCase = theFlag;
		update();
		return this;
	}

	public Label setFixedSize(final boolean theFlag) {
		isFixedSize = theFlag;
		return this;
	}

	public Label setVisible(boolean theFlag) {
		isVisible = theFlag;
		return this;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public ControllerStyle getStyle() {
		return _myControllerStyle;
	}

	interface FontLabel {

		void draw(PApplet theApplet);

		void adjust(int theColor, Label theLabel);

		boolean update();

		String getText();

		int getLineHeight();

		int getLetterSpacing();

		boolean isToUpperCase();

		int getOffsetX();

		int getOffsetY();

		boolean isMultiline();

		int getColor();

		int getCursorPosition();

		int getWidth();

		int getHeight();
	}

	class BitFontLabel implements FontLabel {

		int _myFontIndex;

		private PImage _myImage;

		private PImage _myImageMask;

		BitFontLabel(int theFontIndex) {
			_myFontIndex = theFontIndex;
			init();
		}

		void init() {

			_myImage = cp5.papplet.createImage(_myWidth, _myHeight, PApplet.ARGB);
			_myImageMask = cp5.papplet.createImage(_myWidth, _myHeight, PApplet.RGB);
		}

		int getFontIndex() {
			return _myFontIndex;
		}

		public int getWidth() {
			return _myImage.width;
		}

		public int getHeight() {
			return _myImage.height;
		}

		public int getLineHeight() {
			return _myLineHeight;
		}

		public int getLetterSpacing() {
			return _myLetterSpacing;
		}

		public boolean isToUpperCase() {
			return isToUpperCase;
		}

		public String getText() {
			return _myText;
		}

		public int getOffsetX() {
			return _myOffsetX;
		}

		public int getOffsetY() {
			return _myOffsetY;
		}

		public boolean isMultiline() {
			return isMultiline;
		}

		public void adjust(int theColor, Label theLabel) {
			if (!isFixedSize) {
				update();
			}
			_myColor = theColor;
			textHeight = BitFontRenderer.write(this);
			_myImage.updatePixels();
		}

		PImage getImage() {
			return _myImage;
		}

		public int getColor() {
			return _myColor;
		}

		PImage getImageMask() {
			return _myImageMask;
		}

		public int getCursorPosition() {
			return _myCursorPosition;
		}

		public boolean update() {
			if (!forceupdate) {
				if (_myText.equals(_myPrevText)) {
					return false;
				}
			}

			boolean newImage = true;

			if (!isFixedSize) {
				float then = _myWidth;
				int[] dim = BitFontRenderer.getDimension(_myText, this);
				_myWidth = dim[0];
				_myHeight = dim[1];
				_myWidth += _myText.length() * _myLetterSpacing;
				// check if the new text is longer or smaller than the old one
				// (then) - add a small buffer to initiate a new new image
				// creation when exceeded.
				if (_myWidth > then || _myWidth < then - 8) {
					then = _myWidth;
					newImage = true;
				} else {
					newImage = false;
				}
			}
			if (newImage) {
				_myImage = cp5.papplet.createImage(_myWidth + 4, _myHeight, PApplet.ARGB);
				_myImageMask = cp5.papplet.createImage(_myWidth + 4, _myHeight, PApplet.RGB);
			}
			forceupdate = false;
			return true;
		}

		public void draw(PApplet theApplet) {
			if ((_myImage.width > 0 && _myImage.height > 0)) {
				theApplet.image(_myImage, _myControllerStyle.paddingLeft, _myControllerStyle.paddingTop);
			}
		}
	}

	class ControlFontLabel implements FontLabel {

		ControlFont _myControlFont;

		ArrayList<String> txt;

		ControlFontLabel(ControlFont theFont) {
			_myControlFont = theFont;
			txt = new ArrayList<String>();
		}

		public int getWidth() {
			return _myControlFont.getWidth();
		}

		public int getHeight() {
			return _myControlFont.getHeight();
		}

		public ControlFont getFont() {
			return _myControlFont;
		}

		public int getLineHeight() {
			return _myLineHeight;
		}

		public int getLetterSpacing() {
			return _myLetterSpacing;
		}

		public boolean isToUpperCase() {
			return isToUpperCase;
		}

		public String getText() {
			return _myText;
		}

		public int getOffsetX() {
			return _myOffsetX;
		}

		public int getOffsetY() {
			return _myOffsetY;
		}

		public boolean isMultiline() {
			return isMultiline;
		}

		public int getColor() {
			return _myColor;
		}

		public int getCursorPosition() {
			return _myCursorPosition;
		}

		public void adjust(int theColor, Label theLabel) {
		}

		public boolean update() {
			return true;
		}

		public void draw(PApplet theApplet) {
			theApplet.fill(_myColor);
			theApplet.textFont(_myControlFont.getPFont(), getFontSize());
			theApplet.textLeading(getLineHeight());
			theApplet.textAlign(PApplet.LEFT, PApplet.TOP);
			String s = isToUpperCase ? _myText.toUpperCase() : _myText;
			
			if (isMultiline) {
				textHeight = calculateTextHeight(theApplet, s, _myWidth - 15);
				int maxLineNum = PApplet.round((float) _myHeight / (float) _myLineHeight);
				maxLineNum += (_myHeight - (maxLineNum * _myLineHeight) > 0) ? -1 : 0;
				int offset = PApplet.max(0, PApplet.abs((int) (_myOffsetYratio * (txt.size() - maxLineNum))));
				String st = "";
				for (int i = offset; i < txt.size(); i++) {
					st += txt.get(i) + "\n";
				}
				theApplet.text(st, _myControllerStyle.paddingLeft, _myControllerStyle.paddingTop, _myWidth - 10, _myHeight);
			} else {
				theApplet.text(s, 0, -1);
			}
		}

		int calculateTextHeight(PApplet theApplet, String theString, int theWidth) {
			String[] wordsArray = PApplet.split(theString, " ");
			String tempString = "";
			txt.clear();
			int l = wordsArray.length;
			if (l <= 1) {
				return getLineHeight();
			}
			for (int i = 0; i < l; i++) {
				if (theApplet.textWidth(tempString + wordsArray[i]) < theWidth) {
					tempString += wordsArray[i] + " ";
				} else {
					txt.add(tempString.substring(0, tempString.length() - 1));
					tempString = wordsArray[i] + " ";
				}
			}
			txt.add(tempString.substring(0, tempString.length() - 1));
			// adding a blank line here to guarantee visibility of last line of
			// content
			txt.add("");
			return ((txt.size() * getLineHeight()));
		}

	}

	/**
	 * @deprecated
	 * @param theFont
	 * @return
	 */
	@Deprecated
	public Label setControlFont(ControlFont theFont) {
		return setFont(theFont);
	}

	/**
	 * @deprecated
	 * @param theFont
	 * @return
	 */
	@Deprecated
	public Label setControlFont(int theFontIndex) {
		return setFont(theFontIndex);
	}

	/**
	 * @deprecated
	 * @param theSize
	 * @return
	 */
	@Deprecated
	public Label setControlFontSize(int theSize) {
		return setFontSize(theSize);
	}

	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public ControllerStyle style() {
		return getStyle();
	}

}