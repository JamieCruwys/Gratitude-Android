package uk.co.jamiecruwys.gratitude.settings

import android.content.Context
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.View
import android.widget.TimePicker
import uk.co.jamiecruwys.gratitude.*
import java.util.*

/**
 * Custom dialog preference that allows the user to pick a time of day to be reminded to be grateful
 */
class TimePreference(context: Context, attributeSet: AttributeSet): DialogPreference(context, attributeSet)
{
	init {
		setPositiveButtonText(R.string.settings_time_preference_button_positive)
		setNegativeButtonText(R.string.settings_time_preference_button_negative)
	}

	private var hour = 0
	private var minute = 0
	private lateinit var picker: TimePicker

	override fun onBindDialogView(view: View?)
	{
		super.onBindDialogView(view)

		picker = view?.findViewById(R.id.picker) as TimePicker
		picker.setHourCompat(0)
		picker.setMinuteCompat(0)
	}

	override fun onDialogClosed(positiveResult: Boolean)
	{
		super.onDialogClosed(positiveResult)
		if (!positiveResult) return

		val hour = picker.getHourCompat().toString()
		val minute = if (picker.getMinuteCompat() < 10) picker.getMinuteCompat().toString() + "0" else picker.getMinuteCompat().toString()
		val time = "$hour:$minute"

		if (callChangeListener(time)) persistString(time)
	}

	override fun onGetDefaultValue(a: TypedArray, index: Int): Any? = a.getString(index)

	override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?)
	{
		val time: String = if (restoreValue)
		{
			if (defaultValue == null) getPersistedString("00:00") else getPersistedString(defaultValue.toString())
		}
		else
		{
			defaultValue.toString()
		}

		val calendar = time.toCalendar()
		hour = calendar?.get(Calendar.HOUR_OF_DAY) ?: 0
		minute = calendar?.get(Calendar.MINUTE) ?: 0
	}
}