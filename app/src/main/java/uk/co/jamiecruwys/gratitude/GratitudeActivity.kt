package uk.co.jamiecruwys.gratitude

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_gratitude.*
import uk.co.jamiecruwys.gratitude.settings.SettingsActivity

/**
 * Gratitude activity where the user can read their gratitude list and contribute to it
 */
class GratitudeActivity : AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_gratitude)
		setSupportActionBar(toolbar)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean
	{
		menuInflater.inflate(R.menu.menu_gratitude, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean
	{
		return when (item.itemId)
		{
			R.id.action_settings ->
			{
				startActivity(Intent(this, SettingsActivity::class.java))
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}
}
