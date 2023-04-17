import heapq

goal_state = (1, 2, 3, 4, 5, 6, 7, 8, 0)


def h(state):
    distance = 0
    for i in range(9):
        if state[i] != 0:
            row = abs(i // 3 - (state[i]-1) // 3)
            col = abs(i % 3 - (state[i]-1) % 3)
            distance += row + col
    return distance


def astar(start_state):
    open_set = []
    closed_set = set()

    node = (0, tuple(start_state), [])
    heapq.heappush(open_set, node)

    while open_set:
        f, current_state, path = heapq.heappop(open_set)

        if current_state == goal_state:
            return path

        closed_set.add(tuple(current_state))

        for i in range(9):
            if current_state[i] == 0:
                break
        successors = []
        if i > 2:
            new_state = list(current_state)
            new_state[i], new_state[i-3] = new_state[i-3], new_state[i]
            successors.append(tuple(new_state))
        if i < 6:
            new_state = list(current_state)
            new_state[i], new_state[i+3] = new_state[i+3], new_state[i]
            successors.append(tuple(new_state))
        if i % 3 > 0:
            new_state = list(current_state)
            new_state[i], new_state[i-1] = new_state[i-1], new_state[i]
            successors.append(tuple(new_state))
        if i % 3 < 2:
            new_state = list(current_state)
            new_state[i], new_state[i+1] = new_state[i+1], new_state[i]
            successors.append(tuple(new_state))

        for successor in successors:
            # Calculate the g-score and f-score for the successor node
            g = len(path) + 1
            f = g + h(successor)

            # Check if the successor state is already in the closed set
            if successor in closed_set:
                continue

            # Check if the successor state is already in the open set
            for index, (f_score, state, _) in enumerate(open_set):
                if state == successor:
                    if f < f_score:
                        del open_set[index]
                        break
            else:
                # Add the successor node to the open set
                heapq.heappush(open_set, (f, successor, path + [successor]))

    # If the open set is empty and the goal state has not been found, return None
    return None



start_state = (1, 0, 3, 4, 2, 5, 7, 8, 6)
path = astar(start_state)
index = 0
for i in path:
  print(f"Step {index + 1} --> {i}")
  index = index + 1