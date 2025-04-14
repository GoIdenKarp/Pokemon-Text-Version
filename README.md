# Pokémon Text Version

A text-based recreation of the classic Pokémon games, implemented in Java.

## Features

- Text-based battle system
- Implementation of original Pokémon mechanics
- Region exploration system
- Support for various Pokémon and moves
- JSON-based data storage with Base64 encoding

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Building and Running

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/Pokemon-Text-Version.git
   cd Pokemon-Text-Version
   ```

2. Build with Maven:
   ```bash
   mvn clean install
   ```

3. Run the game:
   ```bash
   mvn exec:java -Dexec.mainClass="game.GameStarter"
   ```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── actions/    - Game action implementations
│   │   ├── areas/      - Game area implementations
│   │   ├── battle/     - Battle system implementation
│   │   ├── data/       - Data structures and models
│   │   ├── enums/      - Enums for different game mechanics
│   │   ├── events/     - Event system
│   │   ├── exceptions/ - Custom exceptions
│   │   ├── game/       - Infrastructure for actually running the game, loading/saving data
│   │   ├── items/      - Item implementations
│   │   ├── moves/      - Move implementations
│   │   ├── pokémon/    - Pokémon-related classes
│   │   ├── testing/    - Test utilities
│   │   ├── trainer/    - Trainer-related implementations
│   │   ├── ui/         - User interface components
│   │   └── util/       - Utility classes
│   └── resources/
│       ├── data/       - Game data files
```

## Legal Notice

This is a fan-made project for educational purposes only. Pokémon and all related trademarks belong to Nintendo, Game Freak, and The Pokémon Company. This project is not affiliated with, endorsed, sponsored, or specifically approved by Nintendo, Game Freak, or The Pokémon Company.

This project is intended for non-commercial use under fair use for educational purposes. All Pokémon names, characters, and related indicia are trademarks of their respective owners.

If you are a representative of Nintendo, Game Freak, or The Pokémon Company and have concerns about this project, please contact me and I will address your concerns promptly.

## License

[MIT](https://choosealicense.com/licenses/mit/) (Applies only to original code, not to Pokémon intellectual property) 