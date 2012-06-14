CLASSES=AI.java Location.java Bridge.java Main.java Panel.java TreeNode.java MCAI.java Utils.java Board.java Constants.java

.SUFFIXES: .java .class

.java.class:
	javac $*.java

all: classes jar docs

classes: $(CLASSES:.java=.class)

jar: classes
	jar cvfm Hex.jar MANIFEST.MF *.class

docs:
	javadoc -d javadoc $(CLASSES)
clean:
	rm *.class
	rm Hex.jar
