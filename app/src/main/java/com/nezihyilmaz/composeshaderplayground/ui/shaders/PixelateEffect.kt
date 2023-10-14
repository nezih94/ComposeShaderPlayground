package com.nezihyilmaz.composeshaderplayground.ui.shaders

import android.graphics.RuntimeShader

val PixelateEffect = RuntimeShader("""
    uniform shader inputTexture; // Input texture
    uniform float blockSize;
    uniform float horizontalProgress;
    uniform vec2 resolution;
    
    vec4 main(vec2 fragCoord){
    
    float horizontalFraction = fragCoord.x / resolution.x;
    
    if(horizontalFraction <  horizontalProgress){
       return inputTexture.eval(fragCoord);
    }

    // Calculate the size of a pixel in texture coordinates
    vec2 texelSize = 1.0 / resolution;

    // Calculate the texture coordinates of the center of the current pixel block
    vec2 texCoord = floor(fragCoord.xy / blockSize) * blockSize;

    // Sample the input texture at the calculated texture coordinates
    vec4 color = inputTexture.eval(texCoord + texelSize * 0.5);

    return color;
}
"""
)