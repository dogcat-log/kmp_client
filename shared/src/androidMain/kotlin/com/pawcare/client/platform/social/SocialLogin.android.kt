package com.pawcare.client.platform.social

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.pawcare.client.BuildConfig
import com.pawcare.client.config.AppConfig
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class SocialLogin {

    actual suspend fun loginWithKakao(context: Any): Result<String> =
        suspendCoroutine { continuation ->
            try {
                val ctx = context as Context
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(ctx)) {
                    UserApiClient.instance.loginWithKakaoTalk(ctx) { token, error ->
                        if (error != null) {
                            continuation.resume(Result.failure(error))
                        } else if (token != null) {
                            continuation.resume(Result.success(token.accessToken))
                        } else {
                            continuation.resume(Result.failure(Exception("token null error")))
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(ctx) { token, error ->
                        if (error != null) {
                            continuation.resume(Result.failure(error))
                        } else if (token != null) {
                            continuation.resume(Result.success(token.accessToken))
                        } else {
                            continuation.resume(Result.failure(Exception("token null error")))

                        }
                    }
                }
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }


    actual suspend fun loginWithNaver(context: Any): Result<String> =
        suspendCoroutine { continuation ->
            try {

                val ctx = context as Context

                NaverIdLoginSDK.authenticate(
                    ctx,
                    object : OAuthLoginCallback {
                        override fun onSuccess() {
                            val accessToken = NaverIdLoginSDK.getAccessToken()
                            if (accessToken != null) {
                                continuation.resume(Result.success(accessToken))
                            } else {
                                continuation.resume(Result.failure(Exception("naver 액세스 토큰을 가져올 수 없습니다..")))
                            }
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            continuation.resume(Result.failure(Exception("naver 액세스 로그인에 실패했습니다. $message")))

                        }

                        override fun onError(errorCode: Int, message: String) {
                            continuation.resume(Result.failure(Exception("naver 액세스 로그인에 실패했습니다. $message")))
                        }
                    }
                )

            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }

        }

    actual suspend fun loginWithGoogle(context: Any): Result<String> =
        suspendCoroutine { continuation ->
            try {
                val activity = context as Activity

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(AppConfig.GOOGLE_CLIENT_ID)
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(activity, gso)
                val signInIntent = googleSignInClient.signInIntent

                activity.startActivityForResult(
                    signInIntent,
                    GOOGLE_SIGN_IN_REQUEST_CODE
                )

            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 9001
    }
}