/*
===============================================================
navRobotTheory.pl
===============================================================
*/

%% Mappa nel formato
%% map(Xmax, Ymax). 
%% mapdata(ID, element(X,Y)).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element

%% ROBOT CONFIG -------------------------------

planFilename("scout.txt").
planName(scout).

/*
planExecutionMode( MODE ) defines how the robot executes the navigation plan

pInter --> the plan is translated in a prolog-like interpretable format.
Then is saved in the file scout.txt, loaded with runPlan and executed with Prolog.

executor -> the plan is translated in a list of executable robot action
then it is executed action by action by the actor

qa --> the plan is translated in a qa file, a compilable software factory (TODO)
*/
planExecutionMode(executor).


defaultSpeed(60).
defaultDuration(1000).

%% INIT NAVIGATION ---------------------------------------------------

initialConfigNavRobot :-
	defaultSpeed(S),
	defaultDuration(T),
	actorobj(Actor),
	actorPrintln(initialConfigNavRobot),
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

robaAcaso :-
	actorPrintln(robaaaaaaa).

notifyObstacle :-
	actorobj(Actor),
	actorPrintln(esegui_cazzo),
	Actor <- notifyObstacle.

javaPlanExecution :-
	actorobj(Actor),
	actorPrintln(esegui_cazzo),
	Actor <- javaPlanExecution.

setNavPlan(plan(PLAN)) :-
	actorobj(Actor),
	planName(NAME),
	actorPrintln(NAME),
	actorPrintln("set Prolog-like Navigation Plan"),
	Actor <- setNavigationPlan(NAME, PLAN).
	
setExeNavPlan(plan(PLAN)) :-	
	actorobj(Actor),
	actorPrintln("set Executable Navigation Plan"),
	Actor <- setExecutableNavigationPlan(PLAN).
	
	
loadNavigationData(PLAN, POS) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),
	configEngine(POS),
	planExecutionMode(EXEMODE),
	switchExecutors(PLAN, EXEMODE).
	
	
loadNavigationData(PLAN, POS, FILENAME) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln(FILENAME),
	actorPrintln("caricamento dati in corso...."),
	configFileEngine(POS, FILENAME),
	actorPrintln("done"),	
	planExecutionMode(EXEMODE),
	actorPrintln(EXEMODE),
	switchExecutors(PLAN, EXEMODE).
	
switchExecutors(PLAN, pInter) :-
	actorPrintln(pInter1),
	setNavPlan(PLAN).
	
switchExecutors(PLAN, executor) :-
	actorPrintln(executor1),
	setExeNavPlan(PLAN).
	
	
loadThePlan( FName ):-
	actorobj(Actor),
	actorPrintln(FName),
	Actor <- consultFromFile(  FName  ).


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

	
%% PLAN EXECUTION -----------------------------------------
	
myRunPlan(PLANNAME) :-
	runPlan(PLANNAME).


myExecPlan(CURPLAN,Actor,P,PC) :-
	plan(PC, P, S),
	actorPrintln(S),
	myRunTheSentence(CURPLAN,Actor,S),
	PC1 is PC + 1,
	myExecPlan(CURPLAN,Actor,P,PC1).


executeCmd(Actor,  move(switchplan,PNAME), Events, Plans, done(switchplan) ):-
	actorPrintln(  PNAME ),
	Actor <- switchPlan(PNAME).
	
	
executeCmd(Actor, move(senseEvent,TIMEOUT,EVENTSLIST, PLANSLIST), Events, Plans, RES ):-
	actorPrintln(sense(EVENTLIST)),
	Actor <- senseEvent(TIMEOUT, EVENTSLIST, PLANSLIST).
	
executeCmd( Actor,  move(print,MSG), Events, Plans, done(print) ):-
	actorPrintln(MSG).
	
executeCmd( Actor, move(robotmove,CMD,SPEED,DURATION,ANGLE), ENDEV, RES ):-
 	!,
 	mapCmdToMove(CMD,MOVE), 
	%% actorPrintln(  executeCmd(Actor,MOVE, SPEED, ANGLE, DURATION, ENDEV ) ),
	Actor <- mtExecute(MOVE, SPEED, ANGLE, DURATION, ENDEV, '', '') returns AAR,
	AAR <- getResult returns RES.
	
executeCmd( Actor, move(robotmove,CMD,SPEED,DURATION,ANGLE), Events, Plans, RES ):-
 	!,
 	mapCmdToMove(CMD,MOVE), 
	%% actorPrintln(  executeCmd(Actor,MOVE, SPEED, ANGLE, DURATION, Events, Plans) ),
	Actor <- myExecute(MOVE, SPEED, ANGLE, DURATION, Events, Plans) returns AAR,
	AAR <- getResult returns RES.

%% WORLD INTERACTION ------------------------------------------------------

updateSimulationWorld :-
	actorobj(Actor),
	Actor <- updateSimulationWorld.

%% OLD ---------------------------------------------------

/*
runResumablePlan(PLAN) :-
	actorobj(Actor),
	myExecPlan(PLAN,Actor,PLAN,1).
*/

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavRobot  :-  
	actorPrintln("initRobot - Navigatin Theory loaded").
 
:- initialization(initNavRobot).