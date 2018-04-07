package uk.co.jamiecruwys.gratitude.settings

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import uk.co.jamiecruwys.gratitude.R
import uk.co.jamiecruwys.gratitude.reminders.ReminderProviderContract
import uk.co.jamiecruwys.gratitude.toCalendar

/**
 * Preference fragment which hosts user settings
 */
@SuppressLint("ValidFragment")
class SettingsFragment(private val reminderProvider: ReminderProviderContract): PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener
{
	object Companion {
		const val REMINDERS_ENABLED_KEY = "reminders_enabled"
		const val REMINDERS_TIME_KEY = "reminders_time"
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		addPreferencesFromResource(R.xml.preferences)
	}

	override fun onResume()
	{
		super.onResume()
		preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
	}

	override fun onPause()
	{
		super.onPause()
		preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
	}

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?)
	{
		when(key)
		{
			Companion.REMINDERS_ENABLED_KEY ->
			{
				if (preferenceManager.sharedPreferences.getBoolean(key, false)) reminderProvider.startReminding() else reminderProvider.stopReminding()
			}

			Companion.REMINDERS_TIME_KEY ->
			{
				val time = preferenceManager.sharedPreferences.getString(key, null)
				reminderProvider.setReminderTimes(time.toCalendar())
			}
		}
	}
}