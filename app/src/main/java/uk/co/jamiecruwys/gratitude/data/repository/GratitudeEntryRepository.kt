package uk.co.jamiecruwys.gratitude.data.repository

import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.storage.StorageProviderContract

/**
 * A repository for our gratitude entries. Pass in a storage provider to persist the data
 */
class GratitudeEntryRepository(private val storage: StorageProviderContract): GratitudeEntryProviderContract
{
	private val items: MutableList<GratitudeEntry> = ArrayList()

	init { load() }

	override fun load(): MutableList<GratitudeEntry>
	{
		items.clear()
		items.addAll(storage.load())
		return items
	}

	override fun getCount(): Int = items.size

	override fun getItem(position: Int): GratitudeEntry? = items.getOrNull(position)

	override fun getEntries(): MutableList<GratitudeEntry> = items

	override fun addEntry(entry: GratitudeEntry)
	{
		items.add(entry)
		save(items)
	}

	override fun updateEntry(position: Int, updated: GratitudeEntry)
	{
		try
		{
			items[position] = updated
		}
		catch (e: IndexOutOfBoundsException) { }
		save(items)
	}

	override fun updateEntry(current: GratitudeEntry, updated: GratitudeEntry)
	{
		val position = items.indexOf(current)
		if (position < 0) return
		items[position] = updated
		save(items)
	}

	override fun removeEntry(position: Int) {
		try
		{
			items.removeAt(position)
		}
		catch (e: IndexOutOfBoundsException) { }
		save(items)
	}

	override fun removeEntry(entry: GratitudeEntry)
	{
		items.remove(entry)
		save(items)
	}

	override fun clear()
	{
		items.clear()
		save(items)
	}

	override fun save(items: MutableList<GratitudeEntry>) = storage.save(items)
}