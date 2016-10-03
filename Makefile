CC=javac
SCR=$(wildcard src/lostinditadura/pkg64/*.java)
BIN=bin
JARS=./lib/lwjgl.jar
DLL=-Djava.library.path=./native
MAIN=Main
all:
	$(CC) -d $(BIN) -classpath .:$(JARS) $(SCR)

run: all 
	java -cp ./$(BIN):$(JARS) $(DLL) $(MAIN)
	
exec:
	java -cp ./$(BIN):$(JARS) $(DLL) $(MAIN)
