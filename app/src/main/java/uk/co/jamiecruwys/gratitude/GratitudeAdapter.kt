package uk.co.jamiecruwys.gratitude

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_gratitude.view.*
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry

class GratitudeAdapter(diffCallback: DiffUtil.ItemCallback<GratitudeEntry>): ListAdapter<GratitudeEntry, GratitudeViewHolder>(diffCallback)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GratitudeViewHolder
	{
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gratitude, parent, false)
		return GratitudeViewHolder(view)
	}

	override fun onBindViewHolder(holder: GratitudeViewHolder, position: Int)
	{
		val item = getItem(position)
		holder.itemView.txt_message.text = item.text
	}
}

class GratitudeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)