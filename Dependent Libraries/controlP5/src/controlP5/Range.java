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

import java.util.Vector;
import java.util.logging.Level;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * A range slider works like a normal slider but can be adjusted on both ends.
 * 
 * @see Slider
 * @example controllers/ControlP5range
 * @nosuperclasses Controller Controller
 */
public class Range extends Controller {

	/*
	 * TODO if range value is int, value labels do initialize as floats. first
	 * click makes them display as ints without decimal point
	 */
	protected static final int HORIZONTAL = 0;

	protected static final int VERTICAL = 1;

	protected int _myDirection;

	public final static int FIX = 1;

	public final static int FLEXIBLE = 0;

	protected int _mySliderMode = FLEXIBLE;

	protected float _myValuePosition;

	protected boolean isDragging;

	protected boolean isDraggable = true;

	protected boolean isFirstClick;

	protected Label _myHighValueLabel;

	protected float _myValueRange;

	protected boolean isMinHandle;

	protected boolean isMaxHandle;

	protected boolean isMoveHandle;

	protected float distanceHandle;

	protected int handleSize = 10;

	protected float minHandle = 0;

	protected float maxHandle = 0;

	protected Vector<TickMark> _myTickMarks;

	protected boolean isShowTickMarks;

	protected boolean isSnapToTickMarks;

	public static int autoWidth = 200;

	public static int autoHeight = 10;

	public static PVector autoSpacing = new PVector(0, 5, 0);

	public int alignValueLabel = CENTER;

	public int valueLabelPositioning = FIX;

	/**
	 * 
	 * @param theControlP5 ControlP5
	 * @param theParent ControllerGroup
	 * @param theName String
	 * @param theMin float
	 * @param theMax float
	 * @param theDefaultValue float
	 * @param theX int
	 * @param theY int
	 * @param theWidth int
	 * @param theHeight int
	 */
	@ControlP5.Invisible
	public Range(ControlP5 theControlP5, ControllerGroup theParent, String theName, float theMin, float theMax, float theDefaultMinValue, float theDefaultMaxValue, int theX, int theY, int theWidth, int theHeight) {
		super(theControlP5, theParent, theName, theX, theY, theWidth, theHeight);
		_myCaptionLabel = new Label(cp5,theName);
		_myCaptionLabel.setColor(color.getCaptionLabel());
		_myArrayValue = new float[] { theDefaultMinValue, theDefaultMaxValue };

		_myMin = theMin;
		_myMax = theMax;

		_myValueRange = _myMax - _myMin;

		minHandle = (theDefaultMinValue / _myValueRange) * width;
		maxHandle = (theDefaultMaxValue / _myValueRange) * width;

		_myValueLabel = new Label(cp5,"" + adjustValue(_myMin));
		_myCaptionLabel.setColor(color.getValueLabel());
		_myValueLabel.set("" + adjustValue(theDefaultMinValue));

		_myHighValueLabel = new Label(cp5,adjustValue(_myMax));
		_myCaptionLabel.setColor(color.getValueLabel());
		_myHighValueLabel.set("" + adjustValue(theDefaultMaxValue));

		_myValue = theDefaultMinValue;

		_myTickMarks = new Vector<TickMark>();

		setSliderMode(FIX);
		// _myDirection = (width > height) ? HORIZONTAL : VERTICAL;
		_myDirection = HORIZONTAL;
		update();

	}

	/**
	 * 
	 * @param theMode int
	 */
	@ControlP5.Invisible
	public Range setSliderMode(int theMode) {
		return this;
	}

	/**
	 * adjusts the size of both slider handles.
	 * 
	 * @param theSize
	 */
	public Range setHandleSize(int theSize) {
		handleSize = theSize;
		update();
		return this;
	}

