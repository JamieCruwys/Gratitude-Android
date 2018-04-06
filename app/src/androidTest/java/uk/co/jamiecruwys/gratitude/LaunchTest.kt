package uk.co.jamiecruwys.gratitude

import org.junit.Test
import uk.co.jamiecruwys.gratitude.base.BaseTest

class LaunchTest: BaseTest()
{
	@Test
	fun testLaunch()
	{
		assert("uk.co.jamiecruwys.gratitude" == context.packageName)
	}
}