def userInterface(gameState,points,lenght,score,lives,speed):
    
    background (0)
    
    if gameState==0:
        record = readRecord()
        
        fill(-1)
        textSize(70)
        text("Slider",140,200)
        textSize(55)
        text("Play",185,400)
        text('Record:'+str(record),100,550)
    
    elif gameState==1:
        
        fill (-1)
        textSize(30)
        text (str(score),350,50)
        
        for point in points:
            point.show()
            point.gravity(speed)
        
        fill (-16776961)
        rect(mouseX-lenght/2,680,lenght,20)
        
        fill(-65536)
        stroke(-1)
        for a in range (lives):
            ellipse(30+a*35,30,30,30)
        stroke(0)
        
        
            
    elif gameState==2:
        record = readRecord()
        fill(-1)
        textSize(55)
        text("Play Again",125,400)
        text("You Lost!",130,120)
        if int(score)>int(record):
            changeRecord(score)
        
        textSize(30)
        text("Record: "+str(record),170,600)
        text("Score: "+str(score),190,550)
        
        
def readRecord():
    file = open("Record.txt", 'r')
    record = file.readlines()
    return record[-1].rstrip()
    file.close() 
    
def changeRecord(score):
    file = open("Record.txt", 'a')
    file.write('\n'+str(score))
    file.close()