/**
 * ControlP5 Color picker. a simple color picker, 
 * 4 horizontal sliders controlling the RGBA channels of a color.
 * to grab the current color value, use function getColorValue() of
 * the color picker.
 *
 * find a list of public methods available for the ColorPicker Controller 
 * at the bottom of this sketch's source code
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlP5
 *
 */
import controlP5.*;

ControlP5 cp5;

ColorPicker cp;

void setup() {
  size(400,400);
  cp5 = new ControlP5(this);
  cp = cp5.addColorPicker("picker",20,20,255,20);
}

void draw() {
  background(cp.getColorValue());
  fill(0);
  rect(10,10,275,80);
}

void keyPressed() {
  switch(key) {
    case('1'):
    // method A to change color
    cp.setArrayValue(new float[] {120,0,120,255});
    break;
    case('2'):
    // method B to change color
    cp.setColorValue(color(255,0,0,255));
    break;
  }
}




/*
 a list of all methods available for the ColorPicker Controller
 use ControlP5.printPublicMethodsFor(ColorPicker.class);
 to print the following list into the console.
 
 You can find further details about class ColorPicker in the javadoc.
 
 Format:
 ClassName : returnType methodName(parameter type)
 

controlP5.ColorPicker : ControllerInterface setColorValue(int) 
controlP5.ColorPicker : int getColorValue() 
controlP5.ColorPicker : void controlEvent(ControlEvent) 
controlP5.ColorPicker : void setArrayValue(float[]) 
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
