package com.bumble.appyx.interactions

actual fun getPlatformName(): String {
    return "Desktop"
}

actual annotation class Parcelize

actual interface Parcelable

@Target(AnnotationTarget.TYPE)
actual annotation class RawValue
