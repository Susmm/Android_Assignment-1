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


