/*
===============================================================
navRobotTheory.pl
===============================================================
*/

tetteculo :-
	actorPrintln(tetteculo).

loadThePlan( FName ):-
	actorobj(Actor),
	actorPrintln(FName),
	Actor <- consultFromFile(  FName  ).

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
	actorPrintln(sono_io),
	Actor <- senseEvents(TIMEOUT, EVENTSLIST, PLANSLIST).
	
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