package uk.co.jamiecruwys.gratitude

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

@Suppress("IllegalIdentifier")
class StringToCalendarTest
{
	object Companion
	{
		/**
		 * Use a fixed milliseconds time rather than the current milliseconds
		 * as execution might span over two days, skewing results
		 */
		const val MILLISECONDS: Long = 1523117714962

		val VALID_HOURS : List<String> = listOf(
				"00", "0", "01", "1", "02", "2", "03", "3", "04", "4",
				"05", "5", "06", "6", "07", "7", "08", "8", "09", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23"
		)

		val VALID_MINUTES : List<String> = listOf(
				"00", "0", "01", "1", "02", "2", "03", "3", "04", "4",
				"05", "5", "06", "6", "07", "7", "08", "8", "09", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
				"50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
		)
	}

	/**
	 * Asserts if the time string given is valid.
	 * This will be asserted against a calendar with the expected hour and minute.
	 */
	fun assertValidTime(string: String?, expectedHour: Int, expectedMinute: Int)
	{
		val calendar = Calendar.getInstance()
		calendar.timeInMillis = Companion.MILLISECONDS
		calendar.set(Calendar.HOUR_OF_DAY, expectedHour)
		calendar.set(Calendar.MINUTE, expectedMinute)

		try
		{
			assertEquals(calendar, string.toCalendar(Companion.MILLISECONDS))
		}
		catch (e: AssertionError)
		{
			throw AssertionError("$string failed to match HOUR_OF_DAY $expectedHour and MINUTE $expectedMinute. ${e.message}", e.cause)
		}
	}

	/**
	 * Asserts if the time string given is invalid
	 */
	fun assertInvalidTime(string: String?)
	{
		try
		{
			assertEquals(null, string.toCalendar(Companion.MILLISECONDS))
		}
		catch (e: AssertionError)
		{
			throw AssertionError("$string failed to produce a null result. ${e.message}", e.cause)
		}
	}

	@Test
	fun `null`()
	{
		assertInvalidTime(null)
	}

	@Test
	fun `blank characters`()
	{
		assertInvalidTime("")
		assertInvalidTime(" ")
		assertInvalidTime("                   ")
		assertInvalidTime("       ")
		assertInvalidTime("\n")
		assertInvalidTime("\n\n")
		assertInvalidTime(":")
		assertInvalidTime("::")
		assertInvalidTime(":::")
		assertInvalidTime(" : : : ")

		assertValidTime("              00:00:00", 0, 0)
		assertValidTime("              00:00:00 ", 0, 0)
		assertValidTime("              00:00:00 ", 0, 0)
		assertValidTime("              00         :         00        :     00        ", 0, 0)
		assertValidTime("              0 0         :         0 0        :     0 0        ", 0, 0 )
		assertValidTime("00:\n00:\n00", 0, 0)
		assertValidTime("0\n0\n:\n0\n0\n:\n0\n0\n", 0, 0)
		assertValidTime(" 0 \n 0 \n : \n 0 \n 0 \n : \n 0 \n 0 \n ",0, 0)
	}

	@Test
	fun `invalid character`()
	{
		assertInvalidTime("👨‍💻")
		assertInvalidTime("☠︎")
		assertInvalidTime("００:００")
		assertInvalidTime("𝟶𝟶:𝟶𝟶")
		assertInvalidTime("àáâãäåçèéêëìíîðñòôõöö")
		assertInvalidTime(" ( \u001F\u001F ) ")
		assertInvalidTime(" “ ‘ ` | / \\ , ; : & < > ^ * ?")
	}

	@Test
	fun `missing separator`()
	{
		assertInvalidTime("13")
		assertInvalidTime("13")
		assertInvalidTime("13-32")
		assertInvalidTime("13-32-24")
		assertInvalidTime("13.32")
		assertInvalidTime("13.32.24")
	}

	@Test
	fun `buffer overflow`()
	{
		/**
		 * The minimum value of a 32 bit [Int] is -2147483648. See [Int.MIN_VALUE]
		 * The maximum value of a 32 bit [Int] is +2147483647. See [Int.MAX_VALUE]
		 *
		 * Let's test with -2147483649 and +2147483648
		 */
		val invalid: List<String> = arrayListOf("-2147483649", "2147483648")
		for (hour: String in invalid)
		{
			for (minute: String in invalid)
			{
				assertInvalidTime("$hour:$minute")
			}
		}
	}

	@Test
	fun `invalid hours`()
	{
		val invalidHours: List<String> = arrayListOf("24", "25", "26")
		for (hour: String in invalidHours)
		{
			for (minute: String in Companion.VALID_MINUTES)
			{
				assertInvalidTime("$hour:$minute")
			}
		}
	}

	@Test
	fun `invalid minutes`()
	{
		val invalidMinutes: List<String> = arrayListOf("60", "61", "62")
		for (hour: String in Companion.VALID_HOURS)
		{
			for (minute: String in invalidMinutes)
			{
				assertInvalidTime("$hour:$minute")
			}
		}
	}

	@Test
	fun `valid times`()
	{
		for (hour: String in Companion.VALID_HOURS)
		{
			for (minute: String in Companion.VALID_MINUTES)
			{
				assertValidTime("$hour:$minute", hour.toInt(), minute.toInt())
			}
		}
	}
}