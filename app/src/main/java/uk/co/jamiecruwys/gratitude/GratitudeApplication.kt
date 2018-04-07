package uk.co.jamiecruwys.gratitude

import android.app.Application
import uk.co.jamiecruwys.gratitude.data.migration.GratitudeMigrator

/**
 * Custom [Application] with Gratitude specific functionality
 */
class GratitudeApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		GratitudeMigrator.migrate(this)
	}
}