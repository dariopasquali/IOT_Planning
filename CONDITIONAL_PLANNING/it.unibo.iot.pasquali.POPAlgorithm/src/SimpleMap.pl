/*
A Map is a set of rooms connected by doors.
I can represent a map as the set of connections between nodes.
- each node has an heuristic distance to the GOAL, but this depends on the GOAL;
- each node has a deterministic distance from the STARTS, nut this depends on the START;

For each map I'll define all the possible moves between rooms.
- each move ha a deterministic cost, base on the distance between the CoG of the 2 rooms.

Here we have a very simple map, already translated in room-graph format.

a ----- b
\       /
 -- c --

I want to move from A to C
*/

%% Map static definition
connected(a,b).
connected(b,a).
connected(b,c).
connected(c,b).
connected(a,c).
connected(c,a).
%%------------------------------------------

/*
 move(From, To, PreList, EffectList, Cost).
 PreList = [at(From), connected(From, To)]
 EffectList = [at(To), not at(From)]
*/

%% Moves
move(a, b, [at(a), connected(a,b)], [at(b), not at(a)], 1).
move(b, a, [at(b), connected(b,a)], [at(a), not at(b)], 1).

move(a, c, [at(a), connected(a,c)], [at(c), not at(a)], 1).
move(c, a, [at(a), connected(c,a)], [at(a), not at(c)], 1).

move(b, c, [at(c), connected(b,c)], [at(c), not at(b)], 1).
move(b, c, [at(b), connected(c,b)], [at(b), not at(c)], 1).
%%------------------------------------------------------------

/*
The Partial Order Planner algorithm will find a solution,
the FIRST SOLUTION, not the best, that bring from START to GOAL.
*/


