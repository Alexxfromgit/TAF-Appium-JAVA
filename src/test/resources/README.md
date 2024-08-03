#Guide

Download this example with:

```shell
git clone git@github.com:Alexxfromgit/apj_automation_framework.git
```

#### Step 1 - Prepare new virtual environment

Create new virtual device via Android Studio using AVD Manager:

##### 1.1 Create new virtual device

![Create new virtual device](instructions/avd_manager.PNG)

##### 1.2 Check new virtual device

Check all available devices for emulation from command prompt on "%\Android\Sdk\tools":

```shell
emulator -avd -list-avds
```

Check all available devices from command prompt on "%\Android\Sdk\tools\bin":

```shell
avdmanager list device
```

##### 1.3 Run new virtual device

```shell
emulator -avd "Nexus_5X_API_26_x86"
```

![Run new virtual device](instructions/run_device.PNG)

#### Step 2 - Get device information

##### 2.1 Get device name and platform version

```properties
android.platform.version=7.1.1
android.device.name=emulator-5554
```

Get this capabilities from another command prompt via ADB on "%":

```shell
adb shell getprop ro.build.version.release
```
```shell
adb devices
```

And if needed use this command to define all needed capabilities:

```shell
adb shell getprop
```

#### Step 3 - Start Appium

![Start Appium](instructions/appium.PNG)

##### NOTE: If needed to inspect apk elements use min set of desired capabilities to start new session:

```json
{
  "platformName": "Android",
  "platformVersion": "7.1.1",
  "app": "\\src\\test\\resources\\Mentoring.apk",
  "deviceName": "emulator-5554",
  "automationName": "UiAutomator2"
}
```

#### Step 4 - Run your written tests
