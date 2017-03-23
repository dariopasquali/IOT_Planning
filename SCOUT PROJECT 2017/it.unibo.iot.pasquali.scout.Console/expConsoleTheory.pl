/*
===============================================================
expConsoleTheory.pl
===============================================================
*/


showClearMap :-
	actorobj(Actor),
	Actor <- showClearMap.
	
showClearMap( map(W,H) ) :-
	actorPrintln(W),
	actorPrintln(H),
	actorobj(Actor),
	Actor <- showClearMap(W,H).

updateMap( position( X , Y ) , STATE) :-
	X>=0,
	Y>=0,
	actorobj(Actor),
	actorPrintln(STATE),
	Actor <- updateMap( X , Y , STATE).

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initExpConsole  :-  
	actorPrintln("initConsole - exploration theory loaded").
 
:- initialization(initExpConsole).