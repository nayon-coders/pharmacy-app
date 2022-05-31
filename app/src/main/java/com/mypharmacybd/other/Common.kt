package com.mypharmacybd.other

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.mypharmacybd.R
import org.w3c.dom.Text

object Common {

    fun getLoadingDrawable(
        context: Context,
        strokeWidth: Float,
        centerRadius: Float
    ): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.backgroundColor = ContextCompat.getColor(context, R.color.white)
        circularProgressDrawable.strokeWidth = strokeWidth
        circularProgressDrawable.centerRadius = centerRadius
        circularProgressDrawable.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.color_primary), PorterDuff.Mode.SRC_IN
        )
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    fun dialogOK(context: Context, @StringRes stringRes:Int){
        AlertDialog.Builder(context)
            .setTitle("Alert")
            .setMessage(context.getString(stringRes))
            .setNegativeButton("OK", null)
            .show()
    }

    fun setSpinnerError(message:String, spinner:Spinner){
        val textView = spinner.selectedView as TextView
        textView.setTextColor(Color.RED)
        textView.text = message
    }

    fun dialogOK(context: Context, message: String){
        AlertDialog.Builder(context)
            .setTitle("Alert")
            .setMessage(message)
            .setNegativeButton("OK", null)
            .show()
    }
}