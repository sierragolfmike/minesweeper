# Java Minesweeper – OOP Coursework (PE7070)

This repository contains a GUI-based implementation of the classic Minesweeper game developed in Java.

## 📋 Project Overview

**Devolped**: 7th July 2024

Developed from a partially complete text-based version, this project completes the missing functionality and upgrades the game to a GUI-based version using Java Swing, while applying object-oriented programming principles such as encapsulation, inheritance, and the MVC design pattern.

---

## 🧱 Architecture

### MVC Pattern

- **Model**: Game logic, mine placement, game state management.
- **View**: Visual display (text-based and GUI).
- **Controller**: User input and interaction handler.

---

## 🧩 Class Overview

| Class         | Description |
|---------------|-------------|
| `Minesweeper` | Core logic – game board, player board, lives, history. |
| `UI`          | Text-based interface and menu-driven control. |
| `GUI`         | Java Swing GUI, event handling, game board rendering. |
| `Slot`        | Represents individual cells in the game grid. |
| `Assign`      | Manages move assignments and grid updates. |

---

## 🔍 Features

- Text and GUI interfaces
- Flag, guess, and reveal cells
- Save and load game state
- Undo and clear game functionality
- Visual feedback via text or GUI
- Modular OOP design using encapsulation and composition

---

## 🧪 Testing

### Testing Strategy

Black-box testing was conducted at three stages:
1. Initial codebase
2. Post text-based UI implementation
3. Post GUI implementation

### Test Coverage

| Feature        | Text UI | GUI | Notes |
|----------------|---------|-----|-------|
| Start Game     | ✅      | ✅  |       |
| Flag Cell      | ✅      | ✅  | Case-insensitive fix added |
| Guess Cell     | ✅      | ✅  | Case-insensitive fix added |
| Save/Load      | ✅      | ✅  | Implemented |
| Undo Move      | ✅      | ✅  | Implemented |
| Clear Game     | ✅      | ✅  | Implemented |
| Quit Game      | ✅      | ✅  | Functional |

---

## 🧠 Reflection

### Lessons Learned

- Importance of early and iterative testing
- GUI development was a strength, logic layer needed deeper planning
- Object-oriented principles helped structure the application well

### Known Limitations

- Did not implement `java.util.Observable` due to deprecation and scope
- GUI refresh is manually triggered rather than observer-based

### Future Improvements

- Observer pattern to automate GUI updates
- Replace text flags with icons
- Add accessibility features (e.g., high contrast mode, keyboard navigation)

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 8+
- IDE like IntelliJ IDEA or Eclipse

### Running the Application

```bash
# Compile
javac *.java

# Run GUI
java GUI

# Run text-based UI
java UI
