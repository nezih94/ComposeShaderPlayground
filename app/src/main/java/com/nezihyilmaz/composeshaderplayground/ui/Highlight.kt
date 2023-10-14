package com.nezihyilmaz.composeshaderplayground.ui

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.nezihyilmaz.composeshaderplayground.ui.shaders.HighlightEffect
import com.nezihyilmaz.composeshaderplayground.ui.theme.BrightPink
import com.nezihyilmaz.composeshaderplayground.ui.theme.Finn
import com.nezihyilmaz.composeshaderplayground.ui.theme.Melon

import com.nezihyilmaz.composeshaderplayground.ui.theme.Pink80
import com.nezihyilmaz.composeshaderplayground.ui.theme.PurpleLight


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Highlight() {
    val rectPos = remember { mutableStateOf(Offset(0f, 0f)) }
    val rectSize = remember { mutableStateOf(Size(0f, 0f)) }
    val highlightOn = remember { mutableStateOf(false) }
    val intensity = animateFloatAsState(
        targetValue = if (highlightOn.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .background(Color.White)
            .graphicsLayer {
                HighlightEffect.setFloatUniform(
                    "rectPosition",
                    rectPos.value.x,
                    rectPos.value.y
                )
                HighlightEffect.setFloatUniform(
                    "rectSize",
                    rectSize.value.height,
                    rectSize.value.width
                )
                HighlightEffect.setFloatUniform("grayscaleIntensity", intensity.value)
                renderEffect = RenderEffect
                    .createRuntimeShaderEffect(
                        HighlightEffect,
                        "inputTexture"
                    )
                    .asComposeRenderEffect()
            }) {
            LazyColumn(modifier = Modifier.padding(vertical = 56.dp)) {
                for (i in 0..8) {
                    item { DummyRow() }
                }
            }
            DummyBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
            DummyToolbar(modifier = Modifier) {
                highlightOn.value = !highlightOn.value
            }

            Box(
                modifier = Modifier
                    .padding(bottom = 80.dp, end = 24.dp)
                    .height(60.dp)
                    .width(60.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(8.dp))
                    .background(Finn, shape = RoundedCornerShape(8.dp))
                    .align(Alignment.BottomEnd)
                    .onGloballyPositioned {
                        rectPos.value = it.positionInRoot()
                        rectSize.value = it.size.toSize()
                    }
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomEnd),
                visible = highlightOn.value,
                enter = fadeIn(tween(delayMillis = 700, durationMillis = 400)) + slideInHorizontally(
                    animationSpec = tween(delayMillis = 700, durationMillis = 400), initialOffsetX = {
                        it / 3
                    }
                ),
                exit = fadeOut(tween(delayMillis = 0, durationMillis = 500)) + shrinkOut(
                    tween(delayMillis = 0, durationMillis = 500), clip = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 150.dp, end = 24.dp)
                        .shadow(6.dp, shape = RoundedCornerShape(8.dp))
                        .background(PurpleLight, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 12.dp)
                            .align(Alignment.Center),
                        text = "Create a new post!",
                        color = Finn,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

    }
}

@Composable
fun DummyRow() {
    Box(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(BrightPink, shape = RoundedCornerShape(8.dp))
        )

        Box(
            modifier = Modifier
                .padding(start = 86.dp)
                .height(20.dp)
                .width(150.dp)
                .background(Melon, shape = RoundedCornerShape(8.dp))
        )

        Box(
            modifier = Modifier
                .padding(start = 86.dp, top = 36.dp)
                .height(100.dp)
                .fillMaxWidth()
                .background(Pink80, shape = RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun DummyBottomBar(modifier: Modifier) {

    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..3) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        if (i == 0) Melon else Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
fun DummyToolbar(modifier: Modifier, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .width(250.dp)
                .background(Finn, shape = RoundedCornerShape(8.dp))
                .clickable { onClick() }
        )
    }
}