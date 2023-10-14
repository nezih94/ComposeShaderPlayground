package com.nezihyilmaz.composeshaderplayground.ui.shaders

import android.graphics.RuntimeShader

val HighlightEffect = RuntimeShader("""
    uniform shader inputTexture; // Input texture
    uniform vec2 rectPosition; // Position of the top-left corner of the excluded rectangle
    uniform vec2 rectSize; // Size of excluded rectangle
    uniform float grayscaleIntensity;
    
    vec4 main(vec2 fragCoord){
    // Get the color from the original input texture
    vec4 originalColor = inputTexture.eval(fragCoord);

    // Check if the current fragment is inside the defined rectangle
    if (fragCoord.x >= rectPosition.x &&
        fragCoord.x <= rectPosition.x + rectSize.x &&
        fragCoord.y >= rectPosition.y &&
        fragCoord.y <= rectPosition.y + rectSize.y)
    {
        // If inside the rectangle, use the original color
        return originalColor;
    }
    else
    {
        // If outside the rectangle, convert to grayscale
        float gray = dot(originalColor.rgb, vec3(0.299, 0.587, 0.114));
        vec3 grayscaleColor = mix(originalColor.rgb, vec3(gray), grayscaleIntensity);
        return vec4(vec3(grayscaleColor), originalColor.a);
    }
}
"""
)