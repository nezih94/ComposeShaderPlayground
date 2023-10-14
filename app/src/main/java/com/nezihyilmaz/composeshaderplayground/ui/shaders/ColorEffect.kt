package com.nezihyilmaz.composeshaderplayground.ui.shaders

import android.graphics.RuntimeShader

val SolidColor = RuntimeShader("""
    
    uniform vec4 color;
    
    vec4 main(vec2 fragCoord){
       return color;
}
"""
)

val GradientColor = RuntimeShader("""
    
uniform vec4 startColor;
uniform vec4 endColor;
uniform float width;

vec4 main(vec2 fragCoord) {
  // Normalized position of current pixel.
  float horizontalFraction = fragCoord.x / width;

  // Linearly interpolate between the two colors based on the normalized horizontal position.
  vec4 gradientColor = mix(startColor, endColor, horizontalFraction);

  // Set the output pixel color.
  return vec4(gradientColor);
}
""")