from guizero import *
import sqlite3
import hashlib
import csv
import time


# ----- Importing the database -----
database = sqlite3.connect('SIS.db', timeout = 10)
cursor = database.cursor()


user_type = 0 #0-Default, 1-Student, 2-Teacher
user_id = 0
user_name = ""


def screen_timetable():
    s_timetable.show()
    s_absences.hide()
    s_grades.hide()
def timetable_fill(user_type, user_id):

    cursor.execute ('''SELECT start
                    From Periods''')
    Periods = cursor.fetchall()
    Periods = [x[0] for x in Periods]
    Days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
    Grid = []

    if user_type == 1:
        query = '''SELECT  Subjects.Subject,
         Teachers.Teacher_Name,
         Teachers.Teacher_Surname
         FROM Lessons
         INNER JOIN Groups ON Groups.ID_Group=Lessons.Group_id
         INNER JOIN Student_Group ON Student_Group.Group_id=Groups.ID_Group
         INNER JOIN Students ON Students.ID_Student = Student_Group.Student_id
         INNER JOIN Teachers ON Teachers.ID_Teacher=Lessons.Group_id
         INNER JOIN Periods ON Periods.ID_Period=Lessons.Period_id
         INNER JOIN Subjects ON Subjects.ID_Subject=Groups.Subject_id
         Where Students.ID_Student= ?
         AND Lessons.Day= ?
         AND Periods.Start= ?
         ;'''
    elif user_type == 2:
        query = '''SELECT Subjects.Subject
            FROM Lessons
            INNER join Groups on Groups.ID_Group = Lessons.Group_id
            INNER join Subjects on Subjects.ID_Subject = Groups.Subject_id
            INNER join Periods on Periods.ID_Period = Lessons.Period_id
            WHERE Teacher_id = ?
            AND Lessons.Day= ?
            AND Periods.Start= ?'''

    for Day in range (5):
        for Period in range (6):
             cursor.execute(query,(user_id,  Days[Day], Periods[Period]))
             data = cursor.fetchone()
             if data == None:
                 Grid.append("")
             elif len(data)>1:
                 Grid.append(data[0]+"\n"+data[1]+" "+data[2])
             else:
                 Grid.append(data)
    return Grid
def timetable_build(user_type, user_id):
    Grid = []

    Days = ["Monday","Tuesday","Wednesday","Thursday","Friday"]
    for Day in range (5):
        Grid.append( Text(s_timetable, text = Days[Day], grid = [Day+1,0]) )

    Periods = ["08:35","09:45","11:10","13:00","14:10","15:15"]
    for Period in range (6):
        Grid.append( Text(s_timetable, text = Periods[Period], grid = [0,Period+1]) )

    for Day in range (5):
        for Period in range (6):
             Grid.append( Text(s_timetable, text = "", grid = [Day+1,Period+1]) )

    lessons = timetable_fill(user_type, user_id)
    for lesson in range(30):
        Grid[lesson+11].value = lessons[lesson]


def validate_pass():
    password = input_confirm_pass.value
    if len(password) >= 8:
        if sum([int(x.islower()) for x in password]) >= 1:
            if sum([int(x.isupper()) for x in password]) >= 1:
                if sum([int(x.islower()) for x in password]) >= 1:
                    return True
    else:
        info(title = "", text = '''Your new password is not valid !

Password need to be:
- 8 characters long
- Have at least 1 digit
- Have at least 1 uppercase AND 1 lowercase character''')
        return False
def screen_change_pass():
    s_login.destroy()
    s_change_pass.show()
def change_pass():
    global user_type, user_id, user_name
    query = ''' UPDATE Students
              SET Password = ?
              WHERE ID_Student = ?'''

    query1 = '''UPDATE Teachers
              SET Password = ?
              WHERE ID_Teacher = ?'''

    if input_new_pass.value == input_confirm_pass.value:
        if validate_pass() == True:
            password = hashlib.md5(input_confirm_pass.value.encode())
            if user_type == 1:
                cursor.execute(query, (password.hexdigest(), user_id))
            elif user_type == 2:
                cursor.execute(query1, (password.hexdigest(), user_id))
            database.commit()
            user_name = input_user.value
            s_change_pass.destroy()
            screen_change_main()
    else:
        info(title = "", text = "Passwords not the same!")