	/**
	 * @see ControllerInterface.updateInternalEvents
	 * 
	 */
	@ControlP5.Invisible
	public Range updateInternalEvents(PApplet theApplet) {
		if (isVisible) {
			float p = _myControlWindow.mouseX - (absolutePosition.x);
			if (!isMousePressed && getIsInside()) {
				float hh1 = minHandle + handleSize;
				float hh2 = maxHandle + handleSize;
				isMinHandle = (p > minHandle && p < hh1) ? true : false;
				isMaxHandle = (p > maxHandle && p < hh2) ? true : false;
			}

			if (isMousePressed && !cp5.keyHandler.isAltDown) {
				if (_myControlWindow.mouseX != _myControlWindow.pmouseX || _myControlWindow.mouseY != _myControlWindow.pmouseY) {
					float h1 = minHandle + handleSize;
					float h2 = maxHandle + handleSize;
					if (!isDragging) {
						isMinHandle = (p > minHandle && p < h1) ? true : false;
						isMaxHandle = (p > maxHandle && p < h2) ? true : false;
						isMoveHandle = (isDraggable) ? ((p > h1 && p < maxHandle) ? true : false) : false;
						isDragging = (isMinHandle || isMaxHandle || isMoveHandle) ? true : false;
					}
					if (isDragging) {
						float pdif = _myControlWindow.mouseX - _myControlWindow.pmouseX;
						if (isMinHandle) {
							minHandle += pdif;
							minHandle = PApplet.constrain(minHandle, 0, maxHandle - handleSize);
							setLowValue(_myMin + (minHandle / ((width - handleSize * 2)) * _myValueRange));
						} else if (isMaxHandle) {
							maxHandle += pdif;
							maxHandle = PApplet.constrain(maxHandle, h1, width - handleSize);
							setHighValue(_myMin + ((maxHandle - handleSize) / ((width - handleSize * 2)) * _myValueRange));
						} else if (isMoveHandle) {
							float mpdif = (pdif * (_myValueRange / width));
							if (_myArrayValue[0] + mpdif >= _myMin && _myArrayValue[1] + mpdif <= _myMax) {
								_myArrayValue[0] += mpdif;
								_myArrayValue[1] += mpdif;
							}
							update();

						}
					}
				}
			}

		}
		return this;
	}

