import pandas as pd
import numpy as np
import random
import math
import sys

# for i, arg in enumerate(sys.argv):
#     print(f"Argument {i:>6}: {arg}")

width = float(sys.argv[1])         # ширина слоев
height_1 = float(sys.argv[2])      # высота 1 слоя
height_2 = float(sys.argv[3])      # высота 2 слоя
height_3 = float(sys.argv[4])      # высота 3 слоя
length_fiber = float(sys.argv[5])  # длина короткого волокна
depth = float(sys.argv[6])         # толщина слоев
diameter = float(sys.argv[7])      # диаметр волокна
min_length = float(sys.argv[8])    # расстояние между волокнами
volume = float(sys.argv[9])        # площадь волокон
num = float(sys.argv[10])          # количество волокон
mode = float(sys.argv[11])         # тип упаковки волокон

# width = 90 / 1000      # ширина слоев
# height_1 = 0   # высота 1 слоя
# height_2 = 20 / 1000  # высота 2 слоя
# height_3 = 0          # высота 3 слоя
# length_fiber = 10 /1000 # длина короткого волокна
# depth = 100 / 1000      # толщина слоев
# diameter = 3 / 1000   # диаметр волокна
# min_length = 1 / 1000  # расстояние между волокнами
# volume = 0            # площадь волокон
# num = 0               # количество волокон
# mode = 0              # тип упаковки волокон
#
fibers = []
coord = {}

def find_coord(width, diameter, min_length, delta):
    coords = []
    r = diameter / 2
    count_fiber_x = math.floor(width / (diameter + min_length))
    length = (width - count_fiber_x * (diameter + min_length)) / count_fiber_x

    delta_x = min_length + length

    for i in range(count_fiber_x):
        if (i == 0):
            x = ((min_length + length) / 2) + delta
        else:
            x += (min_length + length) + diameter
        coords.append(x)

    return coords

def write_results(fibers):
    output_file = open('results.lgw', 'w', encoding='utf-8')
    output_file.write("/PREP7\n")
    for dic in fibers:
        output_file.write("CYL4,{0:.5f},{1:.5f},{2:.5f}, , , , {3:f}\n".format(dic['x'], dic['y'], diameter/2, length_fiber))

    output_file.write("BLC4,0,0,{0:.5f},{1:.5f},{2:.5f}\n".format(width, height_2, depth))

    i = 1
    for dic in fibers:
        output_file.write("FLST, 3, 1, 6, ORDE, 1\n")
        output_file.write("FITEM, 3, {0:d}\n".format(i))
        output_file.write("VGEN, , P51X, , , , , {0:.5f}, , , 1\n".format(dic['z']))
        i += 1

    output_file.write("vovlap, all\n")
    output_file.write("vglue, all")
    output_file.close()

X = find_coord(width, diameter, min_length, diameter/2)
Y = find_coord(height_2, diameter, min_length, diameter/2)
Z = find_coord(depth, length_fiber, min_length, 0)

count_x = len(X)
count_y = len(Y)
count_z = len(Z)

for i in range(count_x):
    for j in range(count_y):
        for k in range(count_z):
            coord['x'] = X[i]
            coord['y'] = Y[j]
            coord['z'] = Z[k]
            fibers.append(coord.copy())

write_results(fibers)

print(fibers[1]['z'])

print('Finish!')

percent = 2/100

fibers = []
coord = {}

print(percent*width*height_2*depth)

while (volume < percent*width*height_2*depth):
    coord['x'] = (width-diameter-min_length)*random.random() + (diameter+min_length)/2
    coord['y'] = (height_2-diameter-min_length)*random.random() + (diameter+min_length)/2
    coord['z'] = (depth-diameter-min_length)*random.random() + (diameter+min_length)/2

    #print(coord['x'])

    overlapping = False
    for i in range(len(fibers)):
        dist = math.sqrt(math.pow(fibers[i]['x']-coord['x'], 2) + math.pow(fibers[i]['y']-coord['y'], 2) + math.pow(fibers[i]['z']-coord['z'], 2))
        if (dist <= min_length + diameter):
            overlapping = True
            break
    if (overlapping == False):
        fibers.append(coord.copy())
        volume += (4 * math.pi * math.pow(diameter/2, 3)) / 3
        print(volume)

print('Finish')
print(len(fibers))
