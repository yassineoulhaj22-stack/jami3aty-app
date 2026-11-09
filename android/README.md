# Jami3ty - Android app

## Requirements
- Android Studio or JDK 17 (CI uses Temurin 17)
- A Firebase project with an Android app for `com.jami3ty.app`

## Setup
1. Download `google-services.json` from the Firebase console.
2. Put it at `android/app/google-services.json` for local builds.
   For CI, base64-encode it and store it in the GitHub secret `GOOGLE_SERVICES_JSON`
   (`base64 -w0 google-services.json`).

## Build
    cd android
    ./gradlew assembleRelease        # APK: app/build/outputs/apk/release/
    ./gradlew assembleDebug          # APK: app/build/outputs/apk/debug/

The release build is signed with the debug key so it can be sideloaded.
Use a real release keystore before publishing to the Play Store.

## CI
`.github/workflows/build_apk.yml` runs on pushes and PRs to main/master.
It fails with a clear message if `GOOGLE_SERVICES_JSON` is missing. The APK is
uploaded as the artifact `jami3ty-release-apk` (Actions tab, latest run).

## Structure
- `ui/login` - biometric login, falls back to PIN setup
- `ui/pin` - 4-digit PIN stored in EncryptedSharedPreferences
- `ui/main` - bottom navigation hosting the tab fragments
- `ui/home`, `ui/discover`, `ui/inbox`, `ui/profile`, `ui/reels` - tab screens (placeholders for now)
- `data/repo` - Firestore access
- Languages: English, French, Arabic (RTL supported)

## Known gaps
- Tab screens are placeholders; no real content yet.
- Login does not verify an existing PIN; without biometrics it always goes to PIN setup.
- Not compiled in the environment where these fixes were made; run CI to confirm.