def logIn():
    global user_type, user_id, user_name

    pass_need_change = False
    password = hashlib.md5(input_pass.value.encode())
    if len(input_user.value.split()) == 2:
        user = input_user.value.split()
    else:
        user = ["", ""]

    cursor.execute('''SELECT ID_Student,
                    Password
                    FROM Students
                    WHERE Student_Name=?
                    AND Student_Surname=?''', (user[0],user[1]))
    data = cursor.fetchone()
    if data != None:
        if password.hexdigest() == data[1]:
            user_id = data[0]
            user_type = 1
            user_name = input_user.value
            if input_pass.value == "12345678":
                pass_need_change = True


    if user_type == 0:
        cursor.execute('''SELECT ID_Teacher,
                        Password
                        FROM Teachers
                        WHERE Teacher_Name=?
                        AND Teacher_Surname=?''', (user[0],user[1]))
        data = cursor.fetchone()
        if data != None:
            if password.hexdigest() == data[1]:
                user_id = data[0]
                user_type = 2
                user_name = input_user.value
                if input_pass.value == "12345678":
                    pass_need_change = True




    if pass_need_change:
        screen_change_pass()
    if user_type == 0:
        info(title = "", text = "Wrong Username or Password !")
    elif user_type == 1 or user_type == 2:
        s_login.destroy()
        screen_change_main()
def screen_change_main():
    s_menu.show()
    timetable_build(user_type, user_id)
    s_timetable.show()
    s_logo = Box(SYSTEM, height = "fill", grid = [0,0,10,1])
    l_logo = Text(s_logo, text = "Oxford Best College - "+ user_name, size=20)
def exit():
    database.close()
    SYSTEM.destroy()


def screen_absences():
    s_timetable.hide()
    s_absences.show()
    s_grades.hide()
    display_absences()
def display_absences():
    global user_type, user_name, s_absences_list, text_absences, text_total

    s_absences_list.destroy()
    s_absences_list = Box(s_absences, height = 300, width = 350, grid = [0,0,1,4], align = "left")

    if user_type == 1:
        query = '''select  Date,
          Subjects.Subject
          from Attendance
          inner join Lessons on Lessons.ID_Lesson = Lesson_id
          inner join Groups on Groups.ID_Group = Group_id
          inner Join Subjects on Subjects.ID_Subject = Subject_id
          Where Attendance.Student_id =  ?
          '''
    elif user_type == 2:
        filter_days_absences()
        query = '''select  Date,
          Students.Student_Name,
          Students.Student_Surname
          from Attendance
          inner join Students on Students.ID_student = Student_id
          inner join Lessons on Lessons.ID_Lesson = Lesson_id
          Where Lessons.Teacher_id =  ?
          '''

    cursor.execute(query, str(user_id))
    data = cursor.fetchall()
    text_absences = ""
    for absence in data:
        absence = list(absence)
        absence[0] = str(absence[0])
        absence[0] = absence[0][:2]+"."+absence[0][2:4]+"."+absence[0][4:]
        if len(absence) == 2:
            for item in absence:
                text_absences += str(item) + "  -  "
            text_absences = text_absences[:len(text_absences)-4]
        else:
            text_absences += absence[0] + "  -  " + absence[1] + " " + absence[2]
        text_absences += "\n"
    text_absences = text_absences[:len(text_absences)-1]
    l_absences = TextBox(s_absences_list, text = text_absences, grid = [0,0], align = "top", width = 100, height = 12, scrollbar = True, multiline = True)
    l_absences.disable()
    text_total = ["Total absences:", len(data)]
    l_absences_total = Text(s_absences_list, text = text_total[0]+" "+str(text_total[1]), grid  =[0,1])
    b_absences_reports = PushButton(s_absences_list, absance_report, text = "Export report", grid = [0,2])
def filter_days_absences():
    global user_id, c_abs_days, s_absences_filters
    s_absences_filters.destroy()
    s_absences_filters = Box(s_absences,  grid = [1,0,1,1], align = "top")
    query = ''' Select Day
            from Lessons
            where Teacher_id = ?
            '''
    cursor.execute(query, str(user_id))
    list_days = [x for x in cursor.fetchall()]
    list_days = set([str(x[0]) for x in list_days])
    c_abs_days = Combo(s_absences_filters, options=list_days, command=filter_periods_absences, grid=[0,0], width=12, align="top")
