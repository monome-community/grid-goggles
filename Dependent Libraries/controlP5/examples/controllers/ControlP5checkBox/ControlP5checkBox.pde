/**
 * ControlP5 Checkbox
 * an example demonstrating the use of a checkbox in controlP5. 
 * CheckBox extends the RadioButton class.
 * to control a checkbox use: 
 * activate(), deactivate(), activateAll(), deactivateAll(), toggle(), getState()
 *
 * find a list of public methods available for the Checkbox Controller 
 * at the bottom of this sketch's source code
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlP5
 *
 */


import controlP5.*;

ControlP5 cp5;

CheckBox checkbox;

int myColorBackground;
void setup() {
  size(400, 400);
  smooth();
  cp5 = new ControlP5(this);
  checkbox = cp5.addCheckBox("checkBox", 20, 20);  
  // make adjustments to the layout of a checkbox.
  checkbox.setColorForeground(color(120));
  checkbox.setColorActive(color(255));
  checkbox.setColorLabel(color(128));
  checkbox.setItemsPerRow(3);
  checkbox.setSpacingColumn(30);
  checkbox.setSpacingRow(10);
  // add items to a checkbox.
  checkbox.addItem("0", 0);
  checkbox.addItem("10", 10);
  checkbox.addItem("50", 50);
  checkbox.addItem("100", 100);
  checkbox.addItem("200", 200);
  checkbox.addItem("5", 5);
}

void keyPressed() {
  if (key==' ') {
    checkbox.deactivateAll();
  } 
  else {
    for (int i=0;i<6;i++) {
      // check if key 0-5 have been pressed and toggle
      // the checkbox item accordingly.
      if (keyCode==(48 + i)) { 
        // the index of checkbox items start at 0
        checkbox.toggle(i);
        println("toggle "+checkbox.getItem(i).name());
        // also see 
        // checkbox.activate(index);
        // checkbox.deactivate(index);
      }
    }
  }
}

void draw() {
  background(myColorBackground);
  fill(0);
  rect(10, 10, 150, 60);
}

void controlEvent(ControlEvent theEvent) {
  if (theEvent.isGroup()) {
    myColorBackground = 0;
    print("got an event from "+theEvent.group().name()+"\t");
    // checkbox uses arrayValue to store the state of 
    // individual checkbox-items. usage:
    for (int i=0;i<theEvent.group().arrayValue().length;i++) {
      int n = (int)theEvent.group().arrayValue()[i];
      print(n);
      if (n==1) {
        myColorBackground += ((RadioButton)theEvent.group()).getItem(i).internalValue();
      }
    }
    println();
  }
}


