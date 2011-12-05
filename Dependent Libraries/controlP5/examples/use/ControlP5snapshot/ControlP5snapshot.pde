/**
* ControlP5 snapshot
* this example shows how to use the snapshot methods for ControllerProperties.
* Snapshots allow you to save controller states in memory, recall, save and remove them.
*
* find a list of public methods available for the ControllerProperties Controller
* at the bottom of this sketch.
*
* by Andreas Schlegel, 2011
* www.sojamo.de/libraries/controlp5
*
*/





import controlP5.*;
ControlP5 cp5;

public float n = 50;
public float s = 10;
public float k = 100;

void setup() {
  size(400, 400);
  smooth();
  cp5 = new ControlP5(this);

  Numberbox nb1 = cp5.addNumberbox("n", 10, 10, 42, 16);
  nb1.setMultiplier(0.1);
  nb1.setMin(60);
  nb1.setMax(140);

  Slider s1 = cp5.addSlider("s", 0, 200, 10, 100, 100, 20);
  s1.setScrollSensitivity(0.01);
  s1.setMin(60);
  s1.setMax(140);
  s1.alignValueLabel(Controller.BOTTOM);

  Knob k1 = cp5.addKnob("k", 0, 200, 200, 100, 100);
  k1.setScrollSensitivity(0.001);
  k1.setMin(60);
  k1.setMax(140);
  k1.setDisplayStyle(Controller.ARC);

  Range r1 = cp5.addRange("r", 0, 200, 10, 200, 100, 20);
  r1.setMin(60);
  r1.setMax(140);
} 


void draw() {
  background(0);
}


void keyPressed() {
  switch(key) {
    case('1'):
    cp5.getProperties().setSnapshot("hello1");
    break;
    case('2'):
    cp5.getProperties().setSnapshot("hello2");
    break;
    case('3'):
    cp5.getProperties().setSnapshot("hello3");
    break;

    case('a'):
    cp5.getProperties().getSnapshot("hello1");
    break;
    case('s'):
    cp5.getProperties().getSnapshot("hello2");
    break;
    case('d'):
    cp5.getProperties().getSnapshot("hello3");
    break;
    
    case('z'):
    cp5.getProperties().removeSnapshot("hello1");
    break;
    case('x'):
    cp5.getProperties().removeSnapshot("hello2");
    break;
    case('c'):
    cp5.getProperties().removeSnapshot("hello3");
    break;
    
    case('i'):
    cp5.getProperties().saveSnapshot("hello1");
    break;
    case('o'):
    cp5.getProperties().load("hello1.ser");
    break;
  }

  println(cp5.getProperties().getSnapshotIndices());
}



/*
a list of all methods available for the ControllerProperties Controller
use ControlP5.printPublicMethodsFor(ControllerProperties.class);
to print the following list into the console.

You can find further details about class ControllerProperties in the javadoc.

Format:
ClassName : returnType methodName(parameter type)


controlP5.ControllerProperties : ArrayList getSnapshotIndices() 
controlP5.ControllerProperties : ControllerProperties addSet(String) 
controlP5.ControllerProperties : ControllerProperties delete(ControllerProperty) 
controlP5.ControllerProperties : ControllerProperties getSnapshot(String) 
controlP5.ControllerProperties : ControllerProperties move(ControllerInterface, String, String) 
controlP5.ControllerProperties : ControllerProperties move(ControllerProperty, String, String) 
controlP5.ControllerProperties : ControllerProperties only(ControllerProperty, String) 
controlP5.ControllerProperties : ControllerProperties print() 
controlP5.ControllerProperties : ControllerProperties register(ControllerInterface, String) 
controlP5.ControllerProperties : ControllerProperties remove(ControllerInterface) 
controlP5.ControllerProperties : ControllerProperties remove(ControllerInterface, String) 
controlP5.ControllerProperties : ControllerProperties remove(ControllerInterface, String, String) 
controlP5.ControllerProperties : ControllerProperties removeSnapshot(String) 
controlP5.ControllerProperties : ControllerProperties saveSnapshot(String) 
controlP5.ControllerProperties : ControllerProperties saveSnapshotAs(String, String) 
controlP5.ControllerProperties : ControllerProperties setSnapshot(String) 
controlP5.ControllerProperties : ControllerProperties updateSnapshot(String) 
controlP5.ControllerProperties : ControllerProperty getProperty(ControllerInterface, String) 
controlP5.ControllerProperties : ControllerProperty getProperty(ControllerInterface, String, String) 
controlP5.ControllerProperties : ControllerProperty register(ControllerInterface, String, String) 
controlP5.ControllerProperties : HashSet getPropertySet(ControllerInterface) 
controlP5.ControllerProperties : List get(ControllerInterface) 
controlP5.ControllerProperties : Map get() 
controlP5.ControllerProperties : String toString() 
controlP5.ControllerProperties : boolean load() 
controlP5.ControllerProperties : boolean load(String) 
controlP5.ControllerProperties : boolean save() 
controlP5.ControllerProperties : boolean saveAs(String) 
controlP5.ControllerProperties : void setFormat(Format) 
java.lang.Object : String toString() 
java.lang.Object : boolean equals(Object) 


*/



