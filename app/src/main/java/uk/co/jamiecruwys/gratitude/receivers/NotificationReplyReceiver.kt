package uk.co.jamiecruwys.gratitude.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.RemoteInput
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.repository.GratitudeEntryRepository
import uk.co.jamiecruwys.gratitude.data.storage.V2StorageProvider
import uk.co.jamiecruwys.gratitude.notifications.NotificationSender

/**
 * Broadcast receiver that is trigger when a user replies to a reminder notification
 */
class NotificationReplyReceiver: BroadcastReceiver()
{
	override fun onReceive(context: Context, intent: Intent?)
	{
		if (NotificationSender.Companion.REMINDER_NOTIFICATION_REPLY_ACTION == intent?.action)
		{
			val message = getMessageText(intent)
			if (!message.isNullOrBlank())
			{
				GratitudeEntryRepository(V2StorageProvider()).addEntry(GratitudeEntry(text = message.toString()))
			}
			NotificationSender.sendReplyConfirmationNotification(context)
		}
	}

	/**
	 * Retrieves the text that the [RemoteInput] stored when the user replied to the notification
	 */
	private fun getMessageText(intent: Intent?): CharSequence?
	{
		val remoteInput = RemoteInput.getResultsFromIntent(intent)
		return remoteInput?.getCharSequence(NotificationSender.Companion.RESULT_KEY_REMINDER_REPLY, null)
	}
}