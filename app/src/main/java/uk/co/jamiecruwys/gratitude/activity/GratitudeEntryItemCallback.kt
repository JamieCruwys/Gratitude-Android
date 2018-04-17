package uk.co.jamiecruwys.gratitude.activity

import android.support.v7.util.DiffUtil
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry

/**
 * Recycler view callback for [GratitudeEntry] items
 */
class GratitudeEntryItemCallback: DiffUtil.ItemCallback<GratitudeEntry>()
{
	override fun areItemsTheSame(oldItem: GratitudeEntry?, newItem: GratitudeEntry?): Boolean = oldItem?.created == newItem?.created

	override fun areContentsTheSame(oldItem: GratitudeEntry?, newItem: GratitudeEntry?): Boolean = oldItem == newItem
}