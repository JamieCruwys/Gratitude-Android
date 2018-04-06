package uk.co.jamiecruwys.gratitude.base

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest
{
	val context: Context = InstrumentationRegistry.getTargetContext()
}