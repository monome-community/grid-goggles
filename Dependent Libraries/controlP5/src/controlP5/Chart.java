package controlP5;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Use charts to display float array data as line chart, yet experimental, but see the
 * ControlP5chart example for more details.
 * 
 * @example controllers/ControlP5chart
 */
public class Chart extends Controller {

	// STATUS unfinished
	// TODO pie-chart, histogram-chart, bar chart, line chart
	// TODO setType(int type,int allignment HORIZONTAL/VERTICAL);

	/*
	 * NOTES what is the difference in meaning between chart and graph
	 * http://answers.yahoo.com/question/index?qid=20090101193325AA3mgMl
	 */

	public final static int LINE = 0;
	public final static int BAR = 1;
	public final static int HISTOGRAM = 2;
	public final static int PIE = 3;
	public final static int AREA = 4;

	protected ArrayList<ChartDataSet> _myDataSet;

	protected float resolution = 1;

	protected float strokeWeight = 1;

	protected Chart(ControlP5 theControlP5, ControllerGroup theParent, String theName, float theX, float theY, int theWidth, int theHeight) {
		super(theControlP5, theParent, theName, theX, theY, theWidth, theHeight);
		_myDataSet = new ArrayList<ChartDataSet>();
		addDataSet();
	}

	public ChartData addData(ChartData theItem) {
		return addData(0, theItem);
	}

	public ChartData addData(int theSetIndex, ChartData theItem) {
		getDataSet(theSetIndex).add(theItem);
		return theItem;
	}

	public ChartData addData(float theValue) {
		ChartData cdi = new ChartData(theValue);
		getDataSet().add(cdi);
		return cdi;
	}

	public ChartData addData(int theSetIndex, float theValue) {
		ChartData cdi = new ChartData(theValue);
		getDataSet(theSetIndex).add(cdi);
		return cdi;
	}

	public ChartData addData(ChartDataSet theChartData, float theValue) {
		ChartData cdi = new ChartData(theValue);
		theChartData.add(cdi);
		return cdi;
	}

	public ChartData push(float theValue) {
		return push(0, theValue);
	}

	public ChartData push(int theSetIndex, float theValue) {
		if (getDataSet(theSetIndex).size() > (width / resolution)) {
			removeLast(theSetIndex);
		}
		return addFirst(theSetIndex, theValue);
	}

	public ChartData addFirst(float theValue) {
		return addFirst(0, theValue);
	}

	public ChartData addFirst(int theSetIndex, float theValue) {
		ChartData cdi = new ChartData(theValue);
		getDataSet(theSetIndex).add(0, cdi);
		return cdi;
	}

	public Chart removeLast() {
		return removeLast(0);
	}

	public Chart removeLast(int theSetIndex) {
		return removeData(getDataSet(theSetIndex).size() - 1);
	}

	public Chart removeData(ChartData theItem) {
		removeData(0, theItem);
		return this;
	}

	public Chart removeData(int theSetIndex, ChartData theItem) {
		getDataSet(theSetIndex).remove(theItem);
		return this;
	}

	public Chart removeData(int theItemIndex) {
		removeData(0, theItemIndex);
		return this;
	}

	public Chart removeData(int theSetIndex, int theItemIndex) {
		if (getDataSet(theSetIndex).size() < 1) {
			return this;
		}
		getDataSet(theSetIndex).remove(theItemIndex);
		return this;
	}

	public ChartData setData(int theItemIndex, ChartData theItem) {
		getDataSet().set(theItemIndex, theItem);
		return theItem;
	}

	public ChartData setData(int theSetItem, int theItemIndex, ChartData theItem) {
		getDataSet(theSetItem).set(theItemIndex, theItem);
		return theItem;
	}

	public ChartDataSet addDataSet() {
		ChartDataSet cd = new ChartDataSet();
		_myDataSet.add(cd);
		return cd;
	}

	public ChartDataSet setDataSet(ChartDataSet theItems) {
		setDataSet(0, theItems);
		return getDataSet();
	}

	public ChartDataSet setDataSet(int theIndex, ChartDataSet theChartData) {
		_myDataSet.set(theIndex, theChartData);
		return theChartData;
	}

	public Chart removeDataSet(ChartDataSet theChartData) {
		_myDataSet.remove(theChartData);
		return this;
	}

	public void removeDataSet(int theIndex) {
		_myDataSet.remove(theIndex);
	}

	public ChartDataSet setData(float[] theValues) {
		setData(0, theValues);
		return getDataSet();
	}

	public ChartDataSet setData(int theSetIndex, float[] theValues) {
		if (_myDataSet.get(theSetIndex).size() != theValues.length) {
			_myDataSet.get(theSetIndex).clear();
			for (int i = 0; i < theValues.length; i++) {
				_myDataSet.get(theSetIndex).add(new ChartData(0));
			}
		}
		int n = 0;
		resolution = (float) width / (_myDataSet.get(theSetIndex).size() - 1);
		for (float f : theValues) {
			_myDataSet.get(theSetIndex).get(n++).setValue(f);
		}
		return getDataSet(theSetIndex);
	}

