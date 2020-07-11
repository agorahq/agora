package org.agorahq.agora.core.api.shared.date

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime

object Dates {

    val simpleDateFormat = DateFormat("yyyy-mm-dd")

    fun renderCurrentDate() = DateTime.now().format(simpleDateFormat)
}
