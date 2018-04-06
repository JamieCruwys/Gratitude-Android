package uk.co.jamiecruwys.gratitude

import uk.co.jamiecruwys.gratitude.base.BaseActivityTest

class MainActivityTest: BaseActivityTest<MainActivity>()
{
	override fun provideTestActivity(): Class<MainActivity>
	{
		return MainActivity::class.java
	}
}