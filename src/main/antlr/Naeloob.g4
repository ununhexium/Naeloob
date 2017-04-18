grammar Naeloob;

parse
 : expression EOF
 ;

expression
 : LPAREN expression RPAREN                                 #parenExpression
 | NOT expression                                           #notExpression
 | left=expression op=comparator right=expression           #comparatorExpression
 | left=expression op=binary right=expression               #binaryExpression
 | bool                                                     #boolExpression
 | string                                                   #stringExpression
 | DECIMAL                                                  #decimalExpression
 | IDENTIFIER                                               #identifierExpression
 ;

comparator
 : EQ
 ;

binary
 : AND | OR
 ;

bool
 : TRUE | FALSE
 ;

string
 : DQ_STRING
 | SQ_STRING
 ;


AND        : 'AND' ;
OR         : '|' ;
NOT        : '!';
TRUE       : 'TRUE' ;
FALSE      : 'FALSE' ;
EQ         : '/' ;
LPAREN     : '(' ;
RPAREN     : ')' ;
DECIMAL    : '-'? [0-9]+ ( '.' [0-9]+ )? ;

DQ         : '"' ;
SQ         : '\'' ;
DQ_STRING  : DQ (~'"')*? DQ ;
SQ_STRING  : SQ (~'\'')*? SQ ;

fragment L : [A-Z] ;
IDENTIFIER : L L L L;

WS         : [ \r\t\u000C\n]+ -> skip;
