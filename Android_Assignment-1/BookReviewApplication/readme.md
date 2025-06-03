# Book Review App

A simple Android application (Java) that allows users to browse a list of books, view detailed information, and save favorites for offline access. It uses MVVM architecture, manual JSON parsing (Gson), Room (SQLite) for persistence, and LiveData for reactive UI updates.

---

## Table of Contents

1. [Features](#features)  
2. [Architecture](#architecture)  
3. [Tech Stack](#tech-stack)  
4. [Screenshots](#screenshots)  
5. [Getting Started](#getting-started)  
   - [Prerequisites](#prerequisites)  
   - [Project Directory Structure](#project-directory-structure)  
   - [Setup & Run](#setup--run)  
6. [JSON Data](#json-data)  
7. [Room Persistence](#room-persistence)  
8. [Demo](#demo)  

---

## Features

- **Book List Screen**
  - Loads ten books from a local JSON file (`assets/books.json`).
  - Displays title, author, and thumbnail.
  - Scrollable list via `RecyclerView`.

- **Book Detail Screen**
  - Shows full description, rating, and a larger cover image.
  - “Add to Favorites” / “Remove from Favorites” button that toggles based on state.

- **Favorites (Bookmark) Functionality**
  - Bookmark icon in the top‐right toolbar toggles between outline and filled states.
  - When tapped (online), shows only favorited books.
  - When tapped again (online), returns to full book list.
  - If favorites list is empty, displays a gray “Your bookmark list is empty” message.

- **Offline Mode**
  - On startup, if no network detected, shows an offline placeholder:
    - Message: “You are currently offline”
    - Button: “Go to Favorites” (navigates to favorites list if any exist)
    - Bookmark icon still present: tapping it returns to offline placeholder if offline.

---

## Architecture

This project follows **MVVM (Model-View-ViewModel)** with a clear separation of concerns:

```text
┌───────────────────────────────────────────────────┐
│ View Layer                                        │
│ (Activities, Adapters, XML Layouts, Resources)    │
│ • BookListActivity                                │
│ • BookDetailActivity                              │
│ • RecyclerView + Adapter                          │
└───────────────────────────────────────────────────┘
▲ ▲
│ │
▼ ▼
┌───────────────────────────────────────────────────────────┐
│ ViewModel Layer                                           │
│ • BookListViewModel                                       │
│ • BookDetailViewModel                                     │
│ • Exposes LiveData<List<Book>> and LiveData<FavoriteBook> │
└───────────────────────────────────────────────────────────┘
▲ ▲
│ │
▼ ▼
┌───────────────────────────────────────────────────┐
│ Repository Layer                                  │
│ • BookRepository (interface)                      │
│ • BookRepositoryImpl (implements repo logic)      │
│ – Loads JSON from assets/books.json via Gson      │
│ – Manages Room DAO for favorites                  │
└───────────────────────────────────────────────────┘
▲ ▲
│ │
▼ ▼
┌───────────────────────────────────────────────────┐
│ Data Layer                                        │
│ • Data Model: Book.java                           │
│ • Local Entity: FavoriteBook.java                 │
│ • Room DAO: FavoriteBookDao.java                  │
│ • Room Database: AppDatabase.java                 │
│ • Local JSON: books.json (in /assets)             │
└───────────────────────────────────────────────────┘
```

- **View Layer**:  
  - `BookListActivity` displays the list or placeholder views.  
  - `BookDetailActivity` shows details and “Add/Remove Favorite” button.

- **ViewModel Layer**:  
  - `BookListViewModel` exposes:
    - `LiveData<List<Book>> getAllBooks()`
    - `LiveData<List<FavoriteBook>> getFavoriteBooks()`
  - `BookDetailViewModel` fetches a single `Book` by ID and checks if favorited.

- **Repository Layer**:  
  - `BookRepositoryImpl` handles:
    - Parsing `assets/books.json` via Gson into `List<Book>`.  
    - Interacting with Room (inserting/deleting favorites).  

- **Data Layer**:  
  - Plain Java classes for `Book` and `FavoriteBook` (annotated for Room).  
  - DAO and Room Database boilerplate for SQLite persistence.

---

## Tech Stack

- **Language**: Java   
- **Networking/Parsing**:  
  - Gson (manual JSON parsing from assets)  
- **Persistence**:  
  - Room (SQLite) for favorites  
- **UI**:  
  - RecyclerView for lists  
  - ConstraintLayout for item/detail screens  
  - LiveData + ViewModel (Android Architecture Components)  
  - Material Components (Toolbar, Button)  
- **Offline Detection**:  
  - `ConnectivityManager.getActiveNetworkInfo()` with `ACCESS_NETWORK_STATE` permission  

---

## Screenshots

1. **Book List UI (Online)**    

<p align="center">
<img src="https://github.com/user-attachments/assets/3b6d7b46-4aaa-41be-8ea7-49e6d45d398f" width="360" alt="Book List UI Online" />
</p>

2. **Book List UI (Offline)**   

<p align="center">
<img src="https://github.com/user-attachments/assets/0c2787d5-8cfb-45d7-8b96-74c84e71c665" width="360" alt="Book List UI Offline" />
</p>

3. **Empty Favorites**  

<p align="center">
<img src="https://github.com/user-attachments/assets/f6261d93-9e17-41f4-b158-ddf14521c016" width="360" alt="Empty Favorites" />
</p>

4. **Book Detail UI**  

<p align="center">
<img src="https://github.com/user-attachments/assets/e741fe90-d0fb-4a96-be7f-802048830c29" width="360" alt="Book Detail UI" />
</p>

5. **Favorites List UI**  

<p align="center">
<img src="https://github.com/user-attachments/assets/20809d07-e8b1-4075-8fc7-cfd47db7ebe3" width="360" alt="Bookmarks List UI" />
</p>

---

## Getting Started

### Prerequisites

- Android Studio (Arctic Fox or later recommended)  
- JDK 11 (or higher) installed  
- Android SDK Platform 33 (or match `compileSdkVersion`)

### Project Directory Structure

```text
BookReviewApp/
 ├── app/
 │    ├── src/
 │    │    ├── main/
 │    │    │    ├── assets/
 │    │    │    │    └── books.json
 │    │    │    ├── java/com/example/bookreviewapp/
 │    │    │    │    ├── data/
 │    │    │    │    │    ├── api/BookApiService.java
 │    │    │    │    │    ├── local/
 │    │    │    │    │    │    ├── AppDatabase.java
 │    │    │    │    │    │    ├── dao/FavoriteBookDao.java
 │    │    │    │    │    │    └── entity/FavoriteBook.java
 │    │    │    │    │    └── model/Book.java
 │    │    │    │    ├── repository/
 │    │    │    │    │    └── BookRepositoryImpl.java
 │    │    │    │    │    └── BookRepository.java
 │    │    │    │    ├── viewmodel/
 │    │    │    │    │    ├── BookListViewModel.java
 │    │    │    │    │    └── BookDetailViewModel.java
 │    │    │    │    └── ui/
 │    │    │    │         ├── booklist/
 │    │    │    │         │     ├── BookListActivity.java
 │    │    │    │         │     └── BookListAdapter.java
 │    │    │    │         └── bookdetail/
 │    │    │    │               └── BookDetailActivity.java
 │    │    │    └── res/
 │    │    │         ├── layout/
 │    │    │         │    ├── activity_book_list.xml
 │    │    │         │    ├── item_book_list.xml
 │    │    │         │    └── activity_book_detail.xml
 │    │    │         ├── drawable/
 │    │    │         │    └── ic_book_placeholder.xml
 |    |    |         |    |__ ic_baseline_bookmark_24.xml
 |    |    |         |    |__ ic_baseline_bookmark_border_24.xml
 │    │    │         └── menu/
 │    │    │              └── menu_book_list.xml
 │    │    └── AndroidManifest.xml
 │    └── build.gradle   (Module: app)
 ├── build.gradle       (Project)
 └── settings.gradle
```


### Setup & Run

1. **Clone the repository**  

   ```bash
   git clone https://github.com/Susmm/Android_Assignment-1.git
   cd Android_Assignment-1/BookReviewApplication

3. **Open in Android Studio**

   - Launch Android Studio → **Open an Existing Project** → select the cloned folder.
   - Wait for Gradle to sync.

4. **Ensure Permissions**

   - In `AndroidManifest.xml`, verify:
   
   ```bash
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   ```

6. **Build & Run**

   - **Clean Project** → **Rebuild Project**.
   - Connect an emulator or physical device (API 21+).
   - Click Run ▶.
  
### JSON Data

   All book data is stored locally in:

   ```bash
   app/src/main/assets/books.json
   ```

   This file contains an array of 10 book objects, each with:
   - `id` (integer)
   - `title` (string)
   - `author` (string)
   - `thumbnailUrl` (string URL)
   - `description` (string)
   - `rating` (float)
   - `imageUrl` (string URL)

   On app startup (if online), `BookRepositoryImpl` reads and parses this file using Gson into a `List<Book>`. The `RecyclerView` adapter then displays these entries.

### Room Persistence

   - **Entity**: `FavoriteBook` (maps one‐to‐one with `Book`)

   - **DAO**: `FavoriteBookDao` provides:
        
        - `LiveData<List<FavoriteBook>> getAllFavorites()`
        - `LiveData<FavoriteBook> getFavoriteById(int bookId)`
        - `@Insert` and `@Delete` methods

   - **Database**: `AppDatabase` (singleton) extends `RoomDatabase`

       - On “Add to Favorites,” an `AsyncTask` inserts a `FavoriteBook` row. On “Remove,” it deletes it. Favorite lists are observed via LiveData.

### Demo

   <p align="center"><img source="https://github.com/user-attachments/assets/1d794e11-3f34-47bd-9f04-0c2475f324b6" alt="App Demo"/></p>
