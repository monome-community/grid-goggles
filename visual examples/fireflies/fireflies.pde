// created by edward loveall, 2011
// http://edwardloveall.com/projects/processing/fireflies/

import oscP5.*;
import netP5.*;

// gridgoggles port
int oscPort = 3030;
OscP5 oscP5;

ArrayList vectorArray;

// monome 128 dimensions
int monomeWidth = 16;
int monomeHeight = 8;
int sizeScale = 50;

void setup() {
  size(monomeWidth*sizeScale, monomeHeight*sizeScale); // 800 x 400
  smooth();
  noStroke();
  
  oscP5 = new OscP5(this, oscPort);
  
  vectorArray = new ArrayList();
}

void draw() {
  background(16);
  
  for (int i = vectorArray.size() - 1; i >= 0; i--) {
    Firefly theVector = (Firefly)vectorArray.get(i);
    
    // remove the object if it has died;
    if (theVector.alive() && theVector.inBounds()) {
      theVector.update();
      theVector.draw();
    } else {
      vectorArray.remove(i);
      continue;
    }
  }
}

void addVector(int count, PVector pos) {
  for(int i = count - 1; i >= 0; i--) {
    
    PVector tempPos = new PVector();
    // 0-99, but higher values flow off screen 
    int xRand = round(random(0, 49));
    int yRand = round(random(0, 49));
    
    tempPos.x = (pos.x * 50) + xRand;
    tempPos.y = (pos.y * 50) + yRand;
    println(tempPos.x + " - " + tempPos.y);
    
    vectorArray.add(new Firefly(tempPos.x, tempPos.y, 100));
  }
}

void oscEvent(OscMessage msg) {
  if(msg.checkAddrPattern("/grid/key")) {
    int state = msg.get(2).intValue();
    int xButton = msg.get(0).intValue();
    int yButton = msg.get(1).intValue();
    int index = yButton * monomeWidth + xButton;
    
    println(xButton + " - " + yButton + " - " + state);
    if (state == 1) {
      println(xButton + " - " + yButton + " - " + state);
      addVector(10, new PVector(xButton, yButton));
    }
  };
}

class Firefly {
  PVector location;
  PVector velocity;
  float decayRate; // expressed in frames until death
  float opacity;
  float size;
  float saturation;
  
  Firefly(float x, float y, int _decayRate) {
    location = new PVector(x, y);
    velocity = new PVector(random(-0.1, 0.1), random(-0.1, 0.1));
    decayRate = 255 / (_decayRate + random(-30, 0));
    opacity = 255;
    size = random(3, 15);
    saturation = random(0, 255);
  }
  
  void update() {
    opacity -= decayRate;
    location.add(velocity);
  }
  
  // colors: change value before "opacity" to desired color
  void draw() {
    fill(saturation, 127+saturation/2, 255, opacity);
    ellipse(location.x, location.y, size, size);
  }
  
  Boolean alive() {
    if (opacity > 0) {
      return true;
    } else {
      return false;
    }
  }
  
  Boolean inBounds() {
    if (location.x - size/2 > width || location.x + size/2 < 0 ||
        location.y - size/2 > height || location.y + size/2 < 0) {
      return false;
    } else {
      return true;
    }
  }
}
