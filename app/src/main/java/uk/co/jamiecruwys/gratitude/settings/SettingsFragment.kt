package uk.co.jamiecruwys.gratitude.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.design.widget.Snackbar
import uk.co.jamiecruwys.gratitude.R
import uk.co.jamiecruwys.gratitude.reminders.ReminderProviderContract


/**
 * Preference fragment which hosts user settings
 */
@SuppressLint("ValidFragment")
class SettingsFragment(private val reminderProvider: ReminderProviderContract): PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener
{
	object Companion {
		const val REMINDERS_ENABLED_KEY = "reminders_enabled"
		const val REMINDERS_TIME_KEY = "reminders_time"
		const val SECURITY_VAULT_KEY = "security_vault"
		const val SECURITY_BACKUP_KEY = "security_backup"
		const val SECURITY_EXPORT_KEY = "security_export"
		const val FEEDBACK_EMAIL_KEY = "feedback_email"
		const val DEVELOPER_WEBSITE_KEY = "developer_website"
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		addPreferencesFromResource(R.xml.preferences)

		findPreference(Companion.SECURITY_EXPORT_KEY).setOnPreferenceClickListener{
			Snackbar.make(view, "Coming soon...", Snackbar.LENGTH_SHORT).show()
			return@setOnPreferenceClickListener false
		}

		findPreference(Companion.FEEDBACK_EMAIL_KEY).setOnPreferenceClickListener{
			val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "gratitude@jamiecruwys.co.uk", null))
			intent.putExtra(Intent.EXTRA_SUBJECT, "Gratitude Feedback")
			intent.putExtra(Intent.EXTRA_TEXT, "")
			startActivity(Intent.createChooser(intent, "Choose an Email client:"))
			return@setOnPreferenceClickListener false
		}

		findPreference(Companion.DEVELOPER_WEBSITE_KEY).setOnPreferenceClickListener{
			startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jamiecruwys.co.uk")))
			return@setOnPreferenceClickListener false
		}
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
				if (preferenceManager.sharedPreferences.getBoolean(key, false))
				{
					reminderProvider.startReminding()
				}
				else
				{
					reminderProvider.stopReminding()
				}
			}

			Companion.REMINDERS_TIME_KEY ->
			{
				reminderProvider.stopReminding()
				reminderProvider.startReminding()
			}

			Companion.SECURITY_VAULT_KEY ->
			{
				val enabled = preferenceManager.sharedPreferences.getBoolean(key, false)
				// TODO: Prompt security passcode/password
			}

			Companion.SECURITY_BACKUP_KEY ->
			{
				val enabled = preferenceManager.sharedPreferences.getBoolean(key, false)
				// TODO: Show the user how to enable backup
			}
		}
	}
}