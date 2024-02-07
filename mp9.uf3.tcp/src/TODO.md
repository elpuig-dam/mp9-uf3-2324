## TCP

### Setmana 4: 05/02 - 11/02  

>**Tasca 1**  
>Implementeu el joc d'endevinar un número que ja hem fet amb UDP però ara amb TCP  
>Amb aquest canvi:
> > - El client i el servidor es parlen només amb Strings, per això la classe SecretNum té el mètode comprova sobrecarregat per usar Strings.


>**Tasca 2**  
>L'Objecte [Llista.java](mp9/uf3/tcp/exemples/Llista.java) consta d'un __nom__(String) i una __llista de numeros__(List -Integer-)
> > - Implementar un servidor TCP perquè permeti comunicar-se amb diferents clients alhora. Aquests li enviaran un objecte de tipus
      > Llista, i el servidor els hi retornarà el mateix objecte amb els números ordenats i sense repetits.
> > - Implementar el client amb TCP que enviï al servidor una Llista omplerta amb les dades (nom i llista de números),
      > > i preparat per rebre un objecte del mateix tipus. El servidor li haurà eliminat els repetits i els números estaran
      > > ordenats. Imprimiu per la consola els resultats per veure el correcte funcionament.