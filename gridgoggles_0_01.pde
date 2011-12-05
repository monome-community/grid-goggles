/*
|| Grid Goggles 0.01
|| by Raymond Weitekamp
|| Date:  12.4.11
|| Summary: A utility for synchronous visualization of time-dependent matrices.  Like a monome.
||         In development...pre-alpha.
||
|| Details: Currently for monome only.  Receives "/key" and "/led" messages on port 3030.  
||          Use AlphaNerd's SerialOSnoopy or Griddle to easily copy your OSC messages:
||          https://github.com/TheAlphaNerd/SerialOSnoopy--Monome
||          http://docs.monome.org/doku.php?id=app:griddle
||
||
|| Instructions for adding: There are specific sections of the code for adding new visualizations.
||       These are tagged with the symbol '@@@'.  
||
|| Note: Currently developed for Processing 1.5.1, and rendered with the GLGraphics library by CodeAntiCode
|| 
|| License: GNU GPL 2.0 (http://creativecommons.org/licenses/GPL/2.0/)
*/


/*
MY TODOs:
  -ADD ControlP5 for OSC port
  -ADD ControlP5 for specific visualization parameters
  -README

WISHLIST:
  -Lots more visualizations!
  -color picker
  -transitions between visuals
  -256 testing
    -test re-sizing
  -cross-platform, streamlined fullscreen mode

SOME IDEAS FOR VISUALS TO ADAPT/ADD 
  -space invaders!
  -particles.  too intensive? see "bouncy boxes": http://www.openprocessing.org/visuals/?visualID=6535
  -flocking
  -wavy?  could be cool? http://www.openprocessing.org/visuals/?visualID=16256

*/

/******* IMPORT LIBRARIES *******/
//import io
import controlP5.*;

//import osc
import oscP5.*;
import netP5.*;

//import openGL
import processing.opengl.*;
import javax.media.opengl.*;

//import GLGraphics
import codeanticode.glgraphics.*;

//image handling
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;


//**SPECIFIC VISUALIZATION LIBRARIES** @@@
      //import toxiclibs things
      import toxi.math.*;
      import toxi.color.*;
      import toxi.geom.*;
      import toxi.geom.mesh.*;
      import toxi.sim.grayscott.*;  //gray scott
      import toxi.sim.automata.*;   //cellular automata
/*******************************/


/******* GLOBAL PARAMETERS *******/
//MONOME
  int w = 8;                  //monome width
  int h = 8;                  //monome height
  int memory = 200;           //number of last events to remember

//VISUAL
  boolean useOpenGL = true;          //USE OPENGL OR PROCESSING RENDERER?
  
  int frameR = 20;            //Framerate
  
  int totalWidth = 800;       //screen size
  int totalHeight = 600;
  
  int frameX = 0;
  int frameY = 20  ;            //FRAME LOCATION
  
  int ww = 200;               //control window size
  int wh = 800;
  int iow = 4;                //control grid size
  int ioh = 4;
  int tw = 50;                //control grid button size
  int th = 50;
  int tgap = 5;               //control grid button gap 
  int marginL = 5;            //control grid margins
  int marginT = 10;
  
  int fudge = 50;             //milliseconds to fudge the OSC playback by...

  //SPECIFIC VISUALIZATION PARAMETERS
  String[] vizNames = {            //names of all visualizations @@@
    "Simple","Gray-Scott","Spherical Harmonics", "Cellular Automata", "Interference","3D Fadaway"
  };
  
  float gsScalar = 3;                //ratio of sketch w,h to GS layer w.h
  int iterations = 3;                //number of GS iterations before rendering
  
  float caScalar = 2;              //ratio of cellular automata frame size
  
  float intScalar = 1;             //interference scalar


  //OSC PARAMETERS
  int routeIn = 3030;      //input port
  String ip = "127.0.0.1";  //ip address
  String ledAddress = "/grid/led/set";  //LED prefix
  String pressAddress = "/grid/key";  //button press prefix

/*******************************/


/***** OBJECTS, CUSTOM THREADS, LIBRARY SETUP & GLOBAL VARIABLES ******/
//MONOME VARIABLES
int x,y,s;
int[][] leds;  //Current x,y matrix of LEDS
int[][] press;  //Current x,y matrix of PRESSES

