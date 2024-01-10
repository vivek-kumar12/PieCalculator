// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}
    plugins {
        id("com.android.application") version "8.1.1" apply false
        id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    }
