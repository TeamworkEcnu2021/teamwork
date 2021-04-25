import abc

class command(metaclass = abc.ABCMeta):
    @abc.abstractmethod
    def execute(self):
        pass

class invoker():
    def deposite(self,acnt,number):
        acnt.balance += number
        print('successfully deposite {}$'.format(number))
        print('the balance of the bank account {} is {}$'.format(acnt.acnt_number,acnt.balance))

    def withdraw(self,acnt,number):
        if acnt.balance >= number:
            acnt.balance -= number
            print('successfully withdraw {}$'.format(number))
            print('the balance of the bank account {} is {}$'.format(acnt.acnt_number,acnt.balance))
        else:
            print('failed, the balance is not enough')
            print('the balance of the bank account {} is {}$'.format(acnt.acnt_number, acnt.balance))

class depo_cmd(command):
    def __init__(self,invo,acnt,number):
        self.invok = invo
        self.tar_acnt = acnt
        self.number = number

    def execute(self):
        self.invok.deposite(self.tar_acnt,self.number)

class wdr_cmd(command):
    def __init__(self,invo,acnt,number):
        self.invok = invo
        self.tar_acnt = acnt
        self.number = number

    def execute(self):
        self.invok.withdraw(self.tar_acnt,self.number)

class receiver():
    def __init__(self):
        self.orderlist = []

    def takeorder(self,order):
        self.orderlist.append(order)

    def do_all(self):
        for order in self.orderlist:
            order.execute()
        self.orderlist.clear()

class account():
    def __init__(self,acnt_no):
        self.balance = 0
        self.acnt_number = acnt_no
        print('successfully create a bank account')
        print('account number: {}   balance: 0$'.format(acnt_no))

invo = invoker()#指令集
acnt_a = account(1)#创建银行账户a
acnt_b = account(2)#创建银行账户b

order1 = depo_cmd(invo,acnt_a,100)
order2 = wdr_cmd(invo,acnt_a,50)
order3 = wdr_cmd(invo,acnt_a,100)
order4 = depo_cmd(invo,acnt_b,100)
order5 = depo_cmd(invo,acnt_b,20)
order6 = wdr_cmd(invo,acnt_b,70)

rcv = receiver()
rcv.takeorder(order1)
rcv.takeorder(order2)
rcv.takeorder(order3)
rcv.takeorder(order4)
rcv.takeorder(order5)
rcv.takeorder(order6)

rcv.do_all()