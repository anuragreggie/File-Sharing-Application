# Simple File Sharing (SFS) Using Java [<img align="right" alt="java" width="60px" src="https://simpleicons.org/icons/java.svg" />][java]

| Contents                                   |
|--------------------------------------------|
| - Brief description of SFS                 |
| - Details needed to share files            |
| - Details to know before sending/receiving |
| - How to compile and run                   |
| - How to exit                         |

### Brief description of SFS
SFS allows files to be shared between two machines. One machine acts as the server and sends files while another acts as
the client and receives the files.
<br/>
SFS supports various formats of files. To list a few: txt, doc, binary and jpg files.

### Details to know before receiving a file
If you are receiving a file then you are required to know the IP Address of the sender as well as the port number.
<br/>
Prior to requesting the file you need to ask the sender for these details.

#### How the sender can view their IP Address
##### Mac + Linux
Type `hostname –I` or `ip addr`  in the terminal.
##### Windows
Type `ipconfig` in the command prompt.
<br />
Check the number next to "IPv4 Address. . . . . . . . . . . :"


### How to compile
- If you are running on the command line, first change the path where the src folder is stored using the `cd` command.
<br/> Eg: `cd Documents/CS2101/W07/src`
- Once in the src folder type `javac *.java` to compile all the files
- Then type `java Main` to run Main

####  Notes for sending files
- To send a file once the program is running type `SEND` to start the server.
- When the client requests to view files, specify the full path of where you want to share from.
<br/> Eg: If I wanted to share files from the CS folder I type `C:\Users\Me\OneDrive\Documents\CS`


#### Notes for receiving files
- When requesting a file to receive, type the full path including the file extension.
<br/> Eg: `C:\Users\Me\IdeaProjects\MP1-Final\src\doc.txt` (On Windows)
- When specifying where you want to save the downloaded file, type the name of the new file as well as the file extension.
<br/> Eg: If I was downloading a text file and wanted to call it download I type `C:\Users\Me\OneDrive\Documents\CS\download.txt` (On Windows)
- If there is a mistake with any of this the file transfer will likely fail.

#### How to exit
- Type either `exit` or press `CTRL + C` to kill the program.




<br />



---
[java]: http://jdk.java.net/
