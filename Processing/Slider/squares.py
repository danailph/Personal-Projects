class squares():
    def __init__(self,x,power,speed):
        self.x=x
        self.y=60
        
        if power==1:
            self.color=-16776961
            self.power=1
        elif power==2:
            self.color=-65536
            self.power=2
       
         # elif power==3:
        #     self.color=-27136
        #     self.power=3
        # elif power==4:
        #     self.color=-16711936
        #     self.power=4
        
        else:
            self.color=-1
            self.power=0
        

        
    def show(self):
        fill (self.color)
        rect (self.x,self.y,20,20)
    
    def gravity(self,speed):
        self.y+=speed
        