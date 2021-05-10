package com.veldan.customviewgroup_with_children_customview

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintSet

class SceneBuilder(
    private val layoutLayout: MotionLayout,
    private val transitionId: Int,
    private val startSetId: Int,
    private val endSetId: Int
) {

    fun buildScene(): MotionScene {
        return MotionScene(layoutLayout).also { scene ->
            val msTransition = createTransition(scene)

            scene.apply {
                addTransition(msTransition)
                setTransition(msTransition)
            }
        }
    }

    private fun createTransition(scene: MotionScene): MotionScene.Transition {
        val start = ConstraintSet()
        val end = ConstraintSet()

        return TransitionBuilder.buildTransition(
            scene,
            transitionId,
            startSetId, start,
            endSetId, end,
        )
    }

}