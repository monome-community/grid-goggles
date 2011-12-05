/**
 * ControlP5 auto
 *
 * this example demonstrates the use of controlP5's shortcut for 
 * adding auto-arranged controllers.
 * for implementation details see controlP5/ControlP5Base and 
 * the related examples ControlP5quick.
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 *
 */
 
import controlP5.*;

ControlP5 cp5;  

float a = 0;
float b = 0;
float c= 0;

// controller toggleB will changed this variable 
public int toggleB = 1;

// controller toggleC will changed this variable 
public int toggleC = 1;

void setup() {
  size(600,400);
  cp5 = new ControlP5(this);
  
  // begin a new group of auto-arranged controllers at position 100,100
  cp5.begin(100,100);
  
  // linebreak() forces the consecutive controller to start in the next row.
  cp5.addSlider("sliderA",0,100).linebreak();
  cp5.addNumberbox("numberboxB");
  cp5.addNumberbox("numberboxC").linebreak();
  cp5.addButton("buttonB");
  cp5.addButton("buttonC");
  // end the grouping of auto-arranged controllers
  cp5.end();
  
  
  // add a new controller window, 250x250 at position 10,10
  ControlWindow cw = cp5.addControlWindow("win",250,250);
  cw.setLocation(10,10);
  
  // create a new group and move it to the previously created ControlWindow.
  ControlGroup cg = cp5.addGroup("myGroup",30,30);
  cg.moveTo(cw);
  
  // add auto-generated controllers to our group
  cp5.begin(cg,0,10);
  cp5.addSlider("hello",0,100).linebreak();
  cp5.addToggle("toggleB");
  cp5.addToggle("toggleC");
  cp5.end();
  
  // change the value of sliderA
  cp5.getController("sliderA").setValue(50);
}


void draw() {
  background(a);
  if(toggleB != 0) {
    fill(b);
    rect(100,100,200,200);
  }
  if(toggleC != 0) {
    fill(c);
    rect(310,100,200,200);
  }
}

// controller sliderA will invoke this function when changed
public void sliderA(int v) { a = v; }

// controller numberboxB will invoke this function when changed
public void numberboxB(int v) { b = v; }

// controller numberboxC will invoke this function when changed
public void numberboxC(int v) { c = v; }

// controller buttonB will invoke this function when changed
public void buttonB(int v) { b = 128; }

// controller buttonC will invoke this function when changed
public void buttonC(int v) { c = 128; }


