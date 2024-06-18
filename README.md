
# Codex Naturalis - GC05
## Software engineering final project
Software Engineering Project 2024: GC05 - Vaccarino, Zacchi, Viganò, Vacis

## Implemented Features
- CLI
- GUI (java FX)
- SOCKET
- RMI
- Complete ruleset
- Advanced Feature: Chat
- Advanced Feature: Multiple games
- Advanced Feature: Resilience 
## Unit Test Coverage Data
Line Coverage:
- Controller: xx%
- Model: xx%
## Run (build from Maven)
--
### 0. Requirements
- Java JDK >= 21
- A Maven version compatible with your installed JDK
### 1. Clone the repository
--
### 2. Build the .jar executable
--
### 3. Testing if the jar works
--
### 4. Running the game
The program takes some arguments

#### Running the GUI
The GUI will start using no additional argument, or using -gui argument.
```
java -jar <name_of_the_jar>.jar [-gui]
```
#### Running the CLI
Appending the argument -cli, a command line interface will be started. 
```
java -jar <name_of_the_jar>.jar -cli
```
#### Running using RMI
The default connection protocol is RMI. You can also use -rmi
```
java -jar <name_of_the_jar>.jar -rmi
```

#### Running using SOCKET
Appending the argument -socket, the game will be started with tcp protocol. 
```
java -jar <name_of_the_jar>.jar -socket
```

#### Running the Server
To start a server run the command
```
java -jar <name_of_the_jar>.jar
```
