Lab4NotesReminderApp  
Student Name: Ramandeep Singh  
Student ID: A00194321  

# Lab4NotesReminderApp – Notes + Reminder Android App

## Overview

Lab4NotesReminderApp is an Android application built in Kotlin that combines local data persistence with background tasks and system event handling. The app allows users to create, view, update, and delete notes stored in a Room database, receive reminder notifications via a background Service, and react to connectivity changes through a BroadcastReceiver.

This project is designed to satisfy the requirements of Lab 4 (Notes + Reminder App) in the course, demonstrating understanding of Room, DAO, Services, BroadcastReceivers, Notifications, RecyclerView, and proper documentation.

## Features

- Add, view, update, and delete notes.
- Persistent storage using Room (SQLite abstraction).
- RecyclerView with:
  - Card-based note items.
  - Click to edit (dialog).
  - Long-press to delete with Snackbar Undo.
- Background Service (`ReminderService`) that:
  - Waits 5 seconds.
  - Shows a high-priority “Check your notes!” notification.
- BroadcastReceiver (`ConnectivityReceiver`) that:
  - Listens for network connectivity changes.
  - Logs and displays a Toast when network is available/lost.
- Clean architecture using:
  - Entity, DAO, RoomDatabase.
  - Repository and AndroidViewModel.
- Full in-code documentation:
  - File headers (course code, student name, ID, date).
  - Class and method KDoc comments.
  - Inline comments on important logic.

## Architecture and Technologies

- Language: Kotlin  
- Minimum SDK: 21 (can be adjusted to match the template).  
- Libraries and components:
  - Room (`room-runtime`, `room-ktx`, `room-compiler` with kapt) for local database.
  - AndroidX Lifecycle (ViewModel, LiveData) for MVVM-style separation of concerns.
  - RecyclerView + DiffUtil + Material Design components for modern UI.
  - NotificationCompat for backward-compatible notifications.

### Logical layers

- **Data Layer**
  - `Note` (Entity)
  - `NoteDao` (DAO)
  - `NotesDatabase` (RoomDatabase)
  - `NotesRepository` (Repository pattern)

- **Presentation Layer**
  - `NotesViewModel` (AndroidViewModel)
  - `MainActivity` (Activity)
  - `NotesAdapter` (RecyclerView Adapter)

- **System Integration**
  - `ReminderService` (Service)
  - `ConnectivityReceiver` (BroadcastReceiver)