def filter_periods_absences(selected_value):
    global user_id, c_abs_periods
    query = ''' select Start
            from Lessons
            inner join Periods on Periods.ID_Period = Lessons.Period_id
            Where Teacher_id = ?
            and Day = ?'''
    cursor.execute(query,(str(user_id),selected_value))
    list_periods = [str(x[0]) for x in cursor.fetchall()]
    list_periods =  sorted(set([x[:2]+":"+x[2:4] for x in list_periods]))
    c_abs_periods = Combo(s_absences_filters, options=list_periods, command=list_students_append, grid=[0,1], width=12, align="left")
def list_students_append(selected_value):
    global user_id, list_students, s_absences_append
    s_absences_append.destroy()
    s_absences_append = Box(s_absences, layout = "grid", align = "left", grid = [1,1,1,3])
    day = c_abs_days.value
    periods = c_abs_periods.value
    query = '''select Students.Student_Name,
            Students.Student_Surname,
            Students.ID_Student
            from Lessons
            inner join Student_Group on Student_Group.Group_id = Lessons.Group_id
            inner join Students on Students.ID_Student = Student_Group.Student_id
            inner join Periods on Periods.ID_Period = Lessons.Period_id
            where Day = ?
            and Periods.Start = ?
            and Teacher_id = ?'''
    cursor.execute(query, (str(day),str(periods[:2]+periods[3:]),str(user_id) ))
    data = cursor.fetchall()
    list_students = []
    for student in range(len(data)):
        list_students.append( CheckBox(s_absences_append, command = get_absent, text = str(str(data[student][2])+" - "+data[student][0]+" "+data[student][1]), grid = [0,student], align = "left"))
    button_submit_absences = PushButton(s_absences_append, add_absences, text = "Submit absences", grid = [0,(student+1)])
def get_absent():
    global list_students, list_absences
    list_absences=[]
    for student in list_students:
        if student.value == 1:
            list_absences.append(student.get_text())
def add_absences():
    global list_absences, user_id, s_absences_append
    query = '''select ID_lesson
                FROM
                Lessons
                inner join Periods on Periods.ID_Period = Period_id
                Where Day = ?
                and Periods.Start = ?
                and Teacher_id = ?'''
    cursor.execute(query, (str(c_abs_days.value), str(c_abs_periods.value[:2]+c_abs_periods.value[3:]), str(user_id)))
    lesson_id = cursor.fetchone()[0]
    query = '''INSERT INTO Attendance
            (Lesson_id, Student_id, Date)
            values(?,?,?)'''
    for student in list_absences:
        student = student.split("-")
        cursor.execute(query, (lesson_id, student[0][0],time.strftime("%d%m%Y", time.localtime())))
        database.commit()
    screen_absences()
    s_absences_append.destroy()
    s_absences_append = Box(s_absences, grid = [0,1])
def absance_report():

    global text_absences, text_total

    text_absences_2 = text_absences.split("\n")
    with open('Report_Attendance.csv', 'w') as csvfile:
        filewriter = csv.writer(csvfile, delimiter=',', quotechar = '|', quoting = csv.QUOTE_MINIMAL)
        filewriter.writerow(["Date","Name"])
        for line in text_absences_2:
            line = line.strip()
            line = line.split(" - ")
            filewriter.writerow(line)
        filewriter.writerow(text_total)


def screen_grades():
    s_timetable.hide()
    s_absences.hide()
    s_grades.show()
    display_grades()
