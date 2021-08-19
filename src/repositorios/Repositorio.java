package repositorios;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;


// este repositorio es el archivo original del proyecto, el otro es el de biale

/** Implementación Genérica de un repositorio usando un EntityManager. 
    Se han agregado los siguientes métodos:
        - iniciarTransaccion()
         - confirmarTransaccion()
         - descartarTransaccion()
  
    Es posible agregar un método para manejar parámetros de búsqueda usando una clase basada en el patrón Utility-Builder          */
 
/*  Proceso para realizar una consulta a la bd, habiendo definido un entity manager y creado el Respositorio(emf):
        creamos un CriteriaBuilder con un metodo del entity manager 
            CriteriaBuilder cb = this.em.getCriteriaBuilder();
         creamos un CriteriaQuery
            CriteriaQuery consulta = cb.createQuery();
        defino el FROM, que es la tabla a la que se le hace la consulta
            Root<T> origen = consulta.from(clase);
        defino el select (opcional)
            consulta.select(origen);
        ejecuto y obtengo el resultado
            return em.createQuery(consulta).getResultList();  
*/

public class Repositorio {
    
    private final EntityManager em;

    public Repositorio(EntityManagerFactory emf){
        this.em = emf.createEntityManager();
    }

    public void iniciarTransaccion() {
        em.getTransaction().begin();
    }
    
    public void confirmarTransaccion() {
        em.getTransaction().commit();
    }

    public void descartarTransaccion() {
        em.getTransaction().rollback();
    }
    
    public void insertar(Object o) {
        this.em.persist(o);
    }
    
    public void modificar(Object o){
        this.em.merge(o);
    }


    
    // se implementa borrado logico para todas las clases  
    public void eliminar(Object o){
        this.em.remove(o);
        
        /*var claseObjeto = o.getClass().toString() ;
        
        switch (claseObjeto) {
            case "Productor":  ; // realizar borrado logico
                break; 
        } */
    }



    public void refrescar(Object o) {
        this.em.refresh(o);
    }



    /*  Metodo genérico para Recuperar Datos de BD:
        Para poder recibir todo tipo de objetos se utiliza T.       T =  cualquier tipo de clase que extienda de Object.
        Devuelve un objeto de tipo (T)  */
    public <T extends Object> T buscar(Class<T> clase, Object o) {
        return (T) this.em.find(clase, o);
    }    


    public <T extends Object> List<T> buscarTodos (Class<T> clase) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<T> consulta = cb.createQuery(clase);
        Root<T> origen = consulta.from(clase);
        consulta.select(origen);
        return em.createQuery(consulta).getResultList();      
    }



     public <T extends Object> List<Object[]> buscarTodos_v2 (Class<T> clase) {
        //CriteriaBuilder cb = this.em.getCriteriaBuilder();
      //  CriteriaQuery<T> consulta = cb.createQuery(clase);
        TypedQuery<Object[]> consulta = em.createQuery("select * FROM productores " + clase , Object[].class);
        //Root<T> origen = consulta.from(clase);
        //consulta.select(origen);
        List<Object[]> resultados = consulta.getResultList(); 
        return resultados;     
    }   



    // el parametro de orden a pasar se obtiene del metamodelo generado por EclipseLink
    public <T extends Object, P extends Object> List<T> buscarTodosOrdenadosPor (Class<T> clase, SingularAttribute<T, P> orden) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<T> consulta = cb.createQuery(clase);
        Root<T> origen = consulta.from(clase); 
        consulta.select(origen);

    // ordenado de forma ascendente (cb.asc() ) 
    // el atributo de origen definido en orden
        consulta.orderBy(cb.asc(origen.get(orden)));
        //consulta.orderBy(cb.desc(origen.get(orden)));
        return em.createQuery(consulta).getResultList();
    }


}
