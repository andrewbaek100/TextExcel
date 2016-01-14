#Text Excel 
By Ryan Chan
For AP Computer Science 2016

Feel free to take inspiration from my code, just don't copy/paste it! If you have any questions, feel free to approach me in class.

##Usage

All commands must be in lowercase and typed perfectly. Error handling has not yet been fully implemented
Supported Commands:

* print

    * Prints out the entire table
* quit

    * Quits the program
* [Cell]

    * Prints out the specified cell
* [Cell] = [Value]

    * Sets [Cell] to [Value] 

    * Possible [Value] types and syntax:

        * String: "input string"

        * Double: 5

        * Date: 1/12/1976

        * Formula: ( 5 + 3 / 2 * A3 )

            * Numbers in a formula can be substituded with a Cell address that contains a double
            
            * The avg and sum commands can be used inside of a formula, ex. ( avg A1 - C2) will take the average of all cells inside the block of cells from A1 to C2

* clear [Cell]

    * Clears the specified cell
* clear

    * If no arguments are passed, clears the entire table
* save [File]

    * Saves current spreadsheet to file named [File] in the current directory
* load [File]

    * Loads spreadsheet from file named [File] in the current directory
* sorta [Cell1] - [Cell2]

    * Sorts double and formula cells within the specified cell block from [Cell1] to [Cell2] in ascending order
* sortd [Cell1] - [Cell2]

    * Sorts double and formula cells within the specified cell block from [Cell1] to [Cell2] in descending order


