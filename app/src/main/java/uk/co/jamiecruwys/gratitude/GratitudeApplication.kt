package uk.co.jamiecruwys.gratitude

import android.app.Application
import uk.co.jamiecruwys.gratitude.data.migration.GratitudeMigrator
import uk.co.jamiecruwys.gratitude.reminders.ReminderSender

/**
 * Custom [Application] with Gratitude specific functionality
 */
class GratitudeApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		GratitudeMigrator.migrate(this)
		ReminderSender(this).startReminding()
	}
}