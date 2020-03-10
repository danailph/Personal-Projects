class pegs():
    def __init__(self, colors, pointsx , pointsy):
        
        if colors == 0 :
            self.color=-65536
        else:
            self.color=-2
        self.x = pointsx
        self.y = pointsy

    def show(self):
        fill (self.color)
        ellipse (self.x,self.y,30,30)