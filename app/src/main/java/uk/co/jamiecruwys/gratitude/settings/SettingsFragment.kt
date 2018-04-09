package uk.co.jamiecruwys.gratitude.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.design.widget.Snackbar
import uk.co.jamiecruwys.gratitude.R
import uk.co.jamiecruwys.gratitude.customtabs.GratitudeCustomTabs
import uk.co.jamiecruwys.gratitude.reminders.ReminderProviderContract


/**
 * Preference fragment which hosts user settings
 */
@SuppressLint("ValidFragment")
class SettingsFragment(private val reminderProvider: ReminderProviderContract): PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		addPreferencesFromResource(R.xml.preferences)

		findPreference(getString(R.string.settings_security_export_key)).setOnPreferenceClickListener{
			Snackbar.make(view, "Coming soon...", Snackbar.LENGTH_SHORT).show()
			return@setOnPreferenceClickListener false
		}

		findPreference(getString(R.string.settings_developer_email_key)).setOnPreferenceClickListener{
			val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "gratitude@jamiecruwys.co.uk", null))
			intent.putExtra(Intent.EXTRA_SUBJECT, "Gratitude")
			intent.putExtra(Intent.EXTRA_TEXT, "")
			startActivity(Intent.createChooser(intent, "Choose an Email client:"))
			return@setOnPreferenceClickListener false
		}

		findPreference(getString(R.string.settings_developer_website_key)).setOnPreferenceClickListener{
			GratitudeCustomTabs.launch(activity, "http://www.jamiecruwys.co.uk")
			return@setOnPreferenceClickListener false
		}

		findPreference(getString(R.string.settings_developer_donate_key)).setOnPreferenceClickListener{
			GratitudeCustomTabs.launch(activity, "https://www.paypal.me/JamieCruwys")
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
			getString(R.string.settings_reminders_enabled_key) ->
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

			getString(R.string.settings_reminders_time_key) ->
			{
				reminderProvider.stopReminding()
				reminderProvider.startReminding()
			}

			getString(R.string.settings_security_vault_key) ->
			{
				// val enabled = preferenceManager.sharedPreferences.getBoolean(key, false)
				// TODO: Prompt security passcode/password
			}

			getString(R.string.settings_security_backup_enabled_key) ->
			{
				// val enabled = preferenceManager.sharedPreferences.getBoolean(key, false)
				// TODO: Show the user how to enable backup
			}
		}
	}
}