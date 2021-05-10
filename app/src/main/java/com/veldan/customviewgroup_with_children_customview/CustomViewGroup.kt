package com.veldan.customviewgroup_with_children_customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.view.children
import androidx.core.view.marginEnd
import androidx.core.view.updateLayoutParams
import kotlin.math.min

class CustomViewGroup(
    context: Context,
    attrs: AttributeSet
) : MotionLayout(context, attrs) {

    private val TAG = this::class.simpleName

    companion object {
        const val DIRECTION_TOP = 0
        const val DIRECTION_RIGHT = 1
        const val DIRECTION_BOTTOM = 2
        const val DIRECTION_LEFT = 3

        const val TRANSITION_ID = R.id.transition_id
        const val START_SET_ID = R.id.start_set_id
        const val END_SET_ID = R.id.end_set_id

        private const val DEFAULT_ID = R.id.default_custom_view
        private const val DEFAULT_SIZE = 100
    }

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup)
    private val direction = typedArray.getInt(R.styleable.CustomViewGroup_direction, DIRECTION_TOP)

    private lateinit var start: ConstraintSet
    private lateinit var end: ConstraintSet

    private var size = 0

    init {
        addDefaultView()

        SceneBuilder(
            layoutLayout = this,
            transitionId = TRANSITION_ID,
            startSetId = START_SET_ID,
            endSetId = END_SET_ID
        ).buildScene().also { scene ->
            setScene(scene)
        }
    }

    private fun addDefaultView() {
        LayoutInflater.from(context).inflate(R.layout.default_custom_view, null).apply {
            addView(this)

            var switch = true

            setOnClickListener {
                getConstraintSet(START_SET_ID).clone(start)

                children.forEachIndexed { index, view ->
                    if (index != 0) {
                        endSetChild(view)?.let { end = it.apply { applyTo(this@CustomViewGroup) } }
                    }
                }

                getConstraintSet(END_SET_ID).clone(end)

                setTransition(TRANSITION_ID)
                setTransitionDuration(3000)

                // START ограничения сделаны неправильно, поэтому на старт и не возвращаеться.

                if (switch) transitionToEnd() else transitionToStart()
                switch = !switch
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (size == 0) {
            when (AT_MOST) {
                getMode(widthMeasureSpec) and getMode(heightMeasureSpec) -> {
                    size = DEFAULT_SIZE.toPx(context)
                }
                else -> {
                    updateLayoutParams {
                        height = WRAP_CONTENT
                        width = WRAP_CONTENT
                    }
                    size = min(getSize(widthMeasureSpec), getSize(heightMeasureSpec))
                }
            }
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {

            start = setDefaultView(size).apply { applyTo(this@CustomViewGroup) }

            children.forEachIndexed { index, view ->
                if (index != 0) {
                    startSetChild(view)?.let {
                        start = it.apply { applyTo(this@CustomViewGroup) }
                    }
                }
            }

            getConstraintSet(START_SET_ID).clone(start)
        }
    }

    private fun setDefaultView(size: Int): ConstraintSet {
        findViewById<ImageButton>(DEFAULT_ID).also { view ->

            view.setImageDrawable(typedArray.getDrawable(R.styleable.CustomViewGroup_image))
            view.elevation = 1f

            return ConstraintSet().apply {
                clone(this@CustomViewGroup)
                constrainWidth(view.id, size)
                constrainHeight(view.id, size)
                setDimensionRatio(view.id, "1:1")
                connect(view.id, START, PARENT_ID, START)
                connect(view.id, TOP, PARENT_ID, TOP)
                connect(view.id, END, PARENT_ID, END)
                connect(view.id, BOTTOM, PARENT_ID, BOTTOM)
            }

        }
    }

    private fun startSetChild(view: View): ConstraintSet? {
        return if (view::class.java.simpleName == CustomView::class.java.simpleName) {

            view.elevation = 0f

            ConstraintSet().apply {
                clone(this@CustomViewGroup)
                constrainWidth(view.id, 0)
                constrainHeight(view.id, 0)
                setDimensionRatio(view.id, "1:1")
                connect(view.id, START, PARENT_ID, START)
                connect(view.id, TOP, PARENT_ID, TOP)
                connect(view.id, END, PARENT_ID, END)
                connect(view.id, BOTTOM, PARENT_ID, BOTTOM)
            }

        } else null
    }

    private fun endSetChild(view: View): ConstraintSet? {
        return if (view::class.java.simpleName == CustomView::class.java.simpleName) {

            ConstraintSet().apply {
                clone(this@CustomViewGroup)
                clear(view.id)
                constrainWidth(view.id, 0)
                constrainHeight(view.id, 0)
                setDimensionRatio(view.id, "1:1")
                connectTowards(direction, view.id, DEFAULT_ID)
            }

        } else null
    }
}