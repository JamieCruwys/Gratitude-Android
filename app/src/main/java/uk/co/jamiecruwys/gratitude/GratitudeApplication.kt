package uk.co.jamiecruwys.gratitude

import android.app.Application
import android.os.Build
import uk.co.jamiecruwys.gratitude.data.migration.GratitudeMigrator
import uk.co.jamiecruwys.gratitude.notifications.NotificationSender
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			NotificationSender.createNotificationChannel(this)
		}
		ReminderSender(this).startReminding()
	}
}