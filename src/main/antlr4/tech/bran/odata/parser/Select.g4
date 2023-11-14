/*
OData $select grammar
Specification: https://docs.oasis-open.org/odata/odata/v4.01/odata-v4.01-part1-protocol.html#_Toc31358942
Reference: https://learn.microsoft.com/en-us/azure/search/search-query-odata-select
*/
grammar Select;

expr        : '*' | fieldPath(',' fieldPath)*;
fieldPath   : IDENT('/'IDENT)*;

IDENT       : (LETTER | '_') (LETTER | '_' | DIGIT)*;

fragment LETTER     : [a-zA-Z] ;
fragment DIGIT      : [0-9] ;
