package uk.co.jamiecruwys.gratitude.customtabs

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.v4.content.ContextCompat
import uk.co.jamiecruwys.gratitude.R

/**
 * Warms up chrome custom tabs
 * According to the docs, doing this can save up to 700ms when opening a link
 * as Chrome has been pre-loaded
 *
 * Only call this when it's likely someone is going to click on a link
 */
object GratitudeCustomTabs
{
	fun warm(context: Context)
	{
		CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", GratitudeCustomTabsService())
	}

	fun launch(context: Context, url: String)
	{
		val builder = CustomTabsIntent.Builder()
		// builder.setStartAnimations(context, R.anim.slide_in_right, android.R.anim.slide_out_left)
		// builder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		builder.setToolbarColor(ContextCompat.getColor(context, R.color.red_heart))

		val intent = builder.build()
		intent.launchUrl(context, Uri.parse(url))
	}
}

class GratitudeCustomTabsService: CustomTabsServiceConnection()
{
	override fun onCustomTabsServiceConnected(name: ComponentName?, client: CustomTabsClient?) { client?.warmup(0) }

	override fun onServiceDisconnected(name: ComponentName?) {}
}