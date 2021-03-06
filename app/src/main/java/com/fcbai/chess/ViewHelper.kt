package com.fcbai.chess

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

object ViewHelper {
    fun getAlphaAnimationForBlink(): AlphaAnimation {
        val animation = AlphaAnimation(1f, 0f)
        animation.duration = 500
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        return animation
    }

    fun combineImage(original: Bitmap, overlay: Bitmap, isRotate: Boolean = false): Bitmap {
        val result = Bitmap.createBitmap(
            original.width, original
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(result)
        val paint = Paint()
        paint.isAntiAlias = true
        canvas.drawBitmap(
            original,
            ((canvas.width - original.width)/2).toFloat(),
            (canvas.height - original.height)/2.toFloat(),
            paint)

        val m = Matrix()
        m.postScale(1F, -1F)

        canvas.drawBitmap(
            if(isRotate)  convert(overlay) else overlay,
            ((canvas.width - overlay.width)/2).toFloat(),
            (canvas.height - overlay.height)/2.toFloat(),
            paint)
        return result
    }


    private fun convert(a: Bitmap): Bitmap {
        val w = a.width
        val h = a.height
        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)// 创建一个新的和SRC长度宽度一样的位图
        val cv = Canvas(newb)
        val m = Matrix()
        m.postScale(1F, -1F)
        val new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true)
        cv.drawBitmap(new2, Rect(0, 0, new2.width, new2.height), Rect(0, 0, w, h), null)
        return newb
    }
}



class HelpDialog: Dialog, View.OnClickListener {

    constructor(context: Context) : super(context)

    init {
        setCancelable(true)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.help_layout)
        val cancelButton = findViewById<ImageView>(R.id.cancel)
        cancelButton.setOnTouchListener{v, event ->
            if (v is ImageButton) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.setScaleX(0.95F)
                        v.setScaleY(0.95F)
                    }
                    MotionEvent.ACTION_UP -> {
                        v.setScaleX(1.0F)
                        v.setScaleY(1.0F)
                    }

                }
            }
            v?.onTouchEvent(event) ?: false
        }
        cancelButton.setOnClickListener(this@HelpDialog)
    }

    override fun onClick(v: View?) {
        this@HelpDialog.dismiss()
    }

}

class LoadDialog: Dialog, View.OnClickListener {

    constructor(context: Context) : super(context)

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_layout)
    }

    override fun onClick(v: View?) {
        this@LoadDialog.dismiss()
    }

}

