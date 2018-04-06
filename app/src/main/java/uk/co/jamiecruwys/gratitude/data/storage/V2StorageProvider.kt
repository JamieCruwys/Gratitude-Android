package uk.co.jamiecruwys.gratitude.data.storage

import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry

/**
 * Storage provider from version 2 that persists gratitude entries
 */
class V2StorageProvider : VersionedStorageProviderContract
{
	override fun load(): MutableList<GratitudeEntry>
	{
		// TODO: Load from somewhere instead of hard coding
		return arrayListOf(
				GratitudeEntry(title = "Beach", text = "Being able to visit the beach", group = "Beach", favourite = true),
				GratitudeEntry(title = "Dogs", text = "Spending time with my dogs", group = "Dogs", favourite = true),
				GratitudeEntry(title = "Colleagues", text = "Finding out new things about my colleagues", group = "Work", favourite = false),
				GratitudeEntry(title = "Sunday Roast", text = "Having a sunday roast with my family", group = "Family", favourite = true),
				GratitudeEntry(title = "App", text = "Having an app that prompts me to be grateful", group = "App", favourite = false),
				GratitudeEntry(title = "Car", text = "Being able to drive", group = "Car", favourite = false)
		)
	}

	override fun hasData(): Boolean = true

	override fun save(items: MutableList<GratitudeEntry>)
	{
		// TODO: Load from somewhere
	}

	override fun destroy()
	{
		// TODO: Destroy data
	}
}