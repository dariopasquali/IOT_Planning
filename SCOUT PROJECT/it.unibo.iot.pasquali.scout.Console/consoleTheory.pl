/*
===============================================================
consoleTheory.pl
===============================================================
*/


%% CONSOLE CONFIG -------------------------------

%%gui(image).
gui(button).


%%defaultSpeed(60).
%%defaultDuration(500).

%%-----------------------------------------------

getElements(List) :-
	getElements([], 1, List).
	
getElements(List, ID, ToList) :-
	mapdata(ID, E), !,
	append(List, [E], List1),
	ID1 is ID + 1,
	getElements(List1, ID1, ToList).
	
getElements(List, _, List).

notContainsElement(E, []).
notContainsElement(E, [E|_]) :- !, fail.
notContainsElement(E, [H|T]) :-
	containsElement(E,T).




/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initConsole  :-  
	actorPrintln("initConsole").
 
:- initialization(initConsole).