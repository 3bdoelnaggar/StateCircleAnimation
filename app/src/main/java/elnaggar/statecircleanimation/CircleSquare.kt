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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.cos
import kotlin.math.sin

import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt


private const val PI = Math.PI.toFloat()
private const val HALF_PI = PI / 2


@Composable
fun CircleSquare(modifier: Modifier = Modifier, mode: Int = 0) {
    val animateProgress = animatedFloat(0f)

    onActive {
        animateProgress.animateTo(targetValue = 1f, anim = repeatable(iterations = AnimationConstants.Infinite, animation = tween(durationMillis = 2500, easing = LinearEasing)))
    }

    val t = animateProgress.value
    Canvas(modifier = modifier) {
        translate(size.width / 2, size.height / 2) {
            when (mode) {
                1 -> mode1(t)
                2->mode2(t)
                3->mode3(t)
                else -> mode0(t)
            }
        }
    }
}

fun DrawScope.mode1(t: Float) {
    if (t >= 0.5f) {
        val tt = map(t, 0f, 0.5f, 0f, 1f)
        val rotation = 90f * ease(tt, 3f)
        rotate(rotation, 0f, 0f) {
            drawCircles(270f, -360f * ease(tt, 3f))
        }
    } else {

        val tt = map(t, 0.5f, 1f, 0f, 1f)
        val rotation = -90f * ease(tt, 3f)

        rotate(rotation, 0f, 0f) {
            drawCircles(360f, 0f)
        }
        rotate(-rotation, 0f, 0f) {
            val rectSize = 2 * size.circleRadius()
            drawRect(
                    color = Color.White,
                    topLeft = Offset(-rectSize / 2f, -rectSize / 2f),
                    size = Size(rectSize, rectSize),
            )
        }

    }

}

fun DrawScope.mode0(t: Float) {
    if (t <= 0.5f) {
        val tt = map(t, 0f, 0.5f, 0f, 1f)
        val rotation = 90f * ease(tt, 3f)
        rotate(rotation, 0f, 0f) {
            drawCircles(270f, -360f * ease(tt, 3f))
        }
    } else {

        val tt = map(t, 0.5f, 1f, 0f, 1f)
        val rotation = -90f * ease(tt, 3f)

        rotate(rotation, 0f, 0f) {
            drawCircles(360f, 0f)
        }
        rotate(-rotation, 0f, 0f) {
            val rectSize = 2 * size.circleRadius()
            drawRect(
                    color = Color.White,
                    topLeft = Offset(-rectSize / 2f, -rectSize / 2f),
                    size = Size(rectSize, rectSize),
            )
        }

    }
}
fun DrawScope.mode2(t: Float) {
    if (t <= 0.5f) {
        val tt = map(t, 0f, 0.5f, 0f, 1f)
        val rotation = -90f * ease(tt, 3f)
        rotate(rotation, 0f, 0f) {
            drawCircles(270f, -360f * ease(tt, 3f))
        }
    } else {

        val tt = map(t, 0.5f, 1f, 0f, 1f)
        val rotation = 90f * ease(tt, 3f)

        rotate(rotation, 0f, 0f) {
            drawCircles(360f, 0f)
        }
        rotate(-rotation, 0f, 0f) {
            val rectSize = 2 * size.circleRadius()
            drawRect(
                    color = Color.White,
                    topLeft = Offset(-rectSize / 2f, -rectSize / 2f),
                    size = Size(rectSize, rectSize),
            )
        }

    }
}
fun DrawScope.mode3(t: Float) {
    if (t <= 0.5f) {
        val tt = map(t, 0f, 0.5f, 0f, 1f)
        val rotation = -90f * ease(tt, 3f)
        rotate(rotation, 0f, 0f) {
            drawCircles(270f, -360f * ease(tt, 3f))
        }
    } else {

        val tt = map(t, 0.5f, 1f, 0f, 1f)
        val rotation = 90f * ease(tt, 3f)

        rotate(-rotation, 0f, 0f) {
            drawCircles(360f, 0f)
        }
        rotate(rotation, 0f, 0f) {
            val rectSize = 2 * size.circleRadius()
            drawRect(
                    color = Color.White,
                    topLeft = Offset(-rectSize / 2f, -rectSize / 2f),
                    size = Size(rectSize, rectSize),
            )
        }

    }
}

private fun Size.circleRadius(): Float {
    return min(width, height) / 4f / sqrt(2f)
}

private fun ease(p: Float, g: Float): Float {
    return if (p < 0.5f) {
        0.5f * pow(2 * p, g)
    } else {
        1 - 0.5f * pow(2 * (1 - p), g)
    }
}

private fun DrawScope.drawCircles(sweepAngle: Float, rotation: Float) {
    val circleRadius = size.circleRadius()

    for (i in 0 until 4) {
        val r = circleRadius * sqrt(2f)
        //Theta (uppercase Θ / lowercase θ), is a letter in the Greek alphabet.
        // It represents the "th" sound (like in "think") in Ancient and Modern Greek.
        // In the system of Greek numerals, it has a value of 9.
        // The Cyrillic letter Ѳ came from Theta.
        // In mathematics, theta is used to represent an angle.
        val theta = (HALF_PI + PI * i) / 2f
        val tx = r * cos(theta)
        val ty = r * sin(theta)

        withTransform({
            translate(-tx, -ty)
            rotate(rotation, 0f, 0f)
        }, {
            val rectSize = 2 * (circleRadius - circleRadius / 16f)
            drawArc(
                    color = Color.Cyan,
                    startAngle = 90f * (i + 1),
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(-rectSize / 2f, -rectSize / 2f),
                    size = Size(rectSize, rectSize)
            )
        })
    }
}

private fun pow(n: Float, e: Float) = n.pow(e)


private fun map(value: Float, start1: Float, stop1: Float, start2: Float, stop2: Float): Float {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))
}