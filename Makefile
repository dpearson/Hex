CLASSES=AI.java DecentAI.java Location.java Main.java Panel.java RandomAI.java

.SUFFIXES: .java .class

.java.class:
	javac $*.java

all: classes

classes: $(CLASSES:.java=.class)
