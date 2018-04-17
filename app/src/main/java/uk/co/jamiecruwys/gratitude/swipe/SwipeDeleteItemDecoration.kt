package uk.co.jamiecruwys.gratitude.swipe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class SwipeDeleteItemDecoration(private val itemColor: ColorDrawable, private val revealColor: ColorDrawable) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (parent.itemAnimator.isRunning) {
            var lastViewComingDown: View? = null
            var firstViewComingUp: View? = null
            val left = 0
            val right = parent.width
            var top = 0
            var bottom = 0

            val childCount = parent.layoutManager.childCount
            for (i in 0 until childCount) {
                val child = parent.layoutManager.getChildAt(i)
                if (child.translationY < 0) {
                    // view is coming down
                    lastViewComingDown = child
                } else if (child.translationY > 0) {
                    // view is coming up
                    if (firstViewComingUp == null) {
                        firstViewComingUp = child
                    }
                }
            }

            if (lastViewComingDown != null && firstViewComingUp != null) {
                // views are coming down AND going up to fill the void
                top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                revealColor.setBounds(left, top, right, bottom)
                revealColor.draw(c)
            } else if (lastViewComingDown != null) {
                // views are going down to fill the void
                top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                bottom = lastViewComingDown.bottom
                itemColor.setBounds(left, top, right, bottom)
                itemColor.draw(c)
            } else if (firstViewComingUp != null) {
                // views are coming up to fill the void
                top = firstViewComingUp.top
                bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                revealColor.setBounds(left, top, right, bottom)
                revealColor.draw(c)
            }
        }
        super.onDraw(c, parent, state)
    }

}