	public ChartDataSet updateData(float[] theValues) {
		return setData(theValues);
	}

	public ChartDataSet updateData(int theSetIndex, float[] theValues) {
		return setData(theSetIndex, theValues);
	}

	public ChartDataSet getDataSet(int theTableIndex) {
		return _myDataSet.get(theTableIndex);
	}

	public ChartDataSet getDataSet() {
		return getDataSet(0);
	}

	public ChartData getData(int theTableIndex, int theItemIndex) {
		return getDataSet(theTableIndex).get(theItemIndex);
	}

	public ChartData getData(int theIndex) {
		return getDataSet().get(theIndex);
	}

	public int size() {
		return _myDataSet.size();
	}

	@Override
	public void onEnter() {
	}

	@Override
	public void onLeave() {
	}

	@Override
	public Controller setValue(float theValue) {
		// TODO Auto-generated method stub
		return this;
	}

	public void setStrokeWeight(float theWeight) {
		strokeWeight = theWeight;
	}

	public float getStrokeWeight() {
		return strokeWeight;
	}

	public void setResolution(int theValue) {
		resolution = theValue;
	}

	public int getResolution() {
		return (int) resolution;
	}

	/**
	 * @exclude
	 */
	@Override
	@ControlP5.Invisible
	public Chart updateDisplayMode(int theMode) {
		return updateViewMode(theMode);
	}
	/**
	 * @exclude
	 */
	@ControlP5.Invisible
	public Chart updateViewMode(int theMode) {
		_myDisplayMode = theMode;
		switch (theMode) {
		case (DEFAULT):
			_myDisplay = new ChartViewBarCentered();
			break;
		case (IMAGE):
			// _myDisplay = new ChartImageDisplay();
			break;
		case (SPRITE):
			// _myDisplay = new ChartSpriteDisplay();
			break;
		case (CUSTOM):
		default:
			break;
		}
		return this;
	}
	
private class ChartViewBar implements ControllerView {
		
		public void display(PApplet theApplet, Controller theController) {
			theApplet.pushStyle();
			theApplet.fill(getColor().getBackground());
			theApplet.rect(0, 0, getWidth(), getHeight());
			theApplet.noStroke();
			for (int n = 0; n < size(); n++) {
				theApplet.fill(getDataSet(n).getColor().getForeground());
				int s = getDataSet(n).size();
				for (int i = 0; i < s; i++) {
					int ww = (int) ((width / s));
					theApplet.rect(i * ww, getHeight(), ww-1, -PApplet.max(0, PApplet.min(getHeight(), getDataSet(n).get(i).getValue())));
				}
			}
			theApplet.popStyle();
		}
	}

	private class ChartViewBarCentered implements ControllerView {
		
		public void display(PApplet theApplet, Controller theController) {
			theApplet.pushStyle();
			theApplet.fill(getColor().getBackground());
			theApplet.rect(0, 0, getWidth(), getHeight());
			theApplet.noStroke();
			for (int n = 0; n < size(); n++) {
				theApplet.fill(getDataSet(n).getColor().getForeground());
				int s = getDataSet(n).size();
				for (int i = 0; i < s; i++) {
					int ww = (int) ((width / s) * 0.5f);
					theApplet.rect(i * ((width / s)) + ww / 2, getHeight(), ww, -PApplet.max(0, PApplet.min(getHeight(), getDataSet(n).get(i).getValue())));
				}
			}
			theApplet.popStyle();
		}
	}

	private class ChartViewLine implements ControllerView {
		public void display(PApplet theApplet, Controller theController) {

			theApplet.pushStyle();
			theApplet.fill(getColor().getBackground());
			theApplet.rect(0, 0, getWidth(), getHeight());

			for (int n = 0; n < size(); n++) {
				theApplet.stroke(getDataSet(n).getColor().getForeground());
				theApplet.strokeWeight(getDataSet(n).getStrokeWeight());
				theApplet.beginShape();
				for (int i = 0; i < getDataSet(n).size(); i++) {
					theApplet.vertex(i * resolution, PApplet.max(0, PApplet.min(getHeight(), getHeight() - getDataSet(n).get(i).getValue())));
				}
				theApplet.endShape();
			}
			theApplet.noStroke();
			theApplet.popStyle();
		}
	}

	@Override
	public String getInfo() {
		return "type:\tChart\n" + super.toString();
	}

	@Override
	public String toString() {
		return super.toString() + " [ " + getValue() + " ]" + " Chart " + "(" + this.getClass().getSuperclass() + ")";
	}

}
