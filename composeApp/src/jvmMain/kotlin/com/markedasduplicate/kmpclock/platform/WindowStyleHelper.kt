package com.markedasduplicate.kmpclock.platform

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

internal interface User32 : Library {
    fun GetWindowLongA(hWnd: Pointer, nIndex: Int): Int
    fun SetWindowLongA(hWnd: Pointer, nIndex: Int, dwNewLong: Int): Int
    fun FindWindowA(lpClassName: String?, lpWindowName: String?): Pointer?

    companion object {
        val INSTANCE: User32 = Native.load("user32", User32::class.java)
        const val GWL_EXSTYLE = -20
        const val WS_EX_TOOLWINDOW = 0x00000080
        const val WS_EX_APPWINDOW = 0x00040000
    }
}

class WindowStyleHelper {
    fun hideFromTaskbar(windowTitle: String) {
        User32.INSTANCE.FindWindowA(null, windowTitle)?.let { hwnd ->
            val exStyle = User32.INSTANCE.GetWindowLongA(hwnd, User32.GWL_EXSTYLE)
            User32.INSTANCE.SetWindowLongA(
                hwnd,
                User32.GWL_EXSTYLE,
                (exStyle or User32.WS_EX_TOOLWINDOW) and User32.WS_EX_APPWINDOW.inv(),
            )
        }
    }
}
