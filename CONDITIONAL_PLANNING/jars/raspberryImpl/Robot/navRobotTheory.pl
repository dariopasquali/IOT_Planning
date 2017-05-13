
%%===============================================================
%%navRobotTheory.pl
%%===============================================================


%% Mappa nel formato
%% map(Xmax, Ymax). 
%% mapdata(ID, element(X,Y)).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element

%% ROBOT CONFIG -------------------------------

defaultSpeed(60).
defaultDuration(1000).

%% INIT NAVIGATION ---------------------------------------------------

initialConfigNavRobot :-
	defaultSpeed(S),
	defaultDuration(T),
	actorobj(Actor),
	Actor <- initialConfigRobot(S,T,S,S).
	
configEngine(position(SX, SY)) :-
	actorPrintln("confingEngine!!!!"),
	actorobj(Actor),
	Actor <- configEngine(SX, SY).
	
configFileEngine(position(SX, SY), FILENAME) :-
	actorPrintln("configFileEngine!!!!"),
	actorobj(Actor),
	Actor <- configFileEngine(SX, SY, FILENAME).

%% PLAN MANAGEMENT ----------------------------------------

setNavPlan(plan(PLAN)) :-
	actorobj(Actor),
	actorPrintln("setNavigationPlan!!!!"),
	Actor <- setNavigationPlan(PLAN).
	
	
loadNavigationData(PLAN, POS) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),
	configEngine(POS),
	setNavPlan(PLAN).
	
	
loadNavigationData(PLAN, POS, FILENAME) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln(FILENAME),
	actorPrintln("caricamento dati in corso...."),
	configFileEngine(POS, FILENAME),
	setNavPlan(PLAN).
	
	
loadThePlan( FName ):-
	actorobj(Actor),
	actorPrintln(FName),
	Actor <- consultFromFile(  FName  ).


navigate :-
	actorobj(Actor),
	Actor <- navigate.





myClearPlan(P) :-
	actorPrintln(clear),
	%%retractall( plan(_, P, _) ),
	myClearPlan(1,P),
	actorPrintln( clearPlan( P,done ) ).

myClearPlan(PC,P):-
	plan(PC,P,S),
	actorPrintln(PC),
	retract(plan(PC,P,S)),
	PC1 is PC + 1,
	myClearPlan(PC1,P).

myClearPlan(_,_).

	
notifyUnexpectedObstacle :-
	actorobj(Actor),
	Actor <- notifyObstacle.

updateSimulationWorld :-
	actorobj(Actor),
	Actor <- updateSimulationWorld.


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavRobot  :-  
	actorPrintln("initRobot - Navigatin Theory loaded").
 
:- initialization(initNavRobot).