/*
 a list of all methods available for the CheckBox Controller
 use ControlP5.printPublicMethodsFor(CheckBox.class);
 to print the following list into the console.
 
 You can find further details about class CheckBox in the javadoc.
 
 Format:
 ClassName : returnType methodName(parameter type)
 
 controlP5.CheckBox : void setArrayValue(float[]) 
 controlP5.RadioButton : PImage setImage(PImage) 
 controlP5.RadioButton : PImage setImage(PImage, int) 
 controlP5.RadioButton : RadioButton setImages(PImage, PImage, PImage) 
 controlP5.RadioButton : String info() 
 controlP5.RadioButton : String toString() 
 controlP5.RadioButton : Toggle addItem(String, float) 
 controlP5.RadioButton : Toggle addItem(Toggle, float) 
 controlP5.RadioButton : Toggle getItem(int) 
 controlP5.RadioButton : boolean getState(String) 
 controlP5.RadioButton : boolean getState(int) 
 controlP5.RadioButton : void activate(String) 
 controlP5.RadioButton : void activate(int) 
 controlP5.RadioButton : void controlEvent(ControlEvent) 
 controlP5.RadioButton : void deactivate(String) 
 controlP5.RadioButton : void deactivate(int) 
 controlP5.RadioButton : void deactivateAll() 
 controlP5.RadioButton : void removeItem(String) 
 controlP5.RadioButton : void setArrayValue(float[]) 
 controlP5.RadioButton : void setItemHeight(int) 
 controlP5.RadioButton : void setItemWidth(int) 
 controlP5.RadioButton : void setItemsPerRow(int) 
 controlP5.RadioButton : void setNoneSelectedAllowed(boolean) 
 controlP5.RadioButton : void setSize(PImage) 
 controlP5.RadioButton : void setSize(int, int) 
 controlP5.RadioButton : void setSpacingColumn(int) 
 controlP5.RadioButton : void setSpacingRow(int) 
 controlP5.RadioButton : void toggle(int) 
 controlP5.RadioButton : void updateLayout() 
 controlP5.ControlGroup : ControlGroup activateEvent(boolean) 
 controlP5.ControlGroup : String info() 
 controlP5.ControlGroup : String stringValue() 
 controlP5.ControlGroup : String toString() 
 controlP5.ControlGroup : boolean isBarVisible() 
 controlP5.ControlGroup : int getBackgroundHeight() 
 controlP5.ControlGroup : int getBarHeight() 
 controlP5.ControlGroup : int listenerSize() 
 controlP5.ControlGroup : void addCloseButton() 
 controlP5.ControlGroup : void addListener(ControlListener) 
 controlP5.ControlGroup : void controlEvent(ControlEvent) 
 controlP5.ControlGroup : void hideBar() 
 controlP5.ControlGroup : void mousePressed() 
 controlP5.ControlGroup : void removeCloseButton() 
 controlP5.ControlGroup : void removeListener(ControlListener) 
 controlP5.ControlGroup : void setBackgroundColor(int) 
 controlP5.ControlGroup : void setBackgroundHeight(int) 
 controlP5.ControlGroup : void setBarHeight(int) 
 controlP5.ControlGroup : void showBar() 
 controlP5.ControllerGroup : CColor getColor() 
 controlP5.ControllerGroup : ControlWindow getWindow() 
 controlP5.ControllerGroup : ControlWindowCanvas addCanvas(ControlWindowCanvas) 
 controlP5.ControllerGroup : Controller getController(String) 
 controlP5.ControllerGroup : ControllerGroup setHeight(int) 
 controlP5.ControllerGroup : ControllerGroup setValue(float) 
 controlP5.ControllerGroup : ControllerGroup setWidth(int) 
 controlP5.ControllerGroup : ControllerInterface add(ControllerInterface) 
 controlP5.ControllerGroup : ControllerInterface hide() 
 controlP5.ControllerGroup : ControllerInterface moveTo(ControlGroup, Tab, ControlWindow) 
 controlP5.ControllerGroup : ControllerInterface registerProperty(String) 
 controlP5.ControllerGroup : ControllerInterface registerProperty(String, String) 
 controlP5.ControllerGroup : ControllerInterface remove(ControllerInterface) 
 controlP5.ControllerGroup : ControllerInterface removeProperty(String) 
 controlP5.ControllerGroup : ControllerInterface removeProperty(String, String) 
 controlP5.ControllerGroup : ControllerInterface setColor(CColor) 
 controlP5.ControllerGroup : ControllerInterface setColorActive(int) 
 controlP5.ControllerGroup : ControllerInterface setColorBackground(int) 
 controlP5.ControllerGroup : ControllerInterface setColorForeground(int) 
 controlP5.ControllerGroup : ControllerInterface setColorLabel(int) 
 controlP5.ControllerGroup : ControllerInterface setColorValue(int) 
 controlP5.ControllerGroup : ControllerInterface setId(int) 
 controlP5.ControllerGroup : ControllerInterface setLabel(String) 
 controlP5.ControllerGroup : ControllerInterface show() 
 controlP5.ControllerGroup : ControllerProperty getProperty(String) 
 controlP5.ControllerGroup : ControllerProperty getProperty(String, String) 
 controlP5.ControllerGroup : Label captionLabel() 
 controlP5.ControllerGroup : Label valueLabel() 
 controlP5.ControllerGroup : PVector getAbsolutePosition() 
 controlP5.ControllerGroup : PVector getPosition() 
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
 controlP5.ControllerGroup : void close() 
 controlP5.ControllerGroup : void disableCollapse() 
 controlP5.ControllerGroup : void enableCollapse() 
 controlP5.ControllerGroup : void moveTo(ControlGroup) 
 controlP5.ControllerGroup : void moveTo(ControlWindow) 
 controlP5.ControllerGroup : void moveTo(ControlWindow, String) 
 controlP5.ControllerGroup : void moveTo(String) 
 controlP5.ControllerGroup : void moveTo(String, ControlWindow) 
 controlP5.ControllerGroup : void moveTo(Tab) 
 controlP5.ControllerGroup : void moveTo(Tab, ControlWindow) 
 controlP5.ControllerGroup : void open() 
 controlP5.ControllerGroup : void remove() 
 controlP5.ControllerGroup : void remove(CDrawable) 
 controlP5.ControllerGroup : void removeCanvas(ControlWindowCanvas) 
 controlP5.ControllerGroup : void setAbsolutePosition(PVector) 
 controlP5.ControllerGroup : void setArrayValue(float[]) 
 controlP5.ControllerGroup : void setGroup(ControllerGroup) 
 controlP5.ControllerGroup : void setGroup(String) 
 controlP5.ControllerGroup : void setMoveable(boolean) 
 controlP5.ControllerGroup : void setOpen(boolean) 
 controlP5.ControllerGroup : void setPosition(PVector) 
 controlP5.ControllerGroup : void setPosition(float, float) 
 controlP5.ControllerGroup : void setTab(ControlWindow, String) 
 controlP5.ControllerGroup : void setTab(String) 
 controlP5.ControllerGroup : void setTab(Tab) 
 controlP5.ControllerGroup : void setUpdate(boolean) 
 controlP5.ControllerGroup : void setVisible(boolean) 
 controlP5.ControllerGroup : void update() 
 controlP5.ControllerGroup : void updateAbsolutePosition() 
 java.lang.Object : String toString() 
 java.lang.Object : boolean equals(Object) 
 
 */