def display_grades():
    global user_type, user_id, s_grades_list, text_grades
    s_grades_list.destroy()
    s_grades_list = Box(s_grades,  height = 300, width = 350, grid = [0,0,1,4], align = "left")

    if user_type == 1:
        query = '''select  Date,
          Subjects.Subject,
          Grade

          from Grades
          inner join Lessons on Lessons.ID_Lesson = Lesson_id
          inner join Groups on Groups.ID_Group = Group_id
          inner Join Subjects on Subjects.ID_Subject = Subject_id
          Where Grades.Student_id =  ?'''
    elif user_type == 2:
        query = '''select  Date,
          Students.Student_Name,
          Students.Student_Surname,
          Grade
          from Grades
          inner join Students on Students.ID_student = Student_id
          inner join Lessons on Lessons.ID_Lesson = Lesson_id
          Where Lessons.Teacher_id =  ?'''
        filter_days_grades()

    cursor.execute(query, str(user_id))
    data = cursor.fetchall()
    text_grades = ""
    for grade in data:
        grade = list(grade)
        grade[0] = str(grade[0])
        grade[0] = grade[0][:2]+"."+grade[0][2:4]+"."+grade[0][4:]

        if len(grade) == 3:
            for item in grade:
                text_grades += str(item) + "  -  "
            text_grades = text_grades[:len(text_grades)-4]
        else:
            text_grades += grade[0] + "  -  " + grade[1] + " " + grade[2] + "  -  "+ grade[3]
        text_grades += "\n"
    text_grades = text_grades[:len(text_grades)-1]
    l_grades = TextBox(s_grades_list, text = text_grades, grid = [0,0], align = "top", width = 100, height = 13, scrollbar = True, multiline = True)
    l_grades.disable()
    b_grades_reports = PushButton(s_grades_list, grades_report, text="Export report", grid = [0,1])
def filter_days_grades():
    global user_id, s_grades_filters, c_days
    s_grades_filters.destroy()
    s_grades_filters = Box(s_grades, grid = [1,0,1,1], align = "top" )
    query = ''' Select Day
            from Lessons
            where Teacher_id = ?
            '''
    cursor.execute(query, str(user_id))
    list_days = [x for x in cursor.fetchall()]
    list_days = set([str(x[0]) for x in list_days])
    c_days = Combo(s_grades_filters, options=list_days, command=filter_periods_grades, grid=[0,0], width=12, align="top")
def filter_periods_grades(selected_value):
    global user_id, c_periods
    query = ''' select Start
            from Lessons
            inner join Periods on Periods.ID_Period = Lessons.Period_id
            Where Teacher_id = ?
            and Day = ?'''
    cursor.execute(query,(str(user_id),selected_value))
    list_periods = [str(x[0]) for x in cursor.fetchall()]
    list_periods =  sorted(set([x[:2]+":"+x[2:4] for x in list_periods]))
    c_periods = Combo(s_grades_filters, options = list_periods, command = filter_students_grades, grid = [0,1], width = 12, align = "top")
def filter_students_grades(selected_value):
    global user_id, c_students
    day = c_days.value
    periods = c_periods.value
    query = '''select Students.Student_Name,
            Students.Student_Surname,
            Students.ID_Student
            from Lessons
            inner join Student_Group on Student_Group.Group_id = Lessons.Group_id
            inner join Students on Students.ID_Student = Student_Group.Student_id
            inner join Periods on Periods.ID_Period = Lessons.Period_id
            where Day = ?
            and Periods.Start = ?
            and Teacher_id = ?'''
    cursor.execute(query, (str(day),str(periods[:2]+periods[3:]),str(user_id) ))
    list_students = [str(x[0]+" "+x[1]) for x in cursor.fetchall()]
    c_students = Combo(s_grades_filters, options = list_students, command = filter_grades_grades, grid = [0,2], width = 12, align = "top")
def filter_grades_grades():
    global c_grades
    list_grades = ["A*", "A", "B", "C", "D", "E", "U"]
    c_grades = Combo(s_grades_filters, options = list_grades, command = get_grade, grid = [0,3], width = 12, align = "top")
def get_grade():
    button_submit_grades =  PushButton(s_grades_filters, add_grades, text = "Submit grade", grid = [0,4], width = 12, align = "top")
def add_grades():
    global s_grades_append
    query = '''SELECT ID_lesson
                FROM
                Lessons
                INNER JOIN Periods ON Periods.ID_Period = Period_id
                Where Day = ?
                AND Periods.Start = ?
                AND Teacher_id = ?'''
    cursor.execute(query, (str(c_days.value), str(c_periods.value[:2]+c_periods.value[3:]), str(user_id)))
    lesson_id = cursor.fetchone()[0]

    query = '''SELECT ID_Student
                FROM Students
                WHERE Student_Name = ?
                AND Student_Surname = ?
                '''
    student_name = str(c_students.value).split()
    cursor.execute(query, (student_name))
    student_id = cursor.fetchone()[0]

    query = '''INSERT INTO Grades
            (Lesson_id, Student_id, Grade, Date)
            values(?,?,?,?)'''
    student = str(c_students.value)

    cursor.execute(query, (lesson_id, student_id, c_grades.value,time.strftime("%d%m%Y", time.localtime())))
    database.commit()
    screen_grades()
    s_grades_append.destroy()
    s_grades_append = Box(s_grades, layout = "auto", grid = [0,1])
