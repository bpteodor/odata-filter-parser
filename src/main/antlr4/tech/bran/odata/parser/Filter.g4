/*
OData Filter grammar
Specification: https://docs.oasis-open.org/odata/odata/v4.01/odata-v4.01-part1-protocol.html#_Toc31358948
Reference: https://learn.microsoft.com/en-us/azure/search/search-query-odata-syntax-reference
*/
grammar Filter;

orExpr              : andExpr ( OR andExpr )*;
andExpr             : compExpr ( AND compExpr )*;
compExpr            : unaryExpr ( (EQUALS | NOTEQ | LT | LTEQ | GT | GTEQ) unaryExpr)*;
unaryExpr           : NOT? primaryExpr;
primaryExpr         : P1 orExpr P2 | inclusion | identifier | literal | ;
inclusion          : identifier IN P1 literal ( SEPARATOR literal )* P2; // TODO same type
identifier          : IDENT;
literal             : INTEGER | FLOAT | STRING | BOOLEAN;

OR      : '|' | 'or';
AND     : '&' | 'and';
EQUALS  : '==' | 'eq';
NOTEQ   : '!=' | 'ne';
LT      : '<' | 'lt';
LTEQ    : '<=' | 'le';
GT      : '>' | 'gt';
GTEQ    : '>=' | 'ge';
NOT     : '!' | 'not';
IN      : 'in';

INTEGER : '-'? DIGIT+;
FLOAT   : '-'? DIGIT+ '.' DIGIT+;
STRING  : '\'' (~ '\'' )* '\''
        | '"' (~ '"' )* '"';
BOOLEAN : 'true' | 'false';

IDENT   : (LETTER | '_') (LETTER | '_' | DIGIT)*;

fragment LETTER     : [a-zA-Z] ;
fragment DIGIT      : [0-9] ;

EOL         : ('\r'? '\n' | '\r') -> skip ;
WHITESPACE  : [ \t]+ -> skip ;
P1          : '(' -> skip;
P2          : ')' -> skip;
SEPARATOR   : ',' -> skip;