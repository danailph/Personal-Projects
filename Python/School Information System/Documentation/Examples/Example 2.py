from guizero import *

SYSTEM = App(title = "Example 2", height = 300, width = 300)
s_login = Box(SYSTEM, layout = "grid", grid = [0,0], align = "top")
l_logo = Text(s_login, text = "Oxford Best College", size = 26, grid = [0,0,2,1])
l_user = Text(s_login, text = "Username:", grid = [0,2], align = "right")
l_pass = Text(s_login, text = "Password:", grid = [0,3], align = "right")
b_login = PushButton(s_login, text = "Log In", width = 10, grid = [0,4])
b_exit = PushButton(s_login, exit, text = "Exit", width = 10, grid = [1,4])
input_user = TextBox(s_login, grid = [1,2], align="left")
input_pass = TextBox(s_login, grid = [1,3], align="left")
input_user.focus()

SYSTEM.display()
