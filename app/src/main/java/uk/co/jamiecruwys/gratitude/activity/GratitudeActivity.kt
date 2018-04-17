package uk.co.jamiecruwys.gratitude.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_gratitude.*
import kotlinx.android.synthetic.main.content_gratitude.*
import uk.co.jamiecruwys.gratitude.R
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.repository.GratitudeEntryRepository
import uk.co.jamiecruwys.gratitude.data.storage.V1StorageProvider
import uk.co.jamiecruwys.gratitude.settings.SettingsActivity
import android.support.v7.widget.helper.ItemTouchHelper
import uk.co.jamiecruwys.gratitude.swipe.SwipeDeleteItemCallback
import uk.co.jamiecruwys.gratitude.swipe.SwipeDeleteItemDecoration


/**
 * Gratitude activity where the user can read their gratitude list and contribute to it
 */
class GratitudeActivity : AppCompatActivity()
{
	private val adapter = GratitudeAdapter(GratitudeEntryItemCallback())

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_gratitude)
		setSupportActionBar(toolbar)

		entryList.adapter = adapter

		// Leave this alone. It works.
		val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
		layoutManager.stackFromEnd = true
		entryList.layoutManager = layoutManager

		entryList.addItemDecoration(SwipeDeleteItemDecoration(ColorDrawable(Color.WHITE), ColorDrawable(Color.RED)))

		val itemTouchHelper = ItemTouchHelper(SwipeDeleteItemCallback(this, ItemTouchHelper.LEFT, { position: Int ->
			GratitudeEntryRepository(V1StorageProvider(this)).removeEntry(position)
			refreshList()
		}))
		itemTouchHelper.attachToRecyclerView(entryList)

		send.setOnClickListener {
			val text = gratitudeTextView.text.toString().trim()

			if (text.isBlank())
			{
				Snackbar.make(gratitudeTextView, "You have to be grateful for something. Please try again!", Snackbar.LENGTH_SHORT).show()
			}
			else
			{
				val entry = GratitudeEntry(text = text)
				GratitudeEntryRepository(V1StorageProvider(this)).addEntry(entry)
				refreshList()
			}

			gratitudeTextView.text.clear()
		}
	}

	override fun onResume()
	{
		super.onResume()
		refreshList()
	}

	private fun refreshList()
	{
		val entries = GratitudeEntryRepository(V1StorageProvider(this)).getEntries()

		if (entries.isEmpty())
		{
			emptyListText.visibility = View.VISIBLE
			entryList.visibility = View.GONE
		}
		else
		{
			emptyListText.visibility = View.GONE
			entryList.visibility = View.VISIBLE
		}

		adapter.submitList(entries)
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