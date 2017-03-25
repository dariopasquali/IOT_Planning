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

%%------------------------------------------------------------------

gui(button).

loadMapFromFileProlog(PATH) :-
	havemap, !,
	retractall(mapdata(_,element(_,_))),
	actorPrintln("retract map elements"),
	retract(map(_,_)),
	actorPrintln("retract map size"),
	retract(havemap),
	actorPrintln("retract map flag"),
	actorobj(Actor),
	Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	map(Xmax, Ymax),
	actorPrintln(Xmax),
	actorPrintln(Ymax),
	Actor <- createMap(Xmax, Ymax),
	getElements(List),
	actorPrintln(List),
	Actor <- setMapElements(List),
	assert(havemap).
	
loadMapFromFileProlog(PATH) :-
	actorobj(Actor),
	Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	map(Xmax, Ymax),
	actorPrintln(Xmax),
	actorPrintln(Ymax),
	Actor <- createMap(Xmax, Ymax),
	mapdata(ID,element(X,Y)),
	getElements(List),
	actorPrintln(List),
	Actor <- setMapElements(List),
	assert(havemap).

loadMap(PATH) :-
	gui(GUIMODE),
	actorPrintln(PATH),
	loadMap(PATH,GUIMODE).

loadMap(PATH, button) :-
	actorobj(Actor),
	Actor <- loadMapButton(PATH),
	assert(havemap).
	
loadMap(PATH, image) :-
	loadMapImage(PATH, MODE).


loadMapImage(PATH, MODE) :-
	actorobj(Actor),
	actorPrintln("consulted map data"),
	Actor <- loadMapImage(PATH, MODE),
	assert(havemap).
	


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initConsole  :-  
	actorPrintln("initConsole - common theory loaded").
 
:- initialization(initConsole).