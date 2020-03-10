import random 
import dots 
import pegs
import rules
import UI


gameState = 0
colours = [-27136,-65536,-256,-16776961,-2,-16711936]
currentScore=0
balls=[]
rounds=1
clicks=1
guess=[]
points=[]
code=[]

def setup():
    size(480,800)
    
def draw():
    global gameState, balls,code,clicks,rounds,guess
    background(-1)
    
    UI.userInterface(gameState,balls,points,currentScore)
        
    if gameState==1:
        for a in range (4):
            code.append(random.randint(0,5))
        print (code)
        gameState=3
    
    if gameState==3:
        if clicks>4:
            rounds+=1
            clicks=1
    
def changeRecod(currentScore):
    file = open("score.txt", 'r')
    wins = file.readlines()
    file.close()
    file = open("score.txt", 'a')
    if currentScore>int(wins[-1].rstrip()):
        file.write('\n'+str(currentScore))
    file.close()

def mousePressed():
    global x,y,gameState,code,balls,guess,clicks,points,rounds,currentScore
     
    y=mouseY//80
    x=mouseX//80
    
    
    if gameState==0:
        if y==3:
            gameState=1
        elif y==4:
            gameState=2
    
    elif gameState==2:
        code.append(x)
        if len(code)==4:
            print (code)
            gameState=3
            
    
    elif gameState==3:
        #print (get(mouseX,mouseY))
        colors=get(mouseX,mouseY)
        if colors in colours and y==0:
            balls.append(dots.dots(colors,clicks,rounds))
            guess.append(x)
            if len(guess)==4:
                print (guess)
                if rules.rules(guess,code,points,rounds):
                    currentScore+=1
                    changeRecod(currentScore)
                    gameState=4
                    currentScore+=1
                guess=[]
            clicks+=1
        if len(balls)>31:
            gameState=5
    
    elif gameState>3:
        if y==4:
            balls=[]
            points=[]
            code=[]
            rounds=1
            clicks=1
            gameState=0
