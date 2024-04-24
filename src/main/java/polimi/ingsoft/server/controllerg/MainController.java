package polimi.ingsoft.server.controllerg;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.model.GameCard;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.PlaceInPublicBoard.Slots;

import java.util.List;

public class MainController {
    final Player first_player;
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
        this.first_player = new Player()
    }



    public void writeMessage(String message){
        this.chatController.writeMessage(message);
    }

    public List<Message> getChat(){
        return this.chatController.getChat();
    }

    public GameCard drawCard(Player player, String deckType, Slots slot){
        GameCard drawedCard;
        drawedCard = this.publicBoardController.drawCard(deckType, slot);

        //adding to the playerhand the card drawed

        return drawedCard;
    }
}