//matrices which store timestamped past events 
  ArrayList<Spot> ledMem;
  ArrayList<Spot> pressMem;
  Spot ledNow;
  Spot pressNow;
  
int[] timeStamp; //timeStamp
int bw,bh,gap;
int[] ledLast;
int[] pressLast;

//OSC OBJECTS
OscP5 router;
NetAddress remote;

//CONTROL WINDOW VARIABLES
ControlP5 controlP5;
ControlWindow controlWindow;
MultiList l;
Slider speed, frameSet, gsScale;
List<controlP5.MultiListButton> selecta; //#look: adapt for visual choice?
boolean go;

//Background image
PImage bk;
color black = color(0,0,0,0);
color offscreenBK = color(255);
int cIdx, cIdx2;                      //color index
color currentColor = color(0);
color altColor = color(100);

//FX VARIABLES
int vizNow, vizThen;
GLGraphicsOffScreen proxy;
GLGraphicsOffScreen intCanvas;
GLGraphicsOffScreen cellular;
GLGraphicsOffScreen gsIn;
GLGraphicsOffScreen gsOut;
color temp;
float factor;

//**SPECIFIC VISUALIZATION VARIABLES** @@@
        //Gray-Scott
        GrayScott gs;
        ToneMap gsToneMap;
        int jLast = 0; //for reweighting the equation.
        boolean beenColored;
        PImage gsProxy;
        
        //Cellular Automata
        CAMatrix ca;
        ToneMap caToneMap;
        
        //Interference
        float intGrid[][][];
        PGraphics interference;
        int page = 0;
        float phase = PI/2;
        
        //SPHERICAL HARMONICS
        TriangleMesh mesh = new TriangleMesh();

        boolean isWireFrame = true;
        boolean showNormals;
        boolean doSave;

        Matrix4x4 normalMap = new Matrix4x4().translateSelf(128,128,128).scaleSelf(200);

/*******************************/

/*******      INIT      *******/
public void init(){
  // to make a frame not displayable, you can
  // use frame.removeNotify()

  frame.removeNotify();
  frame.setUndecorated(true);
  //frame.setAlwaysOnTop(true);
  

  // addNotify, here i am not sure if you have 
  // to add notify again.  
  frame.addNotify();
  
  
  super.init();
}
/*******************************/

