# DigiPet: A Tamagotchi-inspired Android App
# Level 4 Individual Project DigitalPet

DigiPet is a mobile application simulating a digital pet with features such as:
- Fitness tracking
- Puzzle minigames
- Pet evolution and animations
- Happiness Minigames

## Prerequisites
Software
- **Android Studio:** Ladybug (2024.2.1) or later recommended
- **Build System:** Gradle 8.0+ with Kotlin 2.0.0
- **Java Runtime:** JDK 11.

### Android Device / Emulator
- **Minimum SDK:** 33 (Android 13.0)
- **Target/Compile SDK:** 36
- **Target Android Version:** 13
- **Hardware:** A physical device is recommended for the best performance and experience with sensor-based features.

## Project Dependencies
- **Jetpack Compose (BOM 2024.09.00):** Core UI framework using Material 3 and Foundation for layouts and animations
- **Room Persistence (v2.8.4):** SQLite database used to store pet and fitness history
- **DataStore Preferences:** Lightweight storage for pet stats (Hunger, health)
- **WorkManager:** Handles background logic for pet needs
- **Kotlin Serialization:** Used for JSON data-handling, for exporting data.
- **ViewModel & LiveData:** Manages UI state and reactive data flow.

## Setup Instructions
1. Extract the ZIP file
2. Open the project in Android Studio
3. Build the project
    - Android Studio will automatically download all required dependencies.
    - Ensure Gradle sync completes without errors.
4. Run the app
    - Connect a physical Android Device (Android 8.0 / API 26 or higher) or start an emulator supporting API 26+
    - Click **run** in Android Studio to install and launch the app
5. Usage Notes
    - No internet connection is required
    - For best performance, use a device running Android 11+
