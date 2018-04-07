package uk.co.jamiecruwys.gratitude.data.repository

import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.storage.StorageProviderContract

/**
 * A repository for our gratitude entries. Pass in a storage provider to persist the data
 */
class GratitudeEntryRepository(private val storage: StorageProviderContract): GratitudeEntryProviderContract
{
	private val items: MutableList<GratitudeEntry> = load()

	override fun load(): MutableList<GratitudeEntry> = storage.load()

	override fun getCount(): Int = items.size

	override fun getItem(position: Int): GratitudeEntry? = items.getOrNull(position)

	override fun getEntries(): MutableList<GratitudeEntry> = items

	override fun addEntry(entry: GratitudeEntry) { items.add(entry) }

	override fun updateEntry(position: Int, updated: GratitudeEntry)
	{
		try
		{
			items[position] = updated
		}
		catch (e: IndexOutOfBoundsException) { }
	}

	override fun updateEntry(current: GratitudeEntry, updated: GratitudeEntry)
	{
		val position = items.indexOf(current)
		if (position < 0) return
		items[position] = updated
	}

	override fun removeEntry(position: Int) {
		try
		{
			items.removeAt(position)
		}
		catch (e: IndexOutOfBoundsException) { }
	}

	override fun removeEntry(entry: GratitudeEntry) { items.remove(entry) }

	override fun clear() { items.clear() }

	override fun save(items: MutableList<GratitudeEntry>) = storage.save(items)
}