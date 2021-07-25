###INTEGRADOR PROGRAMACIÓN ORIENTADA A OBJETOS I

####Descripción escenario: 
La empresa “La Verde S.A.” presta servicio de cosecha de yerba mate a distintos productores.

####Requerimientos funcionales: 


- llevar registro de los kilos cosechados por cada empleado y a que productor corresponden dichos kilos.
- llevar control de kilos cosechados por empleado a nivel de productor, lote y cuadro,
- llevar control de la diferencia entre kilos entregados al secadero y kilos registrados en pesaje a campo.
- no realizar borrado fisico de datos de la BD. Utilizar borrado lógico: todos las clases tienen un atributo alta. En vez de eliminar los registros de una clase de la BD haciendo un delete, se cambia el estado del alta de true a false, y al realizar las queries sobre la BD para listar las “filas actuales” se utiliza el Alta como validador.
-además, considérese los ABM y las correspondientes queries que pudieran necesitarse sobre una clase a partir del escenario. 



####Diseño de Entrada / Salida

Una pantalla con el mismo diseño que el ejemplo Modelo del profesor. 
A la izquierda los botones para elegir la vista y la derecha el group cambiante.

- Un Group (cambiante) individual de ABM para empleado, productor , 
// servicio_empleado , servicio_productor

y otro compartido para ABM de Cuadro y Lote.
// servicio_CuadroLote


- Un Group (cambiante) compartido entre el ABM Cosecha y el de CosechaSecadero  y, ademas, 
 E/S para realizar el método  "Obtener la diferencia entre kilos pesados en el campo y kilos entregados al secadero"
// servicio_cosechas


- Un Group (cambiante) de E/S para realizar los casos de uso de Seguimiento de empleado
// servicio_seguimientoEmpleado
