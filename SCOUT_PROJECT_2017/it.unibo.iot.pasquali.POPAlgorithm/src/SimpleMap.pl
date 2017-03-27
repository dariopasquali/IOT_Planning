clear(0,0).
clear(0,1).
cleanr(1,1).
not clear(1.0).

%% op( Name , PrecondList , AddList , DeleteList ).

op(move0001 , [p(0,0), clear(0,1)] , [p(0,1)] , [p(0,0)] ).
op(move0100 , [p(0,1), clear(0,0)] , [p(0,0)] , [p(0,1)] ).
op(move0111 , [p(0,1), clear(1,1)] , [p(1,1)] , [p(0,1)] ).
op(move1101 , [p(1,1), clear(0,1)] , [p(0,1)] , [p(1,1)] ).