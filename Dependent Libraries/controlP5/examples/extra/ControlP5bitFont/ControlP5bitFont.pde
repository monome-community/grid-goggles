/**
 * ControlP5 bitFont.
 * this example shows you how to load your own pixel fonts into controlp5. Each letter is 
 * separated by a red dot at the top of the source bit-font image. How a bit-font is created 
 * and has to be formatted, see the examples fonts inside the data folder of this sketch.
 * The following characters are supported (the first character is a SPACE). 
 *  !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~
 * 
 * Example sketch ControlLabelExtended shows you how to use PFont instead of a bitfont.
 * 
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 *
*/

import controlP5.*;


ControlP5 cp5;
Label label;
Textfield myTextfield;
int myBitFontIndex;


void setup() {
  size(400,400);
  cp5 = new ControlP5(this);
  label = new Label(this, "some funny text here.", 100,40, color(255));
  label.position.x = 100;
  label.position.y = 100;
  label.toUpperCase(false);
  
  
  // load a new font and apply it to the newly created label.
  int myBitFontIndex = BitFontRenderer.addBitFont(loadImage("myFontB.gif"));
  label.setFont(myBitFontIndex);

  // apply the newly loaded bit font to the below textfield.
  myTextfield = cp5.addTextfield("textinput",100,160,200,20);
  myTextfield.setFocus(true);
  myTextfield.valueLabel().style().marginTop = -2;
}

void draw() {
  background(0);
  label.draw(this);
}

void textinput(String theValue) {
  label.set(theValue);
}


void mousePressed() {
    label.setFont(ControlP5.standard56);
}

