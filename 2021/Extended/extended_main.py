import random

class Car:
    def __init__(self, num_of_street, streets):
        self.num_of_street = num_of_street
        self.streets = streets

        '''
        tot_duration = 0
        for street in streets:
            tot_duration += street.time
        self.path_duration = tot_duration
        '''

    def description(self):
        return "Num of street along path: " + str(self.num_of_street) + ". \nDescription: " + str(self.streets)


class Street:
    def __init__(self, start_intersec, end_intersec, name, time):
        self.start_intersec = start_intersec
        self.end_intersec = end_intersec
        self.name = name
        self.time = time
        self.num_of_cars = 0
        self.num_of_reamining_routes = 0
        self.positions_in_path = []

    def get_avg_positions(self):
        if not len(self.positions_in_path) == 0:
            return sum(self.positions_in_path) / len(self.positions_in_path)
        else:
            return 0

    def description(self):
        return "Street " + str(self.name) + " [" + str(self.time) + "s] " + str(self.start_intersec) + " --> " + str(self.end_intersec) + " {" \
               + str(self.num_of_cars) +" car pass} with " + str(self.num_of_reamining_routes) + " remaining routes"

class Intersection():
    def __init__(self, id, input_streets=[], output_streets=[]):
        self.id = id
        self.input_streets = []
        self.output_streets = []

    def is_fake(self):
        return len(self.input_streets) == 1 and len(self.output_streets) == 1

    def description(self):
        if not len(self.input_streets) == 1 and len(self.output_streets) == 1:
            fake_desc = " NOT FAKE "
        else:
            fake_desc = " FAKE "

        return "ID: " + str(self.id) + fake_desc + "Input streets " + str([street.description() for street in self.input_streets]) + " \nOutput streets " + str([street.description() for street in self.output_streets])


class Map:
    def __init__(self, duration, num_of_intersec, num_of_street, num_of_cars, bonus):
        self.duration = duration
        self.num_of_intersec = num_of_intersec
        self.num_of_street = num_of_street
        self.num_of_cars = num_of_cars
        self.bonus = bonus


def read_txt(path):
    f = open(path, "r")
    num_line = 0

    x = f.readline()
    infos = x.split("\n")[0].split(" ")
    duration = int(infos[0])
    num_of_intersec = int(infos[1])
    num_of_street = int(infos[2])
    num_of_cars = int(infos[3])
    bonus = int(infos[4])
    map = Map(duration, num_of_intersec, num_of_street, num_of_cars, bonus)

    intersections = []
    for i in range(num_of_intersec):
        intersections.append(Intersection(i, [], []))

    streets = {}
    for s in range(num_of_street):
        infos = f.readline().split("\n")[0].split(" ")
        start = infos[0]
        end = infos[1]
        name = infos[2]
        time = infos[3]
        ss = Street(start, end, name, time)
        streets[name] = ss
        intersections[int(end)].input_streets.append(ss)
        intersections[int(start)].output_streets.append(ss)


    cars = []
    for v in range(num_of_cars):
        streets_infos = f.readline().split("\n")[0].split(" ")
        count = 0
        name_of_streets = []

        for info in streets_infos:
            if count == 0:
                num_of_streets = streets_infos[0]
            else:
                name = streets_infos[count]
                name_of_streets.append(name)
                streets[name].num_of_cars += 1
            count += 1

        for (n, idx) in zip(name_of_streets, range(1, (len(name_of_streets) + 1))):
            streets[n].num_of_reamining_routes += int(num_of_streets) - idx
            streets[n].positions_in_path.append(idx)

        cars.append(Car(num_of_streets, name_of_streets))

    # Using dictionary comprehension to find list
    # keys having value in 3.
    delete = [key for key in streets if streets[key].num_of_cars == 0]

    # delete the key
    for key in delete:
        del streets[key]

    '''
    for cc in cars:
        print(cc.description())
        print()

    for name in streets:
        print(streets[name].description())

    for ii in intersections:
        print(ii.description())
        print()
    '''

    return streets, cars, intersections, map


def work_fn(level):
    if level == 0:
        file_name = "a"
        result_name = "a_result"
    elif level == 1:
        file_name = "b"
        result_name = "b_result"
    elif level == 2:
        file_name = "c"
        result_name = "c_result"
    elif level == 3:
        file_name = "d"
        result_name = "d_result"
    elif level == 4:
        file_name = "e"
        result_name = "e_result"
    elif level == 5:
        file_name = "f"
        result_name = "f_result"

    path = "./" + file_name + ".txt"
    out = "./" + result_name + ".txt"
    streets, cars, intersections, mappa = read_txt(path)

    result = ""
    count_fake = 0

    for intersection in intersections:
        # Calcolo il peso che ha la ogni strada in input rispetto all'intersezione in esame
        tot_car = sum([street.num_of_cars for street in intersection.input_streets])
        tot_remaing_routes = sum([street.num_of_reamining_routes for street in intersection.input_streets])

        if tot_car == 0 or tot_remaing_routes == 0:
            continue

        count_fake += 1
        result += str(intersection.id) + "\n"
        result += str(len(intersection.input_streets)) + "\n"

        # intersection.input_streets.sort(key=lambda x: (x.num_of_cars, -x.num_of_reamining_routes, x.get_avg_positions()), reverse=True)
        # intersection.input_streets.sort(key=lambda x: x.num_of_reamining_routes, reverse=False)
        intersection.input_streets.sort(key=lambda x: x.num_of_cars, reverse=False)
        # random.shuffle(intersection.input_streets)

        intersec_period = len(intersection.input_streets) * 2
        duration = mappa.duration
        for street in intersection.input_streets:

            result += street.name + " " + str(random.randint(1, 5)) + "\n"
            '''
            percentuale = street.num_of_cars / tot_car
            w = int(percentuale * 5 + 1)

            # w = int(street.num_of_cars / tot_car * (mappa.duration / 12))
            # w = int((tot_cycle_time / duration) * duration)
            # tot_cycle_time -= int(tot_cycle_time / duration)

            # w = int(street.num_of_reamining_routes / tot_remaing_routes * (mappa.duration / 1))
            if not w == 0:
                result += street.name + " " + str(w) + "\n"
            else:
                result += street.name + " " + str(1) + "\n"
            '''
    rr = str(count_fake) + "\n" + result
    f = open(out, "w")
    f.write(rr)
    f.close()



# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    for level in range(6):
        print("START " + str(level))
        work_fn(level)
        print("END " + str(level))

