from time import sleep

class Alarm:
    def remind(self,_lesson):
        for student in _lesson.students:
            student.classbegin(_lesson)

    def attach(self,_student,_lesson):
        _lesson.addstudent(_student)
        print('{} has taken {} class!'.format(_student.name,_lesson.name))

class Lesson:

    def __init__(self,classname):
        self.students = []
        self.name = classname

    def addstudent(self,_student):
        self.students.append(_student)

class Student:

    def __init__(self,name):
        self.name = name

    def classbegin(self,_lesson):
        print('{} go to the {} classroom!'.format(self.name,_lesson.name))

zhang_san = Student('Zhang San')
li_si = Student('Li Si')

math = Lesson('math')
english = Lesson('english')

subscriber = Alarm()
subscriber.attach(zhang_san,math)
subscriber.attach(li_si,english)

sleep(3)
print('time for math class')
subscriber.remind(math)

sleep(3)
print('time for english class')
subscriber.remind(english)