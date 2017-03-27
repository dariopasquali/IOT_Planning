%% robotMode(MODE).
%% MODE = gui -> the guimanager is enable and show the gui (for simulation use)
%% MODE = robot -> the guimanager is disabled (for physical robot use)
robotMode(gui).


showMap( position( SX , SY ), FILENAME ):-
	actorPrintln("show Map"),
	actorobj(Actor),
	Actor <- showMap(SX , SY , FILENAME).

updateState( position( SX , SY ) , DIRECTION ):-
	actorPrintln("update"),
	actorobj(Actor),
	Actor <- updateState( SX , SY , DIRECTION ).


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initGUIManager :-  
	actorPrintln("-------*******/*/*/*/*/*----initGUIManager").
 
:- initialization(initGUIManager).