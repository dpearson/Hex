## Hex in Java ##

This is an implementation of Hex in Java. It was originally used as a school project.

### Building ###

You'll need a few dependencies; these include a JDK, `make`, and `zip`. I've only tested the code on Java 6, although both the OpenJDK and the Sun one work.

Then, just run:

	```shell
    make jar
    java -jar Hex.jar

### AI ###

The AI uses Monte Carlo Tree Search, which means that it plays a number of random games (equal to the chosen difficulty level) for each possible move, and chooses the one with the highest winning percentage. In general, as the number of random games played increases, the success of the AI improves.

The AI is currently rather slow, even when it plays with a search depth of one (as it currently does).

### TODO ###

 * Play the random games in parallel, which *should* result in a speed increase on multicore systems.
 * Add a rule in the random simulation to protect bridges.

### License ###

Hex is provided to you under the following license:

	Copyright (c) 2012, David Pearson
	All rights reserved.
	
	Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.