/**
 * ControlP5 properties example (see keyPressed).
 * by andreas schlegel, 2011
 *
 * status: experimental
 * saves/loads controller values into/from a properties-file 
 * by default properties will be saved in a serialized format and use the .ser extension.
 *
 * taken from the changelog;
 * adding saveProperties(String) and loadProperties(String) to 
 * save and load serialized controller properties. 
 * The range of controllers implementing save/load properties currently includes
 * Slider, Knob, Numberbox, Toggle, Checkbox, RadioButton, Textlabel, Matrix,Range, 
 * textarea, ListBox, Dropdown, colorPicker. 
 * Properties are currently saved in the java serialization 
 * format but xml and possibly json alternatives are being worked on.
 *
 *
 * default properties load/save key combinations are 
 * alt+shift+l to load properties
 * alt+shift+s to save properties
 *
 */

import controlP5.*;

ControlP5 controlP5;
public int myColor = color(0,0,0);

public int sliderValue = 100;
public int sliderTicks1 = 100;
public int sliderTicks2 = 30;


void setup() {
  size(400,400);
  controlP5 = new ControlP5(this);
  
  // add a vertical slider
  controlP5.addSlider("slider",0,200,128,20,100,10,100);
  // create another slider with tick marks, now without
  // default value, the initial value will be set according th
  // the value of variable sliderTicks2 then.
  controlP5.addSlider("sliderTicks1",0,255,100,100,10,100);
  Slider s1 = (Slider)controlP5.controller("sliderTicks1");
  s1.setNumberOfTickMarks(5);
  
  
  // add horizontal sliders
  controlP5.addSlider("sliderValue",0,255,128,200,180,100,10);
  controlP5.addSlider("sliderTicks2",0,255,128,200,220,100,10);
  Slider s2 = (Slider)controlP5.controller("sliderTicks2");
  s2.setNumberOfTickMarks(7);
  // use Slider.FIX or Slider.FLEXIBLE to change the slider handle
  // by default it is Slider.FIX
  s2.setSliderMode(Slider.FLEXIBLE);
  
}

void draw() {
  background(sliderTicks1);
  
  fill(sliderValue);
  rect(0,0,width,100);
  
  fill(myColor);
  rect(0,300,width,70);
  
  fill(sliderTicks2);
  rect(0,370,width,30);
}

public void slider(float theColor) {
  myColor = color(theColor);
  println("a slider event. setting background to "+theColor);
}

void keyPressed() {
  // default properties load/save key combinations are 
  // alt+shift+l to load properties
  // alt+shift+s to save properties
  if(key=='1') {
    controlP5.saveProperties(("hello.ser"));
  } else if(key=='2') {
    controlP5.loadProperties(("hello.ser"));
  }
}

