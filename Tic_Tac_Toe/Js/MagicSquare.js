import Game from "./Game.js";
export default class Magic_square
{
    constructor(game)
    {
        //copy instance of game
        this.og_game=Object.assign(Object.create(Object.getPrototypeOf(game)),game);
        this.og_game.board=game.board.slice(0);
        this.og_game.turn=game.turn;

        this.player=this.og_game.turn;//understand who is ai
    }


    makeMove()
    {
        if(!this.og_game.board.includes(null))
            return;

        let positions=this.read_state(this.og_game);//locates postion of opponets plays
        let move;
        for(let i=0;i<positions.length;i++)
        {
            move=this.check_all_pairs(positions,i);//check all pairs for i
            if(move)
            {
                break;
            }
        }
        if(!move){ 
            move=Math.floor(Math.random()*9);
            while(this.og_game.board[move]!=null)
            {
                move=Math.floor(Math.random()*9);
            }
        }
        console.log(move);
        return move;
    }

    check_all_pairs(arr,index)
    {
        let value,d,move=null;
        for(let j=index+1;j<arr.length;j++)
        {
            value=this.getValue(arr[index])+this.getValue(arr[j]);
            d=15-value;
            if(d<0 || d>9)
            {}
            else
            {
                if(this.og_game.board[this.getReverseValue(d)]==null)
                {
                    move=this.getReverseValue(d);
                    break;
                }
            }
        }
        return move;
    }

    getReverseValue(d)
    {
        if(d==8)
        return 0;
        else if(d==3)
        return 1;
        else if(d==4)
        return 2;
        else if(d==1)
        return 3;
        else if(d==5)
        return 4;
        else if(d==9)
        return 5;
        else if(d==6)
        return 6;
        else if(d==7)
        return 7;
        else 
        return 8;
    }


    read_state(game)
    {   
       let arr=new Array();
       for(let i=0;i<game.board.length;i++)
       {
            if(game.board[i]!=this.player && game.board[i]){
                arr.push(i);
            }
       }
       return arr;
    }


    getValue(index)
    {
        if(index==0)
        return 8;
        else if(index==1)
        return 3;
        else if(index==2)
        return 4;
        else if(index==3)
        return 1;
        else if(index==4)
        return 5;
        else if(index==5)
        return 9;
        else if(index==6)
        return 6;
        else if(index==7)
        return 7;
        else 
        return 2;
    }

}