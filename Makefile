CLASSES=AI.java DecentAI.java Location.java Main.java Panel.java RandomAI.java Bridge.java BestAI.java TreeNode.java MCAI.java Utils.java Board.java

.SUFFIXES: .java .class

.java.class:
	javac $*.java

all: classes

classes: $(CLASSES:.java=.class)