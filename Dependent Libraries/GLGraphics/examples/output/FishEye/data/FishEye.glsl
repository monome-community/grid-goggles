// Ported into GLSL from the "Angular Fisheye à la Bourke" sketch by
// Jonathan Cremieux, as shown in the OpenProcessing website:
// http://openprocessing.org/visuals/?visualID=12140

uniform sampler2D src_tex_unit0;
uniform float aperture;

void main(void) {    
  // Getting input texture coordinates:
  vec2 tcoord = gl_TexCoord[0].st;
  vec2 center = vec2(0.5, 0.5); 
  vec2 tcvec = tcoord - center;
  float halfAperture = 0.5 * aperture;    
  float r = length(tcvec);

  // angle phi to x axis:
  float phi = atan(tcvec.y, tcvec.x);

  // r is mapped onto theta and phi is used directly as the polar 
  // coordinates of the direction vector from the camera into the scene:
  float theta = halfAperture * r;

  // Calculate new texture coordinates:
  float x = 0.5 * (sin(theta) * cos(phi) + 1.0);
  float y = 0.5 * (sin(theta) * sin(phi) + 1.0);
  
  gl_FragColor = texture2D(src_tex_unit0, vec2(x, y));
}