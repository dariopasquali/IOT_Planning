/*
===============================================================
robotTheory.pl
===============================================================
*/

%% DEBUG CONTROL -----------------------------------------------

enableDebugSensing :-
	actorobj(Actor),
	actorPrintln("enable debug sensing"),
	Actor <- enableDebugSensing.
	
disableDebugSensing :-
	actorobj(Actor),
	actorPrintln("disable debug sensing"),
	Actor <- disableDebugSensing.	


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initRobot :-  
	actorPrintln("initRobot - Common Theory loaded").
 
:- initialization(initRobot).