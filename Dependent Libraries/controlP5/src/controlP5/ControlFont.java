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

import processing.core.PFont;

/**
 * A ControlFont is a container for a PFont that can be used for customizing the
 * font of a label. Fonts other than the pixel fonts provided by ControlP5 can
 * for now only be used for TextLabels and Controller Labels. Textarea and
 * Textfield are not supported.
 * 
 * @example extra/ControlP5controlFont
 */
public class ControlFont {

	private int fontSize;

	private int lineHeight;

	protected PFont font;

	protected boolean isControlFont;

	protected boolean isSmooth;

	/**
	 * create a controlFont and pass a reference to a PFont. fontsize needs to
	 * be defined as second parameter.
	 * 
	 * @param theFont
	 * @param theFontSize
	 */
	public ControlFont(PFont theFont) {
		this(theFont, theFont.getFont().getSize(), theFont.getFont().getSize() + 2);
	}

	public ControlFont(PFont theFont, int theFontSize) {
		this(theFont, theFontSize, theFontSize + 2);
	}

	public ControlFont(PFont theFont, int theFontSize, int theLineHeight) {
		font = theFont;
		fontSize = theFontSize;
		lineHeight = theLineHeight;
		isControlFont = true;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public int getFontSize() {
		return fontSize;
	}
	
	protected boolean isActive() {
		return isControlFont;
	}
	
	protected boolean setActive(boolean theFlag) {
		isControlFont = theFlag;
		return isControlFont;
	}
	
	public PFont getPFont() {
		return font;
	}
	
	public int size() {
		return fontSize;
	}
	
	public ControlFont setSize(int theSize) {
		fontSize = theSize;
		return this;
	}
	
	int getWidth() {
		return -1;
	}
	
	int getHeight() {
		return -1;
	}
	/**
	 * @deprecated
	 * @exclude
	 * @param theFlag
	 */
	@Deprecated
	public void setSmooth(boolean theFlag) {
		System.out
				.println("deprecated: ControlFont.setSmooth(). PFont.smooth not supported with processing 1.1+ anymore. Set the smooth flag in the constructor when creating a PFont.");
	}

	/**
	 * @deprecated
	 * @exclude
	 */
	@Deprecated
	public boolean isSmooth() {
		System.out
				.println("deprecated: ControlFont.isSmooth(). PFont.smooth not supported with processing 1.1+ anymore. Set the smooth flag in the constructor when creating a PFont.");
		return true;
	}

}

// textorize, a Ruby-based font rasterizer command line utility for Mac OS X
// http://textorize.org/

