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
	actorobj(Actor),
	actorPrintln(STATE),
	Actor <- updateMap( X , Y , STATE).
	
clearGUI :-
	actorobj(Actor),
	Actor <- myClearGUI.

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initExpConsole  :-  
	actorPrintln("initConsole - exploration theory loaded").
 
:- initialization(initExpConsole).