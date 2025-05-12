#!/bin/zsh

# Check if -c flag is present
if [[ "$1" == "-c" ]]; then
    echo "Compiling project..."
    mvn compile
    
    echo "Processing data files..."
    mvn exec:java -Dexec.mainClass="util.JsonBase64Util"
fi

# Run the game
mvn exec:java -Dexec.mainClass="game.GameStarter"