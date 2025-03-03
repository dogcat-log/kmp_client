// LoginScreen.kt
package com.pawcare.client.android.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pawcare.client.presentation.state.AuthState
import com.pawcare.client.presentation.viewmodel.AuthComponent

@Composable
fun LoginScreen(
    authComponent: AuthComponent,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authState by authComponent.authState.collectAsState()
    val context = LocalContext.current

    // 상태 변화에 따른 효과 처리
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                onLoginSuccess() // 로그인 성공 시 콜백 호출
            }
            is AuthState.Error -> {
                // 오류 메시지는 UI에서 표시
            }
            else -> Unit
        }
    }

    // UI 구성
    Scaffold { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (authState) {
                is AuthState.Loading -> {
                    CircularProgressIndicator()
                }
                is AuthState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LoginButtons(authComponent, context)
                    }
                }
                else -> {
                    LoginButtons(authComponent, context)
                }
            }
        }
    }
}

@Composable
private fun LoginButtons(authComponent: AuthComponent, context: Any) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Button(
            onClick = { authComponent.loginWithKakao(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("카카오로 로그인")
        }
        Button(
            onClick = { authComponent.loginWithGoogle(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("구글로 로그인")
        }
        Button(
            onClick = { authComponent.loginWithNaver(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("네이버로 로그인")
        }
    }
}