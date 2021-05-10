package com.veldan.customviewgroup_with_children_customview

import android.content.Context
import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import com.veldan.customviewgroup_with_children_customview.CustomViewGroup.Companion.DIRECTION_BOTTOM
import com.veldan.customviewgroup_with_children_customview.CustomViewGroup.Companion.DIRECTION_LEFT
import com.veldan.customviewgroup_with_children_customview.CustomViewGroup.Companion.DIRECTION_RIGHT
import com.veldan.customviewgroup_with_children_customview.CustomViewGroup.Companion.DIRECTION_TOP

fun Int.toPx(context: Context) = this * context.resources.displayMetrics.density.toInt()
fun Int.toDp(context: Context) = this / context.resources.displayMetrics.density.toInt()

private var previousId = 0
fun ConstraintSet.connectTowards(
    direction: Int,
    whoConnectId: Int,
    firstConnectId: Int,
    margin: Int = 10
) {

    val whomConnectId = if (previousId == 0) firstConnectId else previousId

    val id_1 = whoConnectId
    val id_2 = whomConnectId

    fun connectStartEnd() {
        connect(id_1, START, firstConnectId, START, margin)
        connect(id_1, END, firstConnectId, END, margin)
    }

    fun connectTopBottom() {
        connect(id_1, TOP, firstConnectId, TOP, margin)
        connect(id_1, BOTTOM, firstConnectId, BOTTOM, margin)
    }

    when (direction) {
        DIRECTION_TOP -> {
            connect(id_1, BOTTOM, id_2, TOP, margin)
            connectStartEnd()
        }
        DIRECTION_LEFT -> {
            connect(id_1, END, id_2, START, margin)
            connectTopBottom()
        }
        DIRECTION_RIGHT -> {
            connect(id_1, START, id_2, END, margin)
            connectTopBottom()
        }
        DIRECTION_BOTTOM -> {
            connect(id_1, TOP, id_2, BOTTOM, margin)
            connectStartEnd()
        }
    }

    previousId = whoConnectId
}