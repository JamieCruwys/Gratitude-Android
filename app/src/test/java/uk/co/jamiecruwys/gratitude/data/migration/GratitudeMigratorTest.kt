package uk.co.jamiecruwys.gratitude.data.migration

import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.storage.VersionedStorageProviderContract

@Suppress("IllegalIdentifier")
class GratitudeMigratorTest
{
	@Mock
	private lateinit var fromWithData: VersionedStorageProviderContract

	@Mock
	private lateinit var fromWithEmptyData: VersionedStorageProviderContract

	@Mock
	private lateinit var fromWithoutData: VersionedStorageProviderContract

	@Mock
	private lateinit var toWithData: VersionedStorageProviderContract

	@Mock
	private lateinit var toWithEmptyData: VersionedStorageProviderContract

	@Mock
	private lateinit var toWithoutData: VersionedStorageProviderContract

	private lateinit var migrator: GratitudeMigrator
	private lateinit var mockEntries: MutableList<GratitudeEntry>

	@Before
	fun setUp()
	{
		MockitoAnnotations.initMocks(this)

		mockEntries = arrayListOf(
				GratitudeEntry(title = "Beach", text = "Being able to visit the beach", group = "Beach", favourite = true),
				GratitudeEntry(title = "Dogs", text = "Spending time with my dogs", group = "Dogs", favourite = true),
				GratitudeEntry(title = "Colleagues", text = "Finding out new things about my colleagues", group = "Work", favourite = false),
				GratitudeEntry(title = "Sunday Roast", text = "Having a sunday roast with my family", group = "Family", favourite = true),
				GratitudeEntry(title = "App", text = "Having an app that prompts me to be grateful", group = "App", favourite = false),
				GratitudeEntry(title = "Car", text = "Being able to drive", group = "Car", favourite = false)
		)

		// With data
		whenever(fromWithData.hasData()).thenReturn(true)
		whenever(fromWithData.load()).thenReturn(mockEntries)

		whenever(toWithData.hasData()).thenReturn(true)
		whenever(toWithData.load()).thenReturn(mockEntries)

		// With empty data
		whenever(fromWithEmptyData.hasData()).thenReturn(true)
		whenever(fromWithEmptyData.load()).thenReturn(arrayListOf())

		whenever(toWithEmptyData.hasData()).thenReturn(true)
		whenever(toWithEmptyData.load()).thenReturn(arrayListOf())

		// Without data
		whenever(fromWithoutData.hasData()).thenReturn(false)
		whenever(fromWithoutData.load()).thenReturn(arrayListOf())

		whenever(toWithoutData.hasData()).thenReturn(false)
		whenever(toWithoutData.load()).thenReturn(arrayListOf())

		migrator = GratitudeMigrator
	}

	@Test
	fun `from storage provider with data to storage provider with data`()
	{
		migrator.migrate(fromWithData, toWithData)

		inOrder(fromWithData, toWithData) {
			verify(fromWithData).hasData()
			verify(fromWithData).load()
			verify(toWithData).save(mockEntries)
			verify(fromWithData).destroy()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider with data to storage provider with empty data`()
	{
		migrator.migrate(fromWithData, toWithEmptyData)

		inOrder(fromWithData, toWithEmptyData) {
			verify(fromWithData).hasData()
			verify(fromWithData).load()
			verify(toWithEmptyData).save(mockEntries)
			verify(fromWithData).destroy()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider with data to storage provider without data`()
	{
		migrator.migrate(fromWithData, toWithoutData)

		inOrder(fromWithData, toWithoutData) {
			verify(fromWithData).hasData()
			verify(fromWithData).load()
			verify(toWithoutData).save(mockEntries)
			verify(fromWithData).destroy()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider without data to storage provider with data`()
	{
		migrator.migrate(fromWithoutData, toWithData)

		inOrder(fromWithoutData, toWithData) {
			verify(fromWithoutData).hasData()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider without data to storage provider with empty data`()
	{
		migrator.migrate(fromWithoutData, toWithEmptyData)

		inOrder(fromWithoutData, toWithEmptyData) {
			verify(fromWithoutData).hasData()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider without data to storage provider without data`()
	{
		migrator.migrate(fromWithoutData, toWithoutData)

		inOrder(fromWithoutData, toWithoutData) {
			verify(fromWithoutData).hasData()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider with empty data to storage provider with data`()
	{
		migrator.migrate(fromWithEmptyData, toWithData)

		inOrder(fromWithEmptyData, toWithData) {
			verify(fromWithEmptyData).hasData()
			verify(fromWithEmptyData).load()
			verify(toWithData).save(arrayListOf())
			verify(fromWithEmptyData).destroy()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider with empty data to storage provider with empty data`()
	{
		migrator.migrate(fromWithEmptyData, toWithEmptyData)

		inOrder(fromWithEmptyData, toWithEmptyData) {
			verify(fromWithEmptyData).hasData()
			verify(fromWithEmptyData).load()
			verify(toWithEmptyData).save(arrayListOf())
			verify(fromWithEmptyData).destroy()
			verifyNoMoreInteractions()
		}
	}

	@Test
	fun `from storage provider with empty data to storage provider without data`()
	{
		migrator.migrate(fromWithEmptyData, toWithoutData)

		inOrder(fromWithEmptyData, toWithoutData) {
			verify(fromWithEmptyData).hasData()
			verify(fromWithEmptyData).load()
			verify(toWithoutData).save(arrayListOf())
			verify(fromWithEmptyData).destroy()
			verifyNoMoreInteractions()
		}
	}
}