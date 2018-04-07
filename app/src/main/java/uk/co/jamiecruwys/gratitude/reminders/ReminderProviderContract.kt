package uk.co.jamiecruwys.gratitude.reminders

import java.util.*

interface ReminderProviderContract
{
	fun setReminderTimes(vararg time: Calendar?)
	fun startReminding()
	fun stopReminding()
}