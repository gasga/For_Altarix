d:
rem CHANGE HERE!!!!!!!!!!
cd gas\gas\Mobile_Soft\Android\MyAppFold\AltarixTaskCLI

set JAVA_HOME=D:\gas\D\jdk
set ANDROID_HOME=D:\gas\D\sdk
set DEV_HOME=%CD%

set AAPT_PATH=%ANDROID_HOME%/build-tools/22.0.1/aapt.exe
set DX_PATH=%ANDROID_HOME%/build-tools/22.0.1/dx.bat
set ANDROID_JAR=%ANDROID_HOME%/platforms/android-22/android.jar
set ADB=%ANDROID_HOME%/platform-tools/adb.exe

rem CHANGE HERE!!!!!!!!!!
set PACKAGE_PATH=ru/gostkovas/task
rem CHANGE HERE!!!!!!!!!!
set PACKAGE=ru.gostkovas.task
set MAIN_CLASS=MainActivity

call %AAPT_PATH% package -f -m -S %DEV_HOME%/res -J %DEV_HOME%/src -M %DEV_HOME%/AndroidManifest.xml -I %ANDROID_JAR%


call %JAVA_HOME%/bin/javac -d %DEV_HOME%/obj -cp %ANDROID_JAR% -sourcepath %DEV_HOME%/src %DEV_HOME%/src/%PACKAGE_PATH%/*.java

call %DX_PATH% --dex --output=%DEV_HOME%/bin/classes.dex %DEV_HOME%/obj

call %AAPT_PATH% package -f -M %DEV_HOME%/AndroidManifest.xml -S %DEV_HOME%/res -I %ANDROID_JAR% -F %DEV_HOME%/bin/AndroidTest.unsigned.apk %DEV_HOME%/bin

call %JAVA_HOME%/bin/keytool -genkey -validity 10000 -dname "CN=AndroidDebug, O=Android, C=US" -keystore %DEV_HOME%/AndroidTest.keystore -storepass android -keypass android -alias androiddebugkey -keyalg RSA -v -keysize 2048
call %JAVA_HOME%/bin/jarsigner -sigalg SHA1withRSA -digestalg SHA1 -keystore %DEV_HOME%/AndroidTest.keystore -storepass android -keypass android -signedjar %DEV_HOME%/bin/Task.apk %DEV_HOME%/bin/AndroidTest.unsigned.apk androiddebugkey