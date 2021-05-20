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

