package uk.co.jamiecruwys.gratitude.data.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.jamiecruwys.gratitude.data.model.GratitudeEntry
import uk.co.jamiecruwys.gratitude.data.storage.StorageProviderContract
import kotlin.test.assertEquals

@Suppress("IllegalIdentifier")
class GratitudeEntryRepositoryTest
{
	@Mock
	private lateinit var storageContract: StorageProviderContract

	private lateinit var repository: GratitudeEntryRepository
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

		whenever(storageContract.load()).thenReturn(mockEntries)
		repository = GratitudeEntryRepository(storageContract)
	}

	@Test
	fun `check storage provider is triggered when load is called`()
	{
		verify(storageContract).load()
	}

	@Test
	fun `check storage provider data is being loaded`()
	{
		assertEquals(6, repository.getCount())
		assertEquals("Beach", repository.getItem(0)?.title)
		assertEquals("Car", repository.getItem(5)?.group)
		assertEquals(mockEntries, repository.getEntries())
	}

	@Test
	fun `check adding an item works`()
	{
		val item = GratitudeEntry(text = "This item should be added")
		repository.addEntry(item)
		assertEquals(7, repository.getCount())
		assertEquals(item.hashCode(), repository.getItem(6)?.hashCode())
	}

	@Test
	fun `check update by position works`()
	{
		val item = GratitudeEntry(text = "Fish and chips", group = "Food")
		val position = 3
		repository.updateEntry(position, item)

		assertEquals(6, repository.getCount())

		val updatedItem = repository.getItem(position)
		assertEquals(item.text, updatedItem?.text)
		assertEquals(item.group, updatedItem?.group)
		assertEquals(item.favourite, updatedItem?.favourite)
		assertEquals(item, updatedItem)
	}

	@Test
	fun `try to update with invalid position`()
	{
		repository.updateEntry(-1, GratitudeEntry())
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())

		repository.updateEntry(100, GratitudeEntry())
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())
	}

	@Test
	fun `check update by instance`()
	{
		val item = GratitudeEntry(title = "Football", text = "Playing football with friends", group = "Sport", favourite = true)
		val position = 4
		val current = mockEntries[4]

		repository.updateEntry(current, item)
		assertEquals(6, repository.getCount())

		val updatedItem = repository.getItem(position)
		assertEquals(item.title, updatedItem?.title)
		assertEquals(item.text, updatedItem?.text)
		assertEquals(item.group, updatedItem?.group)
		assertEquals(item.favourite, updatedItem?.favourite)
		assertEquals(item, updatedItem)
	}

	@Test
	fun `try and update with instance that cannot be found`()
	{
		val toUpdate = GratitudeEntry()
		repository.updateEntry(toUpdate, GratitudeEntry(text = "Should not have updated anything"))

		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())
	}

	@Test
	fun `check remove by position`()
	{
		repository.removeEntry(2)
		assertEquals(5, repository.getCount())
		assertEquals("Beach", repository.getItem(0)?.title)

		repository.removeEntry(0)
		assertEquals(4, repository.getCount())
		assertEquals("Dogs", repository.getItem(0)?.title)
	}

	@Test
	fun `try to remove by invalid position`()
	{
		repository.removeEntry(-1)
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())

		repository.removeEntry(100)
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())

		repository.removeEntry(5)
		assertEquals(5, repository.getCount())
		assertEquals(mockEntries[4], repository.getItem(4))

		repository.removeEntry(5)
		assertEquals(5, repository.getCount())
		assertEquals(mockEntries[4], repository.getItem(4))

		repository.removeEntry(5)
		assertEquals(5, repository.getCount())
		assertEquals(mockEntries[4], repository.getItem(4))
	}

	@Test
	fun `check remove by instance`()
	{
		val first = mockEntries[2]
		val second = mockEntries[3]

		repository.removeEntry(first)
		assertEquals(5, repository.getCount())
		assertEquals("Colleagues", first.title)
		assertEquals("Dogs", repository.getItem(1)?.title)
		assertEquals("Sunday Roast", repository.getItem(2)?.title)

		repository.removeEntry(first)
		assertEquals(5, repository.getCount())
		assertEquals("Colleagues", first.title)
		assertEquals("Dogs", repository.getItem(1)?.title)
		assertEquals("Sunday Roast", repository.getItem(2)?.title)

		repository.removeEntry(second)

		assertEquals(4, repository.getCount())
		assertEquals("Dogs", repository.getItem(1)?.title)
		assertEquals("App", repository.getItem(2)?.title)
	}

	@Test
	fun `try and remove by invalid instance`()
	{
		repository.removeEntry(GratitudeEntry())
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())

		repository.removeEntry(GratitudeEntry(text = "Testing nothing is removed"))
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())

		repository.removeEntry(GratitudeEntry(title = "Beach", text = "Being able to visit the beach", group = "Beach", favourite = true))
		assertEquals(6, repository.getCount())
		assertEquals(mockEntries, repository.getEntries())
	}

	@Test
	fun `check clear removes all items`()
	{
		repository.clear()
		assertEquals(0, repository.getCount())
		assertEquals(0, repository.getEntries().size)

		repository.clear()
		assertEquals(0, repository.getCount())
		assertEquals(0, repository.getEntries().size)

		// Lets check it also works when an item is added again
		repository.addEntry(GratitudeEntry())
		repository.clear()

		assertEquals(0, repository.getCount())
		assertEquals(0, repository.getEntries().size)

		repository.clear()
		assertEquals(0, repository.getCount())
		assertEquals(0, repository.getEntries().size)
	}

	@Test
	fun `check storage provider is triggered when save is called`()
	{
		repository.save(arrayListOf())
		verify(storageContract).save(any())

		repository.save(mockEntries)
		verify(storageContract).save(mockEntries)
	}
}