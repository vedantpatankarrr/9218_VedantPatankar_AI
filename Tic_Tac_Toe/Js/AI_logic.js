import Game from "./Game.js";
export default class AI_logic{

    constructor(game)
    {
        //copy instance of game
        this.og_game=Object.assign(Object.create(Object.getPrototypeOf(game)),game);
        this.og_game.board=game.board.slice(0);
        this.og_game.turn=game.turn;

        this.player=this.og_game.turn;//understand who is ai
    
    }

    makeMove()// return move to make
    {
        let possible_moves=this.find_poss_mov(this.og_game);

        let cpy_game=this.copy_state(this.og_game);
        Array.from(possible_moves.keys()).map(key=>{
            possible_moves.set(key,this.simulate(cpy_game,key));
            cpy_game=this.copy_state(this.og_game);
        });
        console.log(possible_moves);

        let best_move=this.choose_bestMove(this.player,possible_moves);
        return best_move;
    }

    simulate(game,index)
    {
        let value=0;
        game.makeMove(index);
        if(this.isInProgress(game))
        {
            let map=this.find_poss_mov(game);
            let copy_game=this.copy_state(game);
            Array.from(map.keys()).map(key=>{
                map.set(key,this.simulate(copy_game,key));
                copy_game=this.copy_state(game);
            });
            value=map.get(this.choose_bestMove(game.turn,map));
        }
        else if(this.hasWon(game)){
            if(game.turn==this.player)
                value+=1;
            else
                value-=1;
        }
        return value;
    }

    find_poss_mov(game)
    {
        let map =new Map();
        for(let i=0;i<game.board.length;i++)
        {
            if(game.board[i]==null)
            map.set(i,null);
        }
        return map;
    }

    copy_state(game)
    {
        let copy_state;
        copy_state=Object.assign(Object.create(Object.getPrototypeOf(game)),game);
        copy_state.board=game.board.slice(0);
        copy_state.turn=game.turn;

        return copy_state;
    }

    choose_bestMove(player,possible_moves)
    {
        let move,best_score;
        if(player==this.player)
            best_score=-Infinity;
        else
            best_score=+Infinity;
    
        Array.from(possible_moves.keys()).map(key=>
        {
            if(player==this.player && possible_moves.get(key)>best_score)
            {
                move=key;
                best_score=possible_moves.get(key);
            }
            else if(player!=this.player && possible_moves.get(key)<best_score)
            {
                move=key;
                best_score=possible_moves.get(key);
            }
        });
        return move;
    }

    isInProgress(game)//for tie and in progress
    {
        return !this.hasWon(game) && game.board.includes(null);
    }

    hasWon(game)
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

            if(game.board[a] && (game.board[a]===game.board[b] && game.board[b]===game.board[c]))
            {
                return comb;
            }
        }
        return null;
    }


}