/*******      SETUP      *******/
void setup() {
  //weird workaround for capture + openGL
  if(useOpenGL){
    try {
      quicktime.QTSession.open();
    } catch (quicktime.QTException qte) {
      qte.printStackTrace();
    }
    size(totalWidth,totalHeight,GLConstants.GLGRAPHICS);       //set screen size w/ OPENGL
  } else {
    size(totalWidth,totalHeight,JAVA2D);          //set screen size w/o OPENGL
  }
  frameRate(frameR);                  //set framerate
  
  //SETUP MONOME
  leds = new int[w][h];     //current led state
  press = new int[w][h];    //current press state
  bw = height/10 - 2; //#change
  bh = height/10 - 2;  //#change
  gap = 5;  
  ledLast = new int[2];
  pressLast = new int[2];
  
  ledNow = new Spot();
  pressNow = new Spot();
  
  ledMem = new ArrayList<Spot>();
  pressMem = new ArrayList<Spot>();
  
  //SETUP OSC
  router = new OscP5(this, routeIn);
  
  //SETUP WINDOW
  frame.setLocation(frameX,frameY);
  
  //SETUP PGraphics
  proxy = new GLGraphicsOffScreen(this, width, height);

  
  
  //SETUP SPECIFIC VISUALIZATIONS @@@
          //GRAY-SCOTT
          gsIn = new GLGraphicsOffScreen(this, int(width/gsScalar),int(height/gsScalar), true, 4);
          gsOut = new GLGraphicsOffScreen(this, int(width/gsScalar),int(height/gsScalar), true, 4);
          gs=new GrayScott(gsIn.width,gsIn.height,true);
          gsToneMap=new ToneMap(0,.25,NamedColor.NAVY,NamedColor.WHITE,256);
          gsProxy = new PImage(gsIn.width,gsIn.height);
          
          //SPHERICAL HARMONICS
          setMesh();
          
          //Cellular Automata
          //cellular = createGraphics(int(width/caScalar),int(height/caScalar),JAVA2D);
          cellular = new GLGraphicsOffScreen(this, int(width/caScalar),int(height/caScalar), true, 4);
          // the birth rules specify options for when a cell becomes active
          // the numbers refer to the amount of ACTIVE neighbour cells allowed,
          // their order is irrelevant
          byte[] birthRules=new byte[] { 
            2,5 };
          // survival rules specify the possible numbers of allowed or required
          // ACTIVE neighbour cells in order for a cell to stay alive
          byte[] survivalRules=new byte[] { 
            3,4,6,7 };
          // setup cellular automata matrix
          ca=new CAMatrix(cellular.width,cellular.height);
          println("setup ca matrix size:"+cellular.width+" X "+cellular.height);
          // assign the rules to the matrix
          // unlike traditional CA's only supporting binary cell states
          // this implementation supports a flexible number of states (cell age)
          // in this demo cell states reach from 0 - 63
          CARule rule=new CARule2D(birthRules,survivalRules,128,true);
          rule.setAutoExpire(true);
          ca.setRule(rule);
          // create a gradient for rendering/mapping the CA
          ColorGradient caColors=new ColorGradient();
          // NamedColors are preset colors, but any TColor can be added
          // see javadocs for list of names:
          // http://toxiclibs.org/docs/colorutils/toxi/color/NamedColor.html
          caColors.addColorAt(0,NamedColor.WHITE);
          caColors.addColorAt(64,NamedColor.YELLOW);
          caColors.addColorAt(128,NamedColor.LIMEGREEN);
          caColors.addColorAt(192,NamedColor.CYAN);
          caColors.addColorAt(240,NamedColor.RED);
          caColors.addColorAt(255,NamedColor.WHITE);
          // the tone map will map cell states/ages to a caColorsient color
          caToneMap=new ToneMap(0,rule.getStateCount()-1,caColors);
          
          //Interference
          intCanvas = new GLGraphicsOffScreen(this, int(width/intScalar),int(height/intScalar), true, 4);
          interference = createGraphics(int(width/intScalar),int(height/intScalar),JAVA2D);
          intGrid = new float[2][interference.width-1][interference.height-1];
          
  //**SETUP GUI
    //CONTROLP5 settings
    controlP5 = new ControlP5(this);
    controlP5.disableShortcuts();
    controlP5.setMoveable(false);
    controlP5.setAutoDraw(false);
    controlP5.setColorActive(255);
    controlP5.setColorForeground(200);
    controlP5.setColorLabel(0);
    controlP5.setColorBackground(150);
  
    //add control window
    controlWindow = controlP5.addControlWindow("CONTROL",1200,0,ww,wh);
    controlWindow.setBackground(color(40));
    controlWindow.hideCoordinates();
    
    //add control objects
    l = controlP5.addMultiList("viz",(ww-120)/2,ww+50 + marginT,120,12);
    l.setWindow(controlWindow);
    l.captionLabel().toUpperCase(true);
    l.captionLabel().set("VISUALIZATIONS");
    for(int i=0;i<vizNames.length;i++) {
      l.add((i+1)+" "+vizNames[i],i).setId(100+i+1);
    }
    selecta = l.subelements();
  
    loadPixels();
    smooth();
}

/*******************************/

