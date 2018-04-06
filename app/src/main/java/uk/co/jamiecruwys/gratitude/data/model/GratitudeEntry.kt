package uk.co.jamiecruwys.gratitude.data.model

/**
 * A model that represents an entry in the gratitude journal
 */
data class GratitudeEntry(val title: String = "",
                          val text: String = "",
                          val group: String = "",
                          val favourite: Boolean = false,
                          val viewCount: Int = 0,
                          val created: Long = System.currentTimeMillis(),
                          val updated: Long = 0)