def grades_report():
    text_grades_2 = text_grades.split("\n")
    with open('Report_Grades.csv', 'w') as csvfile:
        filewriter = csv.writer(csvfile, delimiter=',', quotechar = '|', quoting = csv.QUOTE_MINIMAL)
        filewriter.writerow(["Date","Name","Grade"])
        for line in text_grades_2:
            line = line.strip()
            line = line.split(" - ")
            filewriter.writerow(line)


# ----- GUI -----
SYSTEM = App(title = "School Information System", height = 350, width = 1000, layout = "grid")

# ----- Log In -----
s_login = Box(SYSTEM, layout = "grid", grid = [0,0], align = "top")
l_logo = Text(s_login, text = "Oxford Best College", size = 26, grid = [0,0,2,1])
l_user = Text(s_login, text = "Username:", grid = [0,2], align = "right")
l_pass = Text(s_login, text = "Password:", grid = [0,3], align = "right")
b_login = PushButton(s_login, logIn, text = "Log In", width = 10, grid = [0,4])
b_exit = PushButton(s_login, exit, text = "Exit", width = 10, grid = [1,4])
input_user = TextBox(s_login, grid = [1,2], align="left")
input_pass = TextBox(s_login, grid = [1,3], align="left")
input_user.focus()

# ----- Change default password -----
s_change_pass = Box(SYSTEM, layout = "grid", grid = [0,0], align = "top")
l_logo = Text(s_change_pass, text = "Oxford Best College", size = 26, grid = [0,0,2,1])
l_new_pass = Text(s_change_pass, text = "Enter new Pasword:", grid = [0,1], align = "right")
l_confirm_pass = Text(s_change_pass, text = "Confirm Password:", grid = [0,2], align = "right")
input_new_pass = TextBox(s_change_pass, grid = [1,1], align = "left")
input_confirm_pass = TextBox(s_change_pass, grid = [1,2], align = "left")
button_submit = PushButton(s_change_pass, change_pass, text = "Submit", width = 10, grid = [0,3])
button_exit = PushButton(s_change_pass, exit, text = "Exit", width = 10, grid = [1,3])
input_new_pass.focus()
s_change_pass.hide()

# ----- Menu bar -----
s_menu = Box(SYSTEM, height = "fill", layout = "grid", align = "left", grid = [0,1])
button_timetable = PushButton(s_menu, screen_timetable, text = "Timetable", width = 10, height = 2, grid = [0,2,2,1])
button_absences = PushButton(s_menu, screen_absences, text = "Absences", width = 10, height = 2, grid = [0,3,2,1])
button_grades = PushButton(s_menu, screen_grades, text = "Grades", width = 10, height = 2, grid = [0,4,2,1])
button_exit = PushButton(s_menu, exit, text = "Exit", width = 10, height = 2, grid = [0,5,2,1])
s_menu.hide()

# ----- Timetable -----
s_timetable = Box(SYSTEM, height = "fill", layout = "grid", align = "left", grid = [2,1])
s_timetable.hide()

# ----- Absences -----
s_absences = Box(SYSTEM, height = "fill", layout = "grid", align = "left", grid = [2,1])
s_absences_list = Box(s_absences, grid = [0,0])
s_absences_filters = Box(s_absences, grid = [1,0])
s_absences_append = Box(s_absences, grid = [0,1])
s_absences.hide()

# ----- Grades -----
s_grades = Box(SYSTEM, height = "fill", layout = "grid", align = "left", grid = [2,1])
s_grades_list = Box(s_grades, grid = [0,0])
s_grades_filters = Box(s_grades, grid = [1,0])
s_grades_append = Box(s_grades, layout = "auto", grid = [0,1])
s_grades.hide()

# s_login.destroy()
# s_menu.show()
# lessons = timetable_build(user_type, user_id)
# s_timetable.show()
SYSTEM.display()
