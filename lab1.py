from contextlib import nullcontext
import random

csymbol="X"
usymbol="O"


def TicTacToe():
  
  board_positions = [1, 2, 3, 4, 5, 6, 7, 8, 9]
  end = False
  MagicSquare = [8,1,6,3,5,7,4,9,2]
  

  def PrintBoard():
    print()
    print('', board_positions[0], "|", board_positions[1], "|", board_positions[2])
    print("---|---|---")
    print('', board_positions[3], "|", board_positions[4], "|", board_positions[5])
    print("---|---|---")
    print('', board_positions[6], "|", board_positions[7], "|", board_positions[8])
    print()

  def GetNumber():
    while True:
      number = input("Enter a number to place the symbol: ")
      try:
        number  = int(number)
        if number in range(1, 10):
          return number
        else:
          print("\nNumber not on board")
      except ValueError:
        print("\nThat's not a number. Try again")
        continue

  def Turn(player,symbol):
    global ans
    
    if(player == "user"):
      placing_index = GetNumber() - 1
      
    elif(player == "computer"):
      for x in range(9):
        for y in range(9):
          if x != y:  
            if(board_positions[x] == csymbol and board_positions[y] == csymbol): 
              ans=15-(MagicSquare[x]+MagicSquare[y])
              if(ans > 0 and ans<=9):
                for i in range(9):
                  if MagicSquare[i]==ans:
                    placing_index=i
                    if board_positions[placing_index] == "X" or board_positions[placing_index] == "O":
                      break
                    board_positions[placing_index]=csymbol
                    return
            elif(board_positions[x]==usymbol and board_positions[y]==usymbol):
              ans=15-(MagicSquare[x]+MagicSquare[y])
              if(ans > 0 and ans<=9):
                for i in range(9):
                  if MagicSquare[i]==ans:
                    placing_index=i
                    if board_positions[placing_index] == "X" or board_positions[placing_index] == "O":
                      break
                    board_positions[placing_index]=csymbol
                    return
            else:
                if(board_positions[0]==1):
                    placing_index=0
                elif(board_positions[2]==3):
                    placing_index=2
                elif(board_positions[6]==7):
                    placing_index=6
                elif(board_positions[8]==9):
                    placing_index=8 
                else:
                    placing_index = (random.randint(1,9))-1
    if board_positions[placing_index] == "X" or board_positions[placing_index] == "O":
      print("\nBox already occupied. Try another one")
      Turn(player,usymbol)
    else:
      board_positions[placing_index] = symbol

  def CheckWin(player):
    count = 0

    for x in range(9):
      for y in range(9):
        for z in range(9):
          if x != y and y != z and z != x:
            if board_positions[x] == player and board_positions[y] == player and board_positions[z] == player:
              if MagicSquare[x] + MagicSquare[y] + MagicSquare[z] == 15:
                print("Player", player ,"wins! :)\n")
                return True

    for a in range(9):
      if board_positions[a] == "X" or board_positions[a] == "O":
        count += 1
      if count == 9:
        print("The game ends in a Tie :(\n")
        return True

  while not end:
    
    PrintBoard()
    end = CheckWin(usymbol)
    if end == True:
      break
    print("User's turn to play")
    Turn("user",usymbol)

    PrintBoard()
    end = CheckWin(csymbol)
    if end == True:
      break
    print("Computer's turn to play")
    Turn("computer",csymbol)


usymbol=input('Choose X or O: ').capitalize()
if(usymbol!="X" and usymbol!="O"):
    print("Incorrect symbol")
    exit(0)
elif(usymbol=="X"):
    csymbol="O"
else:
    csymbol="X"
TicTacToe()