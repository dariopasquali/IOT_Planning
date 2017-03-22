/*
===============================================================
ExpRobotTheory.pl
===============================================================
*/



%% ---------------- ASSERTIONS ---------------------
explore(true).
obstacle(none).
notLeftObjectAndNotExplored(false).
currentAlreadyVisited(false).

defaultExpSpeed(30).
defaultExpDuration(1000).

defaultExpSpinSpeed(30).
defaultExpSpinDuration(1000).



%% --------------- INIT -----------------------------

initExploreMap( position( SX , SY ), map( W , H ) ):-
	actorobj(Actor),
	Actor <- initExploreMap(SX, SY, W, H).
	
initExploreMap( position( SX , SY ), FILENAME ):-
	actorobj(Actor),
	Actor <- javaExplorer(SX , SY , FILENAME).
	
/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initExpRobot :-  
	actorPrintln("-----------------------------------------------------initExpRobot").
 
:- initialization(initExpRobot).