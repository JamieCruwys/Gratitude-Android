package uk.co.jamiecruwys.gratitude.reminders

import java.util.*

/**
 * Contract for triggering reminders
 */
interface ReminderProviderContract
{
	/**
	 * Sets a number of numbers that the user should be reminded
	 */
	fun setReminderTimes(vararg time: Calendar?)

	/**
	 * Remind the user from now on
	 */
	fun startReminding()

	/**
	 * Stop reminding the user
	 */
	fun stopReminding()
}