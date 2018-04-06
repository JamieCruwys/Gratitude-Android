package uk.co.jamiecruwys.gratitude.data.repository

import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.storage.StorageProviderContract

/**
 * Contract for a provider of gratitude entries
 */
interface GratitudeEntryProviderContract : StorageProviderContract
{
	/**
	 * How many entries are there?
	 */
	fun getCount(): Int

	/**
	 * Get an item by its position. The position may be outside the range of the data set, so this can return null
	 */
	fun getItem(position: Int): GratitudeEntry?

	/**
	 * Get all the gratitude entries
	 */
	fun getEntries(): MutableList<GratitudeEntry>

	/**
	 * Add a gratitude entry
	 */
	fun addEntry(entry: GratitudeEntry)

	/**
	 * Update an existing entry by its position
	 */
	fun updateEntry(position: Int, updated: GratitudeEntry)

	/**
	 * Update an existing entry by the entry object
	 */
	fun updateEntry(current: GratitudeEntry, updated: GratitudeEntry)

	/**
	 * Remove an entry by position
	 */
	fun removeEntry(position: Int)

	/**
	 * Remove an entry by the entry object
	 */
	fun removeEntry(entry: GratitudeEntry)

	/**
	 * Clear all gratitude entries
	 */
	fun clear()
}