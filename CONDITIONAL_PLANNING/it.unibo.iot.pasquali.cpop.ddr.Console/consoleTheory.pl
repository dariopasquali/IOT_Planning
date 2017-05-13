/*
===============================================================
consoleTheory.pl
===============================================================
*/


%% CONSOLE CONFIG -------------------------------

%%gui(image).
gui(button).

%% USED ------------------------------------------------------------------

loadMap(PATH) :-
	actorobj(Actor),
	Actor <- loadMapButton(PATH),
	assert(havemap).
	
savePlan(PATH) :-
	actorobj(Actor),
	Actor <- savePlan(PATH),
	assert(havemap).


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initConsole  :-  
	actorPrintln("initConsole - common theory loaded").
 
:- initialization(initConsole).