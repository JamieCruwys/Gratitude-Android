package uk.co.jamiecruwys.gratitude.data.migration

import android.content.Context
import uk.co.jamiecruwys.gratitude.data.storage.V1StorageProvider
import uk.co.jamiecruwys.gratitude.data.storage.V2StorageProvider
import uk.co.jamiecruwys.gratitude.data.storage.VersionedStorageProviderContract

/**
 * Migrates the gratitude data to the latest version
 */
object GratitudeMigrator
{
	/**
	 * Migrates all data to the latest implementation
	 */
	fun migrate(context: Context)
	{
		val v1 = V1StorageProvider(context)
		val v2 = V2StorageProvider()

		migrate(v1, v2)
	}

	/**
	 * Migrates from one version to another if necessary
	 */
	private fun migrate(from: VersionedStorageProviderContract, to: VersionedStorageProviderContract)
	{
		if (from.hasData())
		{
			to.save(from.load())
			from.destroy()
		}
	}
}