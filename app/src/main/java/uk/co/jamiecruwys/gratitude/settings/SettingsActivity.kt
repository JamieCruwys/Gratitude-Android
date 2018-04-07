package uk.co.jamiecruwys.gratitude.settings

import android.app.Fragment
import uk.co.jamiecruwys.gratitude.base.BaseActivityWithFragment

class SettingsActivity : BaseActivityWithFragment()
{
	override fun provideFragment(): Fragment
	{
		return Fragment.instantiate(this, SettingsFragment::class.java.name, null)
	}
}