# Capella
Silver Memory?

## Short description
Capella is an Android project designed for users to record and view their memories/moments through daily journaling.
The journal entry also accepts a field for wholesome moment that the user experienced that day, whereby the user can 
access a curated list of wholesome moments to uplift their mood.

The app also allows users to input their daily mood, and be able to view their mood trends over time through visualizations.

The home screen displays a motivational quote which refreshes each time one goes back to the home screen. 
This is achieved through integration with a quotes API (https://zenquotes.io/).

This project is built using Kotlin and Android Jetpack components, following modern Android development practices including MVVM architecture, LiveData, and Room for local data storage.

### Features
- Daily journaling with text input for memories and wholesome moments.
- Mood tracking with visualizations of mood trends over time.
- Motivational quotes on the home screen fetched from an external API.
- Viewing past journal entries and wholesome moments, organised by date 
- User-friendly interface with intuitive navigation.

### Prerequisites
- Android Studio (latest stable)
- JDK 11+ (or the version required by your Android Gradle Plugin)
- Android SDK components (installed via Android Studio)
- Gradle (wrapper included; no global install required)

### Quick start
1. Clone the repository an open the project in Android Studio
   - Choose "Open an existing Android Studio project" and select the project's root folder.
2. Let Android Studio sync Gradle and download dependencies.
3. Run on a device or emulator
   - Create or start an AVD, then click Run ▶ in Android Studio, or use:
     ./gradlew installDebug


Project structure (high level)
- app/               — Android application module
- build/             — build outputs (generated)
- gradle/            — Gradle related config (wrapper)
- README.md          — this file


#### Developed with Love by Jena <3