/*******    DRAW LOOP    *******/
void draw() {
  
  //background call to clear memory
  //background(255);
  
  //MAIN SWITCH TO DRAW VISUALS @@@
  switch(vizNow){
    default:
      //println("default");f
      background(255);
      break;
      
    case 1:
      //println("1");
      background(255,200,100);
      drawLED();
      drawBUT();
      break;
      
    case 2:
      //println("2");
      background(255);
      drawGS_LED(gsIn);
      //gsIn.loadPixels();
      gsIn.getTexture().getImage(gsProxy);
      gsProxy.loadPixels();
      gs.seedImage(gsProxy.pixels,gsIn.getTexture().width,gsIn.getTexture().height);
      //image(gsIn.getTexture(),0,0,width,height);
      
      gsOut.loadPixels();
      for(int i=0; i<iterations; i++) gs.update(1);
      // read out the V result array
      // and use tone map to render colours
      for(int i=0; i<gs.v.length; i++) {
        gsOut.pixels[i]=gsToneMap.getARGBToneFor(gs.v[i]);
      }
      gsOut.updatePixels();
      image(gsOut.getTexture(),0,0,width,height);
      
      break;
      
      
    case 3:
      background(0);
      translate(width / 2, height / 2, 0);
      if (isWireFrame) {
        noFill();
        stroke(255);
      } 
      else {
        fill(255);
        noStroke();
      }
      drawMesh(g, mesh, !isWireFrame, showNormals);
      break;
    
    case 4:
      background(255);
      drawCA_LED(cellular);
      image(cellular.getTexture(),0,0,width,height);
      //image(cellular.getTexture(),500,500,500,500);
      break;
      
    case 5:
      drawLEDboundary(interference);
      oscillator((interference.width-1)/2,(interference.height-1)/2);
      calcInterference(intCanvas);
      image(intCanvas.getTexture(),0,0,width,height);
      break;
      
   case 6:
     background(0);
     for(int i = 0; i < ledMem.size(); i++){
       if(ledMem.get(i) != null){//error check
         if(ledMem.get(i).state == 1){
          int age = millis() - ledMem.get(i).time;  //age in milliseconds
           
          //println(i+" light on was "+ledMem.get(i).x+","+ledMem.get(i).y);
          fill(255,255-max(0,int(age/10)));
          noStroke();
          translate(0,0,-int(age/10));
          rect((width-(w*(bw+gap))+gap)/2 + ((bw+gap)*ledMem.get(i).x),(height-(h*(bh+gap))+gap)/2 + ((bh+gap)*ledMem.get(i).y),bw,bh);
         }
       }
     }  
  }

  
  //DRAW CONTROLS
  controlP5.draw();
  
   //DEBUGGING
  //calc speed every second
  
  /*
  if(frameCount%frameR == 0) {
    if(frameRate>0) {
      println("% of desired framerate "+frameRate*100/frameR);
    }
  }
  */
  
}

/*******************************/

/*******    FUNCTIONS    *******/
//stop (close things properly)
public void stop(){
  super.stop();
}

//KEYPRESS EVENT
void keyPressed() {

}

//OPEN README
void help(){

}

//OSC EVENT
void oscEvent(OscMessage msgs) {
  //print("### received an osc message.");
  //print(" addrpattern: "+msgs.addrPattern());
  //println(" typetag: "+msgs.typetag());
  
  //HANDLE LEDS
  
  if(msgs.checkAddrPattern(ledAddress)){
    //get coordinates
    x = msgs.get(0).intValue();
    y = msgs.get(1).intValue();
    s = msgs.get(2).intValue();
    //println("LED"+x+y+s);
    
    ledNow = new Spot(x,y,s,millis());
    
    if(x < w && y < h){ //error checking
      leds[x][y] = s; //update current matrix

      //update memory matrix
      ledMem.add(0,ledNow);
      if(ledMem.size()>memory){
        ledMem.remove(memory-1);
      }
      
      if(s == 1){
       int[] ledLast = {x,y};
      }
    }
    
  }
  
  //HANDLE PRESSES
  if(msgs.checkAddrPattern(pressAddress)){
    //get coordinates
    x = msgs.get(0).intValue();
    y = msgs.get(1).intValue();
    s = msgs.get(2).intValue();
    //println("PRESS"+x+y+s);
    
    pressNow = new Spot(x,y,s,millis());
    
    if(x < w && y < h){ //error checking
      press[x][y] = s;
      
      //update memory matrix
      pressMem.add(0,pressNow);
      if(pressMem.size()>memory){
        pressMem.remove(memory-1);
      }
      //println(pressMem.size());
      
      //press becomes pressLast      
      if(s == 1){
       int[] pressLast = {x,y};
      }
    }
    
  }
  
  //SPECIFIC VISUALIZATION OSC ACTIONS @@@
  
    //spherical harmonics
    if(vizNow == 3){
      setMesh();
    }
    
    //gray-scott
    if(pressLast[1] != y){
      gs.setF(0.019+(y*0.001));
    }
      
}

//GUI EVENT #change
void controlEvent(ControlEvent theEvent) {
  if(theEvent.controller().id() > 100){
      int t = theEvent.controller().id() - 100;
      if(t > 0 && t <= vizNames.length) {
        switchViz(t);
        
      }
  }
 
}

