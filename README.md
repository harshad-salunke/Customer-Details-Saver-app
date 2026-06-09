# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**OmSai** is an Android customer management application built with Java and AndroidX. It allows users to add, edit, and manage customer information (names, descriptions, contact details, dates, and payment amounts) with a persistent SQLite database. The app displays a list of customers with a summary of total payments.

## Build & Development

### Prerequisites
- Android Studio with Android SDK 30 (compileSdkVersion)
- Java 8+
- Gradle 4.2.1 (defined in build.gradle)

### Building
```bash
# Clean and build the app
gradle clean build

# Build debug APK
gradle assembleDebug

# Build release APK
gradle assembleRelease
```

### Running Tests
```bash
# Run unit tests
gradle test

# Run instrumented tests on a device/emulator
gradle connectedAndroidTest
```

### Linting
The project uses standard Android lint checks. Run:
```bash
gradle lint
```

### Android Emulator / Device
- **minSdkVersion**: 16
- **targetSdkVersion**: 30
- Requires API 16+ device or emulator

## Architecture & Key Components

### Database Layer
- **CustomerContract** (`data/CustomerContract.java`): Defines the content authority, table name, and column constants
- **CustomerhelperDb** (`data/CustomerhelperDb.java`): SQLiteOpenHelper that creates the `customers` table with columns: `_id`, `name`, `discription`, `date`, `date2` (mobile), `payments`
- **CustomerProvider** (`data/CustomerProvider.java`): Content provider implementing CRUD operations (query, insert, update, delete) using UriMatcher for routing

### Activities
- **MainActivity** (`MainActivity.java`): Main screen displaying a ListView of customers, total payment sum, and FAB to add new customer. Shows an empty state image when no customers exist
- **Add_Customer** (`Add_Customer.java`): Screen for adding new customers or editing existing ones. Validates name (≤18 chars) and payment fields
- **intro** (`intro.java`): Splash screen launched on app start

### UI Components
- **CusstomAdappter** (`CusstomAdappter.java`): Custom CursorAdapter binding customer data to list items

### Key Dependencies
- androidx.appcompat:appcompat:1.3.0
- com.google.android.material:material:1.4.0
- androidx.constraintlayout:constraintlayout:2.0.4
- Testing: junit, espresso

## Important Patterns & Notes

### Content Provider Pattern
The app uses Android's ContentProvider pattern for all database access. All CRUD operations flow through `CustomerProvider` using Uris:
- `content://com.example.android.omsai/customer` — list all customers
- `content://com.example.android.omsai/customer/{id}` — single customer operations

### Database Schema
The customers table has these columns:
- `_id`: Primary key (autoincrement)
- `name`: Customer name (required, max 18 chars)
- `discription`: Description/notes
- `date`: Timestamp (auto-filled on add)
- `date2`: Mobile number field (stored as INTEGER)
- `payments`: Payment amount (required)

### Validation
- Name field: Required, max 18 characters
- Payment field: Required, converted to Long

### Lifecycle Considerations
- MainActivity shows toast messages in onPause, onStop, onDestroy (debug-like behavior—verify if intentional)
- onBackPressed in MainActivity shows a "Sorray cant go back" message with a back button guard

## File Structure
```
app/src/main/
├── java/com/example/omsai/
│   ├── MainActivity.java
│   ├── Add_Customer.java
│   ├── intro.java
│   ├── CusstomAdappter.java
│   └── data/
│       ├── CustomerContract.java
│       ├── CustomerhelperDb.java
│       └── CustomerProvider.java
├── res/
│   ├── layout/ (activity_main.xml, activity_add_customer.xml, list.xml)
│   ├── menu/ (main_edit.xml, editing.xml)
│   ├── values/ (strings, colors, styles, themes)
│   └── drawable/ (app icon, empty state image)
└── AndroidManifest.xml
```
