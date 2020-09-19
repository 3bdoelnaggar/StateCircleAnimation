package elnaggar.statecircleanimation

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun StateCircle(modifer: Modifier = Modifier, stateNum: Int) {
    val animateProgress = animatedFloat(initVal = 0f)
    onActive {
        animateProgress.animateTo(01f, anim = repeatable(iterations = AnimationConstants.Infinite, animation = tween(durationMillis = 2500, easing = LinearEasing)))

    }
    val t = animateProgress.value
    val sweepAngle = t * 360f
    Canvas(modifier = modifer) {
        var parts = (sweepAngle / 72).toInt()
        val remains = sweepAngle % 72
        if (remains != 0f) {
            parts += 1
        }
        for (i in 0 until parts) {
            if (i == parts - 1) {
                if (remains == 0f) {
                    drawPart(i + 1, 72f)
                } else {
                    drawPart(i + 1, remains)
                }
            } else {
                drawPart(i + 1, 72f)
            }
        }
        drawCircle(color = Color.Black,radius = 100f,center= Offset((size.width/2f),0f))
        drawCircle(color = Color.Black,radius = 100f,center= Offset((size.width/2f),size.width))
        drawCircle(color = Color.Black,radius = 100f,center= Offset(0f,size.width/2))
        drawCircle(color = Color.Black,radius = 100f,center= Offset(size.width,size.width/2))

    }
}

fun DrawScope.drawPart(part: Int, sweepAngle: Float) {
    val oval = Rect(0f, 0f, size.width, size.width)
    val path = Path()
    path.arcTo(oval, getStartAngelByPart(part).toFloat(), sweepAngle, true)
    drawPath(path = path, brush = SolidColor(value = if (part % 2 == 0) Color.Red else Color.Cyan), style = Stroke(width = 8f))
}


// return where that part start
fun getStartAngelByPart(part: Int): Int {
    var returnedValue = 198 + part * 72
    if (returnedValue > 360) {
        returnedValue %= 360
    }
    return returnedValue
}
