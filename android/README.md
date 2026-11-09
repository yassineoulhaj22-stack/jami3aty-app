# Jami3ty - Android Native App

Complete production-ready source code generated.

## Structure
android/
  settings.gradle
  build.gradle
  gradle.properties
  gradle/wrapper/gradle-wrapper.properties
  gradlew, gradlew.bat
  app/
    build.gradle
    proguard-rules.pro
    src/main/
      AndroidManifest.xml
      java/com/jami3ty/app/Jami3tyApp.kt
      kotlin/com/jami3ty/app/
        ui/login/LoginActivity.kt
        ui/pin/SetPinActivity.kt
        ui/main/MainActivity.kt
        ui/home/HomeFragment.kt
        ui/discover/DiscoverFragment.kt
        ui/inbox/InboxFragment.kt
        ui/reels/ReelsFragment.kt
        ui/profile/ProfileFragment.kt
        data/repo/FirestoreRepo.kt
      res/
        values/ colors.xml strings.xml themes.xml styles.xml
        values-ar/ strings.xml
        values-fr/ strings.xml
        layout/ activity_login.xml activity_main.xml activity_set_pin.xml fragment_home.xml fragment_reels.xml fragment_profile.xml reel_item.xml
        menu/ bottom_nav_menu.xml
        xml/ network_security_config.xml
        raw/ success.json
      google-services.json (injected via GitHub Secret)

## Features
- Min SDK 21 / Target SDK 34
- BiometricPrompt with PIN fallback (EncryptedSharedPreferences)
- Purple Material3 UI, bottom navigation with micro-interactions
- Lottie success animations
- RTL Arabic, French, English i18n
- ViewPager2 Reels with Share intent
- Firestore repository pattern
- GitHub Actions build workflow

## Build
./gradlew assembleRelease

## CI
.github/workflows/build_apk.yml builds on push to main, uses secret GOOGLE_SERVICES_JSON (base64), uploads APK artifact.

Download APK: GitHub → Actions tab → latest run → Artifacts → jami3ty-release-apk
