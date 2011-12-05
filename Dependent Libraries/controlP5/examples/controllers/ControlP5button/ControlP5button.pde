/**
 * ControlP5 Button
 * this example shows how to create buttons with controlP5.
 * 
 * find a list of public methods available for the Button Controller 
 * at the bottom of this sketch's source code
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 *
 */
 
import controlP5.*;

ControlP5 cp5;

Button b;

// the controlP5 button controller 'buttonValue' will change the
// value of variable 'buttonValue'  when pressed.
int buttonValue = 0;

int myColor = color(0,255,0);


void setup() {
  size(640,480);
  smooth();
  frameRate(30);
  cp5 = new ControlP5(this);
  
  // create a new button with name 'buttonA'
  cp5.addButton("buttonA",0,100,100,80,19);
  
  // and add another 2 buttons
  cp5.addButton("buttonB",255,100,120,80,19);
  cp5.addButton("buttonValue",128,100,140,80,19);

}

void draw() {
  background(myColor);
  fill(buttonValue);
  rect(20,20,width-40,height-40);
}

public void controlEvent(ControlEvent theEvent) {
  println(theEvent.controller().name());
  
}

// function buttonA will receive changes from 
// controller with name buttonA
public void buttonA(int theValue) {
  println("a button event from buttonA: "+theValue);
  myColor = color(255,0,0);
}

// function buttonB will receive changes from 
// controller with name buttonB
public void buttonB(int theValue) {
  println("a button event from buttonB: "+theValue);
  myColor = theValue;
}



/*
a list of all methods available for the Button Controller
use ControlP5.printPublicMethodsFor(Button.class);
to print the following list into the console.

You can find further details about class Button in the javadoc.

Format:
ClassName : returnType methodName(parameter type)


controlP5.Button : Button activateBy(int) 
controlP5.Button : Button setOff() 
controlP5.Button : Button setOn() 
controlP5.Button : Button setSwitch(boolean) 
controlP5.Button : Button setValue(float) 
controlP5.Button : Button update() 
controlP5.Button : String getInfo() 
controlP5.Button : String toString() 
controlP5.Button : boolean booleanValue() 
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

