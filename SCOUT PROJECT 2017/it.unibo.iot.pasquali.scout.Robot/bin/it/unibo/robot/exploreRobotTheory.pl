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

initExploreMap :-
	actorobj(Actor),
	Actor <- initExploreMap,
	assert( newCell( position(0,0) , clear ) ).
	
initExploreMap( position( SX , SY ), FILENAME ):-
	actorobj(Actor),
	Actor <- initExploreMap(SX , SY , FILENAME),	
	assert( newCell( position( SX , SY ) , clear ) ).
	
initExploreMap( position( SX , SY ), map( W , H ) ):-
	actorobj(Actor),
	Actor <- initExplorerMap(SX, SY, W, H),
	assert( newCell( position( SX , SY ) , clear ) ).
	
javaExplorer( position( SX , SY ), FILENAME ):-
	actorobj(Actor),
	Actor <- javaExplorer(SX , SY , FILENAME).
	
%% -------- SENSE/CHECK STATE -----------------------

addCurrentToVisited :-
	actorobj(Actor),
	Actor <- addCurrentToVisited.
	
senseAndCheckLeft :-
	actorobj(Actor),
	not obstalce(left),
	Actor <- checkLeftVisited returns VISITED,
	not VISITED, !,
	assert(notLeftObjectAndNotExplored(true)).

senseAndCheckLeft :-
	obstacle(left, obstacle), !,
	retract( obstacle( _ , _ ) ),
	assert(notLeftObjectAndNotExplored(false)).
	
senseAndCheckLeft :-
	actorobj(Actor),
	obstacle(left, clear),
	retract( obstacle( _ , _ ) ),
	Actor <- checkLeftVisited returns VISITED,
	VISITED,
	assert( notLeftObjectAndNotExplored(false) ).
	
checkCurrentAlreadyVisited :-
	actorobj(Actor),
	Actor <- checkCurrentAlreadyVisited returns STATE,
	assert( currentAlreadyVisited(STATE) ).
	
updateModel( DIR , STATE ) :-
	actorobj(Actor),
	actorPrintln(STATE),
	Actor <- updateModel( DIR , STATE ),
	Actor <- getNewCellX returns X,
	Actor <- getNewCellY returns Y,
	%%actorPrintln(position(X,Y)),
	assert( newCell( position( X , Y) , STATE ) ).
	

%%---------------- MOVE -----------------------


moveForward :-
	actorPrintln("mossa avanti!!!!!"),
	actorobj(Actor),
	defaultExpSpeed(SPEED),
	defaultExpDuration(TIME),	
	%%executeCmd(Actor, move(robotmove,forward,SPEED,TIME,0), "", "", RES ),
	Actor <- makeMove(forward),
	Actor <- addCurrentToVisited.
	
turnDoubleRight :-
	actorobj(Actor),
	defaultExpSpinSpeed(SPEED),
	defaultExpSpinDuration(TIME),	
	%%executeCmd(Actor, move(robotmove,right,SPEED,TIME,0), "", "", RES ),
	Actor <- turn(doubleRight).
	

turnDoubleLeft :-
	actorobj(Actor),
	defaultExpSpinSpeed(SPEED),
	defaultExpSpinDuration(TIME),	
	%%executeCmd(Actor, move(robotmove,left,SPEED,TIME,0), "", "", RES ),
	Actor <- turn(doubleLeft).
	

%% ----------------- LOOP AVOIDANCE ------------------------

findNearestNotExploredCell :-
	actorobj(Actor),
	Actor <- findNearestNotExploredCell returns E,
	retract(explore(_)),
	assert( explore(E) ).
	
computeBestPath :-
	actorobj(Actor),
	Actor <- computeBestPath.
	
travel :-
	actorobj(Actor),
	Actor <- travel.






/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initExpRobot :-  
	actorPrintln("-----------------------------------------------------initExpRobot").
 
:- initialization(initExpRobot).