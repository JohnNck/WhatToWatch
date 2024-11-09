# What to Watch

**What to Watch** is a movie and TV discovery app that helps users find something to watch by connecting to **The Movie Database (TMDb) API**. Built using **MVVM architecture**, **Retrofit** for API integration, and **Dagger Hilt** for dependency injection, the app provides users with a seamless experience for discovering popular, upcoming, and newly released movies and TV series.

With additional features like search by genre and keyword, users can easily explore and discover new content to enjoy.

---

## 🎬 Features

- **Movie and TV Discovery**: Browse popular, upcoming, and newly released movies and TV series.
- **Search Functionality**: Search content by genre or keyword to easily find what you're looking for.
- **Details Page**: View detailed information about movies/TV series including ratings, trailer, and summaries.
- **MVVM Architecture**: Ensures separation of concerns and clean code for easier maintenance and testing.
- **Retrofit API Integration**: Efficiently fetches movie and TV data from **The Movie Database (TMDb) API**.
- **Dagger Hilt for Dependency Injection**: Simplifies and manages dependencies across the app.

---

## 🚀 Installation

Follow these steps to set up the project locally:

### 1. **Clone the repository**

Clone the repository using Git:

```bash
git clone https://github.com/yourusername/WhatToWatch.git

2. Open the project
Open the project in Android Studio. Ensure you have the latest version of Android Studio installed.

3. Sync Gradle dependencies
Android Studio will automatically sync the Gradle dependencies. If not, click Sync Now in the top toolbar.

4. Run the app
To run the app, connect your Android device or use an emulator, and click the Run button in Android Studio.

🛠️ Technologies Used
Kotlin: The primary programming language for Android development.
MVVM Architecture: Separates concerns and enhances code testability and maintainability.
Retrofit: A type-safe HTTP client for making API requests to The Movie Database (TMDb).
Dagger Hilt: For dependency injection, simplifying dependency management in the app.
TMDb API: Provides movie and TV show data for the app.

📱 App Structure
The app follows MVVM (Model-View-ViewModel) architecture to separate business logic, UI logic, and data:

Model
Movie and TV Show Entities: Represent the data model for movies and TV shows with attributes like title, overview, release date, ratings, and more.
API Service: Handles API requests using Retrofit to fetch movie and TV show data from TMDb.
View
Main Activity: Displays the main screen with popular, upcoming, and newly released content.
Search Functionality: Allows users to search for movies and TV shows by keyword or genre.
Detail Screen: Displays detailed information about a selected movie/TV show.
ViewModel
LiveData: Observes data changes and updates the UI reactively, ensuring the UI stays in sync with the app's state.
Use Cases/Repository: Encapsulates the logic for fetching data and provides it to the ViewModel.

🎥 How to Use
1. Browse Content
The main screen displays Popular Movies, Best Movies, Upcoming Movies, and Newly Released Movies.
You can scroll through the content and pages to select a movie for more details.
And on the Tv series screen you can see the Now Playing, Best Series and popular series.
having the same options as in the movies screen.

2. Search for Movies/TV Shows
Use the search functionality to find movies or TV shows by genre or keyword.
Enter the search terms in the search bar and view the results.

3. View Details
Tap on any movie or TV show to view detailed information, including:
Title
Overview
Ratings
Poster Image
Trailer

🛠️ Setup Instructions
To run this app locally, follow these steps:
Clone the repository and open it in Android Studio.
Sync the project with Gradle.
Run the app on your emulator or device.

📄 License
This project is licensed under the MIT License. See the LICENSE file for more information.

📝 Acknowledgments
The Movie Database (TMDb) for providing an extensive database of movies and TV shows.
Android Documentation for providing useful resources and examples.
Retrofit for providing an easy way to make network calls and handle API responses.
Dagger Hilt for simplifying dependency injection in the app.

🐞 Troubleshooting
If you encounter any issues while using the app, here are some solutions:
Permissions: Ensure that your app has the necessary permissions to access the internet and fetch data.
API Key Issues: Double-check that your TMDb API key is correctly configured in strings.xml.
App Crashes: Check Logcat for detailed error logs and create an issue in the repository with the error message.
