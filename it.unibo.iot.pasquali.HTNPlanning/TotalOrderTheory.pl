
position(pos1,[a,b,c]).
position(pos2, []).
position(pos3, []).

isEmpty(pos2).
isEmpty(pos3).

isEmptyList([]).

addHead( E , List, [E|List]):-
	actorPrintln([E|List]).
	
add_tail([],X,[X]).
add_tail([H|T],X,[H|L]) :-
	add_tail(T,X,L).

initialize([]) :-
	actorPrintln(init_empty_plan).

take(Pos, Plan) :-
	position(Pos, [Block | []] ),
	actorPrintln(take_last),
	assert(isEmpty(Pos)),
	assert(onCrane(Block)),
	retract(position(Pos, _)),
	assert(position(Pos, [])),
	atom_concat('take(', Block, X),
	atom_concat(X, ' ', Y),
	atom_concat(Y, Pos, K),
	atom_concat(K, ')', Plan),
	%%actorPrintln(Z),
	%%add_tail([], Z, Plan),
	actorPrintln(Plan).


take(Pos, Plan) :-
	actorPrintln(take_block),
	position(Pos, [Block | T] ),
	actorPrintln(T),
	assert(onCrane(Block)),
	retract(position(Pos, _)),
	assert(position(Pos, T)),
	atom_concat('take(', Block, X),
	atom_concat(X, ' ', Y),
	atom_concat(Y, Pos, K),
	atom_concat(K, ')', Plan),
	%%actorPrintln(Z),
	%%add_tail([], Z, Plan),
	actorPrintln(Plan).


drop(Pos, Plan) :-
	actorPrintln(drop_empty_pos),
	isEmpty(Pos),
	retract(isEmpty(Pos)),
	retract(onCrane(Block)),
	position(Pos, List),
	addHead(Block, List, NewList),
	retract(position(Pos, _)),
	assert(position(Pos, NewList)),
	atom_concat('drop(', Block, X),
	atom_concat(X, ' ', Y),
	atom_concat(Y, Pos, K),
	atom_concat(K, ')', Plan),
	%%actorPrintln(Z),
	%%add_tail([], Z, Plan),
	actorPrintln(Plan).

drop(Pos, Plan) :-
	actorPrintln(drop_notEmpty_pos),
	retract(onCrane(Block)),
	position(Pos, List),
	addHead(Block, List, NewList),
	retract(position(Pos, _)),
	assert(position(Pos, NewList)),
	atom_concat('drop(', Block, X),
	atom_concat(X, ' ', Y),
	atom_concat(Y, Pos, K),
	atom_concat(K, ')', Plan),
	%%actorPrintln(Z),
	%%add_tail([], Z, Plan),
	actorPrintln(Plan).

moveTopmostBlock(Pos1, Pos2, PlanOut) :-
	not isEmpty(Pos1),
	actorPrintln(move_topmost_block),
	take(Pos1, Plan1),
	drop(Pos2, Plan2),
	add_tail([], Plan1, P),
	add_tail(P, Plan2, PlanOut),
	actorPrintln(PlanOut).

moveStack(Pos1, Pos2, Plan, Plan) :-
	isEmpty(Pos1),
	actorPrintln(stack_is_empty),
	actorPrintln(stack_moved).
	
moveStack(Pos1, Pos2, PlanOut) :-
	actorPrintln(move_stack_recursive),
	moveTopmostBlock(Pos1, Pos2, Plan1),
	add_tail([], Plan1, Plan2),
	moveStack(Pos1, Pos2, Plan3),
	add_tail(Plan2, Plan3, PlanOut).

moveTwice(CleanedPlan) :-
	actorPrintln(move_twice),
	not isEmpty(pos1),
	isEmpty(pos2),
	isEmpty(pos3),
	actorPrintln(Plan),
	moveStack(pos1, pos2, PlanStack1),
	actorPrintln("........................................."),
	moveStack(pos2, pos3, PlanStack2),
	position(pos1, List1),
	position(pos2, List2),
	position(pos3, List3),
	actorPrintln(List1),
	actorPrintln(List2),
	actorPrintln(List3),
	add_tail([], PlanStack1, Plan2),
	add_tail(Plan2, PlanStack2, PlanOut),
	%%actorPrintln(PlanOut),
	actorobj(Actor),
	Actor <- cleanPlan(PlanOut) returns CleanedPlan.
	

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initTOT  :-  
	actorPrintln("init total order planner").
 
:- initialization(initTOT).