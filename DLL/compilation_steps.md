# First step
```bash
javac -h . -cp . B1.java
```
# Second step
```bash
gcc -shared -fPIC -I/<include-folder's-path> -I/<include-folder's-path>/linux B1.c -o libB1.so
```
- Example
```bash
gcc -shared -fPIC -I/usr/lib/jvm/jdk-23-oracle-x64/include -I/usr/lib/jvm/jdk-23-oracle-x64/include/linux B1.c -o libB1.so
```
- this may not work for you, "jdk-23-oracle-x64" this will depend on your system's java version
- ELSE I HAVE PROVIDED ALL FILES HERE JUST DO THE 3RD STEP 
# Third step
```bash
java -cp . -Djava.library.path=. B1
```
## <include-folder's-path> can be found in others locations -> jvm -> working java version -> include