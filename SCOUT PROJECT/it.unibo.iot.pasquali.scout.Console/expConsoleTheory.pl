/*
===============================================================
expConsoleTheory.pl
===============================================================
*/


showClearMap :-
	actorobj(Actor),
	Actor <- showClearMap.
	
showClearMap( map(W,H) ) :-
	actorobj(Actor),
	Actor <- showClearMap(W,H).

updateMap( position( X , Y ) , STATE) :-
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