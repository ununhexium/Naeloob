grammar Naeloob;

parse
 : expression EOF
 ;

expression
 : LPAREN expression RPAREN                                 #parenExpression
 | NOT expression                                           #notExpression
 | left=IDENTIFIER op=EQ right=(DQ_STRING|SQ_STRING|UQ_STRING)        #comparisonExpression
 | left=expression op=comparator right=expression           #comparatorExpression
 | left=expression op=binary right=expression               #binaryExpression
 | bool                                                     #boolExpression
 | DECIMAL                                                  #decimalExpression
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
DQ_STRING  : DQ (~'"')*? DQ  ;
SQ_STRING  : SQ (~'\'')*? SQ  ;

IDENTIFIER : [a-zA-Z_] [a-zA-Z_0-9]*;
WS         : [ \r\t\u000C\n]+ -> skip;

UQ_STRING  : (~'\'')*? ;
