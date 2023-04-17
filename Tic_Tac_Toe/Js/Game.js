export default class Game
{
    constructor()
    {
        this.turn="X";
        this.board=new Array(9).fill(null);
    }

    nextTurn()
    {
        this.turn=this.turn==="X" ? "O" : "X";
    }

    makeMove(i)
    {
        if(!this.isInProgress())
        {
            return;
        }
        if(this.board[i]){
            return;
        }
        this.board[i]=this.turn;
        if(!this.hasWon())
        {
            this.nextTurn();
        }
        return true;
    }

    undoMove(i)
    {
        this.board[i]=null;
        this.turn=this.turn==="X" ? "O" : "X";
    }

    hasWon()
    {
        const winningCombos=[
            [0,1,2],
            [3,4,5],
            [6,7,8],
            [0,3,6],
            [1,4,7],
            [2,5,8],
            [0,4,8],
            [2,4,6]
        ];
    
        for (const comb of winningCombos) {
            const [a,b,c]=comb;

            if(this.board[a] && (this.board[a]===this.board[b] && this.board[b]===this.board[c]))
            {
                return comb;
            }
        }
        return null;
    }

    isInProgress()//for tie and in progress
    {
        return !this.hasWon() && this.board.includes(null);
    }

}