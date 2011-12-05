/**
 * ControlP5 Accordion
 * arrange controller groups in an accordion like fashion.
 *
 * find a list of public methods available for the Accordion Controller 
 * at the bottom of this sketch.
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 *
 */

import controlP5.*;

ControlP5 cp5;
Accordion a;

void setup() {
  size(720, 400);
  frameRate(30);

  cp5 = new ControlP5(this);

  // group number 1, contains 2 bangs
  ControlGroup g1 = cp5.addGroup("myGroup1", 100, 200, 300);
  cp5.addBang("A-1", 10, 10, 20, 20).moveTo(g1);
  cp5.addBang("A-2", 40, 10, 20, 20).moveTo(g1);
  g1.setBackgroundColor(color(255, 100));
  g1.setBackgroundHeight(150);

  // group number 2, contains a radiobutton
  ControlGroup g2 = cp5.addGroup("myGroup2", 0, 20, 300);
  g2.setBackgroundColor(color(255, 50));
  g2.setBackgroundHeight(150);
  RadioButton r = cp5.addRadioButton("radio", 100, 4);
  r.addItem("black", 0);
  r.addItem("red", 1);
  r.addItem("green", 2);
  r.addItem("blue", 3);
  r.addItem("grey", 4);
  r.setColorLabel(color(255));
  r.setValue(1);
  r.moveTo(g2);

  // group number 3, contains a bang and a slider
  ControlGroup g3 = cp5.addGroup("myGroup3", 100, 100, 300);
  g3.setBackgroundColor(color(255, 50));
  g3.setBackgroundHeight(50);
  cp5.addBang("B-1", 10, 10, 20, 20).moveTo(g3);
  cp5.addSlider("hello", 0, 100, 50, 100, 10, 100, 10).moveTo(g3);

  // create a new accordion
  a = cp5.addAccordion("acco", 100, 100, 200);
  // add g1, g2, and g3 to accordion a
  a.addItem(g1);
  a.addItem(g2);
  a.addItem(g3);
}



void keyPressed() {
  if (key=='1') {
    cp5.remove("myGroup1");
  } 
  else if (key=='2') {
    a.setWidth(300);
  } 
  else if (key=='3') {
    a.setItemHeight(100);
  }
}

void draw() {
  background(0);
}


