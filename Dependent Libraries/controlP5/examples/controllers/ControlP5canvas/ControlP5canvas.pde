/**
 * ControlP5 Canvas
 * The ControlWindowCanvas allow you to add custom graphics to 
 * the default controlP5 renderer or a controlWindow rednerer.
 *
 * find a list of public methods available for the Canvas Controller 
 * at the bottom of this sketch's source code
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 * 
 */


import controlP5.*;

ControlP5 controlP5;
ControlWindow controlWindow;
ControlWindowCanvas cc;

// MyCanvas, your controlWindowCanvas render class
class MyCanvas extends ControlWindowCanvas {

  int y;

  public void setup(PApplet theApplet) {
    y = 200;
  }  

  public void draw(PApplet theApplet) {
    // renders a square with randomly changing colors
    // make changes here.
    theApplet.fill(random(255));
    theApplet.rect(theApplet.mouseX, y, 100, 100);
  }
}


void setup() {
  size(400, 400);
  frameRate(30);
  controlP5 = new ControlP5(this);

  ControlP5.printPublicMethodsFor(ControlWindowCanvas.class);

  // create a new control window.
  controlWindow = controlP5.addControlWindow("controlP5window", 100, 100, 400, 400, 30);

  // for continuous update use ControlWindow.NORMAL  to update a control
  // window only when it is in focus, use ControlWindow.ECONOMIC
  // economic is the default update value.
  controlWindow.setUpdateMode(ControlWindow.NORMAL);


  // create a control window canvas and add it to
  // the previously created control window.  
  cc = new MyCanvas();
  cc.pre(); // use cc.post(); to draw on top of existing controllers.
  controlWindow.addCanvas(cc); // add the canvas to controlWindow

  // now add the same canvas to the default sketch window as well.
  // here, the draw() method of the ControlWindowCanvas will be called a second time every frame,
  // therefore the color value of the square does not match the color of the canvas rendered
  // inside the controlWindow (see above).
  controlP5.addCanvas(cc);
}

void draw() {
  background(0);
  fill(255, 0, 0);
  rect(100, 100, 200, 200);
}


/*
 a list of all methods available for the ControlWindowCanvas Controller
 use ControlP5.printPublicMethodsFor(ControlWindowCanvas.class);
 to print the following list into the console.
 
 You can find further details about class ControlWindowCanvas in the javadoc.
 
 Format:
 ClassName : returnType methodName(parameter type)
 
 controlP5.ControlWindowCanvas : void moveTo(ControlWindow) 
 controlP5.ControlWindowCanvas : void setup(PApplet) 
 controlP5.ControlWindowCanvas : void draw(PApplet) 
 java.lang.Object : String toString() 
 java.lang.Object : boolean equals(Object) 
 */
