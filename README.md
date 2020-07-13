# YaPlaceholder
Custom more advance Placeholder.
## Usage
Use placeholder `%yaplaceholder_sometext%` to get a advance placeholder.  
### Basic Syntax
Use `{ }` to call a function, and use `|` to split the params.  
Example: `{FUNCTION|PARAM1|PARAM2|...}`.  
### Data Type
| Type    | Pattern     | Description      |  
| ------- | ----------- | ---------------- |
| Integer | Nums        | A number.        |  
| Float   | Nums+F      | A float number.  |  
| String  | "Some Text" | A text string.   |   
| Boolean | true/false  | A boolean value. |  
### Expressions
#### Arithmetic Expression
| Name        | Chars | Priority |
| ----------- | ----- | -------- |
| Plus        | +     | 3        |
| Minus       | -     | 3        |
| By          | *     | 2        |
| Divide By   | /     | 2        |
| Mod         | %     | 2        |
| And         | &     | 1        |
| Or          | \|    | 1        |
| Xor         | ^     | 1        |
| Shift Left  | <<    | 1        |
| Shift Right | \>\>  | 1        |
#### Logic Expression 
| Name              | Chars | Priority |
| ----------------- | ----- | -------- |
| Greater Than      | >     | 3        |
| Less Than         | <     | 3        |
| Greater Or Equals | >=    | 3        |
| Less Or Equals    | <=    | 3        |
| Equals            | ==    | 3        |
| Not Equals        | !=    | 3        |
| Not               | !     | 2        |
| And               | &&    | 1        |
| Or                | \|\|  | 1        |
### Functions
#### IF
Params: Boolean bool, Any value1, Any value2  
If the bool is `true`, return value1.  
If the bool is `false`, return value2.  
#### TEXTJOIN
Params: String connect, String strings...  
Return the strings connected with the connect string.  
#### REPLACE
Params: String source, String text1, String text2.  
Return the source string replace all text1 to text2.  
#### TIME
Params: String type.  
Types: `HOUR`, `MINUTE`, `SECOND`, `DAY`, `MONTH`, `YEAR`, `WEEKDAY`  
Return the time now.  
#### SWITCH
Params: Integer number, All selections...  
Return the selection of the number.  
#### PARSE
Params: String text.  
Parse the text by PlaceholderAPI.  
Note: You can use `/&` to replace `%`.  
## Examples
### Player Heart
```
%yaplaceholder_{IF|{PARSE|"/&player_health/&"}!=0.0F|{PARSE|"/&player_health/&"}/2+" Hearts"|"Died"}%
```
When the player's health isn't zero, return the player's heart count(Health/2) + " Hearts".  
When the player's health is zero, return "Died".  
