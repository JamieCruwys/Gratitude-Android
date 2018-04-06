package uk.co.jamiecruwys.gratitude

import uk.co.jamiecruwys.gratitude.base.BaseActivityTest

class MainActivityTest: BaseActivityTest<GratitudeActivity>()
{
	override fun provideTestActivity(): Class<GratitudeActivity>
	{
		return GratitudeActivity::class.java
	}
}