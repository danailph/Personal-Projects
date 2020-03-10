import shapes
def userInterface(gameState,balls,points,currentScore):
    

    
    if gameState==0:
        currentRecord = readRecord()
        textSize(50)
        fill(0)
        text("Mastermind 2.0", 60,140)
        textSize(60)
        text ("Computer",105,320)
        text ("2-Players",110,400)
        textSize(40)
        text("Current Score: "+str(currentScore),100,600)
        text("Current Record: "+str(currentRecord),90,680)
    
    elif gameState==2:
        shapes.shapes()
        textSize (50)
        fill(0)
        text("Choose 4 colors",45,300)
        
    elif gameState>2:
        shapes.shapes()
        for ball in balls:
            ball.show()
            
        for point in points:
            point.show()
        if gameState >3:
            fill(0,0,0,180)
            rect (-1,-1,600,800)
            fill(-1)
            textSize(40)
            text("Current Score: "+str(currentScore),90,600)
            textSize(60)
            text("Play Again",100,400)
        if gameState==4:
            text("You Win !",110,240)
        elif gameState==5:
            text("You Lost !",110,240)

def readRecord():
    file = open("score.txt", 'r')
    wins = file.readlines()
    return wins[-1].rstrip()
    file.close()    