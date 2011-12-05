/**
 * ControlP5 Bang
 * A bang doesnt have a value but only triggers an event that can be received by a
 * function named after the bang's name or parsing an event inside the controlEvent function.
 * By default a bang is triggered when pressed but this can be changed to 'release' 
 * using theBang.setTriggerEvent(Bang.RELEASE).
 *
 * find a list of public methods available for the Bang Controller 
 * at the bottom of this sketch.
 *
 * by andreas schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 * 
 */

import controlP5.*;

ControlP5 controlP5;

int myColorBackground = color(0, 0, 0);

color[] col = new color[] {
  color(100), color(150), color(200), color(250)
};


void setup() {
  size(400, 400);
  frameRate(30);

  controlP5 = new ControlP5(this);
  for (int i=0;i<col.length;i++) {
    controlP5.addBang("bang"+i, 40+i*80, 150, 40, 40).setId(i);
  }
  // change the trigger event, by default it is PRESSED.
  controlP5.addBang("bang", 40, 250, 120, 40).setTriggerEvent(Bang.RELEASE);
  controlP5.controller("bang").setLabel("changeBackground");
}

void draw() {
  background(myColorBackground);
  for (int i=0;i<col.length;i++) {
    fill(col[i]);
    rect(40+i*80, 50, 40, 80);
  }
}

public void bang() {
  int theColor = (int)random(255);
  myColorBackground = color(theColor);
  println("### bang(). a bang event. setting background to "+theColor);
}

public void controlEvent(ControlEvent theEvent) {
  for (int i=0;i<col.length;i++) {
    if (theEvent.controller().name().equals("bang"+i)) {
      col[i] = color(random(255));
    }

    // or alternatively if you want to check controllers by id
    // uncomment the following 3 lines.

    // if(theEvent.controller().id()==i) {
    //   col[i] = color(random(255));
    // }
  }
  println(
  "## controlEvent / id:"+theEvent.controller().id()+
    " / name:"+theEvent.controller().name()+
    " / label:"+theEvent.controller().label()+
    " / value:"+theEvent.controller().value()
    );
}

/*
a list of all methods available for the Bang Controller
 use ControlP5.printPublicMethodsFor(Bang.class);
 to print the following list into the console.
 You can find further details about class Bang in the javadoc.
 
 Format:
 
 ClassName : returnType methodName(parameter type)
 
 
 controlP5.Bang : Bang setTriggerEvent(int) 
 controlP5.Bang : Bang setValue(float) 
 controlP5.Bang : Bang update() 
 controlP5.Bang : String getInfo() 
 controlP5.Bang : String toString() 
 controlP5.Bang : int getTriggerEvent() 
 controlP5.Bang : void updateDisplayMode(int) 
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


