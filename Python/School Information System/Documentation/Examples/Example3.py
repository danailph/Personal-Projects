from guizero import *

SYSTEM = App(title = "Example 3", height = 200, width = 400, layout = "grid")
Grid = []

Days = ["Monday","Tuesday","Wednesday","Thursday","Friday"]
for Day in range (5):
    Grid.append( Text(SYSTEM, text = Days[Day], grid = [Day+1,0]) )

Periods = ["08:35","09:45","11:10","13:00","14:10","15:15"]
for Period in range (6):
    Grid.append( Text(SYSTEM, text = Periods[Period], grid = [0,Period+1]) )

SYSTEM.display()
