class dots():
    def __init__(self, colors, click , round):
        self.color=colors
        self.x = click*80-40
        self.y = 80+(round*80+40)
    def show(self):
        fill (self.color)
        ellipse (self.x,self.y,70,70)