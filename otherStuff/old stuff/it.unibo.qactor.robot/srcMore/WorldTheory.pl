%==============================================
% WorldTheory.pl  
%==============================================
test :-
	stdout <- println(  "hello from world theory in srcMore" ).
	
addRule( Rule ):-
	%%output( addRule( Rule ) ),
	assert( Rule ).
removeRule( Rule ):-
	retract( Rule ),
	%%output( removedFact(Rule) ),
	!.
removeRule( A  ):- 
	%%output( remove(A) ),
	retract( A :- B ).

evalGuard( not(G) ) :-
	G, !, fail .
evalGuard( not(G) ):- !.
evalGuard( true ) :- !.
evalGuard( G ) :-  
	%stdout <- println( evalGuard( G ) ),
	G . 

output( M ):-stdout <- println( M ).
%-------------------------------------------------
%  TuProlo FEATURES of the QActor qa0
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 

%% :- stdout <- println(  "hello from world theory of qa0" ). 

%-------------------------------------------------
%  UTILITIES for TuProlog computations
%-------------------------------------------------
loop( N,Op ) :- 
	assign(loopcount,1),
	loop(loopcount,N,Op).

loop(I,N,Op) :-  
		getVal( I , V ),
		%%output( values( I,V,N ) ),
 		V =< N ,!,
   		%% qa0 <- println( loop( I,V ) ),
     	%% execActor(  println( "doing loop" ) ),
     	execActor( Op  ),
		V1 is V + 1 ,
		assign(I,V1) ,
		loop(I,N,Op).	%%tail recursion
loop(I,N,Op).

getVal( I, V ):-
	value(I,V).

assign( I,V ):-
	retract( value(I,_) ),!,
	assert( value( I,V )).
assign( I,V ):-
	assert( value( I,V )).

actorPrintln( X ):- qa0  <- println( X ).

%-------------------------------------------------
%  User static rules about qa0
%------------------------------------------------- 
/*
------------------------------------------------------------------------
execActor( testForProlog( X)):- 
	java_catch(
		 qa0 <- testForProlo( X) ,
		[('java.lang.Exception'(Cause, Msg, StackTrace),output(Msg))],
		output(done)
	).
execActor( ANY ):- qa0  <- println( execActor(failure,ANY) ).
------------------------------------------------------------------------
*/
execActor( Op ) :- output( Op ), qa0 <- Op, !.
execActor( _ ):- output( failure ).

/*
%-------------------------------------------------
%  Some predefined code
%------------------------------------------------- 
*/
fibo(V,V):-
	fib(V,N),!,
	assert( result( fib,V,N ) ).
fib(0,1).
fib(1,1).
fib(I,N) :- V1 is I-1, V2 is I-2,
  fib(V1,N1), fib(V2,N2),
  N is N1 + N2.


%%---------------------------------------------
%% compute using cache
%% The execution time is very short, since
%% each intermediate value is stored in chache
%%---------------------------------------------
cache_fib( 0,1 ).
cache_fib( 1,1 ).
fibWithCache( V,N ) :-
	cache_fib( V,N ),!.
fibWithCache( V,N ) :-
	V1 is V-1, V2 is V-2,
  	fibWithCache(V1,N1), fibWithCache(V2,N2),
  	N is N1 + N2,
	%%actorPrintln( fibWithCache(V,N ) ),
	assert( cache_fib( V,N ) ).
