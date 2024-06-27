
# Codex Naturalis - GC05
## Software engineering final project
Software Engineering Project 2024:

Members of the group GC05:

- Zacchi Simone
- Vaccarino Giovanni
- Vacis Nicolò 
- Viganò Andrea

## Implemented Features
Legend:

- 🟢: implemented
- 🔴: not implemented

| Feature        | Status |
|----------------|--------|
| Complete rules | 🟢     |
| TCP            | 🟢     |
| RMI            | 🟢     |
| CLI            | 🟢     |
| GUI(FX)        | 🟢     |
| Resilience     | 🟢     |
| Chat           | 🟢     |
| Multiple games | 🟢     |
| Persistence    | 🔴     |

## Unit Test Coverage Data
Line Coverage:
- Controller: 96%
- Model: 96%

The screenshots can be found at deliverables/Test Coverage

## JavaDoc
The JavaDoc can be found at deliverables/JavaDoc, by opening index.html

## Running the game
The jars can be found at deliverables/JARs

### Running the Client
It is requested to insert the server ip. You can run the client using the following command:
```
java -jar <name_of_the_jar>.jar <server_ip>
```

#### Choosing the UI type
The GUI will start using no additional argument. Appending the argument -cli, a command line interface will be started. 
```
java -jar <name_of_the_jar>.jar -cli
```
#### Choosing the comunication protocol
The default comunication protocol is RMI. Appending the argument -socket, the game will be started with tcp protocol. 
```
java -jar <name_of_the_jar>.jar -rmi
```
#### Running the client when JavaFX is not supported
This line allows to execute the client on a computer that doesn't support JavaFx
```
java --module-path Desktop/javafx-sdk-21.0.3/lib --add-modules javafx.controls,javafx.fxml -Dprism.order=sw -Dprism.verbose=true -Djava.awt.headless=false --add-exports=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED -jar Desktop/client.jar
```

### Running the Server
To start the server run the command:
```
java -jar <name_of_the_jar>.jar
```
