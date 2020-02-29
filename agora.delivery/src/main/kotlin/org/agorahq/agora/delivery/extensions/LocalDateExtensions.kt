package org.agorahq.agora.delivery.extensions

import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toTimestamp() = atStartOfDay(ZoneId.systemDefault()).toEpochSecond()