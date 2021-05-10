package com.veldan.customviewgroup_with_children_customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton

class CustomView(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageButton(context, attrs) {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setBackground(background: Drawable?) {
        super.setBackground(context.getDrawable(R.drawable.shape))
    }

}