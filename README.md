# BTDC
blons tower defense clone

how2 run..
* linux => run linuxstart.sh
* windows => run winstart.bat

# PSA
### Ich habe ein Vektorensystem erstellt, das evtl. etwas Erklärung bedarf, da ich es aus Gründen von Typesafety in Kotlin geschrieben habe; hier also die Erklärung:

Die Vektoren-Bibliothek wird importiert mit:
```python
import de.wvsberlin.vektor  # ggf. unter anderem Namen
```
Falls der Import fehlschlägt, einfach zusätzlich das Modul `syspaths` importieren.

Der einzige Zweck von `syspaths` ist es, die sonst unzugänglichen Module zugänglich zu machen.

So, nun hast du sie importiert, die Vektoren.

Das Paket `de.wvsberlin.vektor` enthält drei Klassen: `Vektor`, `MutableVektor` und `Gerade`.

Der Constructor für die beiden Vektorklassen nimmt zwei Argumente x und y, die der x- und der y-Achse entsprechen.

Man kann die meisten Rechenoperationen an Vektoren durchführen:
* Addition zweier Vektoren
* Subtraktion zweier Vektoren
* Multiplikation mit einem Skalar
* Division durch einen Skalar

Man kann literally einfach den jwlg. Rechenoperator benutzen, also zB.
```python
vektor1 + vektor2


Die Multiplikation zweier Vektoren ergibt das Skalarprodukt.

Für Assignment-Operationen der Art `vektor1 += vektor2` o.Ä. sind `MutableVektor`s nötig,
da normale Vektoren unveränderlich sind.

Vektoren können auch erstellt werden mit `Vektor.fromAngle(angle, magnitude)`. Es wird ein Vektor erstellt, der
in die in Grad geg. Richtung zeigt und die geg. Länge hat. (Dasselbe auch mit `MutableVektor`)

Man kann mit `myVektor.getAngle()` den Winkel des Vektors bekommen.

Mit `myVektor.getUnitized()` und `myVektor.getUnitNormal()` bekommt man jeweils den Einheitsvektor (Länge = 1) und die
Einheitsnormale.

Mit `myVektor.abs()` bekommt man die Länge des Vektors.

Geraden nehmen im Constructor einen Stützpunkt (als Ortsvektor) und einen Richtungsvektor.

Ruft man ein Geradenobjekt als Funktion mit einem Parameter, bekommt man einen Ortsvektor, der dem Ortsvektor p> + r * v> entspricht.

Mit `Vektor.dist(vektor1, vektor2)` bekommt man den Abstand zweier (Orts-)Vektoren.
Ruft man die Funktion mit einem dritten Parameter, der auf False gesetzt wird, wird das Quadrat des Abstands zurückgegeben,
da `sqrt` eine relativ langsame Funktion ist und dadurch Zeit gespart wird.

Man kann mit `Gerade.dist(gerade, vektor)` den Abstand des (Orts-)Vektors zu einer Gerade ermitteln.
Hier existiert keine sqrt-Option, da das keinen Leistungsvorteil bringt.

Die Funktion `Vektor.clampedDist(vektorA, vektorB, vektorP)` ermittelt den Abstand der Strecke AB zum Punkt P, und zwar folgendermaßen:

* Ist der Fußpunkt von P auf AB "hinter" A, wird der Abstand zu A gegeben.
* Ist er "hinter" B, wird der Abstand zu B gegeben.
* Ist er auf der Strecke AB, wird der Abstand zur Gerade durch AB gegeben.
