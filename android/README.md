# Jami3ty - Android app

Student platform: register, log in, publish reports, browse services, and show your student barcode.

## Requirements
- JDK 17 (CI uses Temurin 17) or Android Studio
- A Firebase project with an Android app for package `com.jami3ty.app`
  - Enable **Authentication > Email/Password**
  - Create a **Firestore** database and publish `firestore.rules` (in the repo root: firestore.rules)

## Setup
1. Download `google-services.json` from the Firebase console.
2. Put it at `android/app/google-services.json` for local builds.
   For CI, store it base64-encoded in the GitHub secret `GOOGLE_SERVICES_JSON`:
   `base64 -w0 google-services.json`

## Build
    cd android
    ./gradlew assembleRelease     # app/build/outputs/apk/release/
    ./gradlew assembleDebug       # app/build/outputs/apk/debug/

The release build is signed with the debug key so it can be sideloaded.
Use a real release keystore before publishing to the Play Store.

## CI
`.github/workflows/build_apk.yml` runs on pushes and PRs to main/master.
It fails with a clear message if `GOOGLE_SERVICES_JSON` is missing.
The APK is uploaded as the artifact `jami3ty-release-apk`.

## Screens
- Login (email/password), biometric unlock for accounts that enabled it
- Register (name, university, email, password)
- Home: all published reports (newest first), with a Lottie empty state
- `+`: publish a report (Lottie success animation)
- Discover: student services
- My reports (Inbox): only your reports
- Profile: details, Code 128 student barcode, biometric toggle, logout

## Structure
- `data/model` - Student, Report
- `data/repo` - Firebase Auth (AuthRepo), Firestore (FirestoreRepo)
- `data/session` - encrypted local session (EncryptedSharedPreferences)
- `ui/*` - screens; `ui/common` - shared list, biometrics, barcode helpers

Languages: English, French, Arabic (RTL supported).
