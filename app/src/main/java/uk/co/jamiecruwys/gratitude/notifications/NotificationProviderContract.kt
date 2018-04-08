package uk.co.jamiecruwys.gratitude.notifications

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Contract for a provider of notifications
 */
interface NotificationProviderContract
{
	/**
	 * Creates a notification channel for messages to be sent in for [Build.VERSION_CODES.O] and above
	 */
	@RequiresApi(Build.VERSION_CODES.O)
	fun createNotificationChannel(context: Context)

	/**
	 * Sends a reminder notification to the user.
	 * This has the option for the user to directly reply with something they are grateful for.
	 */
	fun sendReminderNotification(context: Context)

	/**
	 * Sends a notification to acknowledge that the direct reply has been received
	 */
	fun sendReplyConfirmationNotification(context: Context)
}