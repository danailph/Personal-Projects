from mine import Board
SIZE = 5
SQSIZE = 100

b = None

def setup():
    size (SIZE*SQSIZE+1, SIZE*SQSIZE+1)
    
    global b 
    b = Board ()
    
def draw():
    background (-1)
    
    fill (200,200,200)
    for x in range(SIZE):
        for y in range(SIZE):
            rect (x*SQSIZE,y*SQSIZE,SQSIZE,SQSIZE)
    b.drawBoard()

def mousePressed():
    if mouseButton == LEFT:
        b.reveal(mouseX//SQSIZE, mouseY//SQSIZE)
    else:
        b.flag(mouseX//SQSIZE, mouseY//SQSIZE)
