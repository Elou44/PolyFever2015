# PolyFever 2015
Project realised by engineering students of Polytech'Nantes (French engineering school). Game inspired by Curve Fever.

### Achtung ! Bien importer le projet dans Eclipse
- Cloner le dépôt git : git clone https://www.github.com/Elou44/PolyFever2015.git
- Sur Eclipse : File -> Import -> General -> Existing Projects into Workspace -> Select root directory (sélectionner le dépôt Git)
- Pour commit : clic droit sur un fichier -> Team -> Commit 

### Nom des packages : 
- polyFever.module.moteurDeJeu
- polyFever.module.affichage
- polyFever.module.menu
- polyFever.module.evenements
- polyFever.module.reseau (coming soon !)

### Param�trer le projet pour LWJGL
- 1 - T�l�charger la version Stable de LWJGL -> http://www.lwjgl.org/download 
- 2 - Dans les propi�t�s du projet : Java build Path -> onglet Libraries -> Cliquer sur "Add External JARs" -> S�lectionner lwjgl.jar 
- 3 - Dans les propi�t�s du projet : Java build Path -> onglet Libraries -> Etendre l'entr�e "lwjgl.jar - ... " -> Cliquer sur "Native Library Location" -> Cliquer sur Edit -> S�lectionner le dossier "native/windows/x86"
- 4 - Dans les propi�t�s du projet : Java Compiler -> Cocher "Enable Project Specific settings" -> Mettre "Compiler compliance Level" � 1.7 -> "Apply"
- 5 - Enjoy !