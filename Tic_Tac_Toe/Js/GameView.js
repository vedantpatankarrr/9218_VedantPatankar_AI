export default class GameView{
    constructor(root)
    {
        this.root=root;
        this.root.innerHTML=`
        <div class="header">
            <div class="header_turn"></div>
            <div class="header_stat"></div>
            <button type="button" class="header_refresh">
                <i class="material-icons">refresh</i>
            </button>
        </div>

        <div class="board">
            <div class="board_tile" data-index="0"></div>
            <div class="board_tile" data-index="1"></div>
            <div class="board_tile" data-index="2"></div>
            <div class="board_tile" data-index="3"></div>
            <div class="board_tile" data-index="4"></div>
            <div class="board_tile" data-index="5"></div>
            <div class="board_tile" data-index="6"></div>
            <div class="board_tile" data-index="7"></div>
            <div class="board_tile" data-index="8"></div>
        </div>
        `;

        this.root.querySelectorAll(".board_tile").forEach(tile=>{
            tile.addEventListener("click",()=>{
                this.onTileClick(tile.dataset.index);
            });
        });

        this.root.querySelector(".header_refresh").addEventListener("click" , ()=>{
            this.onRefreshClick();
        });
    }

    update(game)
    {
        this.updateTurn(game);
        this.updateStatus(game);
        this.updateBoard(game);
    }

    updateTurn(game)
    {
        this.root.querySelector(".header_turn").textContent=`${game.turn}'s turn`;
    }

    updateStatus(game)
    {
        let status="In Progress";
        if(game.hasWon()){
            status=`${game.turn} is the winner!`;
        }
        else if(!game.isInProgress())
        {
            this.root.querySelector(".header_turn").textContent="";
            status="Its a Tie!";
        }
        this.root.querySelector(".header_stat").textContent=status;
        
    }

    updateBoard(game)
    {
        const winningCombo=game.hasWon();

        for(let i=0;i<game.board.length;i++)
        {
            const tile=this.root.querySelector(`.board_tile[data-index="${i}"]`);

            tile.textContent=game.board[i];
            tile.classList.remove("board_tile_winner");    

            if(winningCombo && winningCombo.includes(i))
            {
                tile.classList.add("board_tile_winner");
            }
        }
    }

}