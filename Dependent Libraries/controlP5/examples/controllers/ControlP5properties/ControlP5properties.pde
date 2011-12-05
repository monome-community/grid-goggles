/**
 * ControlP5 Properties 
 *
 * Properties will only save values not the Controller itself.
 *
 * saves controller values to a properties-file
 * loads controller values from a properties-file
 *
 * Use ControllerProperties to to save and load serialized controller properties 
 * inside a properties file. 
 * The controllers implementing save/load properties so far are 
 * Slider, Knob, Numberbox, Toggle, Checkbox, RadioButton, Textlabel, 
 * Matrix, Range, Textarea, ListBox, Dropdown, ColorPicker. 
 * Properties are currently saved in the java serialization 
 * format but xml and possibily json alternatives are being worked on.
 *
 * saveProperties(String theFilename) and loadProperties(String theFilename) 
 * by default properties will be save to your sketch folder as controlP5.properties
 * if that file already exists it will be overwritten. for custom property files
 * see keyPressed() below.
 *
 * find a list of public methods available for the ControllerProperties class 
 * at the bottom of this sketch's source code
 *
 * default properties load/save key combinations are 
 * alt+shift+l to load properties
 * alt+shift+s to save properties
 *
 * by andreas schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 *
 */

import controlP5.*;

ControlP5 cp5;
public int myColor = color(0, 0, 0);

public int sliderValue = 100;
public int sliderTicks1 = 100;
public int sliderTicks2 = 30;


void setup() {
  size(400, 400);
  cp5 = new ControlP5(this);

  // add a vertical slider
  cp5.addSlider("slider", 0, 200, 128, 20, 100, 10, 100);
  cp5.addSlider("sliderTicks1", 0, 255, 100, 100, 10, 100);
  Slider s1 = (Slider)cp5.controller("sliderTicks1");
  s1.setNumberOfTickMarks(5);


  // add horizontal sliders
  cp5.addSlider("sliderValue", 0, 255, 128, 200, 180, 100, 10);
  cp5.addSlider("sliderTicks2", 0, 255, 128, 200, 220, 100, 10);
  Slider s2 = (Slider)cp5.controller("sliderTicks2");
  s2.setNumberOfTickMarks(7);
  s2.setSliderMode(Slider.FLEXIBLE);
  ControlP5.printPublicMethodsFor(ControllerProperty.class);
}

void draw() {
  background(sliderTicks1);

  fill(sliderValue);
  rect(0, 0, width, 100);

  fill(myColor);
  rect(0, 300, width, 70);

  fill(sliderTicks2);
  rect(0, 370, width, 30);
}

public void slider(float theColor) {
  myColor = color(theColor);
  println("a slider event. setting background to "+theColor);
}

void keyPressed() {
  // default properties load/save key combinations are 
  // alt+shift+l to load properties
  // alt+shift+s to save properties
  if (key=='1') {
    cp5.saveProperties(("hello.properties"));
  } 
  else if (key=='2') {
    cp5.loadProperties(("hello.properties"));
  }
}

/*
 a list of all methods available for the ControllerProperties class
 
 use ControlP5.printPublicMethodsFor(ControllerProperties.class);
 to print the following list into the console.
 
 You can find further details about ControllerProperties in controlP5's javadoc.
 
 Format: returnType methodName(parameterType)
 
 ArrayList get(ControllerInterface)
 ControllerProperties remove(ControllerInterface)
 ControllerProperties remove(ControllerInterface, String)
 ControllerProperties remove(ControllerInterface, String, String)
 ControllerProperty getProperty(ControllerInterface, String)
 ControllerProperty getProperty(ControllerInterface, String, String)
 ControllerProperty register(ControllerInterface, String)
 ControllerProperty register(ControllerInterface, String, String)
 HashSet addSet(String)
 HashSet getPropertySet(ControllerInterface)
 Map get()
 String toString()
 boolean equals(Object)
 boolean load()
 boolean load(String)
 void delete(ControllerProperty)
 void move(ControllerInterface, String, String)
 void move(ControllerProperty, String, String)
 void only(ControllerProperty, String)
 void setFormat(Format)
 
 */
