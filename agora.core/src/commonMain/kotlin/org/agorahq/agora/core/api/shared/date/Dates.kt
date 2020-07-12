package org.agorahq.agora.core.api.shared.date

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime

object Dates {

    val simpleDateFormat = DateFormat("yyyy-MM-dd")

    val humanReadableFormat = DateFormat("MMM d, yyyy")

    fun renderCurrentDate() = DateTime.now().format(simpleDateFormat)
}
