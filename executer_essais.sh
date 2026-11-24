#!/bin/sh
# Compile le projet et lance toutes les suites d'essais sur un poste propre.
# Usage : ./executer_essais.sh [chemin/vers/junit-4.13.2.jar] [chemin/vers/hamcrest-core-1.3.jar]
JUNIT=${1:-lib/junit-4.13.2.jar}
HAMCREST=${2:-lib/hamcrest-core-1.3.jar}
CP=".:$JUNIT:$HAMCREST"
set -e

if [ -f "$JUNIT" ] && [ -f "$HAMCREST" ]; then
    javac -Xlint:all -cp "$CP" *.java
    echo "=== 1. Essais hérités (Jalons 1 à 3 : 58 vérifications) ==="
    java Tests
    echo
    echo "=== 2. Essais JUnit 4 (Jalon 4 : 24 tests) ==="
    java -cp "$CP" org.junit.runner.JUnitCore ServicePretTest
    echo
    echo "=== 3. Recette (29 scénarios) ==="
    java Recette
else
    echo "[INFO] Archives JUnit non détectées dans lib/ — compilation sans ServicePretTest..."
    javac -Xlint:all $(ls *.java | grep -v ServicePretTest)
    echo "=== 1. Essais hérités (Jalons 1 à 3 : 58 vérifications) ==="
    java Tests
    echo
    echo "=== 2. Recette (29 scénarios) ==="
    java Recette
fi
