/**
* ControlP5 Range
*
*
* find a list of public methods available for the Range Controller
* at the bottom of this sketch.
*
* by Andreas Schlegel, 2011
* www.sojamo.de/libraries/controlp5
*
*/


import controlP5.*;

ControlP5 cp5;

int myColorBackground = color(0,0,0);

int colorMin = 100;

int colorMax = 100;

Range range;

void setup() {
  size(400,400);
  cp5 = new ControlP5(this);
  range = cp5.addRange("rangeController",0,255, 0,127.5, 50,100,200,12);
  range.setHandleSize(30);
}

void draw() {
  background(colorMin);
  fill(colorMax);
  rect(0,0,width,height/2);
}

void controlEvent(ControlEvent theControlEvent) {
  if(theControlEvent.getController().getName().equals("rangeController")) {
    // min and max values are stored in an array.
    // access this array with controller().arrayValue().
    // min is at index 0, max is at index 1.
    colorMin = int(theControlEvent.getController().getArrayValue(0));
    colorMax = int(theControlEvent.getController().getArrayValue(1));
  }
}


void keyPressed() {
  switch(key) {
    case('1'):range.setLowValue(0);break;
    case('2'):range.setLowValue(100);break;
    case('3'):range.setHighValue(120);break;
    case('4'):range.setHighValue(200);break;
  }
}


