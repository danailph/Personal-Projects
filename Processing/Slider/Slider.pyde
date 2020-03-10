import UI
import squares

gameState=0
points=[]
lenght=100
score=0
lives=5
speed = 4
timer = 980
bonus = 20


def setup():
    global timer1, timer2
    size (500,700)
    timer1 = millis() + 999
    timer2 = second() + 5

def draw():
    global gameState,points,timer1,cordinate,sliderX,lenght,score,lives,speed,timer,timer2,bonus 


    UI.userInterface(gameState,points,lenght,score,lives,speed)
    
    if lives<1:
        gameState=2
    
    if gameState==1:
        
        if millis()>timer1:
            power=int(random(bonus))
            points.append(squares.squares(int(random(25))*20,power,speed))
            timer1 = millis() + timer
            if bonus>10:
                if score%40==0 and score!=0:
                    bonus-=1
        
        if lenght<20:
            lenght=100
            speed=speed+1
            timer=timer-50
            
        for point in reverse(range(len(points))):
            
            if points[point].y>680 and points[point].y<700:
                if points[point].x>((mouseX-lenght/2)-20) and points[point].x<(mouseX+lenght/2):
                    if points[point].power == 1:
                        lenght=160 
                    elif points[point].power == 2:
                        if lives!=6:
                            lives+=1
                    elif points[point].power == 3:
                        pass
                    elif points[point].power == 4:
                        pass
                        
                    score +=1
                    lenght -= 10
                    del points[point]
        
                    
            elif points[point].y >= 700:
                lives -=1
                del points[point]        
                        
def mousePressed():
    global gameState,lives,points,lenght,score,speed,timer
    a=mouseY//50    
    if gameState==0:
        if a==7:
            gameState=1
    elif gameState==2:
        if a==7:
            lives=5
            points = []
            lenght = 100
            score = 0
            speed = 4
            timer = 980
            gameState = 1 
            bonu = 20