package com.nezihyilmaz.composeshaderplayground.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.nezihyilmaz.composeshaderplayground.ui.shaders.GradientColor
import com.nezihyilmaz.composeshaderplayground.ui.shaders.SolidColor

@Composable
fun Coloring() {

    Column(
        verticalArrangement = Arrangement.spacedBy(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .padding(vertical = 70.dp)
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    brush = ShaderBrush(SolidColor.apply {
                        setFloatUniform(
                            "color",
                            1f,
                            0f,
                            0f,
                            1f
                        )
                    }),
                    shape = RoundedCornerShape(30.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    brush = ShaderBrush(GradientColor.apply {
                        setFloatUniform(
                            "startColor",
                            1f, 0f, 0f, 1f
                        )
                        setFloatUniform(
                            "endColor", 0f, 0f, 1f, 1f
                        )
                        setFloatUniform(
                            "width",
                            with(LocalDensity.current){
                                200.dp.toPx()
                            }
                        )
                    }),
                    shape = RoundedCornerShape(30.dp)
                )
        )
    }
}
