# Firestore maps documents to these classes via reflection (toObject / toObjects).
# Without these rules R8 renames/strips fields and release builds read empty data.
-keepclassmembers class com.jami3ty.app.data.model.** {
    <init>();
    <fields>;
    <methods>;
}
-keep class com.jami3ty.app.data.model.** { *; }
-keepattributes Signature,*Annotation*,InnerClasses,EnclosingMethod

# Firebase and Lottie ship their own consumer rules; silence known harmless warnings.
-dontwarn com.google.firebase.**
-dontwarn com.airbnb.lottie.**
-dontwarn com.google.zxing.**
