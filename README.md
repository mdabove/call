# Almundo - Callcenter

Existe un call center donde hay 3 tipos de empleados: operador,
supervisor y director. El proceso de la atención de una llamada
telefónica en primera instancia debe ser atendida por un operador, si
no hay ninguno libre debe ser atendida por un supervisor, y de no
haber tampoco supervisores libres debe ser atendida por un director.

# Solucion

- Los empleados son iguales salvo por su prioridad, asi que extienden de CallCenterEmployee y en su constructor se le asigna que tipo de empleado es.
- El primer problema que me encontre fue la prioridad de los empleados y como sacarlos y encolarlos correctamente. Tome la decision de usar una PriorityBlockingQueue que da la opción de instanciarla con un comparator (se ordena por el orden natural de sus elementos si comparator no es especificado [source1]), donde use la prioridad que se saca de un EmployeeType de los empleados que tiene la prioridad de cada uno de ellos numerada. Ademas, el método take() resuelve el problema de que no haya empleados disponibles para atender llamadas entrantes (de la documentacion): *Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.* 
- El Dispatcher tiene un constructor que crea un ExecutorService con el thread pool parametrizado, ademas recibe la queue de empleados.
- El metodo dispatchCall recibe la llamada, saca a un empleado disponible de la cola con take() e inicia la task que atiende la llamada mediante el ExecutorService. AttendCall() recibe un empleado y la call, el empleado recibe la llamada y en un intento de simulacion de una llamada real, el método makeCall() dicta cuanto tiempo estara sleepeado (la llamada contiene el tiempo de duracion aleatorio entre 5 y 10 segundos). Una vez terminada la llamada se encola al empleado.


# Preguntas extra:

  - **Dar alguna solución sobre qué pasa con una llamada cuando no
hay ningún empleado libre:** gracias al método take() cuando no hay ningun empleado libre la llamada queda on hold hasta que la PriorityBlockingQueue tenga algun elemento (ver más arriba la explicación)
  - **Dar alguna solución sobre qué pasa con una llamada cuando
entran más de 10 llamadas concurrentes:** si hay más llamadas que empleados el problema está resuelto gracias al uso de la PriorityBlockingQueue, similar a la respuesta de arriba, take() bloquea el flujo hasta que la queue sea non-empty.
Por eso, en ambos casos, tanto si no hay un empleado libre o hay 10 llamadas concurrentes, siempre que haya una empleado disponible, se atiende la llamada, sino quedará la llamada a la espera de que algun empleado vuelva a estar disponible.

   [source1]: <http://kickjava.com/src/java/util/PriorityQueue.java.htm#ixzz0yBp7>

