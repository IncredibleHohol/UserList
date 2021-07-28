package com.incrediblehohol.core.extensions

inline fun <T> T?.orIfNull(onNull: () -> T): T = this ?: onNull.invoke()