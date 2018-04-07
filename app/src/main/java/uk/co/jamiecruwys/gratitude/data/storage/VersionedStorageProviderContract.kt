package uk.co.jamiecruwys.gratitude.data.storage

/**
 * Contract for a storage provider that can be migrated from/to
 */
interface VersionedStorageProviderContract : StorageProviderContract
{
	/**
	 * Does it have any data?
	 */
	fun hasData(): Boolean

	/**
	 * Completely destroy the storage mechanism so future calls to [hasData] will return false
	 */
	fun destroy()
}