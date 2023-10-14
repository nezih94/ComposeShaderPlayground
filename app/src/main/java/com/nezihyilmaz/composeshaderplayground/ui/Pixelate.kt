package com.nezihyilmaz.composeshaderplayground.ui

import android.graphics.RenderEffect
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.nezihyilmaz.composeshaderplayground.R
import com.nezihyilmaz.composeshaderplayground.ui.shaders.HighlightEffect
import com.nezihyilmaz.composeshaderplayground.ui.shaders.PixelateEffect

@Composable
fun Pixelate() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val pixelate = remember { mutableStateOf(false) }
        val resolution = remember { mutableStateOf(Size(0f, 0f)) }
        val blockSize = 18.dp
        val horizontalProgress = animateFloatAsState(
            targetValue = if (pixelate.value) 1f else 0f,
            animationSpec = tween(durationMillis = 3000)
        )
        Box() {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .graphicsLayer {
                        PixelateEffect.setFloatUniform(
                            "resolution",
                            resolution.value.width,
                            resolution.value.height
                        )
                        PixelateEffect.setFloatUniform("blockSize", blockSize.toPx())
                        PixelateEffect.setFloatUniform(
                            "horizontalProgress",
                            horizontalProgress.value
                        )
                        renderEffect = RenderEffect
                            .createRuntimeShaderEffect(
                                PixelateEffect,
                                "inputTexture"
                            )
                            .asComposeRenderEffect()
                        clip = true
                    }
                    .clickable { pixelate.value = !pixelate.value }
                    .align(Alignment.Center)
                    .onSizeChanged {
                        resolution.value = it.toSize()
                    },
                painter = painterResource(R.drawable.sample_image_2),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}