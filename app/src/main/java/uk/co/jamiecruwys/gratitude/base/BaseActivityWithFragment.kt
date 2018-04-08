package uk.co.jamiecruwys.gratitude.base

import android.app.Fragment
import android.os.Bundle

abstract class BaseActivityWithFragment : BaseActivity()
{
	abstract fun provideFragment(): Fragment

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		fragmentManager.beginTransaction()
				.replace(android.R.id.content, provideFragment())
				.commit()
	}
}