	/**
	 * set the value of the range-slider.
	 * 
	 * @param theValue float
	 * @return Range
	 */
	@Override
	public Range setValue(float theValue) {
		_myValue = theValue;
		broadcast(ARRAY);
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Range
	 */
	@Override
	public Range update() {
		minHandle = ((_myArrayValue[0] - _myMin) / _myValueRange) * (width - handleSize * 2);
		maxHandle = ((width - handleSize * 2) * ((_myArrayValue[1] - _myMin) / _myValueRange)) + handleSize;
		_myHighValueLabel.set(adjustValue(_myArrayValue[1]));
		_myValueLabel.set(adjustValue(_myArrayValue[0]));
		return setValue(_myValue);
	}

	/**
	 * 
	 * @param theFlag
	 * @return Range
	 */
	public Range setDraggable(boolean theFlag) {
		isDraggable = theFlag;
		isDragging = (theFlag == false) ? false : isDragging;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public float[] getArrayValue() {
		return _myArrayValue;
	}

	/**
	 * set the minimum value of the slider.
	 * 
	 * @param theValue float
	 * @return Range
	 */
	@Override
	public Range setMin(float theValue) {
		_myMin = theValue;
		_myValueRange = _myMax - _myMin;
		setLowValue(_myArrayValue[0]);
		return this;
	}

	/**
	 * set the maximum value of the slider.
	 * 
	 * @param theValue float
	 * @return Range
	 */
	@Override
	public Range setMax(float theValue) {
		_myMax = theValue;
		_myValueRange = _myMax - _myMin;
		setHighValue(_myArrayValue[1]);
		return this;
	}

	/**
	 * @return float
	 */
	public float getLowValue() {
		return _myArrayValue[0];
	}

	/**
	 * @return float
	 */
	public float getHighValue() {
		return _myArrayValue[1];
	}

	/**
	 * @param theValue
	 * @return Range
	 */
	public Range setLowValue(float theValue) {
		_myArrayValue[0] = PApplet.max(_myMin, theValue);
		return update();

	}

	/**
	 * @param theValue
	 * @return Range
	 */
	public Range setHighValue(float theValue) {
		_myArrayValue[1] = PApplet.min(_myMax, theValue);
		return update();
	}

	/**
	 * sets the width of the slider.
	 * 
	 * @param theValue int
	 * @return Range
	 */
	public Range setWidth(int theValue) {
		width = theValue;
		setSliderMode(_mySliderMode);
		return this;
	}

	/**
	 * sets the height of the slider.
	 * 
	 * @param theValue int
	 * @return Range
	 */
	public Range setHeight(int theValue) {
		height = theValue;
		setSliderMode(_mySliderMode);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@ControlP5.Invisible
	public void mouseReleased() {
		isDragging = isMinHandle = isMaxHandle = isMoveHandle = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@ControlP5.Invisible
	public void mouseReleasedOutside() {
		mouseReleased();
	}

	/**
	 * {@inheritDoc}
	 */
	@ControlP5.Invisible
	public void onLeave() {
		isMinHandle = false;
		isMaxHandle = false;
	}

	protected void setTickMarks() {

	}

	/**
	 * @param theNumber
	 * @return Range
	 */
	public Range setNumberOfTickMarks(int theNumber) {
		int n = theNumber - _myTickMarks.size();
		if (n <= theNumber) {
			for (int i = 0; i < n; i++) {
				_myTickMarks.add(new TickMark(this));
			}
		}
		showTickMarks(true);
		snapToTickMarks(true);
		return this;
	}

	/**
	 * @param theFlag
	 * @return Range
	 */
	public Range showTickMarks(boolean theFlag) {
		isShowTickMarks = theFlag;
		return this;
	}

	/**
	 * @param theFlag
	 * @return Range
	 */
	public Range snapToTickMarks(boolean theFlag) {
		isSnapToTickMarks = theFlag;
		return this;
	}

	// set the label of a tick.
	@ControlP5.Invisible
	public TickMark getTickMark() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@ControlP5.Invisible
	public Range updateDisplayMode(int theMode) {
		_myDisplayMode = theMode;
		switch (theMode) {
		case (DEFAULT):
			_myDisplay = new RangeView();
			break;
		case (SPRITE):
			_myDisplay = new RangeSpriteView();
			break;
		case (IMAGE):
			_myDisplay = new RangeImageView();
			break;
		case (CUSTOM):
		default:
			break;
		}
		return this;
	}

	class RangeSpriteView implements ControllerView {
		public void display(PApplet theApplet, Controller theController) {
			ControlP5.logger().log(Level.INFO, "RangeSpriteDisplay not available.");
		}
	}

	class RangeView implements ControllerView {
		public void display(PApplet theApplet, Controller theController) {
			theApplet.fill(color.getBackground());
			theApplet.noStroke();
			theApplet.rect(0, 0, width, height);
			// if(isInside) {
			theApplet.fill(color.getForeground());

			if (isShowTickMarks) {
				theApplet.rect(minHandle + handleSize / 2, 0, maxHandle - minHandle, height);
				theApplet.fill((isMinHandle) ? color.getActive() : color.getForeground());
				theApplet.triangle(minHandle, 0, minHandle + handleSize, 0, minHandle + handleSize / 2, height);
				theApplet.fill((isMaxHandle) ? color.getActive() : color.getForeground());
				theApplet.triangle(maxHandle, 0, maxHandle + handleSize, 0, maxHandle + handleSize / 2, height);
			} else {
				theApplet.rect(minHandle, 0, maxHandle - minHandle, height);
				theApplet.fill((isMinHandle) ? color.getActive() : color.getForeground());
				theApplet.rect(minHandle, 0, handleSize, height);
				theApplet.fill((isMaxHandle) ? color.getActive() : color.getForeground());
				theApplet.rect(maxHandle, 0, handleSize, height);

			}

			if (isLabelVisible) {
				// if (_myDirection == HORIZONTAL) {
				_myCaptionLabel.draw(theApplet, width + 3, height / 2 - 3);
				_myValueLabel.draw(theApplet, 3, height / 2 - 3);
				_myHighValueLabel.draw(theApplet, width - _myHighValueLabel.getWidth(), height / 2 - 3);
			} else {
				_myCaptionLabel.draw(theApplet, 0, height + 3);
				_myValueLabel.draw(theApplet, width + 4, -(int) _myValuePosition + height - 8);
			}
			// }

			if (isShowTickMarks) {
				theApplet.pushMatrix();

				theApplet.translate((_mySliderMode == FIX) ? 0 : 5, getHeight());
				float x = (getWidth() - ((_mySliderMode == FIX) ? 0 : 10)) / (_myTickMarks.size() - 1);
				for (TickMark tm : _myTickMarks) {
					tm.draw(theApplet);
					theApplet.translate(x, 0);
				}
				theApplet.popMatrix();
			}
		}
	}

	class RangeImageView implements ControllerView {
		public void display(PApplet theApplet, Controller theController) {
			ControlP5.logger().log(Level.INFO, "RangeImageDisplay not implemented.");
		}
	}

	@Override
	public String toString() {
		return "type:\tBang\n" + super.toString();
	}

	@Deprecated
	public float lowValue() {
		return getLowValue();
	}

	@Deprecated
	public float highValue() {
		return getHighValue();
	}

	@Deprecated
	public float[] arrayValue() {
		return _myArrayValue;
	}
}
