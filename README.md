# GitHub Android

## Build

* Create **gradle.properties** file in `$USER_HOME/.gradle` directory - if it does not exist yet.
* Create the following keys in **gradle.properties** file:
  * `GITHUB_USERNAME` username for GitHub account.
  * `GITHUB_PASSWORD` password for GitHub account or in case you have MFA enabled use a
  [personal access token](https://github.com/settings/tokens) as password.

Finally build the project with:

```
./gradlew assemble assembleAndroidTest
```

## Test

### Unit Tests

```
./gradlew test
```

### Android Tests

First make sure an Android device or emulator is currently connected to your machine.

```
./gradlew connectedAndroidTest
```

## Architecture

### Clean Architecture

The architecture of the application was inspired by **Clean Architecture**. There are no **Enterprise Business Rules** layer, and the communication between the **Interface Adapters** layer and the **Business Rules** layer is done using shared POJOs (e.g. Repository). This simplifies the communication without violating the **Dependency Rule**, since there is no need for multiple structures to pass data between the layers. These adaptations are specially important considering the memory and performance contraints of a mobile application. Also once the application grows, changes can be made to comply with **Clean Architecture**, if it makes sense.

### MVC

The application uses a MVC pattern where the Fragments belong to Controller and View layers. Since the Model layer encapsulates all the business logic of the application it is easy to change the pattern to MVP or MVVM. Actually the goal is to have MVP or MVVM dependening on the characteristics of each view. MVP could have been used to present the repository list, but since the presentation logic was quite simple and was covered by Unit tests using Espresso, for now MVC seem to be the best choice.

### Reactive

Every action in **Business Rules** layer results in a reaction through the callbacks. **Business Rules** objects are associated with Android Application instance, therefore the configuration changes (e.g. orientation changes) that affect the views don't affect the use cases. 

### Hard Dependencies

#### RxJava

Used for multithreading only. Observables are not exposed to outer layers, instead simple callbacks are used. It would be possible to abstract this dependency, but it would make it hard to use all the features of RxJava, specially its testing support. 

### Abstracted Dependencies

#### RxAndroid

Needed only in app module to use Android schedulers in order to deal with the main thread. Since app module is already in **Frameworks and Drivers** layer this is not considered a hard dependency.

#### OkHttp

Retrofit was not used, because all requests are synchronous, multithreading is controlled by **Business Rules** layer, also JSON deserialization is done using default Java libraries. Retrofit big adavantage is to package deserialization and multithreading with annotation processing. But the usage of JSON deserialization libraries like Moshi or Gson bring a overhead of annotatted POJO classes. Since those POJO classes are annotated it is not possible to use them to communication between **Interface Adapters** layer and the **Business Rules** layer without compromissing the **Dependency Rule**.

## Future Development

### GitHub Login

Currently the application has hardcoded credentials to access GitHub APIs. It is not fit for production, since it would leak sensitiive data. With a login screen the credentials could be obtained in runtime without leaking sensitive data.

### UI Improvements

The repository detail screen has the same information as the cell in repository list screen. The UI can be improved by displaying more information about the repository.











#### 

