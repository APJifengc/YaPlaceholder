# YaPlaceholder
A plugin depends on PlaceholderAPI that can let you custom advancer placeholders.

## Usage
Use placeholder `%e_expression%` to get a advance placeholder. *('e' means 'expression' i guess..)*

## Data Type
| Type    | Pattern          | Description      | In code | Example  |
| ------- | ---------------- | :--------------: | :-----: | :------: |
| Integer | num              | An integer.      | int     |  255892  |
| Decimal | num+(d\|d\|f\|F) | A float number.  | double  |  5663d   |
| String  | "Some Text"      | A text string.   | String  |  "addme" |
| Boolean | true/false       | A boolean value. | boolean |  false   |

## Operator
### Arithmetic Operators
| Name                  | Chars         | Priority       | Example               |
| --------------------- | ------------- | -------------- | -------               |
| Brackets              | ()            | ***Supreme***  | `%e_1+(1-1)%` got `1` |
| Addition              | +             | 11             | `%e_1+2%` got `3`     |
| Subtraction           | -             | 11             | `%e_1-2%` got `-1`    |
| Multiplication        | *             | 12             | `%e_1*2%` got `2`     |
| Division              | /             | 12             | `%e_1/2%` got `0.5`   |
| Modulus               | **# (not %)** | 12             | `%e_1#2%` got `1`     |

### Bitwise Operators
**Note: Integer only, or a exception will be threw out.**
| Name                  | Chars  | Priority | Example                      |
| --------------------- | ------ | -------- | ---------------------------- |
| Left shift            | <<     | 10       | `%e_1<<2` got `4`            |
| Right shift           | \>\>   | 10       | `%e_-1>>2` got `-1`          |
| Zero fill right shift | \>\>\> | 10       | `%e_-1>>>2` got `1073741823` |
| Bitwise and           | &      | 7        | `%e_1&2` got `0`             |
| Bitwise XOR           | ^      | 6        | `%e_1^2` got `3`             |
| Bitwise or            | \|     | 5        | `%e_1|2` got `3`             |

### Logical Operators
| Name               | Chars | Priority | Example                      |
| -----------------  | ----- | -------- | ---------------------------- |
| ~~Not~~ **(todo)** | !     | ?        | ***(Future Feature)***       |
| Logical and        | &&    | 4        | `%e_true&&false%` got `false`|
| Logical or         | \|\|  | 3        | `%e_true||false%` got `true` |

### Assignment Operators
**Note: Except '=='(equals) and '!=' (not equals) can compare string, other operators can only compare numbers.**
| Name              | Chars | Priority | Example               |
| ----------------- | ----- | -------- | --------------------- |
| Greater Than      | >     | 9        | `%e_1>1` got `false`  |
| Less Than         | <     | 9        | `%e_1<2` got `true`   |
| Greater Or Equals | >=    | 9        | `%e_1>=2` got `false` |
| Less Or Equals    | <=    | 9        | `%e_1<=2` got `true`  |
| Equals            | ==    | 8        | `%e_1==1` got `true`  |
| Not Equals        | !=    | 8        | `%e_1!=1` got `false` |

## Function
**Basic syntax: function(param1, param2, ...)**
### if
**Params:** Boolean bool, Object value1, Object value2  
If the bool is `true`, return value1.  
If the bool is `false`, return value2.  
**Example:** `%e_if(value('$player_health$')>0,value('$player_health$'),'Dead')%`  
**Meaning:** If you installed the PlaceholderAPI extension 'player', it should return players' health when they are alive or return 'Dead' when they are dead.

### switch
**Params:** Integer number, Object selections...  
Return the selection of the number.  
**Example:** `%e_switch(1,'Hi, bob!','How are you?')`  
**Meaning:** It should return 'How are you?' though it is the second argument because its index is 1 (index count from 0).

### value
**Params:** String text  
Parse the text to a number or boolean.  
**Example:** `%e_value('1')==1`  
**Meaning:** It will return `true` because they are equals. But `%e_'1'==1` will return `false` because string can't be compared with integer. If you use `value()`, the integer will be turned into a string.