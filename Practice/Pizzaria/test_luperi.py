from collections import OrderedDict

filepaths = [
    'a_example.txt',
    'b_little_bit_of_everything.txt',
    'c_many_ingredients.txt',
    'd_many_pizzas.txt',
    'e_many_teams.txt'
]

def get_coeff(list1, dict1):
    equals_num = 0
    tot = len(list1)+len(dict1)
    for i in list1:
        if i in dict1:
            equals_num += 1
    diff_num = tot - equals_num*2
    return diff_num/tot*diff_num

def run_algo(filepath, output_filepath):
    file1 = open(filepath, 'r')
    Lines = file1.readlines()

    count = 0
    pizzas = []
    list_of_ingredients = {}
    pizzas_per_ingredient = {}
    ingredients_per_pizza = {}
    teams = {}
    # Strips the newline character
    for line in Lines:
        x = line.split()
        # HEADER
        if count == 0:
            pizzas_number = int(x[0])
            teams[2] =int(x[1])
            teams[3] = int(x[2])
            teams[4] = int(x[3])
        # PIZZA
        else:
            pizza_id = count - 1
            ingredients_number = int(x[0])
            ingredients_per_pizza[pizza_id] = ingredients_number
            pizza_ingredients = {}
            for i in range(1, ingredients_number+1):
                ing = x[i]
                # save pizza
                if ing in pizzas_per_ingredient:
                    pizzas_per_ingredient[ing].append(
                        pizza_id)  # append number of pizza
                else:
                    pizzas_per_ingredient[ing] = [pizza_id]
                if ing in list_of_ingredients:
                    list_of_ingredients[ing] += 1
                else:
                    list_of_ingredients[ing] = 1
                pizza_ingredients[x[i]] = 1
            pizzas.append(pizza_ingredients)
        count += 1

    # ordered_list_of_ingredients = dict(
    #     sorted(list_of_ingredients.items(), key=lambda item: item[1]))
    ordered_ingredients_per_pizza = dict(
        sorted(ingredients_per_pizza.items(), reverse=True, key=lambda item: item[1]))

    assert(len(pizzas) == pizzas_number)

    pizza_id_ordered_ingredient = list(ordered_ingredients_per_pizza)

    # print("pizzas", pizzas)
    # print("pizzas_per_ingredient", pizzas_per_ingredient)
    # print("ordered_ingredients_per_pizza", ordered_ingredients_per_pizza)

    # assign pizzas to teams
    team_pizzas = {
        4: [],
        3: [],
        2: []
    }
    pss = {
        4: [],
        3: [],
        2: []
    }

    for team_index in range(3):
        while len(pizza_id_ordered_ingredient) > 3-team_index and teams[3-team_index+1] > 0:
            pss[3-team_index+1] = []
            id_pizza = pizza_id_ordered_ingredient[0]
            del pizza_id_ordered_ingredient[0]
            pss[3-team_index+1].append(str(id_pizza))

            for _ in range(3-team_index):
                best_score = 0
                best_comapare_pizza_id = None
                best_compare_pizza_index = None
                index = 0
                list_of_ingredients = []
                for ip in pss[3-team_index+1]:
                    list_of_ingredients += list(pizzas[int(ip)])
                for compare_pizza_id in pizza_id_ordered_ingredient:
                    if len(list_of_ingredients) + len(pizzas[compare_pizza_id]) < best_score:
                        break
                    coeff = get_coeff(list_of_ingredients, pizzas[compare_pizza_id])
                    if coeff == 0:
                        pass
                        # for other_team_index in range(team_index+1, 3):
                        #     if teams[3-other_team_index+1] > 0:
                        #         pss[3-other_team_index+1].append(compare_pizza_id)
                        #         del pizza_id_ordered_ingredient[index]
                        #         teams[3-team_index+1] -= 1
                        #         break
                    else:
                        if coeff > best_score:
                            best_score = coeff
                            best_comapare_pizza_id = compare_pizza_id
                            best_compare_pizza_index = index
                    index += 1
                if not best_comapare_pizza_id is None and not best_compare_pizza_index is None:
                    pss[3-team_index+1].append(str(best_comapare_pizza_id))
                    del pizza_id_ordered_ingredient[best_compare_pizza_index]
            team_pizzas[4-team_index].append(pss[3-team_index+1])
            teams[3-team_index+1] -= 1

    # writing to file
    lines = []
    file1 = open(output_filepath, 'w')
    tot = 0
    for team_index in range(3):
        for pzs in team_pizzas[3-team_index+1]:
            if len(pzs) > 0:
                tot += 1
                lines.append("\n" + str(3-team_index+1) + ' ' + ' '.join(pzs))
    lines.insert(0, str(tot))
    file1.writelines(lines)
    file1.close()


def judge(filepath, output_filepath):
    file = open(filepath, 'r')
    lines = file.readlines()
    count = 0
    pizzas = []
    for line in lines:
        x = line.split()
        if count == 0:
            pizzas_number = int(x[0])
        else:
            ingredients_number = int(x[0])
            ingredients = []
            for i in range(1, ingredients_number+1):
                ingredients.append(x[i])
            pizzas.append(ingredients)
        count += 1
    assert(len(pizzas) == pizzas_number)

    # read output file
    tot_score = 0
    filepath = output_filepath
    file = open(filepath, 'r')
    lines = file.readlines()
    count = 0
    for line in lines:
        x = line.split()
        if count == 0:
            num_delivery = int(x[0])
        else:
            team_people = int(x[0])
            ingredients = set()
            for i in range(1, team_people + 1):
                pizza_id = int(x[i])
                pizza_ingredients = pizzas[pizza_id]
                for ingredient in pizza_ingredients:
                    ingredients.add(ingredient)
            tot_score += len(ingredients)**2
        count += 1
    print(tot_score)


for filepath in filepaths:
    print("Running on: ", filepath)
    run_algo(filepath, filepath+'.out')
    judge(filepath, filepath+'.out')