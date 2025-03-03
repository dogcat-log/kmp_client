package com.pawcare.client.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

/**
 * Network Module (Koin DI)
 *
 * 이 모듈은 Ktor의 HttpClient 인스턴스를 싱글톤으로 생성하여 네트워크 요청에 사용합니다.
 * 주요 기능:
 * 1. Ktor의 HttpClient를 사용하여 HTTP 요청을 보냄.
 * 2. JSON 직렬화 및 역직렬화 설정 (ContentNegotiation).
 * 3. 요청 및 응답 로그 출력 (Logging).
 *
 * 이 모듈은 네트워크 관련 설정을 통합하고, 전체 애플리케이션에서 재사용 가능한 HttpClient 인스턴스를 제공합니다.
 */
//private const val API_BASE_URL = BuildConfig.NAVER_CLIENT_ID

val networkModule = module {

    // HttpClient 인스턴스를 싱글톤으로 생성하여 애플리케이션 전체에서 재사용
    single {
        HttpClient {
            // ContentNegotiation 플러그인 설정: JSON 직렬화/역직렬화 설정
            install(ContentNegotiation) {
                json()  // Kotlinx JSON을 사용한 직렬화/역직렬화
            }
            // Logging 플러그인 설정: 요청과 응답 로그를 출력하도록 설정
            install(Logging) {
                logger = Logger.DEFAULT  // 기본 로그 출력
                level = LogLevel.ALL  // 모든 로그 레벨을 출력 (디버깅용)
            }
            defaultRequest {
                url {
                    protocol
                }
            }
        }

    }
}