/*
a list of all methods available for the Range Controller
use ControlP5.printPublicMethodsFor(Range.class);
to print the following list into the console.

You can find further details about class Range in the javadoc.

Format:
ClassName : returnType methodName(parameter type)

controlP5.Range : Range setDraggable(boolean) 
controlP5.Range : Range setHandleSize(int) 
controlP5.Range : Range setHeight(int) 
controlP5.Range : Range setHighValue(float) 
controlP5.Range : Range setLowValue(float) 
controlP5.Range : Range setMax(float) 
controlP5.Range : Range setMin(float) 
controlP5.Range : Range setNumberOfTickMarks(int) 
controlP5.Range : Range setValue(float) 
controlP5.Range : Range setWidth(int) 
controlP5.Range : Range showTickMarks(boolean) 
controlP5.Range : Range snapToTickMarks(boolean) 
controlP5.Range : Range update() 
controlP5.Range : String toString() 
controlP5.Range : float getHighValue() 
controlP5.Range : float getLowValue() 
controlP5.Range : float[] getArrayValue() 
controlP5.Controller : CColor getColor() 
controlP5.Controller : ControlBehavior getBehavior() 
controlP5.Controller : ControlWindow getControlWindow() 
controlP5.Controller : ControlWindow getWindow() 
controlP5.Controller : Controller addCallback(CallbackListener) 
controlP5.Controller : Controller addListener(ControlListener) 
controlP5.Controller : Controller hide() 
controlP5.Controller : Controller linebreak() 
controlP5.Controller : Controller listen(boolean) 
controlP5.Controller : Controller lock() 
controlP5.Controller : Controller plugTo(Object) 
controlP5.Controller : Controller plugTo(Object, String) 
controlP5.Controller : Controller plugTo(Object[]) 
controlP5.Controller : Controller plugTo(Object[], String) 
controlP5.Controller : Controller registerProperty(String) 
controlP5.Controller : Controller registerProperty(String, String) 
controlP5.Controller : Controller registerTooltip(String) 
controlP5.Controller : Controller removeBehavior() 
controlP5.Controller : Controller removeCallback() 
controlP5.Controller : Controller removeCallback(CallbackListener) 
controlP5.Controller : Controller removeListener(ControlListener) 
controlP5.Controller : Controller removeProperty(String) 
controlP5.Controller : Controller removeProperty(String, String) 
controlP5.Controller : Controller setArrayValue(float[]) 
controlP5.Controller : Controller setArrayValue(int, float) 
controlP5.Controller : Controller setBehavior(ControlBehavior) 
controlP5.Controller : Controller setBroadcast(boolean) 
controlP5.Controller : Controller setCaptionLabel(String) 
controlP5.Controller : Controller setColor(CColor) 
controlP5.Controller : Controller setColorActive(int) 
controlP5.Controller : Controller setColorBackground(int) 
controlP5.Controller : Controller setColorCaptionLabel(int) 
controlP5.Controller : Controller setColorForeground(int) 
controlP5.Controller : Controller setColorValueLabel(int) 
controlP5.Controller : Controller setDecimalPrecision(int) 
controlP5.Controller : Controller setDefaultValue(float) 
controlP5.Controller : Controller setDisplay(ControllerDisplay) 
controlP5.Controller : Controller setHeight(int) 
controlP5.Controller : Controller setId(int) 
controlP5.Controller : Controller setImages(PImage, PImage, PImage) 
controlP5.Controller : Controller setImages(PImage, PImage, PImage, PImage) 
controlP5.Controller : Controller setLabelVisible(boolean) 
controlP5.Controller : Controller setLock(boolean) 
controlP5.Controller : Controller setMax(float) 
controlP5.Controller : Controller setMin(float) 
controlP5.Controller : Controller setMoveable(boolean) 
controlP5.Controller : Controller setPosition(PVector) 
controlP5.Controller : Controller setPosition(float, float) 
controlP5.Controller : Controller setSize(PImage) 
controlP5.Controller : Controller setSize(int, int) 
controlP5.Controller : Controller setStringValue(String) 
controlP5.Controller : Controller setUpdate(boolean) 
controlP5.Controller : Controller setValueLabel(String) 
controlP5.Controller : Controller setVisible(boolean) 
controlP5.Controller : Controller setWidth(int) 
controlP5.Controller : Controller show() 
controlP5.Controller : Controller unlock() 
controlP5.Controller : Controller unplugFrom(Object) 
controlP5.Controller : Controller unplugFrom(Object[]) 
controlP5.Controller : Controller unregisterTooltip() 
controlP5.Controller : Controller update() 
controlP5.Controller : Controller updateSize() 
controlP5.Controller : ControllerProperty getProperty(String) 
controlP5.Controller : ControllerProperty getProperty(String, String) 
controlP5.Controller : Label getCaptionLabel() 
controlP5.Controller : Label getValueLabel() 
controlP5.Controller : List getControllerPlugList() 
controlP5.Controller : PImage setImage(PImage) 
controlP5.Controller : PImage setImage(PImage, int) 
controlP5.Controller : PVector getAbsolutePosition() 
controlP5.Controller : PVector getPosition() 
controlP5.Controller : String getAddress() 
controlP5.Controller : String getInfo() 
controlP5.Controller : String getLabel() 
controlP5.Controller : String getName() 
controlP5.Controller : String getStringValue() 
controlP5.Controller : String toString() 
controlP5.Controller : Tab getTab() 
controlP5.Controller : boolean isActive() 
controlP5.Controller : boolean isBroadcast() 
controlP5.Controller : boolean isInside() 
controlP5.Controller : boolean isListening() 
controlP5.Controller : boolean isLock() 
controlP5.Controller : boolean isMouseOver() 
controlP5.Controller : boolean isMousePressed() 
controlP5.Controller : boolean isMoveable() 
controlP5.Controller : boolean isUpdate() 
controlP5.Controller : boolean isVisible() 
controlP5.Controller : float getArrayValue(int) 
controlP5.Controller : float getDefaultValue() 
controlP5.Controller : float getMax() 
controlP5.Controller : float getMin() 
controlP5.Controller : float getValue() 
controlP5.Controller : float[] getArrayValue() 
controlP5.Controller : int getHeight() 
controlP5.Controller : int getId() 
controlP5.Controller : int getWidth() 
controlP5.Controller : int listenerSize() 
controlP5.Controller : void remove() 
controlP5.Controller : void setDisplay(ControllerDisplay, int) 
java.lang.Object : String toString() 
java.lang.Object : boolean equals(Object) 


*/

