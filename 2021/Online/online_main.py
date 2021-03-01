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

    def description(self):
        return "Street " + str(self.name) + " [" + str(self.time) + "s] " + str(self.start_intersec) + " --> " + str(self.end_intersec)

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
    map = None

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
        infos = f.readline().split("\n")[0].split(" ")
        count = 0
        name_of_streets = []
        streets_on_path = []

        for info in infos:
            if count == 0:
                num_of_streets = infos[0]
            else:
                name_of_streets.append(infos[count])
                streets[infos[count]].num_of_cars += 1
            count += 1

        cars.append(Car(num_of_streets, name_of_streets))

    '''
    for cc in cars:
        print(cc.description())
        print()

    for ss in streets:
        print(ss.description())

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
        tot_car = sum([street.num_of_cars for street in intersection.input_streets])

        if not tot_car == 0:
            count_fake += 1
            result += str(intersection.id) + "\n"
            result += str(len(intersection.input_streets)) + "\n"
            for street in intersection.input_streets:
                w = int(street.num_of_cars / tot_car * (mappa.duration / 1))
                if not w == 0:
                    result += street.name + " " + str(w) + "\n"
                else:
                    result += street.name + " " + str(1) + "\n"

    '''
    if intersection.is_fake():
        count_fake += 1
        result += str(intersection.id) + "\n"
        result += "1\n"
        assert len(intersection.input_streets) == 1
        result += intersection.input_streets[0].name + " 2\n"
    else:
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

