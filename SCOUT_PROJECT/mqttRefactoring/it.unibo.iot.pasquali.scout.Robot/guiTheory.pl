%% robotMode(MODE).
%% MODE = gui -> the guimanager is enable and show the gui (for simulation use)
%% MODE = robot -> the guimanager is disabled (for physical robot use)
%% MODE = unity -> the guimanager enable a simulation on unity
robotMode(gui).

mqttServer("tcp://m2m.eclipse.org:1883").

initMqtt :-
	mqttServer(SERVER),
	actorobj(Actor),
	Actor <- initMqtt(SERVER).


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