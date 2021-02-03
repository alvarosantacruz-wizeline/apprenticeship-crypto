package com.github.alvarosct02.criptocurrency

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MyRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, FakeApp::class.java.name, context)
    }
}
