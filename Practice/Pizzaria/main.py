import numpy as np
import bisect

from functools import lru_cache as cache
from multiprocessing import Pool

class Order:
    def __init__(self, num_of_team_2, num_of_team_3, num_of_team_4):
        self.num_of_team_2 = num_of_team_2
        self.num_of_team_3 = num_of_team_3
        self.num_of_team_4 = num_of_team_4
        self.total_pizzas = num_of_team_2*2 + num_of_team_3*3 + num_of_team_4*4

    def description(self):
        return "Team #2 " + str(self.num_of_team_2) + "\nTeam #3 " + str(self.num_of_team_3) +\
               "\nTeam #4 " + str(self.num_of_team_4) + "\nTotal pizzas " + str(self.total_pizzas)

class Pizzaria:
    def __init__(self, number_of_type_of_pizza, pizzas_list):
        self.number_of_type_of_pizza = number_of_type_of_pizza
        self.pizzas_list = pizzas_list
        #self.matrix_of_differences = calc_differences(pizzas_list)

    def description(self):
        pizzas_list_desc = ""
        for pizza in self.pizzas_list:
            pizzas_list_desc += "   " + pizza.description() + "\n"
        return "List:\n" + pizzas_list_desc + "\nNumber of pizza available: " + str(self.number_of_type_of_pizza)


class Pizza:
    def __init__(self, num_of_ingredients, ingredients):
        self.num_of_ingredients = num_of_ingredients
        self.ingredients = ingredients

    def description(self):
        return "Num of ingredients: " + str(self.num_of_ingredients) + " Ingredients List: " + str(self.ingredients)


def calc_differences(pizzas_list):
    s = (len(pizzas_list), len(pizzas_list))
    differences_matrix = np.zeros(s)
    for i in range(len(pizzas_list) - 1):
        pizza_i = pizzas_list[i]
        for j in range(i+1, len(pizzas_list)): # matrice triangolare
        # for j in range(len(pizzas_list) - 1):
            if not j == i:
                pizza_j = pizzas_list[j]
                diff = unique_ingredient(pizza_i, pizza_j)
                differences_matrix[i, j] = diff
    return differences_matrix


def read_txt(path):
    f = open(path, "r")
    num_line = 0
    num_of_pizzas_available = 0
    num_of_ingrs = 0
    pizzas_type = []

    for x in f:
        if num_line == 0:
            infos = x.split(" ")
            num_of_pizzas_available = int(infos[0])
            num_of_team_2 = int(infos[1])
            num_of_team_3 = int(infos[2])
            num_of_team_4 = int(infos[3])
            order = Order(num_of_team_2, num_of_team_3, num_of_team_4)
        else:
            pizza_infos = x.split(" ")
            num_of_ingrs = int(pizza_infos[0])
            ingredients = []
            for i in range(1, (num_of_ingrs + 1)):
                ingredients.append(pizza_infos[i])
            pizzas_type.append(Pizza(num_of_ingrs, ingredients))
        num_line += 1
    piffo_pizz = Pizzaria(num_of_pizzas_available, pizzas_type)
    print(piffo_pizz.description())
    print(order.description())

    return order, piffo_pizz


def unique_ingredient(pizza1, pizza2):
    tot_ingr = pizza1.ingredients + pizza2.ingredients
    return len(list(set(tot_ingr)))


def num_of_ing(pizza): return pizza.num_of_ingredients
def knapsack_greedy(items, max_pizza, keyFunc, depth):
    items = sorted(items, key=keyFunc, reverse=True)
    ordine = [items[0]]
    items.remove(items[0])
    num_ingr_unique = 0
    remaining_pizza = max_pizza - 1

    temp_items = items.copy()

    for i in range(depth):
        max_overlap = -50
        choice = None
        for item in temp_items:
            resulting_list = list(item.ingredients)
            resulting_list.extend(x for x in item.ingredients if x not in resulting_list)
            if len(resulting_list) > max_overlap:
                max_overlap = len(resulting_list)
                choice = item

        remaining_pizza -= 1
        num_ingr_unique += item.num_of_ingredients
        assert not choice == None
        ordine.append(choice)
        temp_items.remove(choice)

    return ordine, num_ingr_unique


@cache(maxsize=None)
def work_fn(level):
    if level == 0:
        file_name = "a_example"
        result_name = "a_example_result"
    elif level == 1:
        file_name = "b_little_bit_of_everything"
        result_name = "b_little_bit_of_everything_result"
    elif level == 2:
        file_name = "c_many_ingredients"
        result_name = "c_many_ingredients_result"
    elif level == 3:
        file_name = "d_many_pizzas"
        result_name = "d_many_pizzas_result"
    elif level == 4:
        file_name = "e_many_teams"
        result_name = "e_many_teams_result"

    path = "./" + file_name + ".txt"
    out = "./" + result_name + ".txt"
    order, piffo_pizz = read_txt(path)

    # mat_differences = piffo_pizz.matrix_of_differences
    num_of_teams_served = 0
    pizza_indexes_used = []
    deliveries = ""

    temp_pizza_list = piffo_pizz.pizzas_list.copy()
    temp_pizza_list.sort(key=lambda x: x.num_of_ingredients, reverse=True)
    for team_4 in range(order.num_of_team_4):
        if len(temp_pizza_list) >= 4:
            components = 4
            deliveries += str(components) + " "
            max_weight = components
            items = temp_pizza_list
            k, v = knapsack_greedy(items, max_weight, num_of_ing, 4)
            #print('value: %d\nknapsack: %s' % (v, k))
            num_of_teams_served += 1

            for pizza in k:
                index = piffo_pizz.pizzas_list.index(pizza)
                deliveries += str(index) + " "

                index = temp_pizza_list.index(pizza)
                del temp_pizza_list[index]

            deliveries += "\n"
        else:
            break

    for team_3 in range(order.num_of_team_3):
        if len(temp_pizza_list) >= 3:
            components = 3
            deliveries += str(components) + " "
            max_weight = components
            items = temp_pizza_list
            k, v = knapsack_greedy(items, max_weight, num_of_ing, 3)
            #print('value: %d\nknapsack: %s' % (v, k))
            num_of_teams_served += 1

            for pizza in k:
                index = piffo_pizz.pizzas_list.index(pizza)
                deliveries += str(index) + " "

                index = temp_pizza_list.index(pizza)
                del temp_pizza_list[index]

            deliveries += "\n"
        else:
            break

    for team_2 in range(order.num_of_team_2):
        if len(temp_pizza_list) >= 2:
            components = 2
            deliveries += str(components) + " "
            max_weight = components
            items = temp_pizza_list
            k, v = knapsack_greedy(items, max_weight, num_of_ing, 2)
            #print('value: %d\nknapsack: %s' % (v, k))
            num_of_teams_served += 1

            for pizza in k:
                index = piffo_pizz.pizzas_list.index(pizza)
                deliveries += str(index) + " "

                index = temp_pizza_list.index(pizza)
                del temp_pizza_list[index]

            deliveries += "\n"
        else:
            break

    f = open(out, "w")
    f.write(str(num_of_teams_served) + "\n" + deliveries)
    f.close()

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    for level in range(3):
        work_fn(level)
