### How to launch Selendroid
#### Step 1) Getting an application under test

You can use existing Selendroid test app to check that how Selendroid works

Once a download is complete, copy this APK and the above Selendroid Standalone jar file to a folder with the name resources/selendroid

#### Step 2) Launch the Selendroid

Open the terminal on Windows & navigate to the folder Guru99 created in step 1.

Run the following command

```shell
java -jar selendroid-standalone-0.11.0-with-dependencies.jar
```

After running this command, Selendroid-standalone HTTP server starts! The default port number of this server is 4444. All hardware device, as well as Android Virtual Device, will be scanned and recognized automatically. Selendroid will identify the Android target version and device screen size.

To check the Android target version as well as the device information, you can launch the following URL on a browser:

```html
http://localhost:4444/wd/hub/status
```

### Selendroid basic command

This section introduces you some basic Selendroid-Standalone command line. You may use them to set up the Selendroid testing environment

#### 1. Setting port of Selendroid
The default port of Selendroid is 4444. But you can change to other port by adding a parameter to the command to launch Selendroid

Parameter: -port [port number]

```shell
java -jar selendroid-standalone-0.11.0-with-dependencies.jar -port 5555
```

In above command, 5555 is the new port.

So the URL to check the Android target version is changed to: http://localhost:5555/wd/hub/status

#### 2. Specify the location of the application under test (Binary APK file).
Selendroid often required the absolute path for this file

Parameter: -app [file path]

```shell
java -jar selendroid-standalone-0.11.0-with-dependencies.jar -app "C:\selendroid-test-app-0.12.0.apk"
```

In above command, the Selendroid automatically find the binary file base on the "C:\Guru99App.apk" to get the information of the application under test.

#### 3. Change the port the Selendroid uses to communicate with instrumentation server.
Selendroid uses the port 8080 as the default

Parameter: -selendroidServerPort [port number]

```shell
java -jar selendroid-standalone-0.11.0-with-dependencies.jar -selendroidServerPort 9000
```

#### 4. Change the timeout to start emulators.
The unit is milliseconds.

Parameter: -timeoutEmulatorStart

By default, Selendroid will wait 300,000 milliseconds until the emulator starts. You can change to new timeout (200,000 ms) by command

```shell
java -jar selendroid-standalone-0.11.0-with-dependencies.jar -timeoutEmulatorStart 200000
```

After this time our expired, if the emulator cannot start, the Selendroid will throw the exception error (Error occurred while looking for devices/emulators.) then stop running

#### 5. When you start the Selendroid command on terminal, you will see a log printed out on the screen.
You can change the type of log you see by adding the following parameter
Parameter: -logLevel [type of log]

The log level values are ERROR, WARNING, INFO, DEBUG, and VERBOSE. Default: ERROR.

For example, set Selendroid to print the WARNING log only, you can use this command

```shell
java -jar selendroid-standalone-0.11.0-with-dependencies.jar -logLevel WARNING
```
