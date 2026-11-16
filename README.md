# Zubi-Client

Pleos Connect(Android Automotive OS)용 Zubi 클라이언트 앱입니다.

## 스택

| 항목 | 버전 |
| --- | --- |
| Gradle / AGP | 8.10.2 / 8.8.2 |
| Kotlin | 2.0.21 (Jetpack Compose) |
| compileSdk / targetSdk / minSdk | 35 / 35 / 28 (Pleos 요구사항: API 28 이상) |
| JDK | 17 또는 21 |
| Pleos Vehicle SDK | `ai.pleos.playground:Vehicle:2.3.2` |
| Pleos NaviHelper SDK | `ai.pleos.playground:NaviHelper:3.0.5` (주석 처리됨) |

버전은 `gradle/libs.versions.toml`에서 관리합니다. Pleos SDK는 `settings.gradle.kts`에 등록된
`https://nexus-playground.pleos.ai/repository/maven-releases/` 저장소에서 받습니다.

## 구조

```
app/src/main/java/com/zubi/client/
├── ZubiApplication.kt       # Vehicle SDK 초기화/해제
├── MainActivity.kt          # Compose 진입 화면
├── ui/MainViewModel.kt      # 기어 상태 → UI 상태
├── ui/theme/Theme.kt
└── vehicle/VehicleManager.kt # Pleos Vehicle SDK 래퍼
```

## 실행

1. Android Studio에서 이 폴더를 열고 Gradle JDK를 17 또는 21로 설정합니다.
2. SDK Manager에서 Pleos Connect 에뮬레이터 이미지를 설치하고 AVD를 만듭니다.
3. `app` 구성을 실행합니다.

CLI 빌드:

```bash
JAVA_HOME=$(/usr/libexec/java_home -v 17) ./gradlew assembleDebug
```

## 참고

- 에뮬레이터에서 로컬 서버(`localhost:8000`)는 `BuildConfig.API_BASE_URL` = `http://10.0.2.2:8000`으로 접근합니다.
- Vehicle API를 추가로 쓸 때는 `AndroidManifest.xml`에 `pleos.car.permission.*` 권한을 추가하세요
  (전체 목록: `ai.pleos.playground.vehicle.constant.PleosPermission`).
- 주행 중 표시가 필요하면 `MainActivity`의 `distractionOptimized` meta-data를 활성화하고 UX 제한을 직접 처리해야 합니다.
