import controlP5.*;

ControlP5 cp5;

void setup() {
  size(800, 400);

  cp5 = new ControlP5(this);
  // disable outodraw because we want to draw our 
  // custom cursor on to of controlP5
  cp5.setAutoDraw(false);
  
  cp5.addSlider("hello", 0, 100, 50, 40, 40, 100, 20);
  
  // enable the pointer (and disable the mouse as input) 
  cp5.getPointer().enable();
  cp5.getPointer().set(width/2, height/2);
}


void draw() {
  background(0);
  // first draw controlP5
  cp5.draw();
  
  // the draw our pointer
  cp5.getPointer().set(width - mouseX, height - mouseY);
  pushMatrix();
  translate(cp5.getPointer().getX(), cp5.getPointer().getY());
  stroke(255);
  line(-10,0,10,0);
  line(0,-10,0,10);
  popMatrix();
  println(cp5.isMouseOver());
}

void mousePressed() {
  cp5.getPointer().pressed();
}

void mouseReleased() {
  cp5.getPointer().released();
}
