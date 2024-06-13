package polimi.ingsoft.client.ui.cli;


import polimi.ingsoft.server.model.boards.Coordinates;

import java.util.List;

/**
 * Enumerations that lists all possible arguments for CLI's board management
 */
public enum BoardArgument implements Arguments {

    //TODO FIX THESE COORDINATES
    UP("up", new Coordinates(0, 2), List.of(new Coordinates[]{
            new Coordinates(0, 2),
            new Coordinates(-1, 3),
            new Coordinates(1, 3)
    })),
    DOWN("down", new Coordinates(0, -2), List.of(new Coordinates[]{
            new Coordinates(0, -2),
            new Coordinates(-1, -3),
            new Coordinates(1, -3)
    })),
    LEFT("left", new Coordinates(-2, 0), List.of(new Coordinates[]{
            new Coordinates(-2, 0),
            new Coordinates(-3, 1),
            new Coordinates(-3, -1)
    })),
    RIGHT("right", new Coordinates(2, 0), List.of(new Coordinates[]{
            new Coordinates(2, 0),
            new Coordinates(3, 1),
            new Coordinates(3, -1)
    })),
    UPRIGHT("upright", new Coordinates(1, 1), List.of(new Coordinates[]{
            new Coordinates(1, 1),
            new Coordinates(2, 2),
            new Coordinates(0, 2),
            new Coordinates(2, 0)
    })),
    UPLEFT("upleft", new Coordinates(-1, 1), List.of(new Coordinates[]{
            new Coordinates(-1, 1),
            new Coordinates(-2, 2),
            new Coordinates(-1, 3),
            new Coordinates(-3, 1)
    })),
    DOWNRIGHT("downright", new Coordinates(1, -1), List.of(new Coordinates[]{
            new Coordinates(1, -1),
            new Coordinates(3, 1),
            new Coordinates(2, -2),
            new Coordinates(1, -3)
    })),
    DOWNLEFT("downleft", new Coordinates(-1, -1), List.of(new Coordinates[]{
            new Coordinates(-1, -1),
            new Coordinates(-1, -3),
            new Coordinates(-2, -2),
            new Coordinates(-3, -1)
    }));

    private final String value;
    private final Coordinates offset;
    private final List<Coordinates> placesToCheckBeforePrint;

    BoardArgument(String value, Coordinates offset, List<Coordinates> placesToCheckBeforePrint) {
        this.value = value;
        this.offset = offset;
        this.placesToCheckBeforePrint = placesToCheckBeforePrint;
    }

    public String getValue() {
        return this.value;
    }

    public Coordinates getOffset() {
        return this.offset;
    }

    public List<Coordinates> getPlacesToCheckBeforePrint() {
        return placesToCheckBeforePrint;
    }
}
