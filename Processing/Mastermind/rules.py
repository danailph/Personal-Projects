import pegs

pointsx=340
def rules(guess,code,points,rounds):
    global pointsx,pointsy,gameState

    redPegs=0
    for a in range (4):
        if guess[a]==code[a]:
            redPegs+=1
            points.append(pegs.pegs(0,pointsx,rounds*80+120))
            pointsx+=40
    if redPegs==4:
        pointsx=340
        return True            
    
    whitePegs=0
    guess2=guess[:]
    code2=code[:]
    for a in range (4):
        if guess[a]==code[a]:
            guess2.remove(int(guess[a]))
            code2.remove(int(guess[a]))
    for pegss in guess2:
        if pegss in code2:
            whitePegs+=1
            points.append(pegs.pegs(1,pointsx,rounds*80+120))
            pointsx+=40
    pointsx=340  