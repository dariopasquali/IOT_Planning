/*
===============================================================
robotTheory.pl
===============================================================
*/


%% Non posso lavorare con una mappa prolog
%% Però dovrò salvarla in quel formato

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
	Acotr <- initExploreMap.
	
initExploreMap( position( SX , SY ), map ( W , H ) ):-
	actorobj(Actor),
	Actor <- initExplorerMap(SX, SY, W, H).
	
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
	obstalce(left), !,
	assert(notLeftObjectAndNotExplored(false)).
	
senseAndCheckLeft :-
	actorobj(Actor),
	not obstalce(left),
	Actor <- checkLeftVisited returns VISITED,
	VISITED,
	assert( notLeftObjectAndNotExplored(false) ).
	
checkCurrentAlreadyVisited :-
	actorobj(Actor),
	Actor <- checkCurrentAlreadyVisited returns STATE,
	assert( currentAlreadyVisited(STATE) ).
	
	
%%---------------- MOVE -----------------------


moveforward :-
	actorobj(Actor),
	defaulExpSpeed(SPEED),
	defaultExpDuration(TIME),	
	myExecuteCmd(_, Actor, move(robotmove,forward,SPEED,TIME,0), "", "", RES ),
	Actor <- makeMove(forward) .
	
turnDoubleRight :-
	actorobj(Actor),
	defaulExpSpinSpeed(SPEED),
	defaultExpSpinDuration(TIME),	
	myExecuteCmd(_, Actor, move(robotmove,right,SPEED,TIME,0), "", "", RES ).
	

turnDoubleLeft :-
	actorobj(Actor),
	defaulExpSpinSpeed(SPEED),
	defaultExpSpinDuration(TIME),	
	myExecuteCmd(_, Actor, move(robotmove,left,SPEED,TIME,0), "", "", RES ).
	
	
%% ----------------- LOOP AVOIDANCE ------------------------

findNearestNotExploredCell :-
	actorobj(Actor),
	Actor <- findNearestNotExploredCell returns EXPLORE,
	assert ( explore(EXPLORE) ).
	
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
	actorPrintln("initExpRobot").
 
:- initialization(initExpRobot).