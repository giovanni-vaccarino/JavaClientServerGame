package polimi.ingsoft.server.controllerg;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.enumerations.TurnPhase;
import polimi.ingsoft.server.model.GameCard;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard.Slots;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    final ArrayList<Player> players = new ArrayList<>();
    private TurnPhase turnPhase;
    final ChatController chatController;

    final PublicBoardController publicBoardController;

    public MainController(){
        // qua inizializzo gli altri controller, che inizializzeranno il model della mia partita
        this.chatController = new ChatController();
        this.publicBoardController = new PublicBoardController();
        /*
        for(var i = 0 ; i < numberOfPlayers ; i++){

        }
        */
        this.turnPhase = TurnPhase.DRAW;
    }



    public Message writeMessage(String message){
        return this.chatController.writeMessage(message);
    }

    public MixedCard drawCard(Player player, String deckType, Slots slot){
        // Ensure that the player sending the request is the right player and that it's draw phase

        MixedCard drawedCard = this.publicBoardController.drawCard(deckType, slot);

        //adding to the playerhand the card drawed

        return drawedCard;
    }
}
