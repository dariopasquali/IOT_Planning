

showGUI :-
	actorobj(Actor),
	Actor <- showGUI.

showmap( position( SX , SY ), FILENAME ):-
	actorobj(Actor),
	Actor <- showMap(SX , SY , FILENAME).

updateState( position( SX , SY ) , DIRECTION ):-
	actorobj(Actor),
	Actor <- updateState( SX , SY , DIRECTION ).


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initGUIManager :-  
	actorPrintln("-----------initGUIManager").
 
:- initialization(initGUIManager).