from queue import Queue

class State:
    def __init__(self, jug1, jug2):
        self.jug1 = jug1
        self.jug2 = jug2
        self.path = []

    def __eq__(self, other):
        return self.jug1 == other.jug1 and self.jug2 == other.jug2

    def __hash__(self):
        return hash((self.jug1, self.jug2))

    def __str__(self):
        return f"({self.jug1}, {self.jug2})"

def bfs(jug1_capacity, jug2_capacity, target_amount):
    q = Queue()
    initial_state = State(0, 0)
    q.put(initial_state)
    visited = set()

    while not q.empty():
        state = q.get()
        print(state)
        if state.jug1 == target_amount or state.jug2 == target_amount:
            state.path.append(str(state))
            return state.path
        if state in visited:
            continue
       
        visited.add(state)

        # Fill jug 1
        if state.jug1 < jug1_capacity:
            new_state = State(jug1_capacity, state.jug2)
            new_state.path = state.path + [str(state) + " --> " + str(new_state)]
            q.put(new_state)

        # Fill jug 2
        if state.jug2 < jug2_capacity:
            new_state = State(state.jug1, jug2_capacity)
            new_state.path = state.path + [str(state) + " --> " + str(new_state)]
            q.put(new_state)

        # Empty jug 1
        if state.jug1 > 0:
            new_state = State(0, state.jug2)
            new_state.path = state.path + [str(state) + " --> " + str(new_state)]
            q.put(new_state)

        # Empty jug 2
        if state.jug2 > 0:
            new_state = State(state.jug1, 0)
            new_state.path = state.path + [str(state) + " --> " + str(new_state)]
            q.put(new_state)

        # Pour jug 1 into jug 2
        if state.jug1 > 0 and state.jug2 < jug2_capacity:
            amount = min(state.jug1, jug2_capacity - state.jug2)
            new_state = State(state.jug1 - amount, state.jug2 + amount)
            new_state.path = state.path + [str(state) + " --> " + str(new_state)]
            q.put(new_state)

        # Pour jug 2 into jug 1
        if state.jug2 > 0 and state.jug1 < jug1_capacity:
            amount = min(state.jug2, jug1_capacity - state.jug1)
            new_state = State(state.jug1 + amount, state.jug2 - amount)
            new_state.path = state.path + [str(state) + " --> " + str(new_state)]
            q.put(new_state)
        
    return None

jug1 = int(input("Enter jug 1 capacity: "))
jug2 = int(input("Enter jug 2 capacity: "))
target = int(input("Enter target capacity: "))
bfs(jug1,jug2,target)