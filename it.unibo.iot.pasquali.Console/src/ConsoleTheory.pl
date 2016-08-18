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
	Actor <-  consultFromFile(  FName  ).
	
checkValidState( X , Y) :-
	X > 0,
	Y > 0,
	map(Xmax, Ymax),
	X =< Xmax,
	Y =< Ymax,
	not element(X,Y).

%% START -> position(X,Y)

searchBestPath(position(Sx,Sy) , position(Gx,Gy)) :-
	actorobj(Actor),
	Actor <-  searchBestPath(Sx,Sy,Gx,Gy).
	
sendPlan :-
	actorobj(Actor),
	Actor <-  sendPlan.

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
	  actorPrintln(" *** consoleRobotTheory loaded FOR ROBOTS  *** ")
	).
 
:- initialization(initConsole).