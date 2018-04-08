package uk.co.jamiecruwys.gratitude.reminders

/**
 * Contract for triggering reminders
 */
interface ReminderProviderContract
{
	/**
	 * Remind the user from now on
	 */
	fun startReminding()

	/**
	 * Stop reminding the user
	 */
	fun stopReminding()
}