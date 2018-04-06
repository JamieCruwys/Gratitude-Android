package uk.co.jamiecruwys.gratitude

import android.app.Application
import uk.co.jamiecruwys.gratitude.data.migration.GratitudeMigrator

class GratitudeApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		GratitudeMigrator.migrate(this)
	}
}