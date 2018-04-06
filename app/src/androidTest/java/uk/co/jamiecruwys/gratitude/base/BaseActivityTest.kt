package uk.co.jamiecruwys.gratitude.base

import android.app.Activity
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

abstract class BaseActivityTest<T: Activity>: BaseTest()
{
	@Rule @JvmField
	val test = ActivityTestRule<T>(this.provideTestActivity())

	fun getActivity(): T = test.activity

	abstract fun provideTestActivity(): Class<T>

	@Test
	fun launched()
	{
		assert(test.javaClass == provideTestActivity())
		assert(getActivity().applicationContext != null)
	}
}