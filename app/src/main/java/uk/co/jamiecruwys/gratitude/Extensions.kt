package uk.co.jamiecruwys.gratitude

import android.os.Build
import android.widget.TimePicker
import java.util.*

/**
 * Attempts to convert a [String] in the format of HH:mm to a [Calendar] object
 * HH has an inclusive valid range of 0-23
 * mm has an inclusive valid range of 0-59
 * The HH:mm:ss is supported, but the seconds will be ignored
 * Returns null if the string is not in the specified format
 */
fun String?.toCalendar(timeInMilliseconds: Long = System.currentTimeMillis()): Calendar?
{
	val time: List<String> = this?.split(":")?.filterNot { it.isBlank() } ?: listOf()
	if (time.size < 2) return null

	val hourOfDay24Format = time[0].toInt()
	if (hourOfDay24Format < 0 || hourOfDay24Format > 23) return null

	val minute = time[1].toInt()
	if (minute < 0 || minute > 59) return null

	val calendar = Calendar.getInstance()
	calendar.timeInMillis = timeInMilliseconds
	calendar.set(Calendar.HOUR_OF_DAY, time[0].toInt())
	calendar.set(Calendar.MINUTE, time[1].toInt())
	return calendar
}

/**
 * Compat methods for [TimePicker] as it has two separate mechanisms for getting minute/hours depending on API levels
 * [TimePicker.getCurrentMinute] & [TimePicker.getCurrentHour] are used for API 22 and below
 * [TimePicker.getMinute] & [TimePicker.getHour] are available for API 23 (Marshmallow) and above
 */

fun TimePicker.getMinuteCompat(): Int
{
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) minute else currentMinute
}

fun TimePicker.getHourCompat(): Int
{
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) hour else currentHour
}

fun TimePicker.setMinuteCompat(minute: Int)
{
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this.minute = minute else this.currentMinute = minute
}

fun TimePicker.setHourCompat(hour: Int)
{
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this.hour = hour else this.currentHour = hour
}