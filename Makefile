CLASSES=src/AI.java src/Location.java src/Bridge.java src/Main.java src/Panel.java src/TreeNode.java src/MCAI.java src/Utils.java src/Board.java src/Constants.java

.SUFFIXES: .java .class

.java.class:
	javac -d bin -cp src $*.java

all: classes jar docs

classes: setup $(CLASSES:.java=.class)

setup:
	mkdir -p bin

jar: classes
	cd bin && jar cvfm ../Hex.jar ../MANIFEST.MF *.class

docs:
	javadoc -d javadoc $(CLASSES)

clean:
	rm -rf bin
	rm Hex.jar
