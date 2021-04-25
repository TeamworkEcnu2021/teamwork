import abc

class model(metaclass = abc.ABCMeta):
    @abc.abstractmethod
    def display(self):
        pass

class male_model(model):
    def display(self):
        print('this is a male model')

class female_model(model):
    def display(self):
        print('this is a female model')

class model_decorator(model):
    def __init__(self,deco_model):
        self.de_model = deco_model

    def display(self):
        self.de_model.display()

class shirt_model_deco(model_decorator):
    def __init__(self,deco_model):
        model_decorator.__init__(self,deco_model)

    def display(self):
        self.de_model.display()
        self.wear_shirt(self.de_model)

    def wear_shirt(self,deco_model):
        print('wearing a shirt')

class suit_model_deco(model_decorator):
    def __init__(self,deco_model):
        model_decorator.__init__(self,deco_model)

    def display(self):
        self.de_model.display()
        self.wear_suit(self.de_model)

    def wear_suit(self,deco_model):
        print('wearing a suit')

model_a = male_model()
model_b = female_model()

model_a.display()
model_b.display()

model_a_with_shirt = shirt_model_deco(model_a)
model_b_with_suit = suit_model_deco(model_b)

print('model a and b have dressed up')
model_a_with_shirt.display()
model_b_with_suit.display()