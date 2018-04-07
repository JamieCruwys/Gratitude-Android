package uk.co.jamiecruwys.gratitude.data.storage

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry

/**
 * Storage provider from version 1 that persists gratitude entries using shared preferences
 */
class V1StorageProvider(context: Context): VersionedStorageProviderContract
{
	object Companion {
		const val V1_SHARED_PREFERENCES_NAME = "gratitude_preferences"
		const val V1_SHARED_PREFERENCES_KEY = "gratitude_entries"
	}

	private val gson = Gson()
	private val sharedPreferences = context.getSharedPreferences(Companion.V1_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

	override fun load(): MutableList<GratitudeEntry>
	{
		val json = sharedPreferences.getString(Companion.V1_SHARED_PREFERENCES_KEY, null)

		return try
		{
			val data: MutableList<String> = Gson().fromJson(json, object : TypeToken<Collection<String>>() {}.type)
			data.mapTo(ArrayList(), { GratitudeEntry(text = it) })
		}
		catch (e: JsonParseException)
		{
			Log.e(TAG, "Failed to get gratitude entries from JSON")
			ArrayList()
		}
	}

	override fun hasData(): Boolean = !sharedPreferences.getString(Companion.V1_SHARED_PREFERENCES_KEY, null).isNullOrBlank()

	override fun save(items: MutableList<GratitudeEntry>)
	{
		val strings: ArrayList<String> = items.mapTo(ArrayList(), { it.text })
		val json = gson.toJson(strings)
		sharedPreferences.edit().putString(Companion.V1_SHARED_PREFERENCES_KEY, json).apply()
	}

	override fun destroy()
	{
		sharedPreferences.edit().clear().apply()
	}
}