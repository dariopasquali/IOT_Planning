/*
===============================================================
consoleTheory.pl
===============================================================
*/


%% Mappa nel formato
%% map(Xmax, Ymax). 
%% element(X,Y).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element



loadMapFromFile(FileName) :-
	actorobj(Actor),
	Actor <- consultFromFile(  FName  ),
	assert(havemap),
	Actor <- showMapOnGui.
	
checkValidState( X , Y) :-
	X > 0,
	Y > 0,
	map(Xmax, Ymax),
	X =< Xmax,
	Y =< Ymax,
	not element(X,Y).

%% START -> position(X,Y)

searchBestPath(position(Sx,Sy) , position(Gx,Gy)) :-
	havemap,
	actorobj(Actor),
	Actor <- searchBestPath(Sx,Sy,Gx,Gy),
	Actor <- showPathOnGui.

sendMap :-
	havemap,
	actorobj(Actor),
	Actor <-  sendMap.
	
sendPlan :-
	actorobj(Actor),
	Actor <-  sendPlan.
	
sendPositions :-
	actorobj(Actor),
	Actor <-  sendPositions.
	
enableManipulation :-
	actorobj(Actor),
	Actor <-  enableManipulation.
	
updateMap (element(X,Y)) :-
	actorobj(Actor),
	Actor <- updateMap(element(X,Y)),
	assert(element(X,Y)).

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initConsole  :-  
	actorobj(Actor),
	assign( pc,0 ),
	%% actorPrintln( actorobj(Actor) ),
	( Actor <- isSimpleActor returns R, R=true, !,
	  actorPrintln(" *** consoleTheory loaded FOR ACTORS ONLY  ***  ");
	  actorPrintln(" *** consoleTheory loaded FOR ROBOTS  *** ")
	).
 
:- initialization(initConsole).