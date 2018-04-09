package uk.co.jamiecruwys.gratitude.settings

import android.app.Fragment
import uk.co.jamiecruwys.gratitude.base.BaseActivityWithFragment
import uk.co.jamiecruwys.gratitude.customtabs.GratitudeCustomTabs
import uk.co.jamiecruwys.gratitude.reminders.ReminderSender

/**
 * Settings
 */
class SettingsActivity : BaseActivityWithFragment()
{
	override fun provideFragment(): Fragment
	{
		return SettingsFragment(ReminderSender(this))
	}

	override fun onStart()
	{
		super.onStart()
		GratitudeCustomTabs.warm(this)
	}
}