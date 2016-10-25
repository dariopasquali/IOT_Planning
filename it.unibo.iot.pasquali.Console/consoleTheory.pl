/*
===============================================================
consoleTheory.pl
===============================================================
*/


%% Mappa nel formato
%% map(Xmax, Ymax). 
%% mapdata(ID, element(X,Y)).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element

defaultSpeed(60).
defaultDuration(500).

test_copy_term :-
	actorPrintln("TEST COPY TERM"),
	getElements(Lista),
	acrtorPrintln(Lista),
	copy_term(Lista, X),
	actorPrintln(X).


add_tail([],X,[X]).
add_tail([H|T],X,[H|L]) :-
	add_tail(T,X,L).


getElements(List) :-
	getElements([], 1, List).
	
getElements(List, ID, ToList) :-
	mapdata(ID, E), !,
	%%add_tail(List, E, List1),
	append(List, [E], List1),
	ID1 is ID + 1,
	getElements(List1, ID1, ToList).
	
getElements(List, _, List).

notContainsElement(E, []).
notContainsElement(E, [E|_]) :- !, fail.
notContainsElement(E, [H|T]) :-
	containsElement(E,T)
.
	

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

/*	
loadMapFromFile(PATH) :-
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
	Actor <- loadMap(PATH),
	assert(havemap).
*/

/*	
loadMap(PATH) :-
	actorobj(Actor),
	Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	map(Xmax, Ymax),
	actorPrintln(Xmax),
	actorPrintln(Ymax),
	Actor <- loadMap(PATH),
	assert(havemap).
*/
	
loadMapImage(PATH) :-
	actorobj(Actor),
	%%Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	%%map(Xmax, Ymax),
	%%actorPrintln(Xmax),
	%%actorPrintln(Ymax),
	Actor <- loadMapImage(PATH),
	assert(havemap).
	
loadMapButton(PATH) :-
	actorobj(Actor),
	%%Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	%%map(Xmax, Ymax),
	%%actorPrintln(Xmax),
	%%actorPrintln(Ymax),
	Actor <- loadMapButton(PATH),
	assert(havemap).
	
searchBestPath(position(Sx,Sy) , position(Gx,Gy), ALGO) :-
	actorPrintln(position(Sx,Sy)),
	actorPrintln(position(Gx,Gy)),	
	havemap,
	actorobj(Actor),
	Actor <- searchBestPath(Sx,Sy,Gx,Gy,ALGO),
	Actor <- showPathOnGui,
	assert(haveplan).

sendNavigationData :-
	havemap,
	haveplan,
	actorobj(Actor),
	Actor <-  sendNavigationData.
	
enableManipulation :-
	actorobj(Actor),
	Actor <-  enableManipulation.

updateMap(element(X,Y)) :-
	actorobj(Actor),
	Actor <- updateMap(element(X,Y)),
	assert(element(X,Y)).
/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initConsole  :-  
	actorPrintln("initConsole").
 
:- initialization(initConsole).