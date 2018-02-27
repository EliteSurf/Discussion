%% @doc @todo Add description to 'hello_world'.

-module(hello_world).

-import(string, [len/1, concat/2, chr/2, substr/3, str/2, 
				 to_lower/1, to_upper/1]).

-export([hou_tak/0]).

hou_tak() ->
	io:fwrite("Hello World to Hou Tak !!\n").



