/**
* ControlP5 ControllerDisplay
*
*
* find a list of public methods available for the ControllerDisplay Controller
* at the bottom of this sketch.
*
* by Andreas Schlegel, 2011
* www.sojamo.de/libraries/controlp5
*
*/


import controlP5.*;


ControlP5 cp5;


void setup() {
  size(400, 400);
  smooth();
  cp5 = new ControlP5(this);
  cp5.addButton("hello").setPosition(50, 100).setWidth(100).setHeight(100).setDisplay(new CircularButton());
  cp5.addButton("world").setPosition(250, 100).setWidth(50).setHeight(50).setDisplay(new CircularButton());
}


void draw() {
  background(0);
}

public void hello(int theValue) {
  println("Hello pressed");
}

public void world(int theValue) {
  println("World pressed");
}

class CircularButton implements ControllerDisplay {

  public void display(PApplet theApplet, Controller theController) {
    // display will override the default ControllerDisplay of a controller
    theApplet.pushMatrix();
    // For available button commands see the Button and Controller reference
    Button b = (Button)theController;
    if (b.isInside()) {
      if (b.isPressed()) { // pressed
        theApplet.fill(200, 60, 0);
      }  else { // over
        theApplet.fill(200, 160, 100);
      }
    } else { // outside
      theApplet.fill(0, 160, 100);
    }
    
    theApplet.ellipse(0, 0, b.getWidth(), b.getHeight());
    
    // center the caption label 
    int x = b.getWidth()/2 - b.getCaptionLabel().getWidth()/2;
    int y = b.getHeight()/2 - b.getCaptionLabel().getHeight()/2;
    
    translate(x, y);
    b.getCaptionLabel().draw(theApplet);
    
    theApplet.popMatrix();
  }
}


/*
a list of all methods available for the ControllerDisplay Controller
use ControlP5.printPublicMethodsFor(ControllerDisplay.class);
to print the following list into the console.

You can find further details about class ControllerDisplay in the javadoc.

Format:
ClassName : returnType methodName(parameter type)

controlP5.ControllerDisplay : void display(PApplet, Controller)

*/

