/* OData Filter grammar */
grammar ODF;

expression          : orExpr EOF;

orExpr              : andExpr ( OR andExpr )*;
andExpr             : equalityExpr ( AND equalityExpr )*;
equalityExpr        : relationalExpr ( (EQUALS | NOTEQ) relationalExpr)*;
relationalExpr      : additiveExpr ( (LT | LTEQ | GT | GTEQ) additiveExpr)*;
additiveExpr        : multiplicativeExpr ( (PLUS | MINUS) multiplicativeExpr )*;
multiplicativeExpr  : unaryExpr (( MULT | DIV | MOD ) unaryExpr)*;
unaryExpr           : NOT primaryExpr;
primaryExpr         : '(' orExpr ')' | value;
value               : IDENT | literal;
literal             : INTEGER | FLOAT | STRING | BOOLEAN;

OR      : '|' | 'or';
AND     : '&' | 'and';
EQUALS  : '==' | 'eq';
NOTEQ   : '!=' | 'ne';
LT      : '<' | 'lt';
LTEQ    : '<=' | 'le';
GT      : '>' | 'gt';
GTEQ    : '>=' | 'ge';
PLUS    : '+' | 'add';
MINUS   : '-' | 'sub';
MULT    : '*' | 'mul';
DIV     : '/' | 'div';
MOD     : '%' | 'mod';
NOT     : '!' | 'not';

INTEGER : '-'? DIGIT+;
FLOAT   : '-'? DIGIT+ '.' DIGIT+;
STRING  : '\'' (~ '\'' )* '\'';
BOOLEAN : 'true' | 'false';

IDENT   : (LETTER | '_') (LETTER | '_' | DIGIT)*;

fragment LETTER     : [a-zA-Z] ;
fragment DIGIT      : [0-9] ;
