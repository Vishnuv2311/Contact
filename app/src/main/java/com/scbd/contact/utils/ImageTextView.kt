package com.scbd.contact.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.scbd.contact.R


class ImageTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    private val textView: TextView
    private val imageView: ImageView

    init {

        val array = context.obtainStyledAttributes(attributeSet, R.styleable.ImageTextView, 0, 0)
        val profileImage = array.getDrawable(R.styleable.ImageTextView_profile_image)
        val profileText = array.getText(R.styleable.ImageTextView_first_letter) as String
        array.recycle()

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.cutsom_view, this, true)

        imageView = view.findViewById(R.id.custom_image)
        textView = view.findViewById(R.id.custom_text)

    }

    fun setName(name: String) {
        if (name != "") textView.text = name[0].toString() else "?"
        imageView.setBackgroundColor((Math.random() * 16777215).toInt() or (0xFF shl 24))
    }

    fun setImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }
}
