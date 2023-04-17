import random
# Define the list of cities and their coordinates
cities = {
    'City A': (2, 6),
    'City B': (6, 2),
    'City C': (8, 10),
    'City D': (14, 8),
    'City E': (10, 12)
}

# Set the number of iterations and population size
num_generations = 100
pop_size = 50

# Define the fitness function for each possible route
def fitness(route):
    total_distance = 0
    for i in range(len(route)):
        current_city = route[i]
        next_city = route[(i+1)%len(route)]
        total_distance += ((cities[current_city][0]-cities[next_city][0])**2 
                          + (cities[current_city][1]-cities[next_city][1])**2)**0.5
    return 1/total_distance

# Define the initial population randomly
def initial_population(pop_size):
    population = []
    cities_list = list(cities.keys())
    for i in range(pop_size):
        route = random.sample(cities_list, len(cities_list))
        population.append(route)
    return population

# Define the selection function using tournament selection
def selection(population):
    tournament_size = 3
    tournament = random.sample(population, tournament_size)
    tournament.sort(key=lambda x: fitness(x), reverse=True)
    return tournament[0]

# Define the crossover function using ordered crossover
def crossover(parent1, parent2):
    child = [None]*len(parent1)
    start_index = random.randint(0, len(parent1)-2)
    end_index = random.randint(start_index+1, len(parent1)-1)
    # Copy the slice from parent1 to the child
    child[start_index:end_index+1] = parent1[start_index:end_index+1]
    # Fill in the remaining cities from parent2
    parent2_index = 0
    for i in range(len(child)):
        if child[i] == None:
            while parent2[parent2_index] in child:
                parent2_index += 1
            child[i] = parent2[parent2_index]
            parent2_index += 1
    return child

# Define the mutation function using swap mutation
def mutation(route):
    index1 = random.randint(0, len(route)-1)
    index2 = random.randint(0, len(route)-1)
    route[index1], route[index2] = route[index2], route[index1]
    return route

# Define the main genetic algorithm function
def genetic_algorithm(num_generations, pop_size):
    population = initial_population(pop_size)
    for generation in range(num_generations):
        new_population = []
        for i in range(pop_size):
            parent1 = selection(population)
            parent2 = selection(population)
            child = crossover(parent1, parent2)
            if random.random() < 0.1:
                child = mutation(child)
            new_population.append(child)
        population = new_population
    best_route = max(population, key=lambda x: fitness(x))
    return best_route

# Run the genetic algorithm
best_route = genetic_algorithm(num_generations, pop_size)

# Print the best route and its distance
print('Best route:', best_route)
print('Distance:', 1/fitness(best_route))