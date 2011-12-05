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
 * A matrix is a 2d array with a pointer that traverses through the matrix in a timed interval. if
 * an item of a matrix-column is active, the x and y position of the corresponding cell will trigger
 * an event and notify the program. see the ControlP5matrix example for more information.
 * 
 * @example controllers/ControlP5matrix
 */
public class Matrix extends Controller {

	protected int cnt;

	protected int[][] _myCells;

	protected int stepX;

	protected int stepY;

	protected int cellX;

	protected int cellY;

	protected boolean isPressed;

	protected int _myCellX;

	protected int _myCellY;

	protected int sum;

	protected int _myInterval = 100;

	protected int currentX = -1;

	protected int currentY = -1;

	protected int _myMode = SINGLE_ROW;

	private Thread t;

	public Matrix(ControlP5 theControlP5, ControllerGroup theParent, String theName, int theCellX, int theCellY, int theX, int theY, int theWidth, int theHeight) {
		super(theControlP5, theParent, theName, theX, theY, theWidth, theHeight);
		_myInterval = 100;
		initCells(theCellX, theCellY);
		runThread();
	}

	private void initCells(int theCellX, int theCellY) {
		_myCellX = theCellX;
		_myCellY = theCellY;
		sum = _myCellX * _myCellY;
		stepX = width / _myCellX;
		stepY = height / _myCellY;
		_myCells = new int[_myCellX][_myCellY];
		for (int x = 0; x < _myCellX; x++) {
			for (int y = 0; y < _myCellY; y++) {
				_myCells[x][y] = 0;
			}
		}

	}

	/**
	 * set the speed of intervals in millis iterating through the matrix.
	 * 
	 * @param theInterval int
	 * @return Matrix
	 */

	public Matrix setInterval(int theInterval) {
		_myInterval = theInterval;
		return this;
	}

	public int getInterval() {
		return _myInterval;
	}

	@ControlP5.Invisible
	public Matrix updateInternalEvents(PApplet theApplet) {
		setIsInside(inside());

		if (getIsInside()) {
			if (isPressed) {
				int tX = (int) ((theApplet.mouseX - position.x) / stepX);
				int tY = (int) ((theApplet.mouseY - position.y) / stepY);

				if (tX != currentX || tY != currentY) {
					tX = PApplet.min(PApplet.max(0, tX), _myCellX);
					tY = PApplet.min(PApplet.max(0, tY), _myCellY);
					boolean isMarkerActive = (_myCells[tX][tY] == 1) ? true : false;
					switch (_myMode) {
					default:
					case (SINGLE_COLUMN):
						for (int i = 0; i < _myCellY; i++) {
							_myCells[tX][i] = 0;
						}
						_myCells[tX][tY] = (!isMarkerActive) ? 1 : _myCells[tX][tY];
						break;
					case (SINGLE_ROW):
						for (int i = 0; i < _myCellY; i++) {
							_myCells[tX][i] = 0;
						}
						_myCells[tX][tY] = (!isMarkerActive) ? 1 : _myCells[tX][tY];
						break;
					case (MULTIPLES):
						_myCells[tX][tY] = (_myCells[tX][tY] == 1) ? 0 : 1;
						break;
					}
					currentX = tX;
					currentY = tY;
				}
			}
		}
		return this;
	}

	protected void onEnter() {
		isActive = true;
	}

	protected void onLeave() {
		isActive = false;
	}

	@ControlP5.Invisible
	public void mousePressed() {
		isActive = getIsInside();
		if (getIsInside()) {
			isPressed = true;
		}
	}

	protected void mouseReleasedOutside() {
		mouseReleased();
	}

	@ControlP5.Invisible
	public void mouseReleased() {
		if (isActive) {
			isActive = false;
		}
		isPressed = false;
		currentX = 0;
		currentY = 0;
	}

	@Override
	public Matrix setValue(float theValue) {
		_myValue = theValue;
		broadcast(FLOAT);
		return this;
	}

	@Override
	public Matrix update() {
		return setValue(_myValue);
	}

	/**
	 * set the state of a particular cell inside a matrix. use true or false for parameter theValue
	 * 
	 * @param theX
	 * @param theY
	 * @param theValue
	 * @return Matrix
	 */
	public Matrix set(int theX, int theY, boolean theValue) {
		_myCells[theX][theY] = (theValue == true) ? 1 : 0;
		return this;
	}

	public static int getX(int thePosition) {
		return ((thePosition >> 0) & 0xff);
	}

	public static int getY(int thePosition) {
		return ((thePosition >> 8) & 0xff);
	}

	public static int getX(float thePosition) {
		return (((int) thePosition >> 0) & 0xff);
	}

	public static int getY(float thePosition) {
		return (((int) thePosition >> 8) & 0xff);
	}

	public Matrix setCells(int[][] theCells) {
		initCells(theCells.length, theCells[0].length);
		_myCells = theCells;
		return this;
	}

	public int[][] getCells() {
		return _myCells;
	}

	private void triggerEventFromThread() {
		cnt += 1;
		cnt %= _myCellX;
		for (int i = 0; i < _myCellY; i++) {
			if (_myCells[cnt][i] == 1) {
				_myValue = 0;
				_myValue = (cnt << 0) + (i << 8);
				setValue(_myValue);
			}
		}
	}

	private void runThread() {
		if (t == null) {
			t = new Thread(getName()) {
				public void run() {
					while (true) {
						triggerEventFromThread();
						try {
							sleep(_myInterval);
						} catch (InterruptedException e) {
							// throw new RuntimeException(e);
						}
					}
				}
			};
			t.start();
		}
	}

	@Override
	public void remove() {
		if (t != null) {
			t.interrupt();
		}
		super.remove();
	}

	/**
	 * use setMode to change the cell-activation which by default is ControlP5.SINGLE_ROW, 1 active
	 * cell per row, but can be changed to ControlP5.SINGLE_COLUMN or ControlP5.MULTIPLES
	 * 
	 * @param theMode return Matrix
	 */
	public Matrix setMode(int theMode) {
		_myMode = theMode;
		return this;
	}

	public int getMode() {
		return _myMode;
	}

	@Override
	@ControlP5.Invisible
	public Matrix updateDisplayMode(int theMode) {
		_myDisplayMode = theMode;
		switch (theMode) {
		case (DEFAULT):
			_myDisplay = new MatrixView();
			break;
		case (IMAGE):
		case (SPRITE):
		case (CUSTOM):
		default:
			break;
		}
		return this;
	}

	class MatrixView implements ControllerView {
		public void display(PApplet theApplet, Controller theController) {
			theApplet.noStroke();
			theApplet.fill(color.getBackground());
			theApplet.rect(0, 0, width, height);
			theApplet.noStroke();
			if (isInside()) {
				theApplet.fill(color.getForeground());
				theApplet.rect((int) ((theApplet.mouseX - position.x) / stepX) * stepX, (int) ((theApplet.mouseY - position.y) / stepY) * stepY, stepX, stepY);
			}
			theApplet.stroke(color.getActive());
			theApplet.line(cnt * stepX, 0, cnt * stepX, height);
			theApplet.noStroke();
			theApplet.fill(color.getActive());
			for (int x = 0; x < _myCellX; x++) {
				for (int y = 0; y < _myCellY; y++) {
					if (_myCells[x][y] == 1) {
						theApplet.rect(x * stepX, y * stepY, stepX - 1, stepY - 1);
					}
				}
			}
		}
	}
}
