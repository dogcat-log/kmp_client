package com.pawcare.client.presentation.viewmodel

import com.pawcare.client.data.api.TokenProvider
import com.pawcare.client.domain.usecase.GetUserProfileUseCase
import com.pawcare.client.domain.usecase.SocialLoginUseCase
import com.pawcare.client.platform.social.SocialLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.pawcare.client.domain.model.auth.SocialProvider
import com.pawcare.client.presentation.state.AuthState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthComponent(
    componentContext: ComponentContext,
    private val socialLogin: SocialLogin,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val tokenProvider: TokenProvider
) : ComponentContext by componentContext {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // var로 변경하여 재할당 가능하게 함
    private var loginJob: Job? = null

    init {
        lifecycle.doOnDestroy {
            scope.cancel()
        }

        // 초기 상태 확인
        checkAuthState()
    }

    private fun checkAuthState() {
        scope.launch {
            val accessToken = tokenProvider.getAccessToken()
            if (accessToken.isNotEmpty()) {
                _authState.value = AuthState.Authenticated()
                fetchUserProfile()
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    private fun fetchUserProfile() {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val result = getUserProfileUseCase()

                    withContext(Dispatchers.Main) {
                        result.fold(onSuccess = { user ->
                            _authState.value = AuthState.Authenticated(user.data)
                        }, onFailure = { e ->
                            Napier.e("프로필 조회 실패", throwable = e)
                        })
                    }
                }
            } catch (e: Exception) {
                Napier.e("프로필 조회 중 예외 발생", throwable = e)
            }
        }
    }

    fun loginWithKakao(context: Any) {
        handleSocialLogin(context, SocialProvider.KAKAO)
    }

    fun loginWithGoogle(context: Any) {
        handleSocialLogin(context, SocialProvider.GOOGLE)
    }

    fun loginWithNaver(context: Any) {
        handleSocialLogin(context, SocialProvider.NAVER)
    }

    private fun handleSocialLogin(context: Any, provider: SocialProvider) {
        // 이전 로그인 작업 취소
        loginJob?.cancel()

        loginJob = scope.launch {
            _authState.value = AuthState.Loading

            try {
                withContext(Dispatchers.IO) {
                    val socialAccessToken = when (provider) {
                        SocialProvider.KAKAO -> socialLogin.loginWithKakao(context)
                        SocialProvider.GOOGLE -> socialLogin.loginWithGoogle(context)
                        SocialProvider.NAVER -> socialLogin.loginWithNaver(context)
                    }
                    withContext(Dispatchers.Main) {
                        socialAccessToken.fold(
                            onSuccess = { accessToken ->
                                handleServerAuthentication(accessToken, provider)
                            },
                            onFailure = { e ->
                                val errorMessage = when (provider) {
                                    SocialProvider.KAKAO -> "카카오 로그인에 실패했습니다."
                                    SocialProvider.GOOGLE -> "구글 로그인에 실패했습니다."
                                    SocialProvider.NAVER -> "네이버 로그인에 실패했습니다."
                                }
                                _authState.value = AuthState.Error("$errorMessage : ${e.message}")
                                Napier.e("소셜로그인 실패", throwable = e)
                            }
                        )
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("로그인 중 에러가 발생했습니다.${e.message}")
                Napier.e("소셜로그인 실패", throwable = e)
            }
        }
    }

    private suspend fun handleServerAuthentication(accessToken: String, provider: SocialProvider) {
        try {
            withContext(Dispatchers.IO) {
                val result = socialLoginUseCase(
                    accessToken = accessToken,
                    provider = provider.toServerString()
                )
                withContext(Dispatchers.Main) {
                    result.fold(
                        onSuccess = { authResult ->
                            tokenProvider.saveToken(
                                authResult.accessToken,
                                authResult.refreshToken
                            )
                            _authState.value = AuthState.Authenticated()
                            fetchUserProfile()
                        },
                        onFailure = { e ->
                            _authState.value = AuthState.Error("로그인 중 에러가 발생했습니다.${e.message}")
                            Napier.e("소셜로그인 실패", throwable = e)
                        }
                    )
                }
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error("로그인 중 에러가 발생했습니다.${e.message}")
            Napier.e("소셜로그인 실패", throwable = e)
        }
    }

    fun logout() {
        scope.launch {
            tokenProvider.clearTokens()
            _authState.value = AuthState.Unauthenticated
        }
    }
}