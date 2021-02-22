# Weather_forecast

To introduce the terminology, here is a short introduction to the Wather app with Architecture Components and how they work together. 
Note that this focuses on a subset of the components, namely LiveData, ViewModel and Room/Sqlite
Each component is explained more as you use it.

# To achieve this

 - Designed MainActivity: Display the data from weather API
 - Design WeatherListFragment for display lis of weather from forecast API
 - Designed MapActivity: Add markers on Map
 - Designed WeatherviewModel and ForecastViewModel api call respectivelly.
 
 - Use MVVM architectruce pattern with Coroutine

 - Used android jetpack componanat
 
 - Designed Location database.
 
 - Designed WeatherviewModel: Acts as a communication center between the Repository (data) and the UI. 
 The UI no longer needs to worry about the origin of the data. 
 ViewModel instances survive Activity/Fragment recreation.
 
  - Designed ForecastAdapater: Display the data in list format from forecast api.

# Libraries & Dependency 


# Room components addedd dependencies
 
     implementation "androidx.room:room-runtime:$rootProject.roomVersion"
     kapt "androidx.room:room-compiler:$rootProject.roomVersion"
     implementation "androidx.room:room-ktx:$rootProject.roomVersion"
     androidTestImplementation "androidx.room:room-testing:$rootProject.roomVersion"

# Lifecycle components

    implementation "androidx.lifecycle:lifecycle-extensions:$rootProject.archLifecycleVersion"
    kapt "androidx.lifecycle:lifecycle-compiler:$rootProject.archLifecycleVersion"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.archLifecycleVersion"

# Kotlin components

    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:$rootProject.coroutines"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.coroutines"

# Material design

    implementation "com.google.android.material:material:$rootProject.materialVersion"
    
# Retrofit for network communication 

    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.1'
    
# UI development 
 fragment & Recyclerview, Activity, SVG images. 

# Layouts used 

  Linear, Relative, Constraint, Co-Ordinate.

# Testing

    testImplementation 'junit:junit:4.12'
    androidTestImplementation "androidx.arch.core:core-testing:$rootProject.coreTestingVersion"
    androidx.test.ext:junit:1.1.1
    androidx.test.espresso:espresso-core:3.2.0
   

# Setup the project in Android studio and run project.

1) Download the project code, preferably using git clone.

2) In Android Studio, select File | Open... and point to the ./build.gradle file.
