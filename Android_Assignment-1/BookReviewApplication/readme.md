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
   - [Project Structure](#project-structure)  
   - [Setup & Run](#setup--run)  
6. [JSON Data](#json-data)  
7. [Room Persistence](#room-persistence)  
8. [Usage](#usage)  
9. [License](#license)  

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

- **Language**: Java (no Kotlin)  
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

![Book List UI Online](https://github.com/user-attachments/assets/3b6d7b46-4aaa-41be-8ea7-49e6d45d398f)

2. **Book List UI (Offline)**   

![Book List UI offline](https://github.com/user-attachments/assets/0c2787d5-8cfb-45d7-8b96-74c84e71c665)

3. **Empty Favorites**  
   ![Empty Favorites](screenshots/empty_favorites.png)  

4. **Book Detail (Not Favorited)**  
   ![Book Detail](screenshots/book_detail.png)  

5. **Book Detail (Favorited)**  
   ![Book Detail Favorited](screenshots/book_detail_favorited.png)  

6. **Favorites List (Some Favorites)**  
   ![Favorites List](screenshots/favorites_list.png)  

---

## Getting Started

### Prerequisites

- Android Studio (Arctic Fox or later recommended)  
- JDK 11 (or higher) installed  
- Android SDK Platform 33 (or match `compileSdkVersion`)

### Project Structure


