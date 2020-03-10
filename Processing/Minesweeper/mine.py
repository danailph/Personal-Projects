import random # To allow random placement of the mines
import sys # To allow exiting of the program if a mine has been revealed

SIZE = 5
MINES = 5
SQSIZE = 100

safe_spaces = SIZE*SIZE - MINES

def drawSeaSquare(x,y):
    fill(0,30,255)
    rect (x*SQSIZE,y*SQSIZE,SQSIZE,SQSIZE)
def drawFlag(x,y):
    line (x*SQSIZE+30 ,y*SQSIZE+30,x*SQSIZE+30,y*SQSIZE+80)
    fill(255,0,0)
    triangle(x*SQSIZE+30,y*SQSIZE+30,x*SQSIZE+30,y*SQSIZE+50,x*SQSIZE+50,y*SQSIZE+30)
def drawNumber(num,x,y):
    textSize(32)
    fill(0,100,0)
    text(str(num),x*SQSIZE+40,y*SQSIZE+60)

# Given an x and y generate the co ordinates in a 3*3 grid. Return a list of the coords
def squaresAround(x, y):
    squares = []
    # Loop for 1 before and 1 after the x (Range doesn't include the last number)
    for xaround in range(x-1, x+2):
        # Similar for y
        for yaround in range(y-1, y+2):
            # We will be in this loop 9 times.
            # x-1 y-1, x-1 y, x-1 y+1,     x y-1, x y, x y+1,    x+1 y-1, x+1 y, x+1 y+1
            # Need to make sure they don't go outside of the board which is numbered from 0..SIZE-1
            if xaround > -1 and yaround > -1 and xaround < SIZE and yaround < SIZE:
                squares.append( [xaround, yaround] )
    return squares

class Square():
    def __init__(self, x, y):
        self.revealed = False # Stores whether this square has been revealed
        self.flagged = False # Stores whether this square has been flagged
        self.x = x # Save the co ordinates to the class
        self.y = y
    def reveal(self, board):
        # Board parameter is there as SeaSquare will need it
        self.revealed = True # If square has been revealed, set revealed to True
        global safe_spaces
        safe_spaces -= 1
    def draw(self):
        # Draw needs to return the correct symbol of the Square
        if self.flagged:
            drawFlag(self.x,self.y)

    def isMine(self):
    #Used to determine which squares are mines in the countMines function
        return False
    def flag(self):
        print('flagging')
        self.flagged = True

class Mine(Square):
    def reveal(self, board):
        print("Kaboom! Mine Triggered")
        sys.exit(0) # Exit the program, no matter what loop we are in
    def isMine(self):
        return True
class NumSquare(Square):
    def draw(self):
        if self.revealed:
            drawNumber(self.num,self.x,self.y)
        if self.flagged:
            drawFlag(self.x,self.y)
    def setNum(self, num):
        self.num = num # Used to allow the square to display the correct Number
class SeaSquare(Square):
    def draw(self):
        if self.revealed:
            drawSeaSquare(self.x,self.y)
        if self.flagged:
            drawFlag(self.x,self.y)
    def reveal(self, board):
        # If we've already been revealed, return. This will partly stop infinite recursion
        if self.revealed:
            return
        # If we haven't yet revealed, lets do it now
        self.revealed = True
        global safe_spaces
        safe_spaces -= 1
        # Get the squares around me
        squaresToReveal = squaresAround(self.x, self.y)
        # For each square around me, reveal it
        for (x, y) in squaresToReveal:
            board[y][x].reveal(board)

class Board():
    def __init__(self):
        # Generate a size * size grid of Nones for a template
        self.grid = [ [ None ] * SIZE for y in range(SIZE) ]
        # Instead of a list comprehension this could have been done in a for loop:
        # self.grid = []
        # for row in range(SIZE):
        #   newRow = [ None ] * SIZE
        #   self.grid.append(newRow)

        self.addMines()
        self.fillGrid()
            
    def drawBoard(self):
        for row in self.grid:
            for square in row:
                square.draw()

    def addMines(self):
        for mine in range(MINES):
            x = random.randint(0, SIZE-1) # Generate a random x, y
            y = random.randint(0, SIZE-1)
            # While there's something at that position, keep generating new coords
            while self.grid[y][x] != None:
                x = random.randint(0, SIZE-1) # Generate a random x, y
                y = random.randint(0, SIZE-1)

            self.grid[y][x] = Mine(x, y)

    def minesAround(self, x, y):
        count = 0
        # Loop over each square and count it if it is a mine
        for (xaround, yaround) in squaresAround(x, y):
            # If the square is something, and it is a mine, add to count
            if self.grid[yaround][xaround] != None and self.grid[yaround][xaround].isMine():
                count += 1
        return count

    def fillGrid(self):
        for x in range(SIZE):
            for y in range(SIZE):
                # If the square is none i.e. it isn't a mine
                if self.grid[y][x] == None:
                    # Then count the mines around
                    mines = self.minesAround(x, y)
                    if mines == 0:
                        # If there are no mines it is a sea time
                        self.grid[y][x] = SeaSquare(x, y)
                    else:
                        # Else create a number square, and give it how many mines are around
                        self.grid[y][x] = NumSquare(x, y)
                        self.grid[y][x].setNum(mines)
    def reveal(self, x, y):
        self.grid[y][x].reveal(self.grid)
    def flag(self, x, y):
        self.grid[y][x].flag()

