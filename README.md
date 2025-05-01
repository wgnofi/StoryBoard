# Storyboard App

An Android application for creating and saving storyboards.

## Overview

This app allows users to create storyboards by adding panels. Each panel can contain:

* **Image:** Selected from the device's photo gallery.
* **Text:** A description or note for the panel.

Storyboards and their panels are saved locally using Room Persistence Library. This allows users to create a storyboard, close the app, and find their work saved when they return.

## Key Features

* **Create Storyboards:** Start new storyboards with a custom name.
* **Add Panels:** Easily add new panels to your storyboard.
* **Image Selection:** Select images for each panel directly from your device's gallery using Photo Picker.
* **Text Notes:** Add descriptive text to each panel.
* **Local Saving:** Storyboards and panel data (including image URIs and text) are saved locally using Room database, ensuring persistence across app sessions.
* **Image Display:** Uses Coil library's `AsyncImage` to efficiently display selected images.

## Technologies Used

* **Kotlin:** The primary programming language.
* **Jetpack Compose:** Modern UI toolkit for building native Android UIs.
* **Room Persistence Library:** For local data storage and management.
* **AndroidX Activity Result API (Photo Picker):** For selecting images from the device.
* **Coil:** For asynchronous image loading and display.
* **Kotlin Coroutines:** For managing asynchronous operations.
* **KSP (Kotlin Symbol Processing):** For Room annotation processing.

## Setup Instructions

1.  **Clone the repository:**
    ```
    
    git clone https://github.com/wgnofi/StoryBoard
    
    ```
2.  **Open in Android Studio:** Open the cloned project in Android Studio.
3.  **Build and Run:** Build and run the application on an emulator or a physical Android device.

## How to Use

1.  **Create a New Storyboard:** Look for a button or option to create a new storyboard and give it a name.
2.  **Add Panels:** On the storyboard screen, there should be an option to add a new panel.
3.  **Select Image:** When adding a panel, you'll be prompted to select an image from your device's gallery.
4.  **Add Text:** You can also add a text description to the panel.
5.  **Save:** The app automatically saves your changes locally as you work. You can navigate away and return to find your storyboard in its saved state.

## Screenshots
![Screenshot_20250501_181342](https://github.com/user-attachments/assets/861a70ae-bf31-4624-92ec-440fb19968ec)
![Screenshot_20250501_181350](https://github.com/user-attachments/assets/2be62846-fff8-4294-97a0-f9c8ba1a42bf)
![Screenshot_20250501_181231](https://github.com/user-attachments/assets/5e35e185-f7c5-4199-9bb6-a310f9557e75)
![Screenshot_20250501_181422](https://github.com/user-attachments/assets/adbba9a2-6cc9-47b6-94c7-7967427ecf2c)
![Screenshot_20250501_181517](https://github.com/user-attachments/assets/a5c620ba-1392-4ca0-80fb-72e6536071c7)
![Screenshot_20250501_181526](https://github.com/user-attachments/assets/71f08993-7380-430d-9e1f-12d699a67738)
![Screenshot_20250501_181535](https://github.com/user-attachments/assets/7928a583-a6fd-465a-9974-c519fc9d22c7)

## Note

* This app saves the URI of the selected images. It uses `takePersistableUriPermission()` to ensure that the app can still access the images even after it's restarted.

## Contributing

Feel free to add more features, open to contributions!

## License

MIT License
