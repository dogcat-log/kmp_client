package com.pawcare.client.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.DefaultComponentContext
import com.pawcare.client.android.ui.login.LoginScreen
import com.pawcare.client.presentation.viewmodel.AuthComponent
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    private lateinit var authComponent: AuthComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentContext = DefaultComponentContext(lifecycle = lifecycle)
        authComponent = get { parametersOf(componentContext) }
        setContent {
            DogCatLogTheme {
                LoginScreen(
                    authComponent = authComponent,
                    onLoginSuccess = {
                        // 성공시 다음 화면으로
                        println("로그인 성공")
                    }
                )
            }
        }
    }
}

