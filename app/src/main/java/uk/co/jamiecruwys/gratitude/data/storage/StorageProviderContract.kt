package uk.co.jamiecruwys.gratitude.data.storage

import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry

/**
 * Contract for a storage provider which will persist the gratitude entries
 */
interface StorageProviderContract
{
	/**
	 * Load the data from the storage mechanism and return it
	 */
	fun load(): MutableList<GratitudeEntry>

	/**
	 * Save the data to the storage mechanism
	 */
	fun save(items: MutableList<GratitudeEntry>)
}