/*
a list of all methods available for the Accordion Controller
 use ControlP5.printPublicMethodsFor(Accordion.class);
 to print the following list into the console.
 You can find further details about class Accordion in the javadoc.
 
 Format:
 
 ClassName : returnType methodName(parameter type)
 
 
 controlP5.Accordion : Accordion addItem(ControlGroup) 
 controlP5.Accordion : Accordion remove(ControllerInterface) 
 controlP5.Accordion : Accordion removeItem(ControlGroup) 
 controlP5.Accordion : Accordion setItemHeight(int) 
 controlP5.Accordion : Accordion setMinItemHeight(int) 
 controlP5.Accordion : Accordion setWidth(int) 
 controlP5.Accordion : Accordion updateItems() 
 controlP5.Accordion : int getMinItemHeight() 
 controlP5.ControlGroup : ControlGroup activateEvent(boolean) 
 controlP5.ControlGroup : ControlGroup addListener(ControlListener) 
 controlP5.ControlGroup : ControlGroup hideBar() 
 controlP5.ControlGroup : ControlGroup removeListener(ControlListener) 
 controlP5.ControlGroup : ControlGroup setBackgroundColor(int) 
 controlP5.ControlGroup : ControlGroup setBackgroundHeight(int) 
 controlP5.ControlGroup : ControlGroup setBarHeight(int) 
 controlP5.ControlGroup : ControlGroup showBar() 
 controlP5.ControlGroup : String info() 
 controlP5.ControlGroup : String toString() 
 controlP5.ControlGroup : boolean isBarVisible() 
 controlP5.ControlGroup : int getBackgroundHeight() 
 controlP5.ControlGroup : int getBarHeight() 
 controlP5.ControlGroup : int listenerSize() 
 controlP5.ControllerGroup : CColor getColor() 
 controlP5.ControllerGroup : ControlWindow getWindow() 
 controlP5.ControllerGroup : ControlWindowCanvas addCanvas(ControlWindowCanvas) 
 controlP5.ControllerGroup : Controller getController(String) 
 controlP5.ControllerGroup : ControllerGroup add(ControllerInterface) 
 controlP5.ControllerGroup : ControllerGroup close() 
 controlP5.ControllerGroup : ControllerGroup disableCollapse() 
 controlP5.ControllerGroup : ControllerGroup enableCollapse() 
 controlP5.ControllerGroup : ControllerGroup hide() 
 controlP5.ControllerGroup : ControllerGroup moveTo(ControlWindow) 
 controlP5.ControllerGroup : ControllerGroup open() 
 controlP5.ControllerGroup : ControllerGroup registerProperty(String) 
 controlP5.ControllerGroup : ControllerGroup registerProperty(String, String) 
 controlP5.ControllerGroup : ControllerGroup remove(CDrawable) 
 controlP5.ControllerGroup : ControllerGroup remove(ControllerInterface) 
 controlP5.ControllerGroup : ControllerGroup removeCanvas(ControlWindowCanvas) 
 controlP5.ControllerGroup : ControllerGroup removeProperty(String) 
 controlP5.ControllerGroup : ControllerGroup removeProperty(String, String) 
 controlP5.ControllerGroup : ControllerGroup setAddress(String) 
 controlP5.ControllerGroup : ControllerGroup setArrayValue(float[]) 
 controlP5.ControllerGroup : ControllerGroup setColor(CColor) 
 controlP5.ControllerGroup : ControllerGroup setColorActive(int) 
 controlP5.ControllerGroup : ControllerGroup setColorBackground(int) 
 controlP5.ControllerGroup : ControllerGroup setColorForeground(int) 
 controlP5.ControllerGroup : ControllerGroup setColorLabel(int) 
 controlP5.ControllerGroup : ControllerGroup setColorValue(int) 
 controlP5.ControllerGroup : ControllerGroup setHeight(int) 
 controlP5.ControllerGroup : ControllerGroup setId(int) 
 controlP5.ControllerGroup : ControllerGroup setLabel(String) 
 controlP5.ControllerGroup : ControllerGroup setMoveable(boolean) 
 controlP5.ControllerGroup : ControllerGroup setOpen(boolean) 
 controlP5.ControllerGroup : ControllerGroup setPosition(PVector) 
 controlP5.ControllerGroup : ControllerGroup setPosition(float, float) 
 controlP5.ControllerGroup : ControllerGroup setStringValue(String) 
 controlP5.ControllerGroup : ControllerGroup setUpdate(boolean) 
 controlP5.ControllerGroup : ControllerGroup setValue(float) 
 controlP5.ControllerGroup : ControllerGroup setVisible(boolean) 
 controlP5.ControllerGroup : ControllerGroup setWidth(int) 
 controlP5.ControllerGroup : ControllerGroup show() 
 controlP5.ControllerGroup : ControllerGroup update() 
 controlP5.ControllerGroup : ControllerGroup updateAbsolutePosition() 
 controlP5.ControllerGroup : ControllerProperty getProperty(String) 
 controlP5.ControllerGroup : ControllerProperty getProperty(String, String) 
 controlP5.ControllerGroup : Label captionLabel() 
 controlP5.ControllerGroup : Label valueLabel() 
 controlP5.ControllerGroup : PVector getPosition() 
 controlP5.ControllerGroup : String getAddress() 
 controlP5.ControllerGroup : String getName() 
 controlP5.ControllerGroup : String getStringValue() 
 controlP5.ControllerGroup : String info() 
 controlP5.ControllerGroup : String toString() 
 controlP5.ControllerGroup : Tab getTab() 
 controlP5.ControllerGroup : boolean isCollapse() 
 controlP5.ControllerGroup : boolean isMoveable() 
 controlP5.ControllerGroup : boolean isOpen() 
 controlP5.ControllerGroup : boolean isUpdate() 
 controlP5.ControllerGroup : boolean isVisible() 
 controlP5.ControllerGroup : boolean setMousePressed(boolean) 
 controlP5.ControllerGroup : float getValue() 
 controlP5.ControllerGroup : float[] getArrayValue() 
 controlP5.ControllerGroup : int getHeight() 
 controlP5.ControllerGroup : int getId() 
 controlP5.ControllerGroup : int getWidth() 
 controlP5.ControllerGroup : void remove() 
 java.lang.Object : String toString() 
 java.lang.Object : boolean equals(Object) 
 
 
 */
