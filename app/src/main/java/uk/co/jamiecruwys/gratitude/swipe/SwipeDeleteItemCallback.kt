package uk.co.jamiecruwys.gratitude.swipe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import uk.co.jamiecruwys.gratitude.R

class SwipeDeleteItemCallback(context: Context, swipeDirs: Int, private val swiped: (position: Int) -> Unit) : ItemTouchHelper.SimpleCallback(0, swipeDirs) {
    private val background: Drawable
    private val deleteIcon: Drawable?
    private val deleteIconMargin: Int

    init {
        background = ColorDrawable(Color.RED)
        deleteIcon = context.getDrawable(R.drawable.ic_delete)
        val density = context.resources.displayMetrics.density
        deleteIconMargin = (16 * density + 0.5f).toInt()
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swiped(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        if (viewHolder.adapterPosition == -1) {
            return
        }
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)
        val itemHeight = itemView.bottom - itemView.top
        val intrinsicWidth = deleteIcon!!.intrinsicWidth
        val intrinsicHeight = deleteIcon.intrinsicWidth
        val xMarkLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val xMarkRight = itemView.right - deleteIconMargin
        val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val xMarkBottom = xMarkTop + intrinsicHeight
        deleteIcon.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)
        deleteIcon.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
