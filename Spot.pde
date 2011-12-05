/*A convenient abstraction for saving x,y,state,timestamp of /press and /led events

*/

class Spot{
  
  int x,y,state,time;
  
  Spot(){
  }
  
  Spot(int x_, int y_, int state_, int time_){
    x = x_;
    y = y_;
    state = state_;
    time = time_;
    
  }
  
}
  
  
