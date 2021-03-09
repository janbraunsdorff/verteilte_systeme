package de.dhbw.vs.game;

public class GameField implements GameInterface {
    private final Square[][] field = new Square[7][6];
    private final NetworkInterface network;
    private final Player player;
    private Status status;

    public GameField(NetworkInterface network, boolean isBeginningPlayer) {
        this.network = network;
        this.player = isBeginningPlayer ? Player.WHITE : Player.BLACK;
        this.status = isBeginningPlayer ? Status.ACTIVE : Status.WAITING;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void executeMove(Move move) {
        Square[] column = field[move.getColumnNumber()];

        for (Square square : column) {
            if (square.getSquareState().equals(SquareState.EMPTY)) {
                if (status.equals(Status.ACTIVE)) {
                    square.setSquareState(player.equals(Player.WHITE) ? SquareState.WHITE : SquareState.BLACK);
                } else if (status.equals(Status.WAITING)) {
                    square.setSquareState(player.equals(Player.WHITE) ? SquareState.BLACK : SquareState.WHITE);
                }
                break;
            }
        }

        if (status.equals(Status.ACTIVE)) {
            status = Status.WAITING;
            network.sendMove(move);
        } else if (status.equals(Status.WAITING)) {
            status = Status.ACTIVE;
        }

        checkForWinner();
    }

    public void checkForWinner() {
    }

}
