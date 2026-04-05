package com.markedasduplicate.kmpclock.platform

object NoopWindowStyleHelper : WindowStyleHelper {
    override fun hideFromTaskbar(windowTitle: String) = Unit
}
