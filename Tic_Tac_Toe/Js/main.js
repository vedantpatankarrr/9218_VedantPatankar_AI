import AI_logic from "./AI_logic.js";
import Game from "./Game.js";
import GameView from "./GameView.js";
import MagicSquare from "./MagicSquare.js";

let game=new Game();
let ai;
let bool;
let gameView=new GameView(document.getElementById("app"));


// ai=new AI_logic(game);
// game.makeMove(ai.makeMove());
// gameView.update(game);

gameView.onTileClick = function(i)
{
    console.log("new instance");
    bool=game.makeMove(i);
    gameView.update(game);
    if(bool){
        ai=new AI_logic(game);
        game.makeMove(ai.makeMove());
        gameView.update(game);
    }
};

gameView.onRefreshClick = function()
{
    game=new Game();
    gameView.update(game);
};

gameView.update(game);