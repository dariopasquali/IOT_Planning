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

initialConfigExpRobot :-
	defaultExpSpeed(S),
	defaultExpDuration(T),
	defaultExpSpinSpeed(SS),
	defaultExpSpinDuration(ST),
	actorobj(Actor),
	Actor <- initialConfigRobot(S,T,SS,ST).

initExploreMap( position( SX , SY ), map( W , H ) ):-
	actorobj(Actor),
	Actor <- initExploreMap(SX, SY, W, H).
	
initExploreMap( position( SX , SY ), FILENAME ):-
	actorobj(Actor),
	Actor <- initExploreFile(SX , SY , FILENAME).
	
/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initExpRobot :-  
	actorPrintln("-----------------------------------------------------initExpRobot").
 
:- initialization(initExpRobot).