//VISUAL INPUT/EFFECTS FUNCTIONS
void switchViz(int t) {      //NEW Visualization
    println("switch to visualization:"+t);
    vizNow = t;                      //new visualization

    //unlight old button
    if(vizThen>0 && vizThen<=vizNames.length){
      selecta.get(vizThen-1).setColorBackground(150);
    }
    
    //light new button
    selecta.get(vizNow-1).setColorBackground(250);
    
    //reset simulations
    if(vizThen == 4){
      ca.reset();
    }
    
    if(vizThen == 5){
      resetInterference();
    }
   
    
    vizThen = vizNow;

}


//Convert PGraphics to image
/*
PImage convertPGraph(PGraphics p){
  
  BufferedImage img = new BufferedImage(w, w, BufferedImage.TYPE_INT_ARGB_PRE  );
  Graphics2D g2d = img.createGraphics();
  g2d.drawImage((java.awt.Image)p.image, 0, 0, w, w, this);
  g2d.finalize();
  g2d.dispose();
  
  PImage rtn=new PImage(img);

  PImage rtn=new PImage(img.getWidth(),img.getHeight(),PConstants.ARGB);
  img.getRGB(0, 0, rtn.width, rtn.height, rtn.pixels, 0, rtn.width);
  rtn.updatePixels();
  
  //return rtn;
}
*/

void drawBK(PGraphics p) {      //RESET GRAPHICS
  p.beginDraw();
  p.background(0,0,0,0);
  p.endDraw();
}

void drawLED(GLGraphicsOffScreen p) {    //LIGHTS to GLGraphicsOffScreen
  p.beginDraw();
  p.background(offscreenBK);
  for(int i = 0; i<w; i++) {
    for(int j = 0; j<h; j++) {
      if(leds[i][j] == 1) {
        p.fill(currentColor);
        p.noStroke();
        p.rect((p.width-(w*(bw+gap))+gap)/2 + ((bw+gap)*i),(p.height-(h*(bh+gap))+gap)/2 + ((bh+gap)*j),bw,bh);
      }
    }
  }
  p.endDraw();
}

void drawLED() {    //LIGHTS to CANVAS
  for(int i = 0; i<w; i++) {
    for(int j = 0; j<h; j++) {
      if(leds[i][j] == 1) {
        fill(currentColor);
        noStroke();
        rect((width-(w*(bw+gap))+gap)/2 + ((bw+gap)*i),(height-(h*(bh+gap))+gap)/2 + ((bh+gap)*j),bw,bh);
        //println("draw LED"+i+j);
      }
    }
  }

}

void drawBUT(PGraphics p) {    //BUTTONS to PGRAPHICS
p.beginDraw();
  for(int i = 0; i<w; i++) {
    for(int j = 0; j<h; j++) {
      if(press[i][j] ==1) {
        p.noStroke();
        p.fill(altColor);
        p.smooth();
        p.pushMatrix(); //draw X's
          p.translate((p.width-(w*(bw+gap))+gap)/2+((bw+gap)*i)+bw/2,(p.height-(h*(bh+gap))+gap)/2+((bh+gap)*j)+bh/2);
          p.rectMode(CENTER);
          p.rotate(PI/4);
          p.rect(0,0,bw*1.2,bh/2);
          p.rect(0,0,bw/2,bh*1.2);
        p.popMatrix();
        p.rectMode(CORNER);
      }
    }
  }
  p.endDraw();
}

void drawBUT() {    //BUTTONS to Canvas

  for(int i = 0; i<w; i++) {
    for(int j = 0; j<h; j++) {
      if(press[i][j] ==1) {
        noStroke();
        fill(altColor);
        smooth();
        pushMatrix(); //draw X's
          translate((width-(w*(bw+gap))+gap)/2+((bw+gap)*i)+bw/2,(height-(h*(bh+gap))+gap)/2+((bh+gap)*j)+bh/2);
          rectMode(CENTER);
          rotate(PI/4);
          rect(0,0,bw*1.2,bh/2);
          rect(0,0,bw/2,bh*1.2);
        popMatrix();
        rectMode(CORNER);
      }
    }
  }

}
          //SPECIFIC VISUALIZATION FUNCTIONS @@@
          //*GRAY-SCOTT FUNCTIONS
          void drawGS_LED(GLGraphicsOffScreen p) {    //LIGHTS to GLGraphicsOffScreen
            int bw_ = int(bw/gsScalar);
            int bh_ = int(bh/gsScalar);
            int gap_ = int(gap/gsScalar);
            p.beginDraw();
            p.background(0,0);
            for(int i = 0; i<w; i++) {
              for(int j = 0; j<h; j++) {
                if(leds[i][j] == 1) {
                  p.fill(255,255);
                  p.noStroke();
                  p.rect((p.width-(w*(bw_+gap_))+gap_)/2 + ((bw_+gap_)*i),(p.height-(h*(bh_+gap_))+gap_)/2 + ((bh_+gap_)*j),bw_,bh_);
                }
              }
            }
            p.endDraw();
            p.loadPixels();
          }    
          
          //**SPHERICAL HARMONICS FUNCTIONS**
          void drawAxes(float l) {
            stroke(255, 0, 0);
            line(0, 0, 0, l, 0, 0);
            stroke(0, 255, 0);
            line(0, 0, 0, 0, l, 0);
            stroke(0, 0, 255);
            line(0, 0, 0, 0, 0, l);
          }
          
          void drawMesh(PGraphics gfx, TriangleMesh mesh, boolean vertexNormals, boolean showNormals) {
            
            gfx.beginShape(PConstants.TRIANGLES);
            AABB bounds=mesh.getBoundingBox();
            Vec3D min=bounds.getMin();
            Vec3D max=bounds.getMax();
            if (vertexNormals) {
              for (Iterator i=mesh.faces.iterator(); i.hasNext();) {
                Face f=(Face)i.next();
                Vec3D n = normalMap.applyTo(f.a.normal);
                gfx.fill(n.x, n.y, n.z);
                gfx.normal(f.a.normal.x, f.a.normal.y, f.a.normal.z);
                gfx.vertex(f.a.x, f.a.y, f.a.z);
                n = normalMap.applyTo(f.b.normal);
                gfx.fill(n.x, n.y, n.z);
                gfx.normal(f.b.normal.x, f.b.normal.y, f.b.normal.z);
                gfx.vertex(f.b.x, f.b.y, f.b.z);
                n = normalMap.applyTo(f.c.normal);
                gfx.fill(n.x, n.y, n.z);
                gfx.normal(f.c.normal.x, f.c.normal.y, f.c.normal.z);
                gfx.vertex(f.c.x, f.c.y, f.c.z);
              }
            } 
            else {
              for (Iterator i=mesh.faces.iterator(); i.hasNext();) {
                Face f=(Face)i.next();
                gfx.normal(f.normal.x, f.normal.y, f.normal.z);
                gfx.vertex(f.a.x, f.a.y, f.a.z);
                gfx.vertex(f.b.x, f.b.y, f.b.z);
                gfx.vertex(f.c.x, f.c.y, f.c.z);
              }
            }
            gfx.endShape();
            if (showNormals) {
              if (vertexNormals) {
                for (Iterator i=mesh.vertices.values().iterator(); i.hasNext();) {
                  Vertex v=(Vertex)i.next();
                  Vec3D w = v.add(v.normal.scale(10));
                  Vec3D n = v.normal.scale(127);
                  gfx.stroke(n.x + 128, n.y + 128, n.z + 128);
                  gfx.line(v.x, v.y, v.z, w.x, w.y, w.z);
                }
              } 
              else {
                for (Iterator i=mesh.faces.iterator(); i.hasNext();) {
                  Face f=(Face)i.next();
                  Vec3D c = f.a.add(f.b).addSelf(f.c).scaleSelf(1f / 3);
                  Vec3D d = c.add(f.normal.scale(20));
                  Vec3D n = f.normal.scale(127);
                  gfx.stroke(n.x + 128, n.y + 128, n.z + 128);
                  gfx.line(c.x, c.y, c.z, d.x, d.y, d.z);
                }
              }
            }
            
          }
          
          void randomizeMesh() {
            float[] m=new float[8];
            for(int i=0; i<8; i++) {
              m[i]=(int)random(9);
            }
            SurfaceMeshBuilder b = new SurfaceMeshBuilder(new SphericalHarmonics(m));
            mesh = (TriangleMesh)b.createMesh(null,80, 60);
          }
          
          void setMesh() {
            
            float[] m=new float[h];
            for(int i=0; i<h; i++) {
              for(int j=0; j<w; j++){
                if(leds[j][i] == 1){
                  m[i] = j;
                }
              }
            }
            //randomize first row...its usually 0 with mlr.
            m[0]=(int)random(9);
            //println("harmonics:"+m[0]+m[1]+m[2]+m[3]+m[4]+m[5]+m[6]+m[7]);
            SurfaceMeshBuilder b = new SurfaceMeshBuilder(new SphericalHarmonics(m));
            mesh = (TriangleMesh)b.createMesh(null,80, 60);
            
          }
          
          
        //**CELLULAR AUTOMATA FUNCTIONS
        void drawCA_LED(GLGraphicsOffScreen p){
          int x_;
          int y_;
          //p.beginDraw();
          //p.background(0,0,0,0);
          
          for(int i = 0; i<w; i++) {
            for(int j = 0; j<h; j++) {
              if(leds[i][j] == 1) {
                x_ = int(map((width-(w*(bw+gap))+gap)/2 + ((bw+gap)*i), 0, width, 0, p.width));
                y_ = int(map((height-(h*(bh+gap))+gap)/2 + ((bh+gap)*j),0,height,0,p.height));
                
                ca.drawBoxAt(x_,y_,int(bw/(10*caScalar)),1);
                //ca.drawBoxAt(x_,y_,5,1);
        
              }
            }
          }
          ca.update();
          int[] m=ca.getMatrix();
          //println("pixel length = "+p.pixels.length+"vs. m length:"+m.length);
          
          p.loadPixels();
          for(int i=0; i<m.length; i++) {
            p.pixels[i]=caToneMap.getARGBToneFor(m[i]);
          }
          p.updatePixels();

        }

          
        //**INTERFERENCE FUNCTIONS
        //to offscreen buffer?
        void drawLEDboundary(PGraphics p){
          p.beginDraw();
          p.background(0);
          p.noFill();
          p.strokeWeight(4);
          p.stroke(255);
          for(int i = 0; i<w; i++) {
            for(int j = 0; j<h; j++) {
              if(leds[i][j] == 1) {
              p.rect((p.width-(w*(bw+gap))+gap)/2 + ((bw+gap)*i),(p.height-(h*(bh+gap))+gap)/2 + ((bh+gap)*j),bw,bh);
              }
            }
          }
          p.endDraw();
        }
        
        //to canvas
        void drawLEDboundary(){
          background(0);
          noFill();
          strokeWeight(4);
          stroke(255);
          for(int i = 0; i<w; i++) {
            for(int j = 0; j<h; j++) {
              if(leds[i][j] == 1) {
              rect((width-(w*(bw+gap))+gap)/2 + ((bw+gap)*i),(height-(h*(bh+gap))+gap)/2 + ((bh+gap)*j),bw,bh);
              }
            }
          }
        }
        
        void oscillator(int cx,int cy) {
          intGrid[page][cx][cy] = cos(phase);
          phase += PI/32;
        }
        
        void calcInterference(GLGraphicsOffScreen p){
          p.loadPixels();
          for(int i=1; i<p.width-2; i++) {
            for(int j=1; j<p.height-2; j++) {
            int wa = interference.pixels[i+p.width*j];
            float dx =  (wa == 0xffffffff) ? 0: intGrid[page][i-1][j] + intGrid[page][i+1][j];  
            float dy =  (wa == 0xffffffff) ? 0: intGrid[page][i][j-1] + intGrid[page][i][j+1];  
            float value = (((dx+dy )/2)) - intGrid[page^1][i][j];
            intGrid[page^1][i][j] = value;
            int y = value < 0 ? (int)(-value*512): 0;
            int b = value > 0 ? (int)(value*512): 0;
            y = y > 255 ? 255 : y;
            b = b > 255  ? 255 : b;
            p.pixels[i+j*p.width] = 0xFF000000 | y << 16 | y << 8 | b;	
            }
          }
          page ^= 1;
          p.updatePixels();
        }
        
        void resetInterference(){
           for(int i=0; i<interference.width-1; i++) {
            for(int j=0; j<interference.height-1; j++) {
              intGrid[page][i][j] = 0;
              intGrid[page^1][i][j] = 0;
            }
          }
        }
          


