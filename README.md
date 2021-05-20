# POO Unidad 1 Actividad 2
## Calculadora Científica mediante árbol de expresión. Versión 2
## Reporte
* ### [Ver Reporte](https://www.overleaf.com/read/xqkcgtywhjmb)
### Objetivo de la Práctica

El alumno deberá demostrar solura en los siguientes temas de la Programación Orientada a Objetos.
  * Manejo de excepciones y errores.
  * Pruebas Unitarias

### Descripción de la práctica.

Tomando como base la implementación de la calculadora científica, se necesita el agregar las siguientes mejoras:

  * Se podrán introducir ecuaciones con más de una incognita
    > ```
    > $ 3x_1 + 2x_2^3 + 2
    > ```
  * Lectura de archivo `.equ` que contendrá una o más ecuaciones.
    * Se deberá crear un objeto independiente por cada ecuación.
  * Lectura de un archivo `xlsx` que contendrá los valores de las incognitas dentro de un rango establecido.
  * Escribura de un archivo `xlsx` con el resultado de evaluar una equación.

Se muestran algunos ejemplos:

Ejemplo 1

```
$ f1 = 2x^2
$ Inserta el valor de x: [0, 1, 2]
$ resultado:
$ 0
$ 2
$ 8
```

Ejemplo 2

`archivo1.xlsx` contiene

| x_1 | x_2 |
|---| --- |
| 0 | 10 |
| 1 | 9 |
| 2 | 8 |

```
$ data = read('archivo1.xlsx')
$ f1 = 2x_1^2
$ f1(data['x_1'])
$ 0
$ 2
$ 8
```

Ejemplo 3

`archivo1.xlsx` contiene

| x_1 | x_2 |
|---| --- |
| 0 | 10 |
| 1 | 9 |
| 2 | 8 |

```
$ data = read('archivo1.xlsx')
$ f1 = 2x_1^2 - x_2
$ save(f1(data), 'output.txt')
```
El archivo `output.txt` contendrá
```
-10
-7
0
```


### Requerimientos Funcionales
Se deberá hacer uso de los siguientes temas:

  * Manejo de excepciones
  * Excepciones propias
  * Propagación de errores.
  * Pruebas Unitarias
  * Pruebas de integración
  * Notación infija

De igual manera se deberá tener lo siguiente:

  * Se deberá crear un nuevo proyecto mediante maven. **No se deberá crear una nueva carpeta contenedora** el archivo `pom` deberá estar en la raiz del repositorio.
  * Se deberá utilizar clases que implementen Pruebas Unitarias y Pruebas de Integración.
  * Se podrán introducir los siguientes elementos:
    * Operaciones: `+`, `-`, `*`, `/`, `^`, `sin`, `cos`, `tan`, `sqrt`, `()`, `[]`
    * Operandos: 0-9, x
    * No deberá existir límite en el número de operaciones y operandos.
    * El programa deberá emitir un mensaje de error si existiera una sintaxis erronea en la ecuación.

### Requerimientos no funcionales

  * El programa deberá funcionar como una linea de comandos, y sólo deberá salir si se le envía el comando `!exit`.
  * Se introducirán las ecuaciones en notación infija en la línea de comandos o mediante un archivo.

### Entregables:

  1. Código de la implementación documentado mediante JavaDoc.
  2. Se deberá generar el archivo Jar => **Se subirá a plataforma google classroom**
  3. Se deberá generar un reporte de actividades en formato PDF => **Se subirá a plataforma google classroom**  


------
## División del trabajo
------
Cada integrante realiza una parte del proyecto junto con sus pruebas unitarias, escribiendo en la bitácora y pruebas unitarias los avances.

### **Ecuaciones con multiples variables**
1. Hacer los cambios necesarios a la calculadroa para que soporte multiples variables.
2. Cada ecuación debe ser igual a una variable.

### **Interfaz y Arreglos a la calculadora**
1. Cuando la ecuación ingresada tenga una sola variable solicitar su valor, en el caso contrario un archivo con las variables.
2. Se debe poder ingresar un array de valores para x.

### **Lectura archivo .equ**

Instrucciones:
1. Crear una clase llamada EquationsReader que lea un archivo con terminacion ".equ".
2. La clase debe de tener la sig. función ArrayList<ExpressionTree> readEquations(String path).
	Recibe la ruta del archivo .equ, lo lee y pasa las ecuaciones a la calculadora para un ExpressionTree de cada ecuación, los cuales guarda en un array que retorna al final.

El programa **no** debe tronar cuando:
1. Se lee una ecuación con sintaxis incorrecta
2. Se lee texto que no es una ecuación
3. El archivo no existe

Pruebas Unitarias:
Prueba de funcionamiento
1. Guardar en un archivo ecuaciones.equ las siguientes ecuaciones:
3x + 2
sin(x+3)*sqrt(2)
2^(12-4*(2*x))
3x_1 + 2x_2^3 + 2

2. Instancir la clase EquationsReader y usar la funcion readEquations.
3. Crear las ecuaciones a ExpressionTree manualmente
4. Comprobar que cada ecuación en el ArrayList esta correctamente creada comparandola a las ecuaciones creadas manualmente.

Prueba de no tronar (Expeciones)
1. Guardar en un archivo ecuaciones.equ las siguientes ecuaciones:
3x+%
12*sin()
2*41+
80*12**

2. Instancir la clase EquationsReader y usar la funcion readEquations.
3. Comprobar que el programa no retorne una expeción (el programa maneja las expeciones)


### **Lectura .xlsx**
Instrucciones:
1. Crear una clase ReadVariables
2. Crear una funcion HashTable<Token, Float> readVarFile(String path).
	Recibe la dirección del archivo, lo lee y crea una tabla tipo HashTable con la que se pueda consultar los datos ingresando el nombre de la variable.
	Se pasa la variable a la tabla y retorna un array de floats.
	Ejemplo: table.get("x_1") --->  [7, 8, 7, 5, 15, 70]

Pruebas Unitarias:
1. Instanciar la clase y usarla.
2. Comprobar que la tabla retorna cada array de floats correspondiente a su variable.

### **Salida a .txt**
Instrucciones:
1. Crear una función: void writeToFile(String name). Implementada en Calculator.java
	Recibe el nombre del archivo sin la extension y escribe los resultados de las ecuaciones cargadas en Calculator.

Pruebas Unitarias:
1. Usar la funcion para crear el archivo.
2. Leer el archivo recien creado
3. Comprobar que lo escrito es lo mismo